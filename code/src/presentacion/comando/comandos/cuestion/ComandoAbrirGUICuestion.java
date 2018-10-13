package presentacion.comando.comandos.cuestion;

import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoAbrirGUICuestion implements Comando{

	@Override
	public Contexto execute(Object datos) {

		return new Contexto(Evento.abrirGUIAltaCuestion, datos);
	}

}
