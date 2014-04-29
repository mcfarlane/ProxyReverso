package proxy;

import org.eclipse.jetty.http.HttpFields;

public class Dados{

	byte[] content;
	HttpFields head;
	boolean alterado;
	
	

	public Dados(byte[] bs, HttpFields head){
		setContent(bs);
		setHead(head);
		
	}
	
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public HttpFields getHead() {
		return head;
	}
	public void setHead(HttpFields head) {
		this.head = head;
	}
	public boolean isAlterado() {
		return alterado;
	}
	public void setAlterado(boolean alterado) {
		this.alterado = alterado;
	}
	
	
}
