/*
 * Created by Brian Green on 2014.10.25  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */

package com.brigreen.planneroftheapes;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "group1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Group1.findAll", query = "SELECT g FROM Group1 g"),
    @NamedQuery(name = "Group1.findById", query = "SELECT g FROM Group1 g WHERE g.id = :id"),
    @NamedQuery(name = "Group1.findByGroupUserid", query = "SELECT g FROM Group1 g WHERE g.groupUserid = :groupUserid"),
    @NamedQuery(name = "Group1.findByAdmin", query = "SELECT g FROM Group1 g WHERE g.admin = :admin"),
    @NamedQuery(name = "Group1.findByName", query = "SELECT g FROM Group1 g WHERE g.name = :name")})
public class Group1 implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private Collection<Groupuser> groupuserCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupUser_id")
    private int groupUserid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Admin")
    private String admin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Name")
    private String name;
    @JoinTable(name = "groupuser", joinColumns = {
        @JoinColumn(name = "ID", referencedColumnName = "GroupUser_id")}, inverseJoinColumns = {
        @JoinColumn(name = "User_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<User> userCollection;
    @OneToMany(mappedBy = "groupid")
    private Collection<Assignment> assignmentCollection;

    public Group1() {
    }

    public Group1(Integer id) {
        this.id = id;
    }

    public Group1(Integer id, int groupUserid, String admin, String name) {
        this.id = id;
        this.groupUserid = groupUserid;
        this.admin = admin;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGroupUserid() {
        return groupUserid;
    }

    public void setGroupUserid(int groupUserid) {
        this.groupUserid = groupUserid;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<Assignment> getAssignmentCollection() {
        return assignmentCollection;
    }

    public void setAssignmentCollection(Collection<Assignment> assignmentCollection) {
        this.assignmentCollection = assignmentCollection;
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
        if (!(object instanceof Group1)) {
            return false;
        }
        Group1 other = (Group1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.brigreen.planneroftheapes.Group1[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Groupuser> getGroupuserCollection() {
        return groupuserCollection;
    }

    public void setGroupuserCollection(Collection<Groupuser> groupuserCollection) {
        this.groupuserCollection = groupuserCollection;
    }
    
}
