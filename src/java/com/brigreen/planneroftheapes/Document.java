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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByLink", query = "SELECT d FROM Document d WHERE d.link = :link"),
    @NamedQuery(name = "Document.findByDocumentid", query = "SELECT d FROM Document d WHERE d.documentid = :documentid")})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Link")
    private String link;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "documentid")
    private Integer documentid;
    @JoinColumn(name = "id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Documents id;

    public Document() {
    }

    public Document(Integer documentid) {
        this.documentid = documentid;
    }

    public Document(Integer documentid, String link) {
        this.documentid = documentid;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getDocumentid() {
        return documentid;
    }

    public void setDocumentid(Integer documentid) {
        this.documentid = documentid;
    }

    public Documents getId() {
        return id;
    }

    public void setId(Documents id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentid != null ? documentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.documentid == null && other.documentid != null) || (this.documentid != null && !this.documentid.equals(other.documentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.brigreen.planneroftheapes.Document[ documentid=" + documentid + " ]";
    }
    
}
