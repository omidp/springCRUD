package com.omidbiz.spring.crud.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.omidbiz.spring.crud.dao.AbstractDAO;

/**
 * @author Omid Pourhadi
 *
 */
@Transactional
@Scope("prototype")
public abstract class AbstractCrudService<E> extends AbstractService<E>
{

    @Autowired
    protected EntityManager entityManager;

    public abstract AbstractDAO<E> getDao();

    public abstract Specification<E> getSearchSpec();

    public List<E> load(int first, int pageSize, String sortField)
    {
        Page<E> result = null;
        if (getSearchSpec() != null && isPostback())
            result = getDao().findAll(getSearchSpec(), new PageRequest(first, pageSize));
        else
            result = getDao().findAll(new PageRequest(first, pageSize));
        // TODO : add sorting
        return result.getContent();
    }

    public Long count()
    {
        if (getSearchSpec() != null && isPostback())
            return getDao().count(getSearchSpec());
        else
            return getDao().count();
    }

    public void load()
    {
        initInstance();
    }

    public boolean isManaged()
    {
        // return isIdDefined() && entityManager.contains(getInstance());
        return isIdDefined();
    }

    public void insert()
    {
        E entity = getInstance();
        beforeInsert(entity);
        getDao().save(entity);
        afterInsert(entity);
    }

    public void update()
    {
        E entity = getInstance();
        beforeUpdate(entity);
        getDao().save(entity);
        afterUpdate(entity);
    }

    protected void afterUpdate(E entity)
    {

    }

    protected void beforeUpdate(E entity)
    {

    }

    public void delete()
    {
        E entity = getInstance();
        beforeDelete(entity);
        getDao().delete(entity);
        afterDelete(entity);
    }

    protected void afterDelete(E entity)
    {
        
    }

    protected void beforeDelete(E entity)
    {
        
    }

    protected void afterInsert(E entity)
    {

    }

    protected void beforeInsert(E entity)
    {

    }

    public boolean isPostback()
    {
        return FacesContext.getCurrentInstance().isPostback();
    }

}
