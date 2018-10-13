package presentacion.comando;

import presentacion.comando.imp.FactoriaComandosImp;
import presentacion.controlador.Evento;

public abstract class FactoriaComandos {
	
	private static FactoriaComandos instance;

	public static FactoriaComandos getInstance() {
		
		if (instance == null) {
			instance = new FactoriaComandosImp();
		}
		
		return instance;
	}

	public abstract Comando getComando(Evento evento);
}
