package com.gubs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * A program that demonstrates how to upload files from local computer to a remote FTP server using Apache
 * Commons Net API.
 * 
 * @author www.codejava.net
 */

public class FTPUtils {
  public static void main(String[] args) {
    String server = "test.rebex.net";
    int port = 21;
    String user = "demo";
    String pass = "password";

    FTPClient ftpClient = new FTPClient();
    try {

      ftpClient.connect(server, port);
      ftpClient.login(user, pass);
      ftpClient.enterLocalPassiveMode();

      ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
      ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

      // APPROACH #1: uploads first file using an InputStream
      File firstLocalFile = new File("/tmp/0024/GubsTestSEMJUnitFTPDetails_as_of_date_2014-06-13_15-15-24.csv");

      String firstRemoteFile = "/pub/test/dummy.csv";
      InputStream inputStream = new FileInputStream(firstLocalFile);

      System.out.println("Start uploading first file");
      boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
      inputStream.close();
      if (done) {
        System.out.println("The first file is uploaded successfully.");
      }

      boolean completed = ftpClient.completePendingCommand();
      if (completed) {
        System.out.println("The second file is uploaded successfully.");
      }

    } catch (IOException ex) {
      System.out.println("Error: " + ex.getMessage());
      ex.printStackTrace();
    } finally {
      try {
        if (ftpClient.isConnected()) {
          ftpClient.logout();
          ftpClient.disconnect();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
}
