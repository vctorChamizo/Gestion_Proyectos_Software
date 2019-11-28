package negocio.cuestion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.google.gson.annotations.Expose;

import negocio.cuestionario.Cuestionario;
import negocio.respuesta.Respuesta;

@Entity
@NamedQueries({ @NamedQuery(name = "find question by id", query = "select obj from Cuestion obj where obj.id = :id"),
		@NamedQuery(name = "find all questions", query = "select obj from Cuestion obj"),
		@NamedQuery(name = "find questions by vecesContestada", query = "select obj from Cuestion obj where obj.vecesContestada > 0"),
		@NamedQuery(name = "find by statement", query = "select obj from Cuestion obj where obj.enunciado = :enunciado") })
public class Cuestion {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Expose
	private Integer id;

	@Version
	private Integer version;

	@Expose
	@OneToMany(mappedBy = "cuestion", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Respuesta> respuestas;

	@ManyToMany(mappedBy = "cuestiones")
	private List<Cuestionario> cuestionarios;

	@Expose
	private String enunciado;
	
	@Expose
	private String explicacion;


	@Expose
	private Integer tiempoMinimoEnMilisegundos;

	private int vecesContestada;
	
	private int vecesFallada;



	public Cuestion() {
		super();
		this.respuestas = new ArrayList<>();
		this.vecesContestada = 0;
		this.vecesFallada = 0;
		this.explicacion = new String();
		this.tiempoMinimoEnMilisegundos = 0;
	}

	
	public Cuestion(String enunciado, String explicacion) {
		this.enunciado = enunciado;
		this.explicacion = explicacion;
		this.respuestas = new ArrayList<>();
		this.vecesContestada = 0;
		this.vecesFallada = 0;
		this.explicacion = new String();
		this.tiempoMinimoEnMilisegundos = 0;
	}

	public Cuestion(String enunciado, List<Respuesta> respuestas, String explicacion) {
		this.enunciado = enunciado;
		this.explicacion = explicacion;
		this.respuestas = respuestas;
		this.vecesContestada = 0;
		this.vecesFallada = 0;
		this.explicacion = new String();
		this.tiempoMinimoEnMilisegundos = 0;
	}
  
	public String getEnunciado() {
		return this.enunciado;
	}
	


	public List<Respuesta> getRespuestas() {
		return this.respuestas;
	}
	
	public String getExplicacion() {
		return this.explicacion;
	}

	public void setId(Integer id) {
		this.id = id;
	}
  
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}
	

	public void setTMinimoMs(Integer tMinMs) {
		this.tiempoMinimoEnMilisegundos = tMinMs;
	}

	public Integer getTMinimoMs() {
		return this.tiempoMinimoEnMilisegundos;
	}

	public void setVecesFallada(int a ){
		this.vecesFallada = a;
	}
	
	public int getVecesFallada(){
		return this.vecesFallada;
	}
	
	public void setVecesContestada(int a){
		this.vecesContestada = a;
	}
	
	public int getVecesContestada() {
		return this.vecesContestada;
	}
	
	public void sumarVecesContestada() {
		this.vecesContestada++;
	}
	
	public void sumarVecesFallada() {
		this.vecesFallada++;
	}

  	public Integer getId() {
		return this.id;
	}
  
	@Override
	public String toString() {
		return this.enunciado;
	}

	@Override
	public boolean equals(Object o) {
		Cuestion po = (Cuestion) o;
		/**
		 * Note que todos los atributos que estan aqui deben tener la etiqueta @Expose
		 * de Google Gson para poder ser exportados como JSON y luego ser comparados
		 */
		return o == this || o != null && o instanceof Cuestion
				&& (this.id == po.id || this.id != null && this.id.equals(po.getId()))
				&& (this.enunciado == po.enunciado
						|| this.enunciado != null && this.enunciado.equals(po.getEnunciado()))
				&& (this.tiempoMinimoEnMilisegundos == po.tiempoMinimoEnMilisegundos
						|| this.tiempoMinimoEnMilisegundos != null && this.tiempoMinimoEnMilisegundos.equals(po.getTMinimoMs()))
				&& (this.explicacion == po.explicacion || this.explicacion != null
						&& this.explicacion.equals(po.getExplicacion()))
				&& (this.respuestas == po.respuestas
						|| this.respuestas != null && this.respuestas.containsAll(po.getRespuestas()));
	}
	
}
