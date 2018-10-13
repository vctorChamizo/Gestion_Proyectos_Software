package presentacion.comando.comandos.inicio;

import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoAbrirGUIMenuPrincipal implements Comando{

	@Override
	public Contexto execute(Object datos) {

		return new Contexto(Evento.abrirGUIMenuPrincipal, datos);
	}

}
