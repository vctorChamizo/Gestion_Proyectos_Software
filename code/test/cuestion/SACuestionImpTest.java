package cuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.respuesta.Respuesta;

public class SACuestionImpTest {
	private static Cuestion cuestionDadaDeAltaCorrectamente;
	private Cuestion cuestionExistente;
	private Cuestion cuestionConMenosDeDosRespuestas;
	private Cuestion cuestionConTiempoMinimoNegativo;
	private static SACuestion saCuestion;
	
	@Before
	public void setup() throws Exception {
		saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		
		cuestionExistente = saCuestion.readRandomQuestion();
		
		Respuesta respuesta1 = new Respuesta("Enunciado 1", true);
		Respuesta respuesta2 = new Respuesta("Enunciado 2", false);
		Respuesta respuesta3 = new Respuesta("Enunciado 3", false);
		
		List<Respuesta> respuestas1 = new ArrayList<>();
		respuestas1.add(respuesta1);
		respuestas1.add(respuesta2);
		
		for (Respuesta respuesta : respuestas1) {
			respuesta.setCuestion(cuestionDadaDeAltaCorrectamente);
		}
	
		cuestionDadaDeAltaCorrectamente = new Cuestion("Cuestion no existente", respuestas1, "Explicacion");
		cuestionDadaDeAltaCorrectamente.setTMinimoMs(0);

		
		List<Respuesta> respuestas2 = new ArrayList<>();
		respuesta3.setCuestion(cuestionConMenosDeDosRespuestas);
		respuestas2.add(respuesta3);
		

		cuestionConMenosDeDosRespuestas = new Cuestion("Cuestion con menos de dos respuestas", respuestas2, "Explicacion");
		

		
		cuestionConTiempoMinimoNegativo = new Cuestion("Cuestion con tiempo minimo negativo", respuestas1, "Explicacion");
		cuestionConTiempoMinimoNegativo.setTMinimoMs(ThreadLocalRandom.current().nextInt(-1000, 0));

	}
	
	
	@Test
	public void testAltaCuestionCorrecta() throws Exception {
		assertTrue(saCuestion.alta(cuestionDadaDeAltaCorrectamente) == 0);
	}
	
	@Test
	public void testAltaCuestionExistente() throws Exception {
		assertTrue(saCuestion.alta(cuestionExistente) == -1);
	}
	
	@Test
	public void testAltaCuestionConMenosDeDosRespuestas() throws Exception {
		assertTrue(saCuestion.alta(cuestionConMenosDeDosRespuestas) == -2);
	}
	
	@Test
	public void testAltaCuestionConTiempoMinimoNegativo() throws Exception {
		assertTrue(saCuestion.alta(cuestionConTiempoMinimoNegativo) == -4);
	}
	
	@AfterClass
	public static void end() throws Exception {
		saCuestion.baja(cuestionDadaDeAltaCorrectamente);
	}
}
