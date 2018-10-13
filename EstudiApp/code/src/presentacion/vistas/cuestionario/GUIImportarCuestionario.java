package presentacion.vistas.cuestionario;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import launcher.Main;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

public class GUIImportarCuestionario extends Scene implements GUI {
	private GridPane mainPanel;
	private TextField rutaDelFicheroTextfield;
	private TextField nombreDelCuestionarioTextField;
	private Button buscarButton;
	private Button importarButton;
	
	public GUIImportarCuestionario(Object root) {
		super((BorderPane) root, 800, 500);
		
		mainPanel = new GridPane();
		initGUI(root);
		
		this.getStylesheets().add("presentacion/vistas/css/style.css");
		
		((BorderPane) root).setCenter(mainPanel);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Importar Cuestionario");
		
		
	}

	private void initGUI(Object root) {		
		Label rutaDelFicheroLabel = new Label("Ruta del fichero a importar:");
		rutaDelFicheroLabel.setFont(new Font("Arial", 17));
		this.rutaDelFicheroTextfield = new TextField();
		this.rutaDelFicheroTextfield.setEditable(false);
		this.rutaDelFicheroTextfield.setMaxSize(600, 10);
		this.rutaDelFicheroTextfield.setMinSize(450, 30);
		this.rutaDelFicheroTextfield.setId("rutaDelFicheroTextField");
		
		Label nombreDelCuestionarioLabel = new Label("Nombre del nuevo cuestionario:");
		nombreDelCuestionarioLabel.setFont(new Font("Arial", 17));
		this.nombreDelCuestionarioTextField = new TextField();
		this.nombreDelCuestionarioTextField.setMaxSize(600, 10);
		this.nombreDelCuestionarioTextField.setMinSize(450, 30);
		
		this.buscarButton = new Button("Buscar");
		this.buscarButton.setId("buscarButton");
		this.importarButton = new Button("Importar");
		this.importarButton.setId("importarButton");
		
		this.mainPanel.setHgap(10);
		this.mainPanel.setVgap(10);
		
		this.mainPanel.add(rutaDelFicheroLabel, 0, 0);
		this.mainPanel.add(rutaDelFicheroTextfield, 0, 1);
		this.mainPanel.add(buscarButton, 1, 1);
		
		GridPane.setMargin(rutaDelFicheroLabel, new Insets(20, 10, 0, 20));
		GridPane.setMargin(rutaDelFicheroTextfield, new Insets(0, 10, 20, 20));
		GridPane.setMargin(buscarButton, new Insets(0, 5, 20, 5));
		
		this.mainPanel.add(nombreDelCuestionarioLabel, 0, 2);
		this.mainPanel.add(nombreDelCuestionarioTextField, 0, 3);
		this.mainPanel.add(importarButton, 1, 3);
		
		GridPane.setMargin(nombreDelCuestionarioLabel, new Insets(20, 10, 0, 20));
		GridPane.setMargin(nombreDelCuestionarioTextField, new Insets(0, 10, 20, 20));
		GridPane.setMargin(importarButton, new Insets(0, 5, 20, 5));
		
		
		setBuscarButtonAction();
		setImportarButtonAction();
	}

	
	
	private void setImportarButtonAction() {
		this.importarButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				boolean todosLosCamposRellenados = true;
				
				if (nombreDelCuestionarioTextField.getText().isEmpty()) {
					nombreDelCuestionarioTextField.setStyle("-fx-border-width: 2px; -fx-border-color: red");
					todosLosCamposRellenados = false;
					
				} else {
					nombreDelCuestionarioTextField.setStyle("-fx-border-color: lightgray");
				}
				
				if (rutaDelFicheroTextfield.getText().isEmpty()) {
					rutaDelFicheroTextfield.setStyle("-fx-border-width: 2px; -fx-border-color: red");
					todosLosCamposRellenados = false;
				} else {
					rutaDelFicheroTextfield.setStyle("-fx-background-color: -fx-text-box-border, -fx-background");
				}
				
				if (todosLosCamposRellenados) {
					Pair<String, String> datos = new Pair<>(rutaDelFicheroTextfield.getText(), nombreDelCuestionarioTextField.getText());
					Controlador.getInstance().action(new Contexto(Evento.importarCuestionario, datos));
				} else {
					Alert a = new Alert(AlertType.INFORMATION, "Rellene los campos para importar un cuestionario.");
					a.setHeaderText("Faltan campos por rellenar");
					a.showAndWait();
				}
			}	
		});
	}

	private void setBuscarButtonAction() {
		this.buscarButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser explorador = new FileChooser();
				File archivo = explorador.showOpenDialog(new Stage());
				
				if (archivo != null) {
					
					try {
						Alert info = new Alert(AlertType.CONFIRMATION);
						info.setTitle("Importar...");
						info.setHeaderText("¿Está seguro que desea importar este archivo?");
						info.setContentText(archivo.getPath());
						Optional<ButtonType> result = info.showAndWait();
						
						if (result.get() == ButtonType.OK) {
							rutaDelFicheroTextfield.setText(archivo.getPath());
						}

					} catch (Exception e) {
						new Exception("Error al importar el archivo");
					}
				}
			}
		});
		
	}

	@Override
	public void actualizar(Contexto contexto) {
		Alert a;
		
		switch (contexto.getEvento()) {
		case importarCuestionarioOK:
			a = new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Cuestionario importado correctamente.");
			a.showAndWait();
			
			nombreDelCuestionarioTextField.setText("");
			rutaDelFicheroTextfield.setText("");
			break;
			
		case importarCuestionarioErrorLecturaFichero:
			a = new Alert(AlertType.ERROR, "El fichero indicado no es válido.");
			a.setHeaderText("Error al importar el cuestionario");
			a.showAndWait();
			break;
			
		case importarCuestionarioErrorNombreCuestionarioYaExiste:
			a = new Alert(AlertType.ERROR, "El nombre del cuestionario indicado ya existe en la BBDD.");
			a.setHeaderText("Error al importar el cuestionario");
			a.showAndWait();
			break;
			
		case errorConexionBBDD:
			a = new Alert(AlertType.ERROR, "No ha sido posible conectarse a la base de datos.");
			a.setHeaderText("Error de conexion");
			a.showAndWait();
			break;

		default:
			break;
		}
	}

}
