package com.tranhuudat.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranhuudat.manager.domain.ProductImport;

@Repository
public interface ProductImportRepository extends JpaRepository<ProductImport, Long>{

}
