package Ecommerce.Loja.Controller;


import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job jobProduto; 

    public BatchController(JobLauncher jobLauncher, Job jobProduto) {
        this.jobLauncher = jobLauncher;
        this.jobProduto = jobProduto;
    }

    @PostMapping("/executar")
    public ResponseEntity<String> dispararBatch() {
        try {
            
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(jobProduto, params);
            
            return ResponseEntity.ok("Rotina diária executada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao executar o batch: " + e.getMessage());
        }
    }
}