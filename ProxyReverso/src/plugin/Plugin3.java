package plugin;

import proxy.APlugin;
import proxy.Dados;

public class Plugin3 extends APlugin{

	public Plugin3() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processa(Dados dados) {
		System.out.println("Plugin 3 Ida");

		if(next != null){
			System.out.println("Mandou para o proximo");
			next.processa(dados);
		}
			System.out.println("Plugin 3 volta");
				
	}

	
}
