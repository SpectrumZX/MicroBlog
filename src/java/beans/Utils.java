package beans;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;

@ManagedBean
@Stateful
public class Utils {
    
private String path = "MicroBlog";    //  имя домена для правильного формирования линков  http: //localhost:8080/  #{path}  /faces/admin/index.xhtml
private int preview_length = 10;  // количество символов в превью статьи


    public String getPath() {
        return path;
    }
    
      public int getPreviewLength() {
        return preview_length;
    }  

  
    public String md5(String text){
	try{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes());
		String hash = new BigInteger(1, md.digest()).toString(16);
		while(hash.length() < 32) hash = "0" + hash;
		return hash;
            }
        catch(NoSuchAlgorithmException e){ e.printStackTrace(); }
        
	return "";
}

}
