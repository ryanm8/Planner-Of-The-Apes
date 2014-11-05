package com.brigreen.jsfclassespackage;

import com.brigreen.planneroftheapes.Assignment;
import com.brigreen.jsfclassespackage.util.JsfUtil;
import com.brigreen.jsfclassespackage.util.JsfUtil.PersistAction;
import com.brigreen.planneroftheapes.User;
import com.brigreen.planneroftheapes.Group1;
import com.brigreen.sessionbeanpackage.AssignmentFacade;
import java.io.IOException;

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
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name="assignmentController")
@Named("assignmentController")
@SessionScoped
public class AssignmentController implements Serializable {
    @EJB
    private com.brigreen.sessionbeanpackage.AssignmentFacade ejbFacade;
    private List<Assignment> items = null;
    private Assignment selected;
    private User user;

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
        selected.setAssigneeID(user);
        selected.setId(0);
        selected.setGroupid(null);
        selected.setDocumentsid(null);
        selected.setDocumentPath("Add New Doc?");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AssignmentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createGroupAssign() {
        selected.setAssigneeID(user);
        selected.setId(0);
        //selected.setGroupid(null);
        selected.setDocumentsid(null);
        selected.setDocumentPath("Add New Doc?");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AssignmentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
     public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AssignmentUpdated"));
    }

     public void removeDocument()
     {
         if (selected != null)
         { 
            if (selected.getFile() != null)
            {
                selected.removeDocument();
                update();
            }
         }
     }
     
    public void upload() {
        if (selected.getFile() != null)
        {
            try {
                selected.upload();
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AssignmentUpdated"));
            } catch (IOException ex) {
                Logger.getLogger(AssignmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    
    public void destroy() {
        this.removeDocument();
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AssignmentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void destroyAll()
    {
        this.removeDocument();
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AssignmentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            //items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public int removeAssignmentsForMembers(int id)
    {
        if(id != 0)
        {
            items = this.getFacade().findByQueryOneParam("SELECT a FROM Assignment a WHERE a.groupid.id LIKE :ID", "ID", id);
            for(Assignment item : items)
            {
                selected = item;
                destroyAll();
            }
            selected = null;
            items = null;
        }
        return id;
    }
    
    public List<Assignment> getItems() {
        //if (items == null) {
            items = getFacade().findAll();
        //}
        return items;
    }
    
    public void setUser(User guy)
    {
        user = guy;
    }
    
    public List<Assignment> getItemsByAssignee(int assigneeId) {
        items = getFacade().findByQueryOneParam("SELECT a FROM Assignment a WHERE a.assigneeID.id LIKE :ID AND a.groupid IS NULL ORDER BY a.dueDate", "ID", assigneeId);
        return items;
    }
    
    public List<Assignment> getItemsByGroup(int assigneeId) {
        items = getFacade().findByQueryOneParam("SELECT a FROM Assignment a WHERE a.assigneeID.id LIKE :ID  AND a.groupid IS NOT NULL ORDER BY a.groupid ASC", "ID", assigneeId);
        return items;
    }
    
    public List<Assignment> getGroupItemsByGroupObject(List<Group1> groupObject) 
    {
        List<Assignment> assignmentInfo = new ArrayList<Assignment>();

        for(Group1 mygroup : groupObject)
        {
            assignmentInfo.addAll(getFacade().findByQueryOneParam("SELECT a FROM Assignment a WHERE a.groupid.groupID LIKE :ID", "ID", mygroup.getGroupID()));
        }
        return assignmentInfo;
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
