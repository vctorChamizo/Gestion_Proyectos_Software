package presentacion.comando.comandos.inicio;

import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoAbrirGUIInicio implements Comando{

	@Override
	public Contexto execute(Object datos) {

		return new Contexto(Evento.abrirGUIInicio, datos);
	}

}
