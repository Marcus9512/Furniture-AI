package com.ai.furnitureai.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@EnableScheduling
public class DocumentETL implements SchedulingConfigurer {

    private final Logger LOGGER = LoggerFactory.getLogger(DocumentETL.class);

    private final ETLConfiguration etlConfiguration;
    private final ExecutorService service = new ForkJoinPool(1);

    @Value("${etl.ingest.cron.time:-}")
    public String cronScheduling;

    @Value("${etl.ingest.data:}")
    public String dataDir;

    public DocumentETL(ETLConfiguration etlConfiguration) {
        this.etlConfiguration = etlConfiguration;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(this::performETL, cronScheduling);
    }

    public void performETL() {
        service.submit(() -> {
            LOGGER.info("Starting ETL, ingesting={}", dataDir);

            write(
                    transform(
                            read(dataDir)
                    )
            );

            LOGGER.info("ETL Finished");
        });
    }

    private List<Document> read(String url) {
        LOGGER.info("Reading url={}", url);
        return etlConfiguration.getEtlManager().parseUrl(url);
    }

    private List<Document> transform(List<Document> documents) {
        LOGGER.info("Transforming documents, number of documents={}", documents.size());
        return etlConfiguration.getTransformer().transform(documents);
    }

    private void write(List<Document> documents) {
        LOGGER.info("Adding documents, number of documents={}", documents.size());
        etlConfiguration.getVectorStore().add(documents);

    }
}
