package presentacion.vistas.inicio;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import launcher.Main;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

/**
 * 
 * Genera la escena desde la que se inicia la aplicacion.
 * 
 * @author Diego y Víctor
 *
 */
public class GUIInicio extends Scene implements GUI {
	
	public GUIInicio(Object root) {
		
		super((BorderPane) root, 800, 500);
		
		Controlador.getInstance().action(new Contexto(Evento.abrirGUIMenuPrincipal, root));
		
		ImageView inicio = new ImageView(new Image(this.getClass().getResource("/main/resources/logo/logo.png").toExternalForm()));
		inicio.setOpacity(0.5);
		
		((BorderPane) root).setCenter(inicio);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp");
		
	}
		
	@Override
	public void actualizar(Contexto contexto) {
		
	}

}
