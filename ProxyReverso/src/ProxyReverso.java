import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.swing.text.html.parser.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.StringReader;
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
import org.htmlparser.parserapplications.filterbuilder.FilterBuilder;
import org.htmlparser.scanners.ScriptDecoder;
import org.htmlparser.scanners.ScriptScanner;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


 
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
 			
 		}
         
                
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
//        ScriptDecoder decoder = new ScriptDecoder();
//        ScriptScanner scanner = new ScriptScanner();
        Lexer nos = new Lexer(resposta.getContentAsString());
//        
        
        Parser parser;
		try {
			//response.getWriter().println(nos.getCurrentLine());
			//response.getWriter().println("0");
			parser = new Parser(nos);
			response.getWriter().println(parser.getEncoding());
			NodeFilter filter = new TagNameFilter("html");
	        NodeList list = parser.parse(filter).extractAllNodesThatMatch(filter);
	        response.getWriter().println(list.size());
	 
	      //  list.elements().nextNode().getFirstChild().setParent();
	        response.getWriter().println(list.toHtml());
	        Node no = list.elements().nextNode();
	        
	        //list.remove(no);
	        response.getWriter().println(no.getFirstChild().getText());
	        
//	      	for (NodeIterator e = parser.elements (); e.hasMoreNodes ();)
//				  
//				    	e.nextNode().collectInto(list, filter);
//			
//				  }
//				Node[] node = list.toNodeArray();
//				response.getWriter().println(node.length);
//				
//				response.getWriter().println(node[0].toString());
		} catch (ParserException e) {
			((Throwable) e).printStackTrace();
		}
        
        
//        NodeList listaNo = new NodeList();
//        ParserFeedback fb = null;
//        NodeFilter filter = new TagNameFilter("body");
//        //scanner.scan(tag, nos, listaNo);
//		Parser parse = new Parser(nos, fb);
//		response.getWriter().println(parse.getEncoding());
//		response.getWriter().println(parse.getVersion());
//		response.getWriter().println(parse.getNodeFactory().toString());

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setValidating(false);
//        dbf.setNamespaceAware(true);
//        dbf.setIgnoringComments(false);
//        dbf.setIgnoringElementContentWhitespace(false);
//        dbf.setExpandEntityReferences(false);
//        
//        
//        DocumentBuilder db;
//		try {
//			db = dbf.newDocumentBuilder();
//			db.parse(new InputSource(resposta.getContentAsString()));
//			
//			response.getWriter().println(db.getSchema());
//			response.getWriter().println(db.toString());
//			
//			
//			
//			
//			
//			
//		} catch (ParserConfigurationException | SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//        
//		try {
//			for (NodeIterator e = parse.elements (); e.hasMoreNodes ();)
//			  {
//			    	e.nextNode().collectInto(listaNo, filter);
//		
//			  }
//			Node[] node = listaNo.toNodeArray();
//			response.getWriter().println(node.length);
//			
//			response.getWriter().println("entrou");
//		} catch (ParserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//response.getWriter().println(listaNo.toHtml());

    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(9091);
        server.setHandler(new ProxyReverso());
 
        server.start();
        server.join();
        
			
    }
}