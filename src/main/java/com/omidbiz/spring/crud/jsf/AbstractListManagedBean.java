package com.omidbiz.spring.crud.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.omidbiz.spring.crud.model.AbstractEntity;
import com.omidbiz.spring.crud.service.AbstractCrudService;

/**
 * @author Omid Pourhadi
 *
 */
public abstract class AbstractListManagedBean<E extends AbstractEntity> extends AbstractManagedBean
{

    private LazyDataModel<E> resultList;

    private Long resultCount;

    private E selectedItem;

    public abstract AbstractCrudService<E> getService();

    public E getSelectedItem()
    {
        return selectedItem;
    }

    public void setSelectedItem(E selectedItem)
    {
        this.selectedItem = selectedItem;
    }

    public void delete()
    {
        if (getSelectedItem() != null)
        {
            getService().setInstance(getSelectedItem());
            getService().delete();
            deleteMessage();
        }
    }

    public E getInstance()
    {
        return getService().getInstance();
    }

    public void resetList()
    {
        resultList = null;
        resultCount = null;
    }

    public void clearInstance()
    {
        resultList = null;
        resultCount = null;
        getService().clearInstance();
    }

    public LazyDataModel<E> getResultList()
    {

        if (resultList != null)
            return resultList;
        Long count = getResultCount();
        resultList = new ExtendedLazyDataModel<E>(count) {
            @Override
            protected List<E> lazyLoad(int first, int pageSize, String sortField)
            {
                return getService().load(first, pageSize, sortField);
            }
        };

        return resultList;

    }

    public Long getResultCount()
    {

        if (resultCount != null)
            return resultCount;
        resultCount = getService().count();
        return resultCount;
    }

    public static abstract class ExtendedLazyDataModel<E extends AbstractEntity> extends LazyDataModel<E>
    {

        private Long count;

        public ExtendedLazyDataModel(Long count)
        {
            this.count = count;
        }

        @Override
        public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
        {
            if (count == null || count.longValue() == 0)
                return new ArrayList<E>();
            setRowCount(count.intValue());
            List<E> load = lazyLoad(first, pageSize, sortField);
            return load;
        }

        @Override
        public E getRowData(String rowKey)
        {
            List<E> res = (List<E>) getWrappedData();
            List<E> wrappedData = new ArrayList<>(res);
            for (E e : wrappedData)
            {
                if (e.getId().equals(Long.valueOf(rowKey)))
                    return e;
            }
            return null;
        }

        @Override
        public Object getRowKey(E instance)
        {
            return instance.getId();
        };

        protected abstract List<E> lazyLoad(int first, int pageSize, String sortField);

    }

}
