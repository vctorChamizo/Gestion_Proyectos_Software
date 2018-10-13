package presentacion.dispatcher;

import presentacion.controlador.Contexto;
import presentacion.dispatcher.imp.DispatcherImp;

/**
 * Distribuye el renderizado de las vistas
 * 
 * @author V�ctor
 *
 */
public abstract class Dispatcher {
	
	private static Dispatcher instance;

	public static Dispatcher getInstance() {
		
		if (instance == null) {
			instance = new DispatcherImp();
		}
		
		return instance;
	}

	/**
	 * Todas las vistas hacen una llamada al controlado con el evento "abrirGUIMenuPrincipal", para
	 * que inserte el Menu Principal de la aplicacion en la parte superiro de cada escena.
	 * 
	 * @param context [Evento de renderizado de la vista, datos que se quieren mostrar o actualizar en la vista]
	 */
	public abstract void update(Contexto context);
}
