package presentacion.comando.comandos.cuestion;

import java.util.List;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoLeerTodasCuestiones implements Comando {

	@Override
	public Contexto execute(Object datos) {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		
		try {
			List<Cuestion> cuestiones = saCuestion.readAll();
			if (cuestiones.isEmpty()) {
				return new Contexto(Evento.leerTodasCuestionesNoExisteNinguna, cuestiones);
			} else {
				return new Contexto(Evento.leerTodasCuestionesOK, cuestiones);
			}
		} catch (Exception e) {
			return new Contexto(Evento.errorConexionBBDD, null);
		}
	}
}
