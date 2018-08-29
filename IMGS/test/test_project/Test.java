package test_project;

public class Test {
	
	public static void main(String[] args) {
		try {
			FastDFSClient client = new FastDFSClient("classpath:fdfs_client.conf");
			String uploadFile = client.uploadFile("D:\\Pictures\\test1.png", "jpg", null);
		    System.out.println(uploadFile);
		    
//		    String fileRelativePath = "group1/M00/00/00/wKgMGVpS_IKAb8H5AAAAHjGinYM407.txt";
//		    if(fileRelativePath.startsWith("/group")){
//				fileRelativePath = fileRelativePath.substring(1);
//			}
//			
//			int pos = fileRelativePath.indexOf("/");
//			String groupName = fileRelativePath.substring(0, pos);
//			String subPath = fileRelativePath.substring(pos+1);
//			
//			// 从FastDFS中删除对应文件
//			if(client.deleteFile(groupName, subPath)){
//				System.out.println("删除文件成功...");
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
}
