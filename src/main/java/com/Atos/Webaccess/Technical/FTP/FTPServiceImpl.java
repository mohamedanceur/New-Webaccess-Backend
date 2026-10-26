package com.Atos.Webaccess.Technical.FTP;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FTPServiceImpl implements FTPService {
	

   // private static final Logger logger = LoggerFactory.getLogger(FTPServiceImpl.class);
    
    private FTPProperties FTPProperties ;

    protected FTPClient ftpClient;

    public  FTPServiceImpl(FTPProperties FTPProperties) {
        this.FTPProperties = FTPProperties;
    } 
    
    
    @PostConstruct
    public void init() {
        if (this.FTPProperties.isAutoStart()) {
            this.open();
        }
    }

    public boolean open() {
     //   close();
        ftpClient = new FTPClient();
        boolean loggedIn = false;
        try {
        	ftpClient.connect(FTPProperties.getServer(), FTPProperties.getPort());

            loggedIn = ftpClient.login(FTPProperties.getUsername(), FTPProperties.getPassword());
          

            if (FTPProperties.getKeepAliveTimeout() > 0)
                ftpClient.setControlKeepAliveTimeout(FTPProperties.getKeepAliveTimeout());
            
        } catch (Exception e) {
        	System.out.println(e);
        }
        return loggedIn;
    }

    public void close() {
        if (ftpClient != null) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
			e.printStackTrace();
			}
        }
    }

    public boolean loadFile(String remotePath, String localFile) {
        try {
            return ftpClient.retrieveFile(remotePath, new FileOutputStream(new File(localFile)));
            
        } catch (IOException e) {
        	
            return false;
        }
    }

    public boolean saveFile(InputStream inputStream, String destPath, boolean append) {
        try {
        	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        	System.out.println(destPath);
            if(append) {
                ftpClient.appendFile(destPath, inputStream);
	            inputStream.close();
	            return true;
            }else {
            	ftpClient.deleteFile(destPath);
                ftpClient.storeFile(destPath, inputStream);
                inputStream.close();
                return true;
            }
            
        } catch (IOException e) {
        	e.printStackTrace();
            return false;
        }
    }

    public boolean saveFile(String sourcePath, String destPath, boolean append) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(sourcePath));
        } catch (IOException e) {
        	e.printStackTrace();
            return false;
        }
        return this.saveFile(inputStream, destPath, append);
    }

    public boolean isConnected() {
        boolean connected = false;
        if (ftpClient != null) {
            try {
                connected = ftpClient.sendNoOp();
            } catch (Exception e) {
              //  logger.error(e.getMessage(), e);
            }
        }
        //logger.debug("Checking for connection to FTP server. Is connected: " + connected);
        return connected;
    }


}
