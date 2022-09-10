package com.hamidspecial.SpringBatchProcessing.config;

import com.hamidspecial.SpringBatchProcessing.entity.Survey;
import org.springframework.batch.item.ItemProcessor;

public class SurveyProcessor implements ItemProcessor<Survey, Survey> {
    @Override
    public Survey process(Survey survey) throws Exception {
        return survey;
    }
}
