package com.tranhuudat.manager.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="tbl_category")
@Data
public class Category extends BaseEntity{
	
	
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "categories")
    private List<Product> products ;
}
