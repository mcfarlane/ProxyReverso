package proxy;



public interface IPlugin {
	//parametros
	public int idPlugin = 0;
	
	//métodos
	public void processa(Dados dados);
	
	
	int getIdPlugin();

}
