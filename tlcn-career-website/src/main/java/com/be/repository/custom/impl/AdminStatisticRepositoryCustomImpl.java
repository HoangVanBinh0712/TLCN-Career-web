package com.be.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.be.repository.custom.AdminStatisticRepositoryCustom;

@Repository
public class AdminStatisticRepositoryCustomImpl implements AdminStatisticRepositoryCustom{
    @PersistenceContext
	private EntityManager em;

    

}
