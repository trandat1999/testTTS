package com.tranhuudat.manager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Supplier extends BaseEntity{
	
	private String name;
	
	private String phoneNumber;
	
	private String email;
	
	private String address;
	
	private String des;
}
