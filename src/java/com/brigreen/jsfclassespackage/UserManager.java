/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *  Original Code take from Netbeans Sample Jsf Jpa for Jave EE and modified
 * for the purposes of our project
 */

package com.brigreen.jsfclassespackage;

import com.brigreen.jsfclassespackage.util.JsfUtil;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import com.brigreen.planneroftheapes.User;
import static com.sun.faces.facelets.util.Path.context;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 * <p>A simple managed bean to mediate between the user
 * and the persistence layer.</p>
 * @author rlubke
 */
public class UserManager {
    
    /**
     * <p>The key for the session scoped attribute holding the
     * appropriate <code>Wuser</code> instance.</p>
     */
    public static final String USER_SESSION_KEY = "user";
    
    /**
     * <p>The <code>PersistenceContext</code>.</p>
     */
    @PersistenceContext 
    private EntityManager em;
    
    /**
     * <p>The transaction resource.</p>
     */
    @Resource 
    private UserTransaction utx;
    
    /**
     * <p>User properties.</p>
     */
    private String username;
    private String password;
    private String passwordv;
    private String currentPassword;
    private String currentPasswordv;
    private String fname;
    private String lname; 
    private String email;
    
    // -------------------------------------------------------------- Properties
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPasswordv() {
        return passwordv;
    }
    
    public void setPasswordv(String passwordv) {
        this.passwordv = passwordv;
    }
    
    public String getCurrentPasswordv() {
        return currentPasswordv;
    }
    
    public void setCurrentPasswordv(String passwordv) {
        this.currentPasswordv = passwordv;
    }
    
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public void setCurrentPassword(String password) {
        this.currentPassword = password;
    }
    
    public String getFname() {
        return fname;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }
    
    public String getLname() {
        return lname;
    }
    
    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    // ---------------------------------------------------------- Public Methods
    
    /**
     * Called to allow for the editing of User settings.
     * @param user The currently logged on user.
     */
    public void populate(User user)
    {
        setUsername(user.getPid());
        setFname(user.getFirstName());
        setLname(user.getLastName());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setPasswordv(user.getPassword());
        setCurrentPassword(user.getPassword());   
    }
    
    /**
     * Updates the current user settings
     * @param currentPassword Current password for the user - difficulty getting
     *         it any other way than directly.
     * @param id The user id
     * @return Returns result of logout or null to redirect
     */
    public String updateUserInformation(String currentPassword, int id)
    {
        if (currentPasswordv != null && !currentPasswordv.equals(""))
        {
            FacesContext context = FacesContext.getCurrentInstance();
            User user = em.find(User.class, id);
            if (currentPasswordv.equals(user.getPassword()))
            {
                if (password.equals(passwordv))
                {
                    if (user == null)
                    {
                        return null;
                    }
                    user.setFirstName(fname);
                    user.setLastName(lname);
                    String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                    if(!email.matches(EMAIL_REGEX))
                    {
                        FacesMessage message = new FacesMessage("Please enter a valid Email Address.");
                        context.addMessage(null, message);
                        return null;
                    }
                    user.setEmail(email);
                    user.setPassword(password);
                    try {
                        utx.begin();
                        em.merge(user);
                        utx.commit();
                        return logout();
                    } catch (Exception ex) {
                        Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    JsfUtil.addErrorMessage("New Passwords Do Not Match");
                }
            }
            else
            {
                JsfUtil.addErrorMessage("Incorrect Password");
            }
        }
        return null;
    }
    /**
     * <p>Validates the user.  If the user doesn't exist or the password
     * is incorrect, the appropriate message is added to the current
     * <code>FacesContext</code>.  If the user successfully authenticates,
     * navigate them to the page referenced by the outcome <code>app-main</code>.
     * </p>
     *
     * @return <code>app-main</code> if the user authenticates, otherwise
     *  returns <code>null</code>
     */
    public String validateUser() {   
        FacesContext context = FacesContext.getCurrentInstance();
        User user = getUser();
        if (user != null) {
            if (!user.getPassword().equals(password)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Login Failed!",
                                           "The password specified is not correct.");
                context.addMessage(null, message);
                return null;
            }
            
            context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, user);
            return "app-main";
        } else {           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed!",
                    "Username '"
                    + username
                    +
                    "' does not exist.");
            context.addMessage(null, message);
            return null;
        }
    }
    
    /**
     * <p>Creates a new <code>Wuser</code>.  If the specified user name exists
     * or an error occurs when persisting the Wuser instance, enqueue a message
     * detailing the problem to the <code>FacesContext</code>.  If the 
     * user is created, move the user back to the login view.</p>
     *
     * @return <code>login</code> if the user is created, otherwise
     *  returns <code>null</code>
     */
    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        User wuser = getUser();
        if (wuser == null) {
            if (!password.equals(passwordv)) {
                FacesMessage message = new FacesMessage("The specified passwords do not match.  Please try again");
                context.addMessage(null, message);
                return null;
            }
            wuser = new User();
            wuser.setFirstName(fname);
            wuser.setLastName(lname);
            wuser.setPassword(password);
            wuser.setPid(username);
            String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if(!email.matches(EMAIL_REGEX))
            {
                FacesMessage message = new FacesMessage("Please enter a valid Email Address.");
                context.addMessage(null, message);
                return null;
            }
            wuser.setEmail(email);
            try {
                utx.begin();
                em.persist(wuser);
                utx.commit();
                return "login";
            } catch (Exception e) {               
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                        "Error creating user!",
                                                        "Unexpected error when creating your account.  Please contact the system Administrator");
                context.addMessage(null, message);
                Logger.getAnonymousLogger().log(Level.SEVERE,
                                                "Unable to create new user",
                                                e);
                return null;
            }
        } else {           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    "Username '"
                                                      + username 
                                                      + "' already exists!  ",
                                                    "Please choose a different username.");
            context.addMessage(null, message);
            return null;
        }        
    }
    
    
    /**
     * <p>When invoked, it will invalidate the user's session
     * and move them to the login view.</p>
     *
     * @return <code>login</code>
     */
    public String logout() {
        HttpSession session = (HttpSession)
             FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";
        
    }
    
    // --------------------------------------------------------- Private Methods
    
    
    /**
     * <p>This will attempt to lookup a <code>Wuser</code> object
     * based on the provided user name.</p>
     *
     * @return a <code>Wuser</code> object associated with the current
     *  username, otherwise, if no <code>Wuser</code> can be found,
     *  returns <code>null</code>
     */
    private User getUser() {
        try {
            User user = (User)
            em.createNamedQuery("user.findByPid").
                    setParameter(1, username).getSingleResult();
            return user; 
        } catch (NoResultException nre) {
            return null;
        }
    }
   
}
