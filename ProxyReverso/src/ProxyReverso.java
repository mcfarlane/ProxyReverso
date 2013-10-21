import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.*;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;


 
public class ProxyReverso extends AbstractHandler
{
	ContentResponse resposta;
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {  
    	 HttpClient cliente = new HttpClient();
    	 try {
			cliente.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         //Request req = HttpClient.newRequest("teste");
         try {
             

         	resposta = cliente.newRequest("http://localhost:8080").method(HttpMethod.HEAD).agent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:17.0) Gecko/20100101 Firefox/17.0").send();
         	
         	for (int i = 0; i < 9; i++) {
				
			
         	response.getWriter().println(resposta.getHeaders().getField(i).toString());
         	}
         } catch (InterruptedException | ExecutionException | TimeoutException e) {
 			e.printStackTrace();
 			response.getWriter().println(e.toString());
 		}
         
                
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println(resposta.getContentAsString());

    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(9091);
        server.setHandler(new ProxyReverso());
 
        server.start();
        server.join();
        
			
    }
}