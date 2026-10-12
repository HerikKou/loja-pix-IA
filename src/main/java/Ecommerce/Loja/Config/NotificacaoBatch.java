package Ecommerce.Loja.Config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import Ecommerce.Loja.Model.Notificacao;
import Ecommerce.Loja.Model.Produto;
import Ecommerce.Loja.Model.Transacao;
import Ecommerce.Loja.Repository.NotificacaoRepository;
import Ecommerce.Loja.Service.IAService;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class NotificacaoBatch {

    @Bean
    public Job jobProduto(JobRepository jobRepository, Step stepEstoque, Step stepVendas, Step stepGerarRelatorioVendas) {
        return new JobBuilder("ProdutosJob", jobRepository)
                .start(stepEstoque)
                .next(stepVendas)
                .next(stepGerarRelatorioVendas)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Produto> leitorEstoque(EntityManagerFactory factory) {
        return new JpaPagingItemReaderBuilder<Produto>()
                .name("estoqueProdutosReader")
                .entityManagerFactory(factory)
                .queryString("SELECT p FROM Produto p WHERE p.quantidadeEstoque <= 5")
                .pageSize(10)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Transacao> leitorVendas(EntityManagerFactory factory) {
        return new JpaPagingItemReaderBuilder<Transacao>()
                .name("totalVendidoReader")
                .entityManagerFactory(factory)
                .queryString("SELECT t FROM Transacao t WHERE t.status = 'APROVADO'")
                .pageSize(10)
                .build();
    }

    @Bean
    public Step stepEstoque(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             JpaPagingItemReader<Produto> leitorEstoque,
                             NotificacaoRepository notificacaoRepository) {
        return new StepBuilder("stepEstoque", jobRepository)
                .<Produto, Notificacao>chunk(10, transactionManager)
                .reader(leitorEstoque)
                .processor(item -> new Notificacao("Aviso: O produto " + item.getNome() + " está com estoque baixo!"))
                .writer(chunk -> notificacaoRepository.saveAll(chunk.getItems()))
                .build();
    }

    @Bean
    public Step stepVendas(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            JpaPagingItemReader<Transacao> leitorVendas,
                            NotificacaoRepository notificacaoRepository) {
        return new StepBuilder("stepVendas", jobRepository)
                .<Transacao, Notificacao>chunk(10, transactionManager)
                .reader(leitorVendas)
                .processor(transacao -> new Notificacao("Venda realizada por " + transacao.getFuncionario().getNome()))
                .writer(chunk -> notificacaoRepository.saveAll(chunk.getItems()))
                .build();
    }

    @Bean
    public Step stepGerarRelatorioVendas(JobRepository jobRepository,
                                          PlatformTransactionManager transactionManager,
                                          IAService iaService,
                                          NotificacaoRepository notificacaoRepository) {
        return new StepBuilder("stepGerarRelatorioVendas", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    String mensagemIa = iaService.gerarAnaliseDoDia();
                    Notificacao novaNotificacao = new Notificacao(mensagemIa);
                    notificacaoRepository.save(novaNotificacao);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}