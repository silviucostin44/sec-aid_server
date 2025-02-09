package com.example.secaidserver.repository;

import com.example.secaidserver.model.questionnaire.QuestionnaireDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionnaireRepository extends JpaRepository<QuestionnaireDB, UUID> {
}
