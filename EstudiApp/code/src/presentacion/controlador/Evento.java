package presentacion.controlador;

public enum Evento {
	
	/*
	 * Mantener el orden alfabetico dentro de cada apartado
	 * 
	 * Los eventos de redireccion entre vistas los llamaremos
	 * abrirGUI[nombreVista], asi todos seguimos la misma nomenclatura
	 */
	
	errorConexionBBDD,
	
	//PRINCIPAL
	
	abrirGUIInicio,
	abrirGUIMenuPrincipal,
	
	//CUESTION

	abrirGUIAltaCuestion,
	abrirGUISeleccionCuestion,
	abrirGUIEstadisticasCuestion,
	
	actualizarCuestion,
	actualizarCuestionOK,
	actualizarCuestionNoExistente,
	actualizarCuestionModificacionConcurrente,
	
	
	altaCuestion,
	altaCuestionErrorCuestionYaExistente,
	altaCuestionErrorCuestionConMenosDeDosRespuestas,
	altaCuestionErrorCuestionTiempoMinimoNegativo,
	altaCuestionErrorModificacionConcurrente,
	altaCuestionOK,
	
	leerCuestionAleatoria,
	leerCuestionAleatoriaOK,
	leerCuestionAleatoriaNoExisteNinguna,
	
	leerTodasCuestiones, 
	leerTodasCuestionesOK,
	leerTodasCuestionesNoExisteNinguna,
	
	resolverCuestion,
	
	mostrarAclaracionCuestion,
	
	//CUESIONARIO
	
	abrirGUIResolverCuestionario,
	abrirGUISeleccionCuestionario,
	abrirGUIAltaCuestionario,
	abrirGUIImportarCuestionario,
	abrirGUIExportarCuestionario,
	
	altaCuestionario,
	altaCuestionarioErrorCuestionarioYaExistente,
	altaCuestionarioErrorCuestionarioConMenosDeDosRespuestas,
	altaCuestionarioErrorModificacionConcurrente,
	altaCuestionarioOK,
	
	
	
	importarCuestionario,
	importarCuestionarioOK,
	importarCuestionarioErrorLecturaFichero,
	importarCuestionarioErrorNombreCuestionarioYaExiste,
	
	leerTodosCuestionarios,
	leerTodosCuestionariosOK,
	leerTodosCuestionariosNoExisteNinguno,
	
	resolverCuestionario,
	siguienteCuestion,
	finalizarCuestionario,


	exportarCuestionario,
	exportarCiestionarioOkey,
	exportarCiestionarioErr

	
}
