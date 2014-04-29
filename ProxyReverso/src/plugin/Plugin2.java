package plugin;

import proxy.APlugin;
import proxy.Dados;
import proxy.IPlugin;

public class Plugin2 extends APlugin implements IPlugin {

	public Plugin2() {
		super();
		// TODO Auto-generated constructor stub
	}

		@Override
	public int getIdPlugin() {
		// TODO Auto-generated method stub
		return 0;
	}

		@Override
		public void processa(Dados dados) {
			System.out.println("Plugin 2 Ida");

			if(next != null){
				System.out.println("Mandou para o proximo");
				next.processa(dados);
			}
				System.out.println("Plugin 2 volta");
			
		}

}
