package presentacion.controlador.imp;

import presentacion.comando.Comando;
import presentacion.comando.FactoriaComandos;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.dispatcher.Dispatcher;

public class ControladorImp extends Controlador{

	@Override

	public void action(Contexto context) {
		Comando comando = FactoriaComandos.getInstance().getComando(context.getEvento());
		Contexto respuesta = comando.execute(context.getDatos());
		Dispatcher.getInstance().update(respuesta);
	}
}

