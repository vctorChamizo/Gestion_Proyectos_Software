package cuestionario;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import negocio.cuestion.Cuestion;
import negocio.cuestion.SACuestion;
import negocio.cuestionario.Cuestionario;
import negocio.cuestionario.SACuestionario;
import negocio.cuestionario.SACuestionarioImp;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.respuesta.Respuesta;

public class ImportarCuestionarioTest {

	private static Cuestionario cuestionario;
	private static Cuestion cuestion1;
	private static Cuestion cuestion2;
	
	private static SACuestionario saCuestionario;
	private static SACuestion saCuestion;

	@Before
	public void setup() throws Exception {
		

		saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();
		saCuestion = FactoriaNegocio.obtenerInstancia().crearSACuestion();
		
		cuestion1 = new Cuestion("enunciado1",
				Arrays.asList(new Respuesta("resp1", false), new Respuesta("resp2", true)), "Explicacion");
		
		cuestion2 = new Cuestion("enunciado2",
				Arrays.asList(new Respuesta("resp1", false), new Respuesta("resp2", true)), "Explicacion");
		
		saCuestion.alta(cuestion1);
		saCuestion.alta(cuestion2);
		
		cuestionario = new Cuestionario("nombre cuestionario importar", Arrays.asList(cuestion1, cuestion2));

		saCuestionario.alta(cuestionario);
	}
	
	
	@Test
	public void importarCuestionarioConRutaInexistente() {
		
		int respuesta = saCuestionario.importar("ruta que no existe", "cuestionario con ruta inexistente");
		assertTrue(respuesta == -1);
	}
	
	@Test
	public void importarCuestionarioExistente() {

		int respuesta = saCuestionario.importar("ruta", "nombre cuestionario importar");
		assertTrue(respuesta == -2);
	}
	
	@AfterClass
	public static void end() throws Exception {
		saCuestionario.baja(cuestionario.getNombre());
		saCuestion.baja(cuestion1);
		saCuestion.baja(cuestion2);
	}
}
