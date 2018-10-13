package negocio.cuestion;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import negocio.respuesta.Respuesta;

public class SACuestionImp implements SACuestion {

	@Override
	public List<Cuestion> readAll() throws Exception {
		List<Cuestion> cuestiones = null;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();

			cuestiones = em.createNamedQuery("find all questions", Cuestion.class).getResultList();

			em.close();
			emf.close();
		} catch (Exception e) {
			throw new Exception("Error en la conexion a la BBDD.");
		}

		return cuestiones;
	}

	@Override
	public Cuestion readRandomQuestion() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
		EntityManager em = emf.createEntityManager();
		Random rand = new Random();
		Cuestion cuestion = null;

		try {
			List<Cuestion> cuestiones = em.createNamedQuery("find all questions", Cuestion.class).getResultList();
			if (!cuestiones.isEmpty()) {
				cuestion = cuestiones.get(rand.nextInt(cuestiones.size()));
			}
			em.close();
			emf.close();
		} catch (Exception e) {
			throw new Exception("Error en la conexion a la BBDD.");
		}

		return cuestion;
	}

	/**
	 * Alta de una cuestion y de sus respuestas
	 * 
	 * @param cuestion
	 *            - Cuestion que se quiere dar de alta
	 * @throws Exception
	 *             - Se lanza cuando falla la conexion con la base de datos
	 * @return 0 si no hay error, -1 si la cuestion ya existia, -2 si la cuestion
	 *         tiene menos de 2 respuestas, -4 si la cuestion tiene tiempo minimo
	 *         de respuesta negativo, -3 si hay error de concurrencia
	 */
	@Override
	public int alta(Cuestion cuestion) throws Exception {
		int sol;
	
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			List<Cuestion> cuestionesBBDD = em.createNamedQuery("find by statement", Cuestion.class)
					.setParameter("enunciado", cuestion.getEnunciado()).getResultList();

			if (!cuestionesBBDD.isEmpty()) {
				// Si ya esta creada una cuestion con ese nombre.
				sol = -1;
				em.getTransaction().rollback();
			} else if (cuestion.getRespuestas().size() < 2) {
				sol = -2;
				em.getTransaction().rollback();
			} else if (cuestion.getTMinimoMs() < 0) {
				sol = -4;
				em.getTransaction().rollback();
			} else {
				// Como Respuesta es el poseedor de la relación es necesario
				// que cada respuesta tenga el atributo cuestion inicializado
				// para que la relación se guarde en la BBDD
				List<Respuesta> respuestas = cuestion.getRespuestas();
				for (Respuesta respuesta : respuestas) {
					respuesta.setCuestion(cuestion);
				}

				sol = 0;
				em.persist(cuestion);
				em.getTransaction().commit();

			}

			em.close();
			emf.close();
		} catch (RollbackException r) {
			// Error de concurrencia
			sol = -3;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error en la conexion a la BBDD.");
		}
		return sol;
	}

	@Override
	public int baja(Cuestion cuestion) throws Exception {
		int sol;
		List<Cuestion> cuestionesBBDD = null;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			cuestionesBBDD = em.createNamedQuery("find by statement", Cuestion.class)
					.setParameter("enunciado", cuestion.getEnunciado()).getResultList();
			if (cuestionesBBDD.isEmpty()) {
				sol = -1;
				em.getTransaction().rollback();
			} else {
				em.remove(cuestionesBBDD.get(0));
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

	@Override
	public int actualizar(Cuestion cuestion) throws Exception {
		int sol;
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("EstudiApp");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			Cuestion cuestionBBDD = em.find(Cuestion.class, cuestion.getId());

			if (cuestionBBDD == null) {
				sol = -1;
				em.getTransaction().rollback();
			} else {	
				cuestionBBDD.setVecesContestada(cuestion.getVecesContestada());
				cuestionBBDD.setVecesFallada(cuestion.getVecesFallada());
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

}
