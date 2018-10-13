package presentacion.comando.imp;

import presentacion.comando.Comando;
import presentacion.comando.FactoriaComandos;
import presentacion.comando.comandos.cuestion.ComandoAbrirGUICuestion;
import presentacion.comando.comandos.cuestion.ComandoAbrirGUIEstadisticasCuestion;
import presentacion.comando.comandos.cuestion.ComandoActualizarCuestion;
import presentacion.comando.comandos.cuestion.ComandoAltaCuestion;
import presentacion.comando.comandos.cuestion.ComandoLeerCuestionAleatoria;
import presentacion.comando.comandos.cuestion.ComandoLeerTodasCuestiones;
import presentacion.comando.comandos.cuestion.ComandoResolverCuestion;
import presentacion.comando.comandos.cuestionario.ComandoAbrirGUIAltaCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoAbrirGUIImportarCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoAbrirGUIExportarCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoAbrirGUIResolverCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoAbrirGUISeleccionCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoAltaCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoExportarCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoFinalizarCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoImportarCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoLeerTodosCuestionarios;
import presentacion.comando.comandos.cuestionario.ComandoMostrarAclaracionCuestion;
import presentacion.comando.comandos.cuestionario.ComandoResolverCuestionario;
import presentacion.comando.comandos.cuestionario.ComandoSiguienteCuestion;
import presentacion.comando.comandos.inicio.ComandoAbrirGUIInicio;
import presentacion.comando.comandos.inicio.ComandoAbrirGUIMenuPrincipal;
import presentacion.controlador.Evento;

public class FactoriaComandosImp extends FactoriaComandos {

	@Override
	public Comando getComando(Evento evento) {
		
		switch(evento) {

		
			//PRINCIPAL
			case abrirGUIInicio:
				return new ComandoAbrirGUIInicio();
		
			case abrirGUIMenuPrincipal:
				return new ComandoAbrirGUIMenuPrincipal();
				
			//CUESTIONARIO
			case abrirGUIAltaCuestionario:
				return new ComandoAbrirGUIAltaCuestionario();
				
			case abrirGUIResolverCuestionario:
				return new ComandoAbrirGUIResolverCuestionario();
				
			case abrirGUISeleccionCuestionario:
				return new ComandoAbrirGUISeleccionCuestionario();
				
			case abrirGUIImportarCuestionario:
				return new ComandoAbrirGUIImportarCuestionario();

			case actualizarCuestion:
				return new ComandoActualizarCuestion();

			case abrirGUIExportarCuestionario:
				return new ComandoAbrirGUIExportarCuestionario();
				
			case altaCuestionario:
				return new ComandoAltaCuestionario();
				
			case importarCuestionario:
				return new ComandoImportarCuestionario();
				
			case leerTodosCuestionarios:
				return new ComandoLeerTodosCuestionarios();
				
			case resolverCuestionario:
				return new ComandoResolverCuestionario();
			
			case siguienteCuestion:
				return new ComandoSiguienteCuestion();
			
			case finalizarCuestionario:
				return new ComandoFinalizarCuestionario();
				
			case mostrarAclaracionCuestion:
				return new ComandoMostrarAclaracionCuestion();
				
			//CUESTION
			case abrirGUIAltaCuestion:
				return new ComandoAbrirGUICuestion();

			case abrirGUIEstadisticasCuestion:
				return new ComandoAbrirGUIEstadisticasCuestion();
		
			case altaCuestion:
				return new ComandoAltaCuestion();
				
			case leerCuestionAleatoria:
				return new ComandoLeerCuestionAleatoria();
				
			case leerTodasCuestiones:
				return new ComandoLeerTodasCuestiones();
				
			case resolverCuestion:
				return new ComandoResolverCuestion();

			case exportarCuestionario:
				return new ComandoExportarCuestionario();

			default:
				return null;

		}
	}

}
