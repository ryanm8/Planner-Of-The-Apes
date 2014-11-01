package com.brigreen.jsfclassespackage;

import com.brigreen.planneroftheapes.Assignment;
import com.brigreen.jsfclassespackage.util.JsfUtil;
import com.brigreen.jsfclassespackage.util.JsfUtil.PersistAction;
import com.brigreen.sessionbeanpackage.AssignmentFacade;

import java.io.Serializable;
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

@Named("assignmentController")
@SessionScoped
public class AssignmentController implements Serializable {

    @EJB
    private com.brigreen.sessionbeanpackage.AssignmentFacade ejbFacade;
    private List<Assignment> items = null;
    private Assignment selected;

    public AssignmentController() {
    }

    public Assignment getSelected() {
        return selected;
    }

    public void setSelected(Assignment selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AssignmentFacade getFacade() {
        return ejbFacade;
    }

    public Assignment prepareCreate() {
        selected = new Assignment();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AssignmentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AssignmentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AssignmentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Assignment> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<Assignment> getItemsByAssignee(int assigneeId) {
        items = getFacade().findByQueryOneParam("SELECT a FROM Assignment a WHERE a.assigneeID.id LIKE :ID AND a.groupid IS NULL ORDER BY a.dueDate", "ID", assigneeId);
        return items;
    }
    
    public List<Assignment> getItemsByGroup(int assigneeId) {
        items = getFacade().findByQueryOneParam("SELECT a FROM Assignment a WHERE a.assigneeID.id LIKE :ID  AND a.groupid IS NOT NULL ORDER BY a.groupid ASC", "ID", assigneeId);
        return items;
    }
    
    public List<Assignment> getItemsArbitraryQuery(String query) {
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

    public Assignment getAssignment(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Assignment> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Assignment> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Assignment.class)
    public static class AssignmentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AssignmentController controller = (AssignmentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "assignmentController");
            return controller.getAssignment(getKey(value));
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
            if (object instanceof Assignment) {
                Assignment o = (Assignment) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Assignment.class.getName()});
                return null;
            }
        }

    }

}
