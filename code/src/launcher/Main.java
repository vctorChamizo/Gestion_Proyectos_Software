package launcher;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;

public class Main extends Application {
	
	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			
			stage = primaryStage;
			stage.getIcons().add(new Image(this.getClass().getResource("/main/resources/logo/logo.png").toExternalForm()));
			
			BorderPane root = new BorderPane();
			Controlador.getInstance().action(new Contexto(Evento.abrirGUIInicio, root));
			
			stage.getScene().getStylesheets().add("presentacion/vistas/css/style.css");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
