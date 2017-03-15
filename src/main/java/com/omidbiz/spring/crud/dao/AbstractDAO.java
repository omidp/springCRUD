package com.omidbiz.spring.crud.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface AbstractDAO<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T>
{

    //implements Custom behaviour
    
}
