package negocio.cuestionario;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;

import negocio.cuestion.Cuestion;
import negocio.respuesta.Respuesta;

import com.google.gson.GsonBuilder;


public class SACuestionarioImp implements SACuestionario {
	private static Gson gson;

	@Override
	public List<Cuestionario> readAll() throws Exception {
		List<Cuestionario> cuestionarios = null;
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();

			cuestionarios = em.createNamedQuery("find all questionnaires", Cuestionario.class).getResultList();

			em.close();
			emf.close();
		} catch (Exception e) {
			throw new Exception("Error en la conexion a la BBDD.");
		}
		return cuestionarios;
	}

	@Override
	public int alta(Cuestionario cuestionario) throws Exception {
		int sol;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			List<Cuestionario> cuestionariosBBDD = em.createNamedQuery("find by name", Cuestionario.class)
					.setParameter("nombre", cuestionario.getNombre()).getResultList();
			// Comprbacion de si existe un cuestionario con ese mismo nombre
			if (!cuestionariosBBDD.isEmpty()) {
				sol = -1;
				em.getTransaction().rollback();
			} else if (cuestionario.getCuestiones().size() < 2) {
				sol = -2;
				em.getTransaction().rollback();
			} else {
				sol = 0;
				em.persist(cuestionario);
				em.getTransaction().commit();
			}

			em.close();
			emf.close();
		} catch (RollbackException r) {
			// Error de concurrencia
			return -3;
		} catch (Exception e) {
			throw new Exception("Error en la conexion a la BBDD.");
		}
		return sol;
	}

	@Override

	public Integer importar(String rutaDelFichero, String nombreDelCuestionario) {
		Cuestionario cuestionario = null;
		Integer resultado = 0;
		EntityManagerFactory emf = null;
		EntityManager em = null;

		try {
			emf = Persistence.createEntityManagerFactory("EstudiApp");
			em = emf.createEntityManager();
			em.getTransaction().begin();

			List<Cuestionario> cuestionariosBBDD = em.createNamedQuery("find by name", Cuestionario.class)
					.setParameter("nombre", nombreDelCuestionario).getResultList();

			if (cuestionariosBBDD.isEmpty()) {
				useGson();
				DataInputStream reader = new DataInputStream(new FileInputStream(new File(rutaDelFichero)));
				String json = reader.readUTF();
				reader.close();
				cuestionario = SACuestionarioImp.gson.fromJson(json, Cuestionario.class);
				cuestionario.setNombre(nombreDelCuestionario);
				
				//Es necesario guardar en una nueva lista las cuestiones que se añadan
				//al cuestionario porque si se modifica la lista al ietrarla se lanza la
				//excepción ConcurrentModificationException
				List<Cuestion> cuestiones = new ArrayList<>();
				
				// Hay que almacenar en la BBDD las cuestiones que no estén para
				// poder guardar el cuestionario
				for (Cuestion cuestion : cuestionario.getCuestiones()) {
					List<Cuestion> cuestionesBBDD = em.createNamedQuery("find by statement", Cuestion.class)
							.setParameter("enunciado", cuestion.getEnunciado()).getResultList();
					
					if (cuestionesBBDD.isEmpty()) {
						//Como el poseedor de la relación es Respuesta hay que indicar
						//a cada respuesta cuál es su cuestión para que la relación se alamcene en
						//en la base de datos correctamente
						for (Respuesta respuesta : cuestion.getRespuestas()) {
							respuesta.setId(null);
							respuesta.setCuestion(cuestion);
						}
						cuestion.setId(null);
						cuestiones.add(cuestion);
						em.persist(cuestion);
					} else {
						cuestiones.add(cuestionesBBDD.get(0));
					}
				}
				
				cuestionario.setCuestiones(cuestiones);
				em.persist(cuestionario);
				resultado = 0;
				em.getTransaction().commit();
			} else {
				resultado = -2;
				em.getTransaction().rollback();
			}

		} catch (JsonSyntaxException | IOException e) {
			resultado = -1;
			em.getTransaction().rollback();
		} catch (Exception e) {
			e.printStackTrace();
			resultado = -5;
		} finally {
			if (em != null)
				em.close();
			if (emf != null)
				emf.close();
		}

		return resultado;
	}

	@Override
	public void exportar(Cuestionario cuestionario, File file) throws IOException {
		useGson();
		DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		outStream.writeUTF(SACuestionarioImp.gson.toJson(cuestionario));
		outStream.close();
	}

	@Override
	public int baja(String nombreCuestionario) throws Exception {
		int sol;
		List<Cuestionario> cuestionariosBBDD = null;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			cuestionariosBBDD = em.createNamedQuery("find by name", Cuestionario.class)
					.setParameter("nombre", nombreCuestionario).getResultList();

			if (cuestionariosBBDD.isEmpty()) {
				sol = -1;
				em.getTransaction().rollback();
			} else {
				em.remove(cuestionariosBBDD.get(0));
				em.getTransaction().commit();
				sol = 0;
			}

			em.close();
			emf.close();
		} catch (RollbackException r) {
			// Error de concurrencia
			sol = -3;
		} catch (Exception e) {
			throw new Exception("Error en la conexion a la BBDD.");
		}
		return sol;
	}

	private void useGson() {
		if (SACuestionarioImp.gson == null)
			SACuestionarioImp.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

}
