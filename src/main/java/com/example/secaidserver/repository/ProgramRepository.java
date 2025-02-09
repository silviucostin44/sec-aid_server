package com.example.secaidserver.repository;

import com.example.secaidserver.model.program.ProgramDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<ProgramDB, Long> {
}
