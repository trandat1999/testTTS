package com.tranhuudat.manager.service.impl;

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
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ProductImportDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.repository.BillImportRepository;
import com.tranhuudat.manager.repository.ProductImportRepository;
import com.tranhuudat.manager.repository.ProductRepository;
import com.tranhuudat.manager.service.ProductImportService;
import com.tranhuudat.manager.type.YESNO;

@Service
public class ProductImportServiceImpl implements ProductImportService {

	@Autowired
	private ProductImportRepository productImportRepository;

	@Autowired
	public EntityManager manager;

	@Autowired
	private BillImportRepository billImportRepository;

	@Autowired
	private ProductRepository productRepository;

	@Cacheable("productimport")
	@Override
	public ResponseDto<ProductImportDto> findById(long id) {
		ResponseDto<ProductImportDto> res = new ResponseDto<>();
		ProductImport entity = productImportRepository.findById(id).get();
		if (entity != null) {
			res.setStatus(YESNO.YES);
			res.setObject(new ProductImportDto(entity));
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@CacheEvict("productimport")
	@Override
	public ResponseDto<ProductImportDto> delete(long id) {
		ResponseDto<ProductImportDto> res = new ResponseDto<>();
		ProductImport entity = productImportRepository.findById(id).get();
		if (entity != null) {
			productImportRepository.delete(entity);
			res.setStatus(YESNO.YES);
			res.setObject(new ProductImportDto(entity));
			res.setMessage("delete success");
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@Override
	public Page<ProductImportDto> findPage(FilterDto filter) {
		if (filter != null) {
			String SQL = " SELECT new com.tranhuudat.manager.dto.ProductImportDto(s) from ProductImport s WHERE 1=1 ";
			String countSQL = " SELECT COUNT(s.id) from ProductImport s WHERE 1=1 ";
			String whereClause = "";
			Query q = manager.createQuery(SQL + whereClause, ProductImportDto.class);
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
			List<ProductImportDto> entities = q.getResultList();
			Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getPageSize());
			Page<ProductImportDto> result = new PageImpl<ProductImportDto>(entities, pageable, count);

			return result;
		} else {
			return null;
		}
	}

	@CachePut(value = "productimport")
	@Override
	public ProductImportDto saveOrUpdate(ProductImportDto dto) {
		if (dto != null) {

			ProductImport entity = null;
			if (dto.getId() > 0) {
				entity = productImportRepository.findById(dto.getId()).get();
			}
			if (entity == null) {
				entity = new ProductImport();
			}
			entity.setQuantity(dto.getQuantity());
			entity.setPriceImport(dto.getPriceImport());
			if (dto.getBillImport() != null && dto.getBillImport().getId() > 0) {
				BillImport bill= billImportRepository.findById(dto.getBillImport().getId()).get();
				entity.setBillImport(bill);
			}
			if (dto.getProduct() != null && dto.getProduct().getId() > 0) {
				Product pro= productRepository.findById(dto.getProduct().getId()).get();
				entity.setProduct(pro);
			}
			entity = productImportRepository.save(entity);
			if (entity != null) {
				return new ProductImportDto(entity);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
