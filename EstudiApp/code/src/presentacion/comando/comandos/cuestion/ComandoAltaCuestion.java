package presentacion.comando.comandos.cuestion;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoAltaCuestion implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia()
				.crearSACuestion();
	
		try {
			int respuesta = saCuestion.alta((Cuestion) datos);
			
			switch(respuesta) {
				case 0:
					return new Contexto(Evento.altaCuestionOK, null);
				case -1:
					return new Contexto(Evento.altaCuestionErrorCuestionYaExistente, null);
				case -2:
					return new Contexto(Evento.altaCuestionErrorCuestionConMenosDeDosRespuestas, null);
				case -3:
					return new Contexto(Evento.altaCuestionErrorModificacionConcurrente, null);
				case -4:
					return new Contexto(Evento.altaCuestionErrorCuestionTiempoMinimoNegativo, null);
				default:
					return null;
			}
		} catch (Exception e) {
			return new Contexto(Evento.errorConexionBBDD, null);
		}
	}
}
