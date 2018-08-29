package com.donwait.file;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * Created by winner_0715 on 2016/10/11.
 */
public class FastDFSClient {
    private static TrackerClient trackerClient = null;
    private static TrackerServer trackerServer = null;
    private static StorageClient1 storageClient = null; 
    private static StorageServer storageServer = null;
    private static String fdfs_file = "classpath:fdfs_client.conf";
    private static Logger logger = Logger.getLogger(FastDFSClient.class);
    
    static{
    	init();
    	connect();
    }
    
    public static void init(){
    	if (fdfs_file.contains("classpath:")) {
        	fdfs_file = fdfs_file.replace("classpath:", FastDFSClient.class.getResource("/").getPath());
        }		
        try {
			ClientGlobal.init(fdfs_file);
		} catch (IOException | MyException e) {
			e.printStackTrace();
		}
    }
    
    public static boolean connect(){
    	try{
    		logger.info(new Date() + " strat connect: " + fdfs_file);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            
            storageServer = null;
            storageClient = new StorageClient1(trackerServer, storageServer);
    	}catch(Exception e){
    		logger.error(new Date() + " connect: " + e.toString());
    		close();
    		return false;
    	}
    	return true;
    }
    
    public static void close(){
		try {
			if(storageServer != null){
				storageServer.close();
			}
			if(trackerServer != null){
				trackerServer.close();
			}
			storageServer = null;
			storageClient = null;
		} catch (IOException e) {
			logger.error(new Date() + " close: " + e.toString());
			e.printStackTrace();
		}
    }

    public synchronized static String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
    	String result = "";
        try{
        	result = storageClient.upload_file1(fileName, extName, metas);
        }catch(Exception e){
        	logger.error(new Date() + " uploadFile: " + e.toString());
        	close();
        	connect();
        }
        return result;
    }
    
    public synchronized static String uploadFile(byte[] file_buff, String extName, NameValuePair[] metas) throws Exception {
    	String result = "";
    	try{
        	result = storageClient.upload_file1(file_buff, extName, metas);
        }catch(Exception e){
        	logger.error(new Date() + " uploadFile: " + e.toString());
        	close();
        	connect();
        }
        return result;
    }

    public synchronized static String uploadFile(String fileName) throws Exception {
    	String result = "";
    	try{
        	result = storageClient.upload_file1(fileName, null, null);
        }catch(Exception e){
        	logger.error(new Date() + " uploadFile: " + e.toString());
        	close();
        	connect();
        }
        return result;
    }

    public synchronized static String uploadFile(String fileName, String extName) throws Exception {
    	String result = "";
    	try{
        	result = storageClient.upload_file1(fileName, extName, null);
        }catch(Exception e){
        	logger.error(new Date() + "  uploadFile: " + e.toString());
        	close();
        	connect();
        }
        return result;
    }
    
    public synchronized static boolean deleteFile(String gourp, String fileName) throws IOException, MyException{
    	try{
    		return 0 == storageClient.delete_file(gourp, fileName);
        }catch(Exception e){
        	logger.error(new Date() + " deleteFile: " + e.toString());
        	close();
        	connect();
        }
        return false;    	 
    }
    public static void main(String[] args) {
    	new FastDFSClient();
	}
}
