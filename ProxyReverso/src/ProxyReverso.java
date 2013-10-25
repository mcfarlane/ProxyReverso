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
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.scanners.ScriptDecoder;
import org.htmlparser.scanners.ScriptScanner;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;


 
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
             

         	resposta = cliente.newRequest("http://localhost:8080").method(HttpMethod.GET).send();
         	
         } catch (InterruptedException | ExecutionException | TimeoutException e) {
 			e.printStackTrace();
 			response.getWriter().println(e.toString());
 		}
         
                
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        //resposta.getContent().
        ScriptDecoder decoder = new ScriptDecoder();
        ScriptScanner scanner = new ScriptScanner();
        Lexer nos = new Lexer(resposta.toString());
        NodeList listaNo = new NodeList();
        ParserFeedback fb = null;
        NodeFilter filter = new TagNameFilter("body");
        //scanner.scan(tag, nos, listaNo);
		Parser parse = new Parser(nos, fb);
		response.getWriter().println(parse.getEncoding());
		response.getWriter().println(parse.getVersion());
		response.getWriter().println(parse.getNodeFactory().toString());

		try {
			for (NodeIterator e = parse.elements (); e.hasMoreNodes ();)
			  {
			    	e.nextNode().collectInto(listaNo, filter);
		
			  }
			Node[] node = listaNo.toNodeArray();
			response.getWriter().println(node.length);
			
			response.getWriter().println("entrou");
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//response.getWriter().println(listaNo.toHtml());

    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(9091);
        server.setHandler(new ProxyReverso());
 
        server.start();
        server.join();
        
			
    }
}