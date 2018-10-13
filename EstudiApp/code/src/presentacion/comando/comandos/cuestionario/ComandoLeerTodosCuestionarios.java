package presentacion.comando.comandos.cuestionario;

import java.util.List;

import negocio.cuestionario.Cuestionario;
import negocio.cuestionario.SACuestionario;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoLeerTodosCuestionarios implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();
		
		try {
			List<Cuestionario> cuestionarios = saCuestionario.readAll();
			
			if (cuestionarios == null || cuestionarios.isEmpty()) {
				return new Contexto(Evento.leerTodosCuestionariosNoExisteNinguno, cuestionarios);
			} else {
				return new Contexto(Evento.leerTodosCuestionariosOK, cuestionarios);
			}		
		} catch (Exception e) {
			return new Contexto(Evento.errorConexionBBDD, null);
		}
	}
}
