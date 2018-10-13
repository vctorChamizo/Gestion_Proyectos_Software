package presentacion.controlador;

public class Contexto {
	private Object datos;
	private Evento evento;
	
	public Contexto(Evento evento, Object datos) {
		this.evento = evento;
		this.datos = datos;
	}
	
	public Object getDatos() {
		return this.datos;
	}
	
	public Evento getEvento() {
		return this.evento;
	}
}
