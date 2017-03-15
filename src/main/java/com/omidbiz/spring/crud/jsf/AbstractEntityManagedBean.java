package com.omidbiz.spring.crud.jsf;

import java.util.Locale;

import com.omidbiz.spring.crud.service.AbstractCrudService;

public abstract class AbstractEntityManagedBean<E> extends AbstractManagedBean
{

    public abstract AbstractCrudService<E> getService();

    public void load()
    {
        getService().load();
    }

    public E getInstance()
    {
        return getService().getInstance();
    }

    public boolean isManaged()
    {
        return getService().isManaged();
    }

    public String persist()
    {
        getService().insert();
        createMessage();
        return getNavigation().insertAction();
    }

    public String update()
    {
        E instance = getInstance();
        getService().update();
        updateMessage();
        return getNavigation().updateAction();
    }

    public String delete()
    {
        getService().update();
        return getNavigation().deleteAction();
    }

    protected NavigationRule getNavigation()
    {
        return DefaultNavigationRule.INSTANCE.build(getInstance().getClass());
    }

    private static class DefaultNavigationRule implements NavigationRule
    {

        public static final DefaultNavigationRule INSTANCE = new DefaultNavigationRule();

        private Class<?> entityClass;
        private String navigationPath;

        private DefaultNavigationRule()
        {
        }

        public DefaultNavigationRule build(Class<?> clz)
        {
            if (clz == null)
                throw new IllegalArgumentException("clz is null");
            this.entityClass = clz;
            String simpleName = entityClass.getSimpleName();
            navigationPath = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            return this;
        }

        @Override
        public String deleteAction()
        {
            // return navigationPath + "List.xhtml?faces-redirect=true";
            return null;
        }

        @Override
        public String updateAction()
        {
            return null;
        }

        @Override
        public String insertAction()
        {
            return null;
        }
    }

    public interface NavigationRule
    {
        public String deleteAction();

        public String updateAction();

        public String insertAction();
    }

}
