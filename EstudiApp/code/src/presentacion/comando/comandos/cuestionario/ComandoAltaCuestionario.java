package presentacion.comando.comandos.cuestionario;

import negocio.cuestionario.Cuestionario;
import negocio.cuestionario.SACuestionario;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoAltaCuestionario implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia()
				.crearSACuestionario();

		try {
			int respuesta = saCuestionario.alta((Cuestionario) datos);
		
			switch(respuesta) {
			case 0:
				return new Contexto(Evento.altaCuestionarioOK, null);
			case -1:
				return new Contexto(Evento.altaCuestionarioErrorCuestionarioYaExistente, null);
			case -2:
				return new Contexto(Evento.altaCuestionarioErrorCuestionarioConMenosDeDosRespuestas, null);
			case -3:
				return new Contexto(Evento.altaCuestionarioErrorModificacionConcurrente, null);
			default:
				return null;
			}
		} catch (Exception e) {
			return new Contexto(Evento.errorConexionBBDD, null);
		}
	}
}
