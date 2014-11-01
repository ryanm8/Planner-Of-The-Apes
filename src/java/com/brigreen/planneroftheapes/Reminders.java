/*
 * Created by Brian Green on 2014.10.31  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.brigreen.planneroftheapes;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "reminders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reminders.findAll", query = "SELECT r FROM Reminders r"),
    @NamedQuery(name = "Reminders.findById", query = "SELECT r FROM Reminders r WHERE r.id = :id"),
    @NamedQuery(name = "Reminders.findByDate", query = "SELECT r FROM Reminders r WHERE r.date = :date"),
    @NamedQuery(name = "Reminders.findByText", query = "SELECT r FROM Reminders r WHERE r.text = :text"),
    @NamedQuery(name = "Reminders.findByTitle", query = "SELECT r FROM Reminders r WHERE r.title = :title"),
    @NamedQuery(name = "Reminders.findByEmail", query = "SELECT r FROM Reminders r WHERE r.email = :email"),
    @NamedQuery(name = "Reminders.findByFirstName", query = "SELECT r FROM Reminders r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Reminders.findByUserID", query = "SELECT r FROM Reminders r WHERE r.userID = :userID")})
public class Reminders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Text")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Title")
    private String title;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "User_ID")
    private int userID;

    public Reminders() {
    }

    public Reminders(Integer id) {
        this.id = id;
    }

    public Reminders(Integer id, Date date, String text, String title, String email, String firstName, int userID) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.title = title;
        this.email = email;
        this.firstName = firstName;
        this.userID = userID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reminders)) {
            return false;
        }
        Reminders other = (Reminders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.brigreen.planneroftheapes.Reminders[ id=" + id + " ]";
    }
    
}
