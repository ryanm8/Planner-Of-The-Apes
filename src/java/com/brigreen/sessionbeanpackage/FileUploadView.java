/*
 * Created by Ryan Miller on 2014.11.01  * 
 * Copyright © 2014 Ryan Miller. All rights reserved. * 
 */
package com.brigreen.sessionbeanpackage;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
     
    private UploadedFile file;
    private String path = "assignments/";
 
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
        OutputStream out = new FileOutputStream(new File(path + fileName));

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
