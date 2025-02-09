package com.example.secaidserver.repository.test;

import com.example.secaidserver.model.program.ActionAnalysisDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionAnalysisRepository extends JpaRepository<ActionAnalysisDB, String> {
}
