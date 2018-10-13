package presentacion.comando.comandos.cuestion;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoLeerCuestionAleatoria implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		try {
			Cuestion cuestion = saCuestion.readRandomQuestion();
			if (cuestion == null) {
				return new Contexto(Evento.leerCuestionAleatoriaNoExisteNinguna, null);
			} else {
				return new Contexto(Evento.leerCuestionAleatoriaOK, cuestion);
			}
			
		} catch(Exception e) {
			return new Contexto(Evento.errorConexionBBDD, null);
		}
	}
}
