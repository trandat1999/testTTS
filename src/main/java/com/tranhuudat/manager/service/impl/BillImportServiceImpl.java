package com.tranhuudat.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tranhuudat.manager.domain.BillImport;
import com.tranhuudat.manager.domain.Product;
import com.tranhuudat.manager.domain.ProductImport;
import com.tranhuudat.manager.domain.Supplier;
import com.tranhuudat.manager.dto.BillImportDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ProductImportDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.repository.BillImportRepository;
import com.tranhuudat.manager.repository.ProductImportRepository;
import com.tranhuudat.manager.repository.ProductRepository;
import com.tranhuudat.manager.repository.SupplierRepository;
import com.tranhuudat.manager.service.BillImportService;
import com.tranhuudat.manager.type.YESNO;

@Service
public class BillImportServiceImpl implements BillImportService {

	@Autowired
	private BillImportRepository billImportRepository;

	@Autowired
	private ProductImportRepository productImportRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	public EntityManager manager;

	@Cacheable("bill")
	@Override
	public ResponseDto<BillImportDto> findById(long id) {
		ResponseDto<BillImportDto> res = new ResponseDto<BillImportDto>();
		BillImport entity = billImportRepository.findById(id).get();
		if (entity != null) {
			res.setStatus(YESNO.YES);
			res.setObject(new BillImportDto(entity));
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}
	
	@CacheEvict("bill")
	@Override
	public ResponseDto<BillImportDto> delete(long id) {
		ResponseDto<BillImportDto> res = new ResponseDto<BillImportDto>();
		BillImport entity = billImportRepository.findById(id).get();
		if (entity != null) {
			billImportRepository.delete(entity);
			res.setStatus(YESNO.YES);
			res.setObject(new BillImportDto(entity));
			res.setMessage("delete success");
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@Override
	public Page<BillImportDto> findPage(FilterDto filter) {
		if (filter != null) {
			String SQL = " SELECT new com.tranhuudat.manager.dto.BillImportDto(s) from BillImport s WHERE 1=1 ";
			String countSQL = " SELECT COUNT(s.id) from BillImport s WHERE 1=1 ";
			String whereClause = "";
			Query q = manager.createQuery(SQL + whereClause, BillImportDto.class);
			Query qCount = manager.createQuery(countSQL + whereClause);
			Long count = (long) qCount.getSingleResult();
			if (filter.getPageIndex() > -1 && filter.getPageSize() > 0) {
				int startPosition = filter.getPageIndex() * filter.getPageSize();
				q.setFirstResult(startPosition);
				q.setMaxResults(filter.getPageSize());
			} else {
				q.setFirstResult(0);
				if (count != null && count > 0) {
					q.setMaxResults(count.intValue());
					filter.setPageSize(count.intValue());
				} else {
					q.setMaxResults(10);
					filter.setPageSize(10);
				}
			}
			@SuppressWarnings("unchecked")
			List<BillImportDto> entities = q.getResultList();
			Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getPageSize());
			Page<BillImportDto> result = new PageImpl<BillImportDto>(entities, pageable, count);

			return result;
		} else {
			return null;
		}
	}

	@CachePut(value = "bill")
	@Override
	public BillImportDto saveOrUpdate(BillImportDto dto) {
		if (dto != null) {

			BillImport entity = null;
			if (dto.getId() > 0) {
				entity = billImportRepository.findById(dto.getId()).get();
			}
			if (entity == null) {
				entity = new BillImport();
			}
			entity.setDateImport(dto.getDateImport());
			entity.setNote(dto.getNote());
			entity.setStatus(dto.getStatus());
			if (dto.getProductImports() != null && dto.getProductImports().size() > 0) {
				List<ProductImport> productimports = new ArrayList<>();
				for (ProductImportDto piDto : dto.getProductImports()) {
					ProductImport pi = null;
					if (piDto.getId() > 0) {
						pi = productImportRepository.findById(piDto.getId()).get();
						if (pi == null) {
							pi = new ProductImport();
						}
						pi.setBillImport(entity);
						pi.setQuantity(piDto.getQuantity());
						pi.setPriceImport(piDto.getPriceImport());
						if (piDto.getProduct() != null && piDto.getProduct().getId() > 0) {
							Product prod= productRepository.findById(piDto.getProduct().getId()).get();
							pi.setProduct(prod);
						}
					} else {
						pi = new ProductImport();
						pi.setBillImport(entity);
						pi.setQuantity(piDto.getQuantity());
						pi.setPriceImport(piDto.getPriceImport());
						if (piDto.getProduct() != null && piDto.getProduct().getId() > 0) {
							Product prod= productRepository.findById(piDto.getProduct().getId()).get();
							pi.setProduct(prod);
							
						}
					}
					productimports.add(pi);
				}
				entity.setProductImports(productimports);

			}
			if (dto.getSupplier() != null) {
				Supplier supplier = null;
				if (dto.getSupplier().getId() > 0) {
					supplier = supplierRepository.findById(dto.getSupplier().getId()).get();
				}
				if (supplier == null) {
					supplier = new Supplier();
				}
				supplier.setAddress(dto.getSupplier().getAddress());
				supplier.setDes(dto.getSupplier().getDes());
				supplier.setEmail(dto.getSupplier().getEmail());
				supplier.setName(dto.getSupplier().getName());
				supplier.setPhoneNumber(dto.getSupplier().getPhoneNumber());
				entity.setSupplier(supplier);
			}
			entity = billImportRepository.save(entity);
			if (entity != null) {
				return new BillImportDto(entity);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

}
