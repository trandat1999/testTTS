package com.tranhuudat.manager.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.tranhuudat.manager.domain.Category;
import com.tranhuudat.manager.dto.CategoryDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.repository.CategoryRepository;
import com.tranhuudat.manager.service.CategoryService;
import com.tranhuudat.manager.type.YESNO;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	public EntityManager manager;

	@Cacheable("category")
	@Override
	public ResponseDto<CategoryDto> findById(long id) {
		ResponseDto<CategoryDto> res = new ResponseDto<CategoryDto>();
		Category entity=null;
		entity = categoryRepository.findById(id).orElseThrow(()-> new NoSuchElementException());
		if (entity != null) {
			res.setStatus(YESNO.YES);
			res.setObject(new CategoryDto(entity));
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@CacheEvict("category")
	@Override
	public ResponseDto<CategoryDto> delete(long id) {
		ResponseDto<CategoryDto> res = new ResponseDto<CategoryDto>();
		Category entity = categoryRepository.findById(id).get();
		if (entity != null) {
			categoryRepository.delete(entity);
			res.setStatus(YESNO.YES);
			res.setObject(new CategoryDto(entity));
			res.setMessage("delete success");
		} else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@Override
	public Page<CategoryDto> findPage(FilterDto filter) {
		if (filter != null) {
			String SQL = " SELECT new com.tranhuudat.manager.dto.CategoryDto(s) from Category s WHERE 1=1 ";
			String countSQL = " SELECT COUNT(s.id) from Category s WHERE 1=1 ";
			String whereClause = "";
			Query q = manager.createQuery(SQL + whereClause, CategoryDto.class);
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
			List<CategoryDto> entities = q.getResultList();
			Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getPageSize());
			Page<CategoryDto> result = new PageImpl<CategoryDto>(entities, pageable, count);

			return result;
		} else {
			return null;
		}
	}

	@CachePut(value = "category")
	@Override
	public CategoryDto saveOrUpdate(CategoryDto dto) {
		if (dto != null) {

			Category entity = null;
			if (dto.getId() > 0) {
				entity = categoryRepository.findById(dto.getId()).get();
			}
			if (entity == null) {
				entity = new Category();
			}
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
			if (entity != null) {
				return new CategoryDto(entity);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
