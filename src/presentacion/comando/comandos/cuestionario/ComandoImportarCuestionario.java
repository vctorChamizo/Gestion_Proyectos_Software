package presentacion.comando.comandos.cuestionario;

import javafx.util.Pair;
import negocio.cuestionario.SACuestionario;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoImportarCuestionario implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();
		Integer respuesta;
		
		Pair<String, String> data = (Pair<String, String>) datos;
			
		respuesta = saCuestionario.importar(data.getKey(), data.getValue());
		switch(respuesta) {
		case 0:
			return new Contexto(Evento.importarCuestionarioOK, null);

		case -1:
			return new Contexto(Evento.importarCuestionarioErrorLecturaFichero, null);
			
		case -2:
			return new Contexto(Evento.importarCuestionarioErrorNombreCuestionarioYaExiste, null);

		case -5:
			return new Contexto(Evento.errorConexionBBDD, null);
			
		default:
			return null;
		}
	
			
		
	}

}
