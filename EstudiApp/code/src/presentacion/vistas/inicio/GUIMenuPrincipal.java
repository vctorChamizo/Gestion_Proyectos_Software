package presentacion.vistas.inicio;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

/**
 * Crea e inserta el Menu Principal en la parte superior de todas las escenas.
 * 
 * @author Diego y Victor.
 *
 */
public class GUIMenuPrincipal extends MenuBar implements GUI {

	public GUIMenuPrincipal(Object root) {

		super();

		setMenuPrincipal();
		((BorderPane) root).setTop(this);

	}

	private void setMenuPrincipal() {

		Menu menuArchivo = new Menu("_Archivo");
		menuArchivo.setMnemonicParsing(true);
		setMenuArchivo(menuArchivo);
      
		Menu menuCuestionario = new Menu("_Cuestionario");
		menuCuestionario.setMnemonicParsing(true);
		setMenuCuestionario(menuCuestionario);


		Menu menuJuego = new Menu("_Herramientas");
		menuJuego.setMnemonicParsing(true);
		setMenuCuestionario(menuJuego);
		
		Menu menuEstadisticas = new Menu("_Estadísticas");
		menuEstadisticas.setMnemonicParsing(true);
		setMenuEstadisticas(menuEstadisticas);

		this.getMenus().addAll(menuArchivo, menuJuego, menuEstadisticas);

	}

	

	private void setMenuArchivo(Menu menuArchivo) {

		Menu menuNuevo = new Menu("_Nuevo", new ImageView(new Image(this.getClass().getResource("/main/resources/menu/new2.png").toExternalForm())));
		menuNuevo.setMnemonicParsing(true);

		// NUEVA CUESTION
		MenuItem menuCuestion = new MenuItem("Cuestión", new ImageView(
				new Image(this.getClass().getResource("/main/resources/menu/question.png").toExternalForm())));
		menuCuestion.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.SHORTCUT_DOWN));

		menuCuestion.setOnAction((event) -> {
			BorderPane root = new BorderPane();
			root.setTop(this);
			Controlador.getInstance().action(
					new Contexto(Evento.abrirGUIAltaCuestion, root));
		});

		// NUEVO CUESTIONARIO
		MenuItem menuCuestionario = new MenuItem("Cuestionario", new ImageView(
				new Image(this.getClass().getResource("/main/resources/menu/questionnaire.png").toExternalForm())));
		menuCuestionario.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));

		menuCuestionario.setOnAction((event) -> {
			BorderPane root = new BorderPane();
			root.setTop(this);
			Controlador.getInstance().action(
					new Contexto(Evento.abrirGUIAltaCuestionario, root));
			Controlador.getInstance().action(
					new Contexto(Evento.leerTodasCuestiones, null));
		});

		menuNuevo.getItems().addAll(menuCuestionario, menuCuestion);
		
		// IMPORTAR
		MenuItem menuImportar = new MenuItem("Importar cuestionario", new ImageView(new Image(this.getClass().getResource("/main/resources/menu/import.png").toExternalForm())));
		menuImportar.setAccelerator(new KeyCodeCombination(
				KeyCode.I, KeyCombination.SHIFT_DOWN));
		menuImportar.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				BorderPane root = new BorderPane();
				root.setTop(GUIMenuPrincipal.this);
				Controlador.getInstance().action(new Contexto(Evento.abrirGUIImportarCuestionario, root));
			}
		});
	
		// EXPORTAR CUESTIONARIO
		MenuItem menuExportarCuestionario = new MenuItem("Exportar cuestionario",
				new ImageView(new Image(this.getClass().getResource("/main/resources/menu/export.png").toExternalForm())));
		menuExportarCuestionario.setAccelerator(new KeyCodeCombination(
				KeyCode.S, KeyCombination.CONTROL_DOWN));

		menuExportarCuestionario.setOnAction((event) -> {
				BorderPane root = new BorderPane();
				root.setTop(this);
				Controlador.getInstance().action(
						new Contexto(Evento.abrirGUIExportarCuestionario, root));
				Controlador.getInstance().action(
						new Contexto(Evento.leerTodosCuestionarios, null));
			});


		// SALIR
		MenuItem menuSalir = new MenuItem("Salir",
				new ImageView(new Image(this.getClass().getResource("/main/resources/menu/exit.png").toExternalForm())));
		menuSalir.setAccelerator(
				new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
		
		menuSalir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				System.exit(0);
			}
		});


		menuArchivo.getItems().addAll(menuNuevo, new SeparatorMenuItem(), menuImportar, menuExportarCuestionario, new SeparatorMenuItem(), menuSalir);
	}

	private void setMenuCuestionario(Menu mHerramientas) {

		// SELECCIONAR CUESTIONARIO
		MenuItem menuSeleccionarCuestionario = new MenuItem("Hacer Cuestionario",
				new ImageView(new Image(this.getClass().getResource("/main/resources/menu/select.png").toExternalForm())));

		menuSeleccionarCuestionario.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
		
		menuSeleccionarCuestionario.setOnAction((event) -> {
			BorderPane root = new BorderPane();
			root.setTop(this);
			Controlador.getInstance().action(new Contexto(Evento.abrirGUISeleccionCuestionario, root));
			Controlador.getInstance().action(new Contexto(Evento.leerTodosCuestionarios, null));
		});


		mHerramientas.getItems().addAll(menuSeleccionarCuestionario);
	}
	
	private void setMenuEstadisticas(Menu menuEstadisticas) {
		
		MenuItem menuEstadisticasCuestion = new MenuItem("Cuestiones",
				new ImageView(new Image(this.getClass().getResource("/main/resources/menu/pie-chart.png").toExternalForm())));
		menuEstadisticasCuestion.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN));

		menuEstadisticasCuestion.setOnAction((event) -> {
			BorderPane root = new BorderPane();
			root.setTop(this);
			Controlador.getInstance().action(new Contexto(Evento.abrirGUIEstadisticasCuestion, root));
			Controlador.getInstance().action(new Contexto(Evento.leerTodasCuestiones, null));
		});

		menuEstadisticas.getItems().add(menuEstadisticasCuestion);

	}

	@Override
	public void actualizar(Contexto contexto) {

	}

}// GUIMenuPrincipal
