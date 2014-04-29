package plugin;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import proxy.APlugin;
import proxy.Dados;
import proxy.IPlugin;


public class Plugin1 extends APlugin implements IPlugin {
	public Plugin1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	static Dados dados;

	@Override
	public void processa(Dados dadosRec){
		dados = dadosRec;
		System.out.println("Plugin 1 Ida");
		/*
		 Lexer nos = new Lexer(dados.toString().replaceAll("\n", "").replaceAll("\t", ""));
	        
	        Parser parser;
			try {
	            parser = new Parser(nos);
				NodeFilter filter = new TagNameFilter("html");
		        NodeList list = parser.parse(filter).extractAllNodesThatMatch(filter);
		        
		        String[][] resultSet = new String[list.size()][2];
			
		        for (int i=0;i<list.size();i++) {
	                Node n = list.elementAt(i);
	                resultSet[i][0]=n.toPlainTextString().trim();
		            resultSet[i][1]=null;
		            Node c = n.getLastChild();
		            while( c!=null ) {
		            	System.out.println( c.toString());
		            	if( c instanceof BodyTag ) {
		            		System.out.println("IF");
		                    resultSet[i][1] = ((BodyTag) c).getTagName();
		                    ((BodyTag) c).setTagName("Head");
		                    Node end = ((BodyTag) c).getEndTag();
		                    Tag endBody= new HeadTag();
		                    endBody.setTagName("/Head");
		                    ((BodyTag) c).setEndTag(endBody);
		                    //((Tag) end).setEndTag(endBody);
		                    System.out.println(end.getText());
		                    break;
		                }
		                c.getFirstChild();
		            }
		            dados.setContent(c.getText().getBytes());
		            dados.setAlterado(true);
		        } 
			} catch (ParserException e) {
				((Throwable) e).printStackTrace();
			}
			*/
		System.out.println("Plugin 1 Ida");

		if(next != null){
			System.out.println("Mandou para o proximo");
			next.processa(dados);
		}
			System.out.println("Plugin 1 volta");
	}

	@Override
	public int getIdPlugin() {
		// TODO Auto-generated method stub
		return 0;
	}


}
	