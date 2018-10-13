package cuestionario;


import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



import org.junit.AfterClass;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import negocio.cuestion.Cuestion;
import negocio.cuestionario.Cuestionario;
import negocio.cuestionario.SACuestionario;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.respuesta.Respuesta;

public class SACuestionarioTest {

	@Test
	public void readAllTest() {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();

		try {
			List<Cuestionario> cuestionarios = saCuestionario.readAll();
			assertNotNull(cuestionarios);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void toJson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		Cuestionario cuestionario = null;

		try {
			cuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario().readAll().get(0);
		} catch (Exception e) {
			System.err.println("No se ha podido probar con una entidad JPA 'Cuestionario'");
			e.printStackTrace();
		}
		String result = gson.toJson(cuestionario);
		Cuestionario cuImp = gson.fromJson(result, Cuestionario.class);
		
		assertEquals(cuImp, cuestionario);
		assertEquals(result, gson.toJson(cuImp));
	}

	@Test
	public void testImportar() {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();

		Cuestionario cuestionario = new Cuestionario("nombre", Arrays.asList(new Cuestion("enunciado1",
				Arrays.asList(new Respuesta("resp1", false), new Respuesta("resp2", true)), "explicacion")));

		try {
			// Para que sea efectivo debe probarse con una entity JPA
			cuestionario = saCuestionario.readAll().get(0);
			
			// Modifico para que no este en la DB al importar
			cuestionario.setNombre(cuestionario.getNombre() + " - Test - " + new Random().hashCode());
		} catch (Exception e1) {
			System.err.println("Error al leer de la DB");
			e1.printStackTrace();
		}

		try {
			File f = File.createTempFile("cuestionarioImportar", ".tmp");
			saCuestionario.exportar(cuestionario, f);
			Integer respuesta  = saCuestionario.importar("cuestionarioImportar.tmp", "nuevo cuestionario");
			//assertTrue(respuesta == 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@AfterClass
	public static void end() throws Exception {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();
		saCuestionario.baja("nuevo cuestionario");
	}


	@Test
	public void testExportar() {
		SACuestionario saCuestionario = FactoriaNegocio.obtenerInstancia().crearSACuestionario();
		boolean iguales = true;
		Cuestionario cuestionario = new Cuestionario("nombre", Arrays.asList(new Cuestion("enunciado1",
				Arrays.asList(new Respuesta("resp1", false), new Respuesta("resp2", true)), "explicacion")));
		try {
			File f = File.createTempFile("cuestionarioExportar", ".tmp");
			saCuestionario.exportar(cuestionario, f);
			File f2 = File.createTempFile("cuestionarioExportarDos", ".tmp");
			saCuestionario.exportar(cuestionario, f2);
			FileInputStream fi = new FileInputStream(f);
			FileInputStream fi2 = new FileInputStream(f2);
			int i1 = fi.read();
			int i2 = fi2.read();
			while (i1 != -1) {
				if (i1 != i2) {
					iguales = false;
					break;
				}
				i1 = fi.read();
				i2 = fi2.read();
			}
			if (i1 != i2)
				iguales = false;
			fi.close();
			fi2.close();
			assertTrue("Error en el metodo exportar", iguales);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
