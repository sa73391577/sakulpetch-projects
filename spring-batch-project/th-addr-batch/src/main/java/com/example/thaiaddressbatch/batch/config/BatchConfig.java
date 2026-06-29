package com.example.thaiaddressbatch.batch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.thaiaddressbatch.batch.processor.BatchProcessor;
import com.example.thaiaddressbatch.batch.reader.BatchReader;
import com.example.thaiaddressbatch.batch.writer.BatchWriter;
import com.example.thaiaddressbatch.model.dto.external.ProvinceResponseDto;
import com.example.thaiaddressbatch.model.dto.internal.ProvinceCompositeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BatchConfig {
	
	@Bean
	public Job importProvinceJob(JobRepository jobRepository, Step apiStep) {
	    return new org.springframework.batch.core.job.builder.JobBuilder("importProvinceJob", jobRepository)
	            .start(apiStep)
	            .build();
	}
	
	@Bean
	public Step apiStep(JobRepository jobRepository, 
            PlatformTransactionManager transactionManager,
            BatchReader reader, 
            BatchProcessor processor,
            BatchWriter writer) {
		return new StepBuilder("apiStep", jobRepository)
                .<ProvinceResponseDto , ProvinceCompositeDto>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
	}
	
}
