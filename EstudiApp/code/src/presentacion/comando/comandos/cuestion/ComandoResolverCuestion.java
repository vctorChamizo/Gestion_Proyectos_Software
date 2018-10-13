package presentacion.comando.comandos.cuestion;

import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoResolverCuestion implements Comando {

	@Override
	public Contexto execute(Object datos) {
		return new Contexto(Evento.resolverCuestion, datos);
	}
}
