package com.hamidspecial.SpringBatchProcessing.config;

import com.hamidspecial.SpringBatchProcessing.entity.Survey;
import com.hamidspecial.SpringBatchProcessing.repository.SurveyRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private SurveyRepository surveyRepository;

    @Bean
    public FlatFileItemReader<Survey> reader(){
        FlatFileItemReader<Survey> itemReader = new FlatFileItemReader<>();
        itemReader.setName("csvReader");
        itemReader.setResource(new FileSystemResource("src/main/resources/survey.csv"));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Survey> lineMapper(){
        DefaultLineMapper<Survey> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","Series_reference", "Period", "Data_value", "Suppressed", "STATUS", "UNITS", "Magnitude",
                "Subject", "Group", "Series_title_1", "Series_title_2", "Series_title_3", "Series_title_4", "Series_title_5");

        BeanWrapperFieldSetMapper<Survey> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Survey.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public SurveyProcessor processor(){
        return new SurveyProcessor();
    }

    @Bean
    public RepositoryItemWriter<Survey> writer(){
        RepositoryItemWriter<Survey>  writer = new RepositoryItemWriter<>();
        writer.setRepository(surveyRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("csv-step").<Survey,Survey>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob(){
        return jobBuilderFactory.get("importSurveyInfo")
                .flow(step1())
//                .next(step1())
                .end().build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(20);
        return asyncTaskExecutor;
    }
}
