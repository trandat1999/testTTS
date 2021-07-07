package com.tranhuudat.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranhuudat.manager.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
