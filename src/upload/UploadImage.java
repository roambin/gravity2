package upload;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class UploadImage {
	ServletConfig config;
	HttpServletRequest request;
	HttpServletResponse response;
	public UploadImage(ServletConfig config,HttpServletRequest request,HttpServletResponse response){
		this.config=config;
		this.request=request;
		this.response=response;
	}
	public SmartUpload smartUpload(){
		System.out.println("upload>"+this.getClass().getName()+" smartUpload:");
	    //新建一个jsmartUpLoad对象
	    SmartUpload smartUpload = new SmartUpload();
	    //上传初始化
	    try {
			smartUpload.initialize(config,request,response);
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
	    try {
	    		smartUpload.setMaxFileSize(1024*1024*10);//限制每个上传文件的最大长度
	    		smartUpload.setTotalMaxFileSize(1024*1024*20);//限制总上传数据的长度
	    		smartUpload.setAllowedFilesList("jpg,JPG,png,PNG,gif,GIF,jpeg,JPEG");//限制允许上传的文件类型、允许jpg,gif,JPG,png,PNG文件
	    		smartUpload.setDeniedFilesList("exe,jsp,,");//限制禁止上传的文件类型,禁止exe、jsp、和没有扩展名的文件
	    		smartUpload.upload();//上传文件
	    } catch (SmartUploadException e) {
	    		System.out.println("fail>"+this.getClass().getName()+" smartUpload");
	    		e.printStackTrace();
	    } catch (ServletException e) {
	    		System.out.println("fail>"+this.getClass().getName()+" smartUpload");
			e.printStackTrace();
		} catch (IOException e) {
    			System.out.println("fail>"+this.getClass().getName()+" smartUpload");
			e.printStackTrace();
		} catch (SQLException e) {
    			System.out.println("fail>"+this.getClass().getName()+" smartUpload");
			e.printStackTrace();
		}
	    return smartUpload;
	}
	public String saveFile(SmartUpload smartUpload,String path,String id){
		System.out.println("upload>"+this.getClass().getName()+" saveFile:");
		String filename=null,exten=null,fullPath=null;
	    	try {
	    		com.jspsmart.upload.File file=smartUpload.getFiles().getFile(0);
			filename = file.getFileName();
			exten=filename.substring(filename.lastIndexOf("."));
			filename = id+exten;
			file.saveAs(path+filename);
			fullPath=path+filename;
		} catch (SmartUploadException e) {
			System.out.println("fail>"+this.getClass().getName()+" file");
			e.printStackTrace();
		} catch (IOException e) {
	    		System.out.println("fail>"+this.getClass().getName()+" file");
			e.printStackTrace();
		}
	    	return fullPath;
	}
}
