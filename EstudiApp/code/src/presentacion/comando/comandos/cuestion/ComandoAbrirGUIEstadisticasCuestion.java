package presentacion.comando.comandos.cuestion;

import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoAbrirGUIEstadisticasCuestion implements Comando {

	@Override
	public Contexto execute(Object datos) {
		return new Contexto(Evento.abrirGUIEstadisticasCuestion, datos);
	}

}
