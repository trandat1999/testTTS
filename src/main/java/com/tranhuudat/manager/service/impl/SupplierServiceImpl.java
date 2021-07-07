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

import com.tranhuudat.manager.domain.Supplier;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.dto.SupplierDto;
import com.tranhuudat.manager.repository.SupplierRepository;
import com.tranhuudat.manager.service.SupplierService;
import com.tranhuudat.manager.type.YESNO;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	public EntityManager manager;

	@Cacheable("supplier")
	@Override
	public ResponseDto<SupplierDto> findById(long id) {
		ResponseDto<SupplierDto> res = new ResponseDto<>();
		Supplier entity = supplierRepository.findById(id).get();
		if (entity != null) {
			res.setStatus(YESNO.YES);
			res.setObject(new SupplierDto(entity));
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@CacheEvict("supplier")
	@Override
	public ResponseDto<SupplierDto> delete(long id) {
		ResponseDto<SupplierDto> res = new ResponseDto<>();
		Supplier entity = supplierRepository.findById(id).get();
		if (entity != null) {
			supplierRepository.delete(entity);
			res.setStatus(YESNO.YES);
			res.setObject(new SupplierDto(entity));
			res.setMessage("delete success");
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@CachePut(value = "supplier")
	@Override
	public Page<SupplierDto> findPage(FilterDto filter) {
		if (filter != null) {
			String SQL = " SELECT new com.tranhuudat.manager.dto.SupplierDto(s) from Supplier s WHERE 1=1 ";
			String countSQL = " SELECT COUNT(s.id) from Supplier s WHERE 1=1 ";
			String whereClause = "";
			Query q = manager.createQuery(SQL + whereClause, SupplierDto.class);
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
			List<SupplierDto> entities = q.getResultList();
			Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getPageSize());
			Page<SupplierDto> result = new PageImpl<SupplierDto>(entities, pageable, count);

			return result;
		} else {
			return null;
		}
	}

	@Override
	public SupplierDto saveOrUpdate(SupplierDto dto) {
		if (dto != null) {

			Supplier entity = null;
			if (dto.getId() > 0) {
				entity = supplierRepository.findById(dto.getId()).get();
			}
			if (entity == null) {
				entity = new Supplier();
			}
			entity.setName(dto.getName());
			entity.setAddress(dto.getAddress());
			entity.setDes(dto.getDes());
			entity.setPhoneNumber(dto.getPhoneNumber());
			entity.setEmail(dto.getEmail());
			entity = supplierRepository.save(entity);
			if (entity != null) {
				return new SupplierDto(entity);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
