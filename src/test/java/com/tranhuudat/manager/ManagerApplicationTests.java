package com.tranhuudat.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tranhuudat.manager.service.SupplierService;
import com.tranhuudat.manager.type.YESNO;


@SpringBootTest
class ManagerApplicationTests {
	
	@Autowired
    private SupplierService supplierService;

	@Test
	void contextLoads() {
		 Assertions.assertEquals(YESNO.YES, supplierService.findById(1).getStatus());
	}

}
