package com.tranhuudat.manager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tranhuudat.manager.type.Status;

import lombok.Data;

@Entity
@Table(name="tbl_bill_import")
@Data
public class BillImport extends BaseEntity{
	
	
	private Date dateImport;
    private String note;
    private Status status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "billImport", orphanRemoval = true)
    private List<ProductImport> productImports;

    @ManyToOne(targetEntity = Supplier.class, cascade =CascadeType.ALL)
    @JoinColumn(name="supplier_id")
    private Supplier supplier;

}
