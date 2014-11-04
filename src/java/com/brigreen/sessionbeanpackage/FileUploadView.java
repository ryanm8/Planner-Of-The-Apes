/*
 * Created by Ryan Miller on 2014.11.01  * 
 * Copyright © 2014 Ryan Miller. All rights reserved. * 
 */
package com.brigreen.sessionbeanpackage;
 

import com.brigreen.jsfclassespackage.AssignmentController;
import com.brigreen.planneroftheapes.Assignment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
     
    private UploadedFile file;
    private String path = "C:\\Users\\Ryan\\Desktop\\";
    
    @ManagedProperty(value="#{assignmentController}")
    private AssignmentController asgController;
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    

    public void upload() throws IOException{
        if(file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
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
        String asg = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(),"#{assignmentUploads}", String.class);
        String tempFileName = path + asg;
        String newFileName = path + asg + "\\" + fileName;
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
    
    
}
