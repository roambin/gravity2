package util;
import java.net.URI;

public class UriUtil{
	URI uri;
	public UriUtil(URI uri) {
		this.uri=uri;
	}
	public UriUtil() {
		
	}
	public String getParameter(String key) {
		String value=null;
		String query="&"+uri.getQuery();
		int indexKey=query.indexOf("&"+key+"=");
		if(indexKey==-1) {
			value=null;
		}else {
			int indexStart=indexKey+key.length()+2;
			int indexEnd=query.indexOf("&",indexStart);
			if(indexEnd==-1) {
				indexEnd=query.length();
			}
			value=query.substring(indexStart,indexEnd);
		}
		return value;
	}
}
