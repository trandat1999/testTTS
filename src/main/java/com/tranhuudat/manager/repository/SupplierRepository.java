package com.tranhuudat.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranhuudat.manager.domain.Supplier;

@Repository
public interface SupplierRepository  extends JpaRepository<Supplier, Long>{

}
