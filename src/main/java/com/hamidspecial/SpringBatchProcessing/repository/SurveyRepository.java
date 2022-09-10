package com.hamidspecial.SpringBatchProcessing.repository;

import com.hamidspecial.SpringBatchProcessing.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
