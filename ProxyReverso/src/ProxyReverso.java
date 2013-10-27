import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.swing.text.html.parser.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;
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
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;
import org.w3c.dom.Document;
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
         try {
             

         	resposta = cliente.newRequest("http://localhost:8080").method(HttpMethod.GET).send();
         	
         } catch (InterruptedException | ExecutionException | TimeoutException e) {
 			e.printStackTrace();
 			
 		}
         
                
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        Lexer nos = new Lexer(resposta.getContentAsString().replaceAll("\n", "").replaceAll("\t", ""));
        
        Parser parser;
		try {
            //System.out.println("TRY");

			parser = new Parser(nos);
			response.getWriter().println(parser.getEncoding());
			NodeFilter filter = new TagNameFilter("html");
	        NodeList list = parser.parse(filter).extractAllNodesThatMatch(filter);
	        response.getWriter();
	        response.getWriter().println(list.size());
	        response.getWriter().println(list.toHtml().trim());
	        
	        String[][] resultSet = new String[list.size()][2];
		
	        for (int i=0;i<list.size();i++) {
                Node n = list.elementAt(i);
                response.getWriter().println(n); // DEBUG remove me!
	            resultSet[i][0]=n.toPlainTextString().trim();
	            resultSet[i][1]=null;
	            Node c = n.getLastChild();
	            response.getWriter().println(c.toHtml());
	            while( c!=null ) {
	            	//System.out.println( c.toString());
	            	if( c instanceof BodyTag ) {
	            		System.out.println("IF");
	                	response.getWriter().println(c.getClass().getName());
	                    resultSet[i][1] = ((BodyTag) c).getTagName();
	                    ((BodyTag) c).setTagName("Head");
	                    Node end = ((BodyTag) c).getEndTag();
	                    //((BodyTag) end).setEndTag(new Tag().setTagName("head"));
	                    System.out.println(end.toString());

	                    
	                    break;
	                }
	                c.getFirstChild();
	            }
	            response.getWriter().println(c.toHtml());
	            response.getWriter().println(i+" text :"+resultSet[i][0]); // DEBUG remove me!
	            response.getWriter().println(i+" link :"+resultSet[i][1]); // DEBUG remove me!
	        } 
		
		} catch (ParserException e) {
			((Throwable) e).printStackTrace();
		}

    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(9091);
        server.setHandler(new ProxyReverso());
 
        server.start();
        server.join();
        
			
    }
}