package presentacion.factoriaPresentacion;

import presentacion.factoriaPresentacion.imp.FactoriaPresentacionImp;
import presentacion.vistas.GUI;

public abstract class FactoriaPresentacion {
	
	private static FactoriaPresentacion instance;
	
	public static FactoriaPresentacion getInstance() {
		
		if (instance == null) {
			instance = new FactoriaPresentacionImp();
		}
		
		return instance;
	}
	
	//PRINCIPAL
	public abstract GUI crearGUIInicio(Object datos);
	public abstract GUI crearGUIMenuPrincipal(Object datos);
	
	//CUESTION
	public abstract GUI crearGUICuestion(Object datos);
	public abstract GUI crearGUIAltaCuestion(Object datos);
	public abstract GUI crearGUIEstadisticasCuestion(Object datos);
	
	//CUESTIONARIO
	public abstract GUI crearGUIResolverCuestionario(Object datos);
	public abstract GUI crearGUISeleccionCuestionario(Object datos);
	public abstract GUI crearGUIAltaCuestionario(Object datos);
	public abstract GUI crearGUIImportarCuestionario(Object datos);
	public abstract GUI crearGUIExportarCuestionario(Object datos);

}
