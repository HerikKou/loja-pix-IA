package Ecommerce.Loja.Config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job jobProduto;

    public BatchScheduler(JobLauncher jobLauncher, Job jobProduto) {
        this.jobLauncher = jobLauncher;
        this.jobProduto = jobProduto;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void executarDiariamente() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(jobProduto, params);
    }
}