package presentacion.comando;

import presentacion.controlador.Contexto;

public interface Comando {
	public Contexto execute(Object datos);
}
