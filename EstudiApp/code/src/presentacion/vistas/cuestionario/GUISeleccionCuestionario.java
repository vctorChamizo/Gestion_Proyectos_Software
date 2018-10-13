package presentacion.vistas.cuestionario;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import launcher.Main;
import negocio.cuestionario.Cuestionario;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

public class GUISeleccionCuestionario extends Scene implements GUI {
	private ObservableList<Cuestionario> items;

	public GUISeleccionCuestionario(Object root) {
		
		super((BorderPane) root, 800, 500);
		
		ListView<Cuestionario> list = new ListView<>();
		items = FXCollections.observableArrayList();
		Label label = new Label("Seleccione el cuestionario");

	
		list.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Cuestionario cuestionarioSeleccionado = list.getSelectionModel().getSelectedItem();
				if (cuestionarioSeleccionado != null) {
					Alert a = new Alert(AlertType.CONFIRMATION,
							"¿Confirma la selección del cuestionario \"" + cuestionarioSeleccionado + "\"?");
					a.setHeaderText("Cuestionario " + cuestionarioSeleccionado + " seleccionado.");
					Optional<ButtonType> result = a.showAndWait();

					if (result.get() == ButtonType.OK) {
						BorderPane r = new BorderPane();
						r.setTop(((BorderPane) root).getTop());
						
						Controlador.getInstance().action(new Contexto(Evento.abrirGUIResolverCuestionario, r));
						Controlador.getInstance().action(new Contexto(Evento.resolverCuestionario, cuestionarioSeleccionado));
					}
				}
			}
		});

		list.setItems(items);
		StackPane stack = new StackPane();

		StackPane.setAlignment(label, Pos.TOP_CENTER);
		stack.getChildren().add(label);
		label.setFont(new Font("Baskerville", 30));

		StackPane.setMargin(list, new Insets(50, 8, 8, 8));
		stack.getChildren().add(list);

		((BorderPane) root).setCenter(stack);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Selección de Cuestionario");
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Contexto contexto) {
		switch (contexto.getEvento()) {
		case leerTodosCuestionariosOK:
			items.addAll((List<Cuestionario>) contexto.getDatos());
			break;
		case leerTodosCuestionariosNoExisteNinguno:
			Alert a = new Alert(AlertType.ERROR, "No hay cuestionarios guardados.");
			a.setTitle("Error: No hay cuestionarios guardados");
			a.setHeaderText("Ha ocurrido un error");
			a.show();
			break;
		default:
			break;
		}
	}
}