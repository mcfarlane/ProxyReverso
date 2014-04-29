package proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ProxyReverso extends AbstractHandler {

	HttpClient client;
	static ArrayList<APlugin> classes = new ArrayList<APlugin>();
	private static String url = "http://localhost:80";
	private static Dados dados;
	static int porta = 9090;
	static String pathPlugin = "/home/luciano/git/ProxyReverso/ProxyReverso/bin/plugin/";

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Event event = new Event();

		if (request.getMethod().equals("POST")) {
			event.lePlugins();
			event.doPost(request, response, baseRequest);
		} else {
			event.lePlugins();
			event.doGet(request, response, baseRequest);
		}
	}

	static class Event extends HttpServlet {
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response, Request baseRequest)
				throws ServletException, IOException {
			HttpClient cliente = new HttpClient();
			ContentResponse resposta = null;
			System.out.println("GET");

			try {
				cliente.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				System.out.println(request.getRequestURI());
				resposta = cliente.newRequest(url + request.getRequestURI())
						.method(HttpMethod.GET).send();

				/*
				 * HttpFields headers = resposta.getHeaders();
				 * 
				 * // headers.remove("Server");
				 * 
				 * Iterator<HttpField> iterator = headers.iterator();
				 * 
				 * while (iterator.hasNext()) { HttpField field =
				 * iterator.next(); // System.out.println("Nome: " +
				 * field.getName() + " Valor: "+ field.getValue()); //if
				 * (field.getName().equals("Content-Length")) //
				 * response.setHeader(field.getName(), "500"); ///else {
				 * 
				 * response.setHeader(field.getName(), field.getValue());
				 * 
				 * //} }
				 */
				System.out.println("antes");

				dados = new Dados(resposta.getContent(), resposta.getHeaders(), null);
				System.out.println("depois");

				/*
				 * byte[] respEmBytes = resposta.getContent();
				 * 
				 * System.out.println(new String(respEmBytes));
				 * 
				 * ServletOutputStream outputStream =
				 * response.getOutputStream();
				 * 
				 * outputStream.write(respEmBytes);
				 * 
				 * System.out.println(new String(respEmBytes));
				 */
			} catch (InterruptedException | TimeoutException
					| ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			
//			if (classes.size() > 0) {
//				classes.get(0).processa(dados);
//			}
//			System.out.println("Treminou");

			Iterator<HttpField> iterator = dados.getHead().iterator();

			while (iterator.hasNext()) {
				HttpField field = iterator.next();
				System.out.println("Nome: " + field.getName() + " Valor: "
						+ field.getValue());
					response.setHeader(field.getName(), field.getValue());
			}
			
			System.out.println(response.getHeader("Server"));
			
			response.setStatus(HttpServletResponse.SC_OK);
			baseRequest.setHandled(true);
			try {
				System.out.println("Responra");
				System.out.println(dados.getContentAsString());
				
				response.getWriter().println(dados.getContentAsString());
				System.out.println("Respondeu");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		protected void doPost(HttpServletRequest request,
				HttpServletResponse response, Request baseRequest) {
			System.out.println("POST");
			ContentResponse resposta;
			HttpClient cliente = new HttpClient();

			try {
				cliente.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			resposta = null;
			try {
				resposta = cliente.newRequest(url + request.getRequestURI())
						.method(HttpMethod.POST).send();
			} catch (InterruptedException | TimeoutException
					| ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dados = new Dados(resposta.getContent(), resposta.getHeaders(), null);
			
			if (classes.size() > 0) {
				classes.get(0).processa(dados);
			}
			System.out.println("Treminou");
			

			Iterator<HttpField> iterator = dados.getHead().iterator();

			while (iterator.hasNext()) {
				HttpField field = iterator.next();
				System.out.println("Nome: " + field.getName() + " Valor: "
						+ field.getValue());
				if (field.getName().equals("Content-Length"))
					response.setHeader(field.getName(), "500");
				else {
					response.setHeader(field.getName(), field.getValue());

				}
			}
			try {
				System.out.println("Respondeu");
				System.out.println(dados.getContent());
				byte[] bytes = dados.getContent();
				System.out.println("Tamanho do conteudo= "+bytes.length );
				for (int i = 0; i < bytes.length; i++) {
					
					System.out.println(bytes[i]);
				}
				response.getWriter().println(dados.getContent().toString());
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(response.getHeader("Server"));
			

				
			response.setStatus(HttpServletResponse.SC_OK);
			baseRequest.setHandled(true);

			// PrintWriter out = response.getWriter();
			// out.println(response.getOutputStream());
			// out.println("Bem Vindo");
			// out.close();

		}

		private void lePlugins() {
			System.out.println("Lendo");

			String[] arquivos;
			System.out.println("File");

			File files = new File(pathPlugin);
			System.out.println("List");
			classes.clear();
			if (files.exists() && files.isDirectory()) {
				arquivos = files.list();
				System.out.println("Listou");

				System.out.println("Tamanho: " + arquivos.length);

				for (int i = 0; i < arquivos.length; i++) {
					System.out.println("Fazendo Split... " + arquivos[i]);
					String[] nomeClasse = arquivos[i].split("\\.");

					System.out.println(nomeClasse.length);
					if (nomeClasse.length > 0) {
						System.out.println(nomeClasse[0]);
						APlugin classe = null;
						try {
							classe = (APlugin) Class.forName(
									"plugin." + nomeClasse[0]).newInstance();
						} catch (InstantiationException
								| IllegalAccessException
								| ClassNotFoundException e) {
							// TODO Auto-generated catch block
							System.out
									.println("Erro ao instancias as classes dos plugins");
						}
						System.out.println("Carregou as classes ");
						classes.add(classe);
					}else{
						System.out.println("Não foi possivel carregar os nomes das classes");
					}

				}

				if (classes.size() > 0) {
					for (int i = 1; i < classes.size(); i++) {
						classes.get(i - 1).setNext(classes.get(i));

					}
				} else {
					System.out.println("Nenhuma classe de plugin carregada ");

				}
			} else {
				System.out
						.println("Caminho do diretório de plugins não encontrado");
			}
		}

	}

	public static void main(String[] args) throws Exception {
		Server server = new Server(porta);
		server.setHandler((Handler) new ProxyReverso());

		server.start();
		server.join();

	}
}