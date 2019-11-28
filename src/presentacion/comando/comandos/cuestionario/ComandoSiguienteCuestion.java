package presentacion.comando.comandos.cuestionario;

import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoSiguienteCuestion implements Comando {

	@Override
	public Contexto execute(Object datos) {
		
		return new Contexto(Evento.siguienteCuestion, null);
	}

}
