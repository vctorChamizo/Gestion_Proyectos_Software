package negocio.factoriaNegocio;

import negocio.cuestion.SACuestion;
import negocio.cuestionario.SACuestionario;

public abstract class FactoriaNegocio {
	private static FactoriaNegocio instancia;
	
	public static FactoriaNegocio obtenerInstancia() {
		if (instancia == null) {
			instancia = new FactoriaNegocioImp();
		}
		
		return instancia;
	}
	
	public abstract SACuestion crearSACuestion();
	public abstract SACuestionario crearSACuestionario();
}
