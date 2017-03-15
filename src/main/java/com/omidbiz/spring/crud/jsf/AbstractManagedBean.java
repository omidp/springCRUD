package com.omidbiz.spring.crud.jsf;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class AbstractManagedBean implements Serializable
{

    @Autowired
    MessageSource messageSource;

    /**
     * @see FacesContext#addMessage(String, FacesMessage)
     */
    public void showMessage(String clientId, Severity severity, String summary, String details)
    {
        FacesContext context = getCurrentInstance();
        context.addMessage(clientId, new FacesMessage(severity, summary, details));
    }

    /**
     * shows information message for component <code><h:message/></code> with
     * the given clientId
     * 
     * @param clientId
     *            of the component
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showInfoMessage(String clientId, String summary, String details)
    {
        showMessage(clientId, FacesMessage.SEVERITY_INFO, summary, details);
    }

    /**
     * shows warning message for component <code><h:message/></code> with the
     * given clientId
     * 
     * @param clientId
     *            of the component
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showWarningMessage(String clientId, String summary, String details)
    {
        showMessage(clientId, FacesMessage.SEVERITY_WARN, summary, details);
    }

    /**
     * shows error message for component <code><h:message/></code> with the
     * given clientId
     * 
     * @param clientId
     *            of the component
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showErrorMessage(String clientId, String summary, String details)
    {
        showMessage(clientId, FacesMessage.SEVERITY_ERROR, summary, details);
    }

    /**
     * shows fatal message for component <code><h:message/></code> with the
     * given clientId
     * 
     * @param clientId
     *            of the component
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showFatalMessage(String clientId, String summary, String details)
    {
        showMessage(clientId, FacesMessage.SEVERITY_FATAL, summary, details);
    }

    /**
     * shows information messages globally. <code><h:messages ></code> component
     * will show it.
     * 
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showGlobalInfoMessage(String summary, String details)
    {
        showMessage(null, FacesMessage.SEVERITY_INFO, summary, details);
    }

    /**
     * shows warning messages globally. <code><h:messages ></code> component
     * will show it.
     * 
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showGlobalWarningMessage(String summary, String details)
    {
        showMessage(null, FacesMessage.SEVERITY_WARN, summary, details);
    }

    /**
     * shows error messages globally. <code><h:messages ></code> component will
     * show it.
     * 
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showGlobalErrorMessage(String summary, String details)
    {
        showMessage(null, FacesMessage.SEVERITY_ERROR, summary, details);
    }

    /**
     * shows fatal messages globally. <code><h:messages ></code> component will
     * show it.
     * 
     * @param summary
     *            message
     * @param details
     *            message
     */
    public void showGlobalFatalMessage(String summary, String details)
    {
        showMessage(null, FacesMessage.SEVERITY_FATAL, summary, details);
    }

    /**
     * @return a map that contains parameters for the current faces context.a
     *         situations such as
     *         <code><h:commandLink><f:param /><f:param /></h:commandLink></code>
     *         .
     */
    public Map<String, String> getRequestParameterMap()
    {
        return getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

    /**
     * @param parameterName
     * @return parameterValue of the given parameterName for the current faces
     *         context.
     */
    public String getRequestParameterValue(String parameterName)
    {
        Map<String, String> parameterMap = getRequestParameterMap();
        if (parameterMap != null)
        {
            return parameterMap.get(parameterName);
        }
        return null;
    }

    /**
     * @return view id (page name) of the current context.
     */
    public String getCurrentViewID()
    {
        String viewId = getCurrentInstance().getViewRoot().getViewId();
        return viewId.substring(viewId.lastIndexOf('/') + 1);
    }

    public Locale getCurrentLocale()
    {
        FacesContext facesContext = getCurrentInstance();
        // first get locale from UIViewRoot.
        Locale requestLocale = facesContext.getViewRoot().getLocale();
        if (requestLocale == null)
        {
            return facesContext.getApplication().getDefaultLocale();
        }
        else
        {
            return requestLocale;
        }
    }

    protected FacesContext getCurrentInstance()
    {
        return FacesContext.getCurrentInstance();
    }

    protected void createMessage()
    {
        Locale currentLocale = getCurrentLocale();
        showGlobalInfoMessage(messageSource.getMessage("core.attention", null, currentLocale),
                messageSource.getMessage("core.savedSuccessfully", null, currentLocale));
        getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    protected void updateMessage()
    {
        Locale currentLocale = getCurrentLocale();
        showGlobalInfoMessage(messageSource.getMessage("core.attention", null, currentLocale),
                messageSource.getMessage("core.updatedSuccessfully", null, currentLocale));
        getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    protected void deleteMessage()
    {
        Locale currentLocale = getCurrentLocale();
        showGlobalInfoMessage(messageSource.getMessage("core.attention", null, currentLocale),
                messageSource.getMessage("core.updatedSuccessfully", null, currentLocale));
        getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

}
