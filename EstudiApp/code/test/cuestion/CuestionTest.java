package cuestion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.respuesta.Respuesta;

public class CuestionTest {

	@Test
	public void readAllTest() {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		
		try {
			List<Cuestion> cuestiones = saCuestion.readAll();
			assertNotNull(cuestiones);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readRandomQuestionTest() {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();

		try {
			Cuestion cuestion = saCuestion.readRandomQuestion();
			assertNotNull(cuestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void createCuestionConTiempoMinimo() {
		SACuestion saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		Cuestion cuestion = new Cuestion();
		
		cuestion.setEnunciado("Enunciado cuestion con tiempo minimo");
		List<Respuesta> respuestas = new ArrayList<>();
		respuestas.add(new Respuesta("Respuesta 1", true));
		respuestas.add(new Respuesta("Respuesta 2", false));
		cuestion.setRespuestas(respuestas);
		
		cuestion.setTMinimoMs(1200);

		try {
			saCuestion.alta(cuestion);
			List<Cuestion> cuestiones = saCuestion.readAll();
			Cuestion cuestionLeida = cuestiones.get(cuestiones.size() - 1);
			assertTrue(cuestionLeida.getTMinimoMs().equals(cuestion.getTMinimoMs()));
			saCuestion.baja(cuestionLeida);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void contadoresTest(){
		Cuestion cuestionPrueba = new Cuestion("Cuestion de prueba", "Explicacion");
		assertTrue(cuestionPrueba.getVecesContestada() == 0);
		assertTrue(cuestionPrueba.getVecesFallada() == 0);
		cuestionPrueba.sumarVecesContestada();
		assertTrue(cuestionPrueba.getVecesContestada() == 1);
		assertTrue(cuestionPrueba.getVecesFallada() == 0);
		cuestionPrueba.sumarVecesFallada();
		assertTrue(cuestionPrueba.getVecesContestada() == 1);
		assertTrue(cuestionPrueba.getVecesFallada() == 1);
		cuestionPrueba.setVecesContestada(0);
		cuestionPrueba.setVecesFallada(0);
		assertTrue(cuestionPrueba.getVecesContestada() == 0);
		assertTrue(cuestionPrueba.getVecesFallada() == 0);
	}
}
