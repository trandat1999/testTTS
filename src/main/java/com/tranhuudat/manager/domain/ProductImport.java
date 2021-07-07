package com.tranhuudat.manager.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name="tbl_product_import")
@Data
public class ProductImport extends BaseEntity{
	
	
	private double priceImport;
    private int quantity;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(targetEntity = BillImport.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="bill_import_id")
    private BillImport billImport;

}
