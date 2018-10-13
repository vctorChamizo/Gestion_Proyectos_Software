package negocio.factoriaNegocio;

import negocio.cuestion.SACuestion;
import negocio.cuestion.SACuestionImp;
import negocio.cuestionario.SACuestionario;
import negocio.cuestionario.SACuestionarioImp;

public class FactoriaNegocioImp extends FactoriaNegocio {

	@Override
	public SACuestion crearSACuestion() {
		return new SACuestionImp();
	}

	@Override
	public SACuestionario crearSACuestionario() {
		return new SACuestionarioImp();
	}

}
