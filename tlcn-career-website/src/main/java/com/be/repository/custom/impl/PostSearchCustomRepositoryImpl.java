package com.be.repository.custom.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import com.be.model.Post;
import com.be.model.Post_;
import com.be.repository.custom.PostSearchCustomRepository;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

@Repository
public class PostSearchCustomRepositoryImpl implements PostSearchCustomRepository {

	@PersistenceContext
	EntityManager em;

	public List<Post> adminSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
			Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId, Page page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Post> cq = cb.createQuery(Post.class);
		Root<Post> root = cq.from(Post.class);
		List<Predicate> lstPredicate = new ArrayList<>();

		if (StringUtils.isNotBlank(keyword))
			lstPredicate.add(cb.like(root.get(Post_.title), "%" + keyword + "%"));

		if (recruit != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.recruit), recruit));
		if (salary != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.salary), salary));
		if (eSalary != null)
			lstPredicate.add(cb.equal(root.get(Post_.salaryType), eSalary));
		if (authorId != null)
			lstPredicate.add(cb.equal(root.get(Post_.employer), authorId));
		if (fieldId != null)
			lstPredicate.add(cb.equal(root.get(Post_.field), fieldId));
		if (cityId != null)
			lstPredicate.add(cb.equal(root.get(Post_.city), cityId));
		if (expirationDate != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.expirationDate), expirationDate));
		if (startDate != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.startDate), startDate));
		if (serviceId != null)
			lstPredicate.add(cb.equal(root.get(Post_.service), serviceId));
		if (status != null)
			lstPredicate.add(cb.equal(root.get(Post_.status), status));
		else {
			lstPredicate.add(cb.or(cb.equal(root.get(Post_.status), EStatus.ACTIVE),
					cb.equal(root.get(Post_.status), EStatus.DISABLE)));
		}

		if(page.getSort() != null)
		{
			cq.orderBy(QueryUtils.toOrders(page.getSort(), root, cb));
		}
		Predicate predicate = cb.and(lstPredicate.toArray(new Predicate[0]));

		cq.select(root).where(predicate);
		TypedQuery<Post> query = em.createQuery(cq);

		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return query.getResultList();
	}

	public Long adminCountBeforeSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
			Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Post> root = cq.from(Post.class);
		List<Predicate> lstPredicate = new ArrayList<>();

		if (StringUtils.isNotBlank(keyword))
			lstPredicate.add(cb.like(root.get(Post_.title), "%" + keyword + "%"));

		if (recruit != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.recruit), recruit));
		if (salary != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.salary), salary));
		if (eSalary != null)
			lstPredicate.add(cb.equal(root.get(Post_.salaryType), eSalary));
		if (authorId != null)
			lstPredicate.add(cb.equal(root.get(Post_.employer), authorId));
		if (fieldId != null)
			lstPredicate.add(cb.equal(root.get(Post_.field), fieldId));
		if (cityId != null)
			lstPredicate.add(cb.equal(root.get(Post_.city), cityId));
		if (expirationDate != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.expirationDate), expirationDate));
		if (startDate != null)
			lstPredicate.add(cb.greaterThanOrEqualTo(root.get(Post_.startDate), startDate));
		if (serviceId != null)
			lstPredicate.add(cb.equal(root.get(Post_.service), serviceId));
		if (status != null)
			lstPredicate.add(cb.equal(root.get(Post_.status), status));
		else {
			lstPredicate.add(cb.or(cb.equal(root.get(Post_.status), EStatus.ACTIVE),
					cb.equal(root.get(Post_.status), EStatus.DISABLE)));
		}

		Predicate predicate = cb.and(lstPredicate.toArray(new Predicate[0]));

		cq.select(cb.count(root)).where(predicate);
		TypedQuery<Long> query = em.createQuery(cq);

		return query.getSingleResult();
	}
}
