package com.tranhuudat.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranhuudat.manager.domain.BillImport;

@Repository
public interface BillImportRepository extends JpaRepository<BillImport, Long>{

}
