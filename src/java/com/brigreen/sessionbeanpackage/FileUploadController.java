/*
 * Created by Brian Green on 2014.11.02  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.brigreen.sessionbeanpackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadController")

public class FileUploadController {

    UploadedFile file;

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "uploaded" + File.separator + file.getFileName();
        System.out.println(newFileName);
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        try {
            FileOutputStream fos = new FileOutputStream(new File(newFileName));
            InputStream is = file.getInputstream();
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];
            int a;
            while (true) {
                a = is.read(buffer);
                if (a < 0) {
                    break;
                }
                fos.write(buffer, 0, a);
                fos.flush();
            }
            fos.close();
            is.close();
        } catch (IOException e) {
        }
    }
}
//public class FileUploadController {
//   private String destination="C:\\tmp\\";
// 
//    public void upload(FileUploadEvent event) {  
//        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        // Do what you want with the file        
//        try {
//            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
// 
//    }  
// 
//    public void copyFile(String fileName, InputStream in) {
//           try {
//              Path currentRelativePath = Paths.get("");
//              String s = currentRelativePath.toAbsolutePath().toString();
//              System.out.println("Current relative path is: " + s);
//              System.out.println(FacesContext
//                .getCurrentInstance()
//                .getApplication()
//                .evaluateExpressionGet(FacesContext.getCurrentInstance(),
//                        "#{str}", String.class));
//              String path = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(),"#{str}", String.class);
//              destination =  s + "/img/" ;
//                // write the inputStream to a FileOutputStream
//                OutputStream out = new FileOutputStream(new File(destination + fileName));
//              
//                int read = 0;
//                byte[] bytes = new byte[1024];
//              
//                while ((read = in.read(bytes)) != -1) {
//                    out.write(bytes, 0, read);
//                }
//              
//                in.close();
//                out.flush();
//                out.close();
//              
//                System.out.println("New file created!");
//                } catch (IOException e) {
//                System.out.println(e.getMessage());
//                }
//    }
//}
