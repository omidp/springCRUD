package com.omidbiz.spring.crud.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.transaction.annotation.Transactional;

import com.omidbiz.spring.crud.dao.AbstractDAO;

/**
 * @author Omid Pourhadi
 *
 */
public abstract class AbstractService<E>
{

    private Class<E> entityClass;
    protected E instance;
    private Object id;

    protected E createInstance()
    {

        if (getEntityClass() != null)
        {
            try
            {
                return getEntityClass().newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return null;
        }
    }
    
    public void clearInstance()
    {
        instance = null;
        id = null;
    }

    @Transactional
    public E getInstance()
    {
        if (instance == null)
        {
            initInstance();
        }
        return instance;
    }

    /**
     * Load the instance if the id is defined otherwise create a new instance
     * <br />
     * Utility method called by {@link #getInstance()} to load the instance from
     * the Persistence Context if the id is defined. Otherwise a new instance is
     * created.
     * 
     * @see #find()
     * @see #createInstance()
     */
    protected void initInstance()
    {
        if (isIdDefined())
        {

            setInstance(find());

        }
        else
        {
            setInstance(createInstance());
        }
    }

    protected E find()
    {
        throw new NotYetImplementedException("must implement find method");
    }

    public boolean isIdDefined()
    {
        return getId() != null && !"".equals(getId());
    }

    /**
     * Get the id of the object being managed.
     */
    public Object getId()
    {
        return id;
    }

    /**
     * Set/change the entity being managed by id.
     * 
     * @see #assignId(Object)
     */
    public void setId(Object id)
    {
        this.id = id;
    }

    public void setInstance(E instance)
    {

        this.instance = instance;
    }

    /**
     * Get the class of the entity being managed. <br />
     * If not explicitly specified, the generic type of implementation is used.
     */
    public Class<E> getEntityClass()
    {
        if (entityClass == null)
        {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType)
            {
                ParameterizedType paramType = (ParameterizedType) type;
                if (paramType.getActualTypeArguments().length == 2)
                {
                    // likely dealing with -> new
                    // EntityHome<Person>().getEntityClass()
                    if (paramType.getActualTypeArguments()[1] instanceof TypeVariable)
                    {
                        throw new IllegalArgumentException("Could not guess entity class by reflection");
                    }
                    // likely dealing with -> new Home<EntityManager, Person>()
                    // { ... }.getEntityClass()
                    else
                    {
                        entityClass = (Class<E>) paramType.getActualTypeArguments()[1];
                    }
                }
                else
                {
                    // likely dealing with -> new PersonHome().getEntityClass()
                    // where PersonHome extends EntityHome<Person>
                    entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
                }
            }
            else
            {
                throw new IllegalArgumentException("Could not guess entity class by reflection");
            }
        }
        return entityClass;
    }

}
