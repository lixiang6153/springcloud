package test_project;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

/**
 * Created by winner_0715 on 2016/10/11.
 */
public class FastDFSClient {
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageClient1 storageClient = null;
    private StorageServer storageServer = null;

    public FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);

    }

    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        String result = storageClient.upload_file1(fileName, null, null);
        return result;
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, null);
        return result;
    }

    public boolean deleteFile(String gourp, String fileName) throws IOException, MyException{
    	return 0 == storageClient.delete_file(gourp, fileName);
    }
}
