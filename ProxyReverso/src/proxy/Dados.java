package proxy;

import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

import org.eclipse.jetty.http.HttpFields;

public class Dados{

	byte[] content;
	HttpFields headers;
	boolean alterado;
    private String encoding;

	
	

	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public Dados(byte[] bs, HttpFields head,String encoding){
		setContent(bs);
		setHead(head);
		setEncoding(encoding);
			
	}
	
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public HttpFields getHead() {
		return headers;
	}
	public void setHead(HttpFields head) {
		this.headers = head;
	}
	public boolean isAlterado() {
		return alterado;
	}
	public void setAlterado(boolean alterado) {
		this.alterado = alterado;
	}
	
	 public String getContentAsString()
	    {
	        String encoding = this.encoding;
	        try
	        {
	            return new String(getContent(), encoding == null ? "UTF-8" : encoding);
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            throw new UnsupportedCharsetException(encoding);
	        }
	    }

	
	
}
