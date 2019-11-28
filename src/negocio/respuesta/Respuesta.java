package negocio.respuesta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.google.gson.annotations.Expose;

import negocio.cuestion.Cuestion;

@Entity
public class Respuesta {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Expose
	private Integer id;

	@Version
	private Integer version;

	@ManyToOne
	private Cuestion cuestion;

	@Expose
	private String enunciado;
	
	@Expose
	private boolean esCorrecta;

	public Respuesta() {
	}

	public Respuesta(String enunciado, boolean esCorrecta) {
		this.enunciado = enunciado;
		this.esCorrecta = esCorrecta;
	}

	public String getEnunciado() {
		return this.enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public boolean esCorrecta() {
		return this.esCorrecta;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setEsCorrecta(boolean esCorrecta) {
		this.esCorrecta = esCorrecta;
	}

	public Cuestion getCuestion() {
		return this.cuestion;
	}

	public void setCuestion(Cuestion cuestion) {
		this.cuestion = cuestion;
	}
	
	@Override
	public String toString() {
		return this.enunciado;
	}

	@Override
	public boolean equals(Object o) {
		Respuesta po = (Respuesta) o;
		/**
		 * Note que todos los atributos que estan aqui deben tener la etiqueta @Expose
		 * de Google Gson para poder ser exportados como JSON y luego ser comparados
		 */
		return o == this || o != null && o instanceof Respuesta
				&& (this.id == po.id || this.id != null && this.id.equals(po.id))
				&& (this.esCorrecta == po.esCorrecta)
				&& (this.enunciado == po.enunciado
						|| this.enunciado != null && this.enunciado.equals(po.getEnunciado()));
	}
}
