package com.Atos.Webaccess.Technical.FTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class SFTPServiceImpl implements FTPService {

	private FTPProperties FTPProperties;

	protected SSHClient client;
	private SFTPClient sftpClient;

	public SFTPServiceImpl(FTPProperties FTPProperties) {
		this.FTPProperties = FTPProperties;
	}

	@Override
	public boolean open() {

		client = new SSHClient();
		client.addHostKeyVerifier(new PromiscuousVerifier());

		try {
			client.connect(FTPProperties.getServer());
			client.authPassword(FTPProperties.getUsername(), FTPProperties.getPassword());

			if (client.isConnected()) {

				sftpClient = client.newSFTPClient();

				return true;

			} else
				return false;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void close() {

	}

	@Override
	public boolean loadFile(String remotePath, String localFile) {
		
		try {
				sftpClient.get(remotePath, localFile);
				sftpClient.close();
				client.disconnect();
				return true;
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveFile(InputStream inputStream, String destPath, boolean append) {
		return false;
	}

	@Override
	public boolean saveFile(String sourcePath, String destPath, boolean append) {

		try {

				sftpClient.put(sourcePath, destPath);
				sftpClient.close();
				client.disconnect();
				return true;
				
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

}
