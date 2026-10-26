package com.Atos.Webaccess.Technical.FTP;

import com.Atos.Webaccess.Constants.AppConstants;

public class FTPConficurationFactory {

	
	public  FTPService getFTPConfigiration (String protocol, FTPProperties ftpProperties) 
	{
		switch(protocol) {
		case AppConstants.FTP : return new FTPServiceImpl(ftpProperties);
		case AppConstants.SFTP : return new SFTPServiceImpl(ftpProperties);

		default : return null;
			
		}
	}
}
