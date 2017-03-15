package com.omidbiz.spring.crud.model;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public interface EntityDTO<E extends Persistable<Serializable>, D>
{

    public E getInstance();

    public D getDtoInstance();

}
