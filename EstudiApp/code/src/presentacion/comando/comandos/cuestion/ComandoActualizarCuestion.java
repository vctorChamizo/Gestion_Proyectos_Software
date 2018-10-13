package presentacion.comando.comandos.cuestion;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoActualizarCuestion implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		
		try {
			Cuestion cuestion = (Cuestion) datos;
			int respuesta = saCuestion.actualizar(cuestion);
			
			switch(respuesta) {
			case 0:
				return new Contexto(Evento.actualizarCuestionOK, null);
				
			case -1:
				return new Contexto(Evento.actualizarCuestionNoExistente, null);
				
			case -3:
				return new Contexto(Evento.actualizarCuestionModificacionConcurrente, null);
				
				default:
					return null;
			}
			
		} catch (Exception e) {
			return new Contexto(Evento.errorConexionBBDD, null);
		}
	}

}
