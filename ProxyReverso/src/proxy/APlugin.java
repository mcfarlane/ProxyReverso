package proxy;


public abstract class APlugin {

	protected APlugin next;
	
	public APlugin() {
        next = null;
        
    }
	
	public void setNext(APlugin plugin) {
        if (next == null) {
            next = plugin;
        } else {
            next.setNext(plugin);
        }
    }
	public abstract void processa(Dados dados);
 
}
