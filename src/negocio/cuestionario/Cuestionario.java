package negocio.cuestionario;

import negocio.cuestion.Cuestion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import com.google.gson.annotations.Expose;

@Entity
@NamedQueries({
		@NamedQuery(name = "find questionnaire by id", query = "select obj from Cuestionario obj where obj.id = :id"),
		@NamedQuery(name = "find all questionnaires", query = "select obj from Cuestionario obj"),
		@NamedQuery(name = "find by name", query = "select obj from Cuestionario obj where obj.nombre = :nombre") })
public class Cuestionario {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;

	@Version
	private Integer version;

	@ManyToMany
	@Expose
	private List<Cuestion> cuestiones;

	@Expose
	private String nombre;

	/**
	 * Permite construir un Cuestionario sin nombre ni cuestiones
	 */
	public Cuestionario() {
		this.cuestiones = new ArrayList<>();
	}

	/**
	 * Construye un cuestionario con un nombre y unas cuestiones
	 * 
	 * @param nombre
	 *            Nombre del cuestionario
	 * @param cuestiones
	 *            Lista de cuestiones
	 */
	public Cuestionario(String nombre, List<Cuestion> cuestiones) {
		this.nombre = nombre;
		this.cuestiones = cuestiones;
	}

	/**
	 * Construye un cuestionario con un nombre y sin cuestiones
	 * 
	 * @param nombre
	 *            Nombre del cuestionario
	 */
	public Cuestionario(String nombre) {
		this.nombre = nombre;
		this.cuestiones = new ArrayList<>();
	}

	/**
	 * Devuelve las cuestiones del cuestionario
	 * 
	 * @return Lista de cuestiones
	 */
	public List<Cuestion> getCuestiones() {
		return this.cuestiones;
	}

	/**
	 * Asigna una lista de cuestiones a un cuestionario
	 * 
	 * @param cs
	 *            Lista de cuestiones
	 */
	public void setCuestiones(List<Cuestion> cuestiones) {
		this.cuestiones = cuestiones;
	}

	public void addCuestion(Cuestion cuestion) {
		this.cuestiones.add(cuestion);
	}

	/**
	 * Retorna el nombre del cuestionario actual
	 * 
	 * @return String con el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del cuestionario
	 * 
	 * @param n
	 *            Nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public boolean equals(Object o) {
		Cuestionario po = (Cuestionario) o;
		/**
		 * Note que todos los atributos que estan aqui deben tener la etiqueta @Expose
		 * de Google Gson para poder ser exportados como JSON y luego ser comparados
		 */
		return o == this || o != null && o instanceof Cuestionario
				&& (this.nombre == po.nombre || this.nombre != null && this.nombre.equals(po.getNombre()))
				&& (po.cuestiones == this.cuestiones
						|| this.cuestiones != null && this.cuestiones.containsAll(po.getCuestiones()));

	}

	public void addCuestiones(Collection<Cuestion> cuestiones) {
		this.cuestiones.addAll(cuestiones);

	}
}
