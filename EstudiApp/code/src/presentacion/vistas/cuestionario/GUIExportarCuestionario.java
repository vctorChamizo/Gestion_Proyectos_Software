package presentacion.vistas.cuestionario;

import java.io.File;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import launcher.Main;
import negocio.cuestionario.Cuestionario;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

public class GUIExportarCuestionario extends Scene implements GUI {
	private ObservableList<Cuestionario> items;
	private Cuestionario cselected;

	public GUIExportarCuestionario(Object root) {

		super((BorderPane) root, 800, 500);
		
		this.getStylesheets().add("presentacion/vistas/css/style.css");
		
		ListView<Cuestionario> list = new ListView<>();
		items = FXCollections.observableArrayList();
		Label label = new Label("Seleccione el cuestionario");

		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cuestionario>() {
			@Override
			public void changed(ObservableValue<? extends Cuestionario> observable, Cuestionario oldValue,
					Cuestionario newValue) {
				cselected = newValue;
			}
		});

		list.setItems(items);
		StackPane stack = new StackPane();
		
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		stack.getChildren().add(label);
		label.setFont(new Font("Baskerville", 30));

		StackPane.setMargin(list, new Insets(50, 8, 8, 8));
		stack.getChildren().add(list);

		BorderPane bottom = new BorderPane();
		Button exportarButton = new Button("Exportar");
		exportarButton.setOnAction((event) -> {
			if (this.cselected == null) {
				new Alert(AlertType.ERROR,
						"Por favor seleccione un cuestionario para exportar.").show();
			} else {
				FileChooser explorador = new FileChooser();
				File file = explorador.showSaveDialog(new Stage());
				
				if (file != null) {
					Controlador.getInstance().action(
							new Contexto(Evento.exportarCuestionario, new Pair<Cuestionario, File>(this.cselected, file)));
				}
			}
		});
		exportarButton.setId("exportarButton");
		bottom.setCenter(exportarButton);
		bottom.setPadding(new Insets(8, 50, 20, 8));
		
		((BorderPane) root).setBottom(bottom);
		((BorderPane) root).setCenter(stack);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Exportar Cuestionario");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Contexto contexto) {
		Alert a;
		switch (contexto.getEvento()) {
		case leerTodosCuestionariosOK:
			items.addAll((List<Cuestionario>) contexto.getDatos());
			break;
		case leerTodosCuestionariosNoExisteNinguno:
			a = new Alert(AlertType.ERROR, "No hay cuestionarios guardados.");
			a.setTitle("Error: No hay cuestionarios guardados");
			a.setHeaderText("Ha ocurrido un error");
			a.show();
			break;
		case exportarCiestionarioErr:
			a = new Alert(AlertType.ERROR,
					"No se ha podido exportar el cuestionario seleccionado, inténtelo de nuevo.");
			a.setTitle("Error: No se ha podido exportar");
			a.setHeaderText("Ha ocurrido un error");
			a.show();
			break;
		case exportarCiestionarioOkey:
			a = new Alert(AlertType.INFORMATION, "¡Cuestionario exportado correctamente!");
			a.setTitle("Información");
			a.setHeaderText("Se ha exportado el cuestionario");
			a.show();
			break;
		default:
			break;
		}
	}
}
