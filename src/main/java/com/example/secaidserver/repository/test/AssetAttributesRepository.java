package com.example.secaidserver.repository.test;

import com.example.secaidserver.model.program.AssetAttributeDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetAttributesRepository extends JpaRepository<AssetAttributeDB, Long> {
}
