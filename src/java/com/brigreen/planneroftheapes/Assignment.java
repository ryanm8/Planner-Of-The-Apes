/*
 * Created by Brian Green on 2014.10.31  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.brigreen.planneroftheapes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Brian
 */
@Entity
@Table(name = "assignment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assignment.findAll", query = "SELECT a FROM Assignment a"),
    @NamedQuery(name = "Assignment.findById", query = "SELECT a FROM Assignment a WHERE a.id = :id"),
    @NamedQuery(name = "Assignment.findByDueDate", query = "SELECT a FROM Assignment a WHERE a.dueDate = :dueDate"),
    @NamedQuery(name = "Assignment.findByPriority", query = "SELECT a FROM Assignment a WHERE a.priority = :priority"),
    @NamedQuery(name = "Assignment.findByProgress", query = "SELECT a FROM Assignment a WHERE a.progress = :progress"),
    @NamedQuery(name = "Assignment.findByClass1", query = "SELECT a FROM Assignment a WHERE a.class1 = :class1"),
    @NamedQuery(name = "Assignment.findByDifficulty", query = "SELECT a FROM Assignment a WHERE a.difficulty = :difficulty"),
    @NamedQuery(name = "Assignment.findByNotes", query = "SELECT a FROM Assignment a WHERE a.notes = :notes"),
    @NamedQuery(name = "Assignment.findByType", query = "SELECT a FROM Assignment a WHERE a.type = :type"),
    @NamedQuery(name = "Assignment.findByDocumentPath", query = "SELECT a FROM Assignment a WHERE a.documentPath = :documentPath"),
    @NamedQuery(name = "Assignment.findByAssignmentName", query = "SELECT a FROM Assignment a WHERE a.assignmentName = :assignmentName"),
    @NamedQuery(name = "Assignment.findByStarred", query = "SELECT a FROM Assignment a WHERE a.starred = :starred")})
public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Due_Date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "Priority")
    private String priority;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "Progress")
    private String progress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Class")
    private String class1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "Difficulty")
    private String difficulty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 127)
    @Column(name = "Notes")
    private String notes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Document_Path")
    private String documentPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Assignment_Name")
    private String assignmentName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Starred")
    private int starred;
    @JoinColumn(name = "Assignee_ID", referencedColumnName = "ID")
    @ManyToOne
    private User assigneeID;
    @JoinColumn(name = "Documents_id", referencedColumnName = "ID")
    @ManyToOne
    private Documents documentsid;
    @JoinColumn(name = "Group_id", referencedColumnName = "ID")
    @ManyToOne
    private Group1 groupid;
    @Transient
    private UploadedFile file;
    @Transient
    private String path = "/home/CS4704/planneroftheapes/Documents/";

    public Assignment() {
    }

    public Assignment(Integer id) {
        this.id = id;
    }

    public Assignment(Integer id, Date dueDate, String priority, String progress, String class1, String difficulty, String notes, String type, String documentPath, String assignmentName, int starred) {
        this.id = id;
        this.dueDate = dueDate;
        this.priority = priority;
        this.progress = progress;
        this.class1 = class1;
        this.difficulty = difficulty;
        this.notes = notes;
        this.type = type;
        this.documentPath = documentPath;
        this.assignmentName = assignmentName;
        this.starred = starred;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UploadedFile getFile()
    {
        return file;
    }
    
    public void setFile(UploadedFile newFile)
    {
        file = newFile;
    }
    
    public void removeDocument()
    {
        if (file != null)
        {
            File newFile = new File(path + id + "/" + documentPath);
            if (newFile.delete())
            {
                System.out.println("FILE DELETED GOOD JOB!");
            }
            else
            {
                System.out.println("FILE STILL THERE. NO GOOD.");
            }
            documentPath = "Add new doc?";
            
        }
    }
    public void upload() throws IOException{
        if (!documentPath.equals("Add New Doc?"))
        {
            removeDocument();
        }
        if(file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            documentPath = file.getFileName();
             try {
             copyFile(file.getFileName(), file.getInputstream());
             } catch (IOException e) {
             e.printStackTrace();
             } 
        }
    }

    private void copyFile(String fileName, InputStream inputstream) {
        // write the inputStream to a FileOutputStream
        try {
        String tempFileName = path + id;
        String newFileName = path + id + "/" + fileName;
        File tempFile = new File(tempFileName);
        tempFile.mkdirs();
        OutputStream out = new FileOutputStream(new File(newFileName));

        
//        ExternalContext tmpEC;
//        Map sMap;
//        tmpEC = FacesContext.getCurrentInstance().getExternalContext();
//        sMap = tmpEC.getSessionMap();
//        AssignmentController asgController = (AssignmentController) sMap.get("assignmentController");
//        Assignment asg1 = asgController.getAssignment(Integer.parseInt(asg));
//        asg1.setDocumentPath(asg + "\\" + fileName);
//        asgController.setSelected(asg1);
//        asgController.update();
        
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputstream.read(bytes)) != -1) {
             out.write(bytes, 0, read);
         }

        inputstream.close();
        out.flush();
        out.close();

        System.out.println("New file created!");
        } catch (IOException e) {
        System.out.println(e.getMessage());
        }
    }
    
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getStarred() {
        return starred;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public User getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(User assigneeID) {
        this.assigneeID = assigneeID;
    }

    public Documents getDocumentsid() {
        return documentsid;
    }

    public void setDocumentsid(Documents documentsid) {
        this.documentsid = documentsid;
    }

    public Group1 getGroupid() {
        return groupid;
    }

    public void setGroupid(Group1 groupid) {
        this.groupid = groupid;
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
        if (!(object instanceof Assignment)) {
            return false;
        }
        Assignment other = (Assignment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.brigreen.planneroftheapes.Assignment[ id=" + id + " ]";
    }
    
}
