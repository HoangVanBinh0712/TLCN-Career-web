package com.be.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.be.model.Field;
import com.be.model.Field_;
import com.be.repository.custom.FieldCustomRepository;
import com.be.utility.Page;

public class FieldCustomRepositoryImpl implements FieldCustomRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public Long countBeforeSearch(String key) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Field> root = cq.from(Field.class);
		List<Predicate> lstPredicate = new ArrayList<>();

		if (key != null)
			lstPredicate.add(cb.like(root.get(Field_.name), "%" + key + "%"));
		Predicate predicate = cb.and(lstPredicate.toArray(new Predicate[0]));
		cq.select(cb.count(root)).where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	@Override
	public List<Field> searchAndPaging(String key, Page page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Field> cq = cb.createQuery(Field.class);
		Root<Field> root = cq.from(Field.class);
		List<Predicate> lstPredicate = new ArrayList<>();

		if (key != null)
			lstPredicate.add(cb.like(root.get(Field_.name), "%" + key + "%"));
		Predicate predicate = cb.and(lstPredicate.toArray(new Predicate[0]));
		cq.select(root).where(predicate);

		TypedQuery<Field> query = em.createQuery(cq);

		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return query.getResultList();
	}
}
