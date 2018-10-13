package presentacion.comando.comandos.cuestionario;

import java.io.File;
import java.io.IOException;

import javafx.util.Pair;
import negocio.cuestionario.Cuestionario;
import negocio.cuestionario.SACuestionario;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.comando.Comando;
import presentacion.controlador.Contexto;
import presentacion.controlador.Evento;

public class ComandoExportarCuestionario implements Comando {

	@Override
	public Contexto execute(Object datos) {
		Contexto r = new Contexto(Evento.exportarCiestionarioOkey, null);
		try {
			SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia()
					.crearSACuestionario();
			@SuppressWarnings("unchecked")
			Pair<Cuestionario, File> c = (Pair<Cuestionario, File>) datos;
			saCuestionario.exportar(c.getKey(), c.getValue());
		} catch (IOException e) {
			r = new Contexto(Evento.exportarCiestionarioErr, e);
		}
		
		return r;
	}
}
