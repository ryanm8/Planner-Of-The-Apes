package com.brigreen.jsfclassespackage;

import com.brigreen.planneroftheapes.Group1;
import com.brigreen.jsfclassespackage.util.JsfUtil;
import com.brigreen.jsfclassespackage.util.JsfUtil.PersistAction;
import com.brigreen.sessionbeanpackage.Group1Facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("group1Controller")
@SessionScoped
public class Group1Controller implements Serializable {

    @EJB
    private com.brigreen.sessionbeanpackage.Group1Facade ejbFacade;
    private List<Group1> items = null;
    private Group1 selected;

    public Group1Controller() {
    }

    public Group1 getSelected() {
        return selected;
    }

    public void setSelected(Group1 selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private Group1Facade getFacade() {
        return ejbFacade;
    }

    public Group1 prepareCreate() {
        selected = new Group1();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("Group1Created"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("Group1Updated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("Group1Deleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Group1> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Group1 getGroup1(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Group1> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Group1> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public String getGroupNameFromID(int groupId) 
    {
    List<Group1> myItems = getFacade().findByQueryOneParam("SELECT a FROM Group1 a WHERE a.groupID LIKE :ID", "ID", groupId);
    items = myItems;
    return myItems.get(0).getName();
    }
    
    public List<Group1> getGroupInfoFromUserID(int userId) 
    {
    List<Group1> myItems = getFacade().findByQueryOneParam("SELECT a FROM Group1 a WHERE a.userID LIKE :ID", "ID", userId);
    items = myItems;
    List<Group1> groupinfo = new ArrayList<Group1>();
    
    for (Group1 group: myItems)
    {
        groupinfo.addAll(getFacade().findByQueryOneParam("SELECT a FROM Group1 a WHERE a.groupID LIKE :ID", "ID", group.getGroupID()));
    }
    return groupinfo;
    }

    @FacesConverter(forClass = Group1.class)
    public static class Group1ControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Group1Controller controller = (Group1Controller) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "group1Controller");
            return controller.getGroup1(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Group1) {
                Group1 o = (Group1) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Group1.class.getName()});
                return null;
            }
        }

    }

}
