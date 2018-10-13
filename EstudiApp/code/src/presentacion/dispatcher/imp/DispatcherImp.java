package presentacion.dispatcher.imp;

import presentacion.controlador.Contexto;
import presentacion.dispatcher.Dispatcher;
import presentacion.factoriaPresentacion.FactoriaPresentacion;
import presentacion.vistas.GUI;

public class DispatcherImp extends Dispatcher {
	private GUI gui;
	
	@Override

	public void update(Contexto context)  {


		switch (context.getEvento()) {
		
			// MENU INICIAL
			case abrirGUIInicio:
				gui = FactoriaPresentacion.getInstance().crearGUIInicio(context.getDatos());
				break;
				
			case abrirGUIMenuPrincipal:
				gui = FactoriaPresentacion.getInstance().crearGUIMenuPrincipal(context.getDatos());
				break;
				
			//CUESTION
			case abrirGUIAltaCuestion:
				gui = FactoriaPresentacion.getInstance().crearGUICuestion(context.getDatos());
				break;
				
			case abrirGUIEstadisticasCuestion:
				gui = FactoriaPresentacion.getInstance().crearGUIEstadisticasCuestion(context.getDatos());
				break;
		
			//CUESTIONARIO
			case abrirGUIResolverCuestionario:
				gui = FactoriaPresentacion.getInstance().crearGUIResolverCuestionario(context.getDatos());
				break;
				
			case abrirGUIExportarCuestionario:
				gui = FactoriaPresentacion.getInstance().crearGUIExportarCuestionario(context.getDatos());
				break;
				
			case abrirGUISeleccionCuestionario:
				gui = FactoriaPresentacion.getInstance().crearGUISeleccionCuestionario(context.getDatos());
				break;
				
			case abrirGUIAltaCuestionario:
				gui = FactoriaPresentacion.getInstance().crearGUIAltaCuestionario(context.getDatos());
				break;
				
			case abrirGUIImportarCuestionario:
				gui = FactoriaPresentacion.getInstance().crearGUIImportarCuestionario(context.getDatos());
				break;
				
			default:
				gui.actualizar(context);
				break;
		}
	}
}
