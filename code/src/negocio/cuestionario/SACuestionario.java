package negocio.cuestionario;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface SACuestionario {
	/**
	 * Lee todos los cuestionarios y los devuelve en una lista
	 * 
	 * @return Lista de cuestionarios
	 * @throws Exception
	 *             En caso de errores al consultar la BBDD.
	 */
	List<Cuestionario> readAll() throws Exception;

	/**
	 * Metodo que da de alta un cuestionario en la base de datos.
	 * 
	 * @param cuestionario
	 *            Cuestionario
	 * @return
	 * @throws Exception
	 */

	public int alta(Cuestionario cuestionario) throws Exception;


	/**
	 * Permite importar un Cuestionario que ha sido exportado desde un archivo y 
	 * guardarlo en la BBDD.
	 * @param rutadelFichero ruta del fichero con el contenido del cuestionario
	 * @param nombreDelCuestionario nombre con el que se quiere almacenar el nuevo cuestionario en la BBDD.     
	 * @return 0 si el cuestionario ha sido importado correctamente y guardado en la BBDD, 
	 * -1 si no se ha importado el cuestionario debido a un error al leer el fichero, -2
	 * si ya existe un cuestionario con el mismo nombre en la BBDD y -5 si hay un error
	 * al conectarse a la BBDD.
	 * @throws Exception
	 *             En caso de error al conectarse a la BBDD
	 */
	public Integer importar(String rutaDelFichero, String nombreDelCuestionario);

	/**
	 * Escribe un Cuestionario en un archivo
	 * 
	 * @param cuestionario
	 *            Cuestionario a exportar
	 * @param file
	 *            Archivo a escribir
	 * @throws IOException
	 *             En caso de errores al escribir el archivo
	 */
	public void exportar(Cuestionario cuestionario, File file) throws IOException;

	
	public int baja(String nombreCuestionario) throws Exception;



}
