package presentacion.factoriaPresentacion.imp;

import presentacion.factoriaPresentacion.FactoriaPresentacion;
import presentacion.vistas.GUI;
import presentacion.vistas.cuestion.GUIAltaCuestion;
import presentacion.vistas.cuestion.GUIEstadisticasCuestion;
import presentacion.vistas.cuestionario.GUIAltaCuestionario;
import presentacion.vistas.cuestionario.GUIImportarCuestionario;
import presentacion.vistas.cuestionario.GUIExportarCuestionario;
import presentacion.vistas.cuestionario.GUIResolverCuestionario;
import presentacion.vistas.cuestionario.GUISeleccionCuestionario;
import presentacion.vistas.inicio.GUIInicio;
import presentacion.vistas.inicio.GUIMenuPrincipal;

public class FactoriaPresentacionImp extends FactoriaPresentacion {

	//PRINCIPAL
	@Override
	public GUI crearGUIInicio(Object datos) {
		return new GUIInicio(datos);
	}
	
	@Override
	public GUI crearGUIMenuPrincipal(Object datos) {
		return new GUIMenuPrincipal(datos);
	}
	
	//CUESTION
	@Override
	public GUI crearGUICuestion(Object datos) {
		return new GUIAltaCuestion(datos);
	}
	
	@Override
	public GUI crearGUIAltaCuestion(Object datos) {
		return new GUIAltaCuestion(datos);
	}
	
	@Override
	public GUI crearGUIEstadisticasCuestion(Object datos) {
		return new GUIEstadisticasCuestion(datos);
	}
	
	//CUESTIONARIO
	
	@Override
	public GUI crearGUIResolverCuestionario(Object datos) {
		return new GUIResolverCuestionario(datos);
	}
	
	@Override
	public GUI crearGUISeleccionCuestionario(Object datos) {
		return new GUISeleccionCuestionario(datos);
	}
	
	@Override
	public GUI crearGUIAltaCuestionario(Object datos) {
		return new GUIAltaCuestionario(datos);
	}

	@Override
	public GUI crearGUIImportarCuestionario(Object datos) {
		return new GUIImportarCuestionario(datos);
	}

	public GUI crearGUIExportarCuestionario(Object datos) {
		return new GUIExportarCuestionario(datos);
	}
	
}
