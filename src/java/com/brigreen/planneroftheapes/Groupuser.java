/*
 * Created by Brian Green on 2014.10.31  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */

package com.brigreen.planneroftheapes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "groupuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupuser.findAll", query = "SELECT g FROM Groupuser g"),
    @NamedQuery(name = "Groupuser.findByGroupuserid", query = "SELECT g FROM Groupuser g WHERE g.groupuserid = :groupuserid")})
public class Groupuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "groupuserid")
    private Integer groupuserid;
    @JoinColumn(name = "ID", referencedColumnName = "GroupUser_id")
    @ManyToOne(optional = false)
    private Group1 id;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userID;

    public Groupuser() {
    }

    public Groupuser(Integer groupuserid) {
        this.groupuserid = groupuserid;
    }

    public Integer getGroupuserid() {
        return groupuserid;
    }

    public void setGroupuserid(Integer groupuserid) {
        this.groupuserid = groupuserid;
    }

    public Group1 getId() {
        return id;
    }

    public void setId(Group1 id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupuserid != null ? groupuserid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupuser)) {
            return false;
        }
        Groupuser other = (Groupuser) object;
        if ((this.groupuserid == null && other.groupuserid != null) || (this.groupuserid != null && !this.groupuserid.equals(other.groupuserid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.brigreen.planneroftheapes.Groupuser[ groupuserid=" + groupuserid + " ]";
    }
    
}
