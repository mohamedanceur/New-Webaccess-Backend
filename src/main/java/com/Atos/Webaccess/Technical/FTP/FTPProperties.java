package com.Atos.Webaccess.Technical.FTP;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.stereotype.Service;



public class FTPProperties {
	
	private static final String ENABLE_FTP = "enableFtp";
	private static final String PROTOCOL = "protocol";
	private static final String PORT = "port";
	private static final String SERVER = "server";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String KEEP_ALIVE_TIME_OUT = "keepAliveTimeout";
	private static final String AUTO_START = "autoStart";

    private String protocol;
    private String server;
    private String username;
    private String password;
    @Min(0)
    @Max(65535)
    private int port;
    private int keepAliveTimeout;
    private boolean autoStart;
    private int enableFtp;

    
    
    public FTPProperties(String prefix ) {
    		readProp(prefix);
	}


	@PostConstruct
    public void init() {
        if (port == 0) {
            port = 21;
        }
    }
    
    
    


	public int getEnableFtp() {
		return enableFtp;
	}


	public void setEnableFtp(int enableFtp) {
		this.enableFtp = enableFtp;
	}


	public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public void setKeepAliveTimeout(int keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }
    
    
    
    public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	public  void readProp(String prefix) {
    	
    	
		 try {
		    Properties prop = new Properties();
		    prop.load(FTPProperties.class.getResourceAsStream("/application.properties"));

		    Enumeration enuKeys = prop.keys();
		        while (enuKeys.hasMoreElements()) {
		                String key = (String) enuKeys.nextElement();
		                String value = prop.getProperty(key);
		                if (key.startsWith(prefix) ) {
		                			            
		                	switch (key.substring(prefix.length()+1)) {
		                	case PORT : 		            	
		                	 {port=Integer.valueOf(value);  break;}
		                	case SERVER : 
		                	{ server=value;     break;}
		                	case PROTOCOL : 
		                	{ protocol=value;     break;}
		                	case USERNAME : 
		                	{ username=value;     break;}
		                	case PASSWORD : 
		                	{ password=value;     break;}
		                	case KEEP_ALIVE_TIME_OUT : 
		                	{ keepAliveTimeout=Integer.valueOf(value);     break;}
		                	case AUTO_START : 
		                	{ 
		                		if(value.equals("true"))
		                			autoStart=true;
		                		else 
		                			autoStart=false;
		                			break;}
		                	case ENABLE_FTP : {
		                	enableFtp = Integer.valueOf(value);     break;
		                	}
		                	default : {break;}
		                	}
		                	
		                	
		                }
		            }
		        
		            } catch (FileNotFoundException e) {
		            //System.out.print("System cannot find file");
		                e.printStackTrace();
		            } catch (IOException e) {
		                //System.out.print("System cannot find file");
		                e.printStackTrace();
		            }
	}
    
}
