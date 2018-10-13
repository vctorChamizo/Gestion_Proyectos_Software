package presentacion.vistas.cuestionario;

import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import launcher.Main;
import negocio.cuestion.Cuestion;
import negocio.cuestionario.Cuestionario;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class GUIAltaCuestionario extends Scene implements GUI {
	private TextField nombreTextField;
	private Label nombreLabel;
	
	private Label tablaLabel;
	private ListView<Cuestion> lista;
	private ObservableList<Cuestion> cuestiones;
	private ObservableList<Cuestion> cuestionesSeleccionadas;
	private Button botonAgregarCuestion;
	
	private Button botonCrear;
	private Button botonCancelar;
	
	private BorderPane nombrePane;
	private BorderPane tablaPane;
	private BorderPane botonesPane;
	private BorderPane mainPane;
	
	private Cuestionario cuestionario;
	
	
	public GUIAltaCuestionario(Object root) {
		super((BorderPane) root, 800, 500);
		
		ImageView inicio = new ImageView(new Image("main/resources/logo/logo.png"));
		inicio.setOpacity(0.5);
		
		cuestionario = new Cuestionario();
		
		initGUI(root);
		
		mainPane = new BorderPane();
		mainPane.setTop(nombrePane);
		mainPane.setCenter(tablaPane);
		mainPane.setBottom(botonesPane);
		
		this.getStylesheets().add("presentacion/vistas/css/style.css");
		
		((BorderPane) root).setCenter(mainPane);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Alta Cuestionario");
	}
	
	private void initGUI(Object root) {
		
		initNombrePane();
		initTablaPane(root);
		initBotonesPane(root);
	}
	
	

	private void initNombrePane() {
		nombrePane = new BorderPane();
		nombreTextField = new TextField();
		nombreLabel = new Label("Nombre del cuestionario: ");
		nombreLabel.setFont(new Font("Baskerville", 20));
	
		BorderPane.setAlignment(nombreLabel, Pos.TOP_LEFT);
		BorderPane.setAlignment(nombreTextField, Pos.TOP_CENTER);
		BorderPane.setMargin(nombreTextField, new Insets(30, 200, 8, 8));
		BorderPane.setMargin(nombreLabel, new Insets(30, 8, 8, 120));
		
		nombrePane.setLeft(nombreLabel);
		nombrePane.setCenter(nombreTextField);
		
	}
	

	private void initTablaPane(Object root) {
		tablaLabel = new Label("Seleccione las cuestiones del cuestionario");
		tablaLabel.setFont(new Font("Baskerville", 30));
		tablaPane = new BorderPane();
		
		BorderPane.setMargin(tablaPane, new Insets(50, 8, 8, 8));
		BorderPane.setAlignment(tablaLabel, Pos.TOP_CENTER);
	
		lista = new ListView<>();
		cuestiones = FXCollections.observableArrayList();
		lista.setItems(cuestiones);
		lista.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				
		lista.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2) {
					cuestionesSeleccionadas = lista.getSelectionModel().getSelectedItems();
					
					cuestionario.addCuestiones(cuestionesSeleccionadas);
					cuestiones.removeAll(cuestionesSeleccionadas);
					cuestionesSeleccionadas = null;
				}
			}
		});
		
		botonAgregarCuestion = new Button("Agregar al cuestionario");
		botonAgregarCuestion.setId("addCuestionAlCuestionarioButton");
		
		botonAgregarCuestion.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				cuestionesSeleccionadas = lista.getSelectionModel().getSelectedItems();
			
				if (cuestionesSeleccionadas != null && cuestionesSeleccionadas.size() > 0) {
					System.out.println(System.getProperty("line.separator"));
					
					cuestionario.addCuestiones(cuestionesSeleccionadas);
					cuestiones.removeAll(cuestionesSeleccionadas);
					cuestionesSeleccionadas = null;
				
				} else {
					Alert a = new Alert(AlertType.ERROR,
							"Seleccione al menos una cuestión para añadirla al cuestionario.");
					a.setHeaderText("No hay ninguna cuestión seleccionada");
					a.showAndWait();
				}	
			}
		});
		
		BorderPane.setAlignment(botonAgregarCuestion, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(botonAgregarCuestion, new Insets(8, 8, 8, 8));
		
		tablaPane.setTop(tablaLabel);
		tablaPane.setCenter(lista);
		tablaPane.setBottom(botonAgregarCuestion);
	}
	
	private void initBotonesPane(Object root) {
		botonCrear = new Button("Crear cuestionario");
		botonCrear.setId("addCuestionarioButton");
		
		botonCancelar = new Button("Cancelar");
		botonCancelar.setId("cancelarAltaCuestionarioBtn");
		
		BorderPane.setMargin(botonCancelar, new Insets(8, 8, 8, 8));
		BorderPane.setMargin(botonCrear, new Insets(8, 8, 8, 8));
		
		botonesPane = new BorderPane();
		botonesPane.setRight(botonCrear);
		botonesPane.setLeft(botonCancelar);
		
		botonCancelar.setOnAction((event) -> {
			
			Alert a = new Alert(AlertType.CONFIRMATION, "¿Está seguro que desea cancelar el cuestionario?");
			a.setHeaderText("Cancelar Cuestionario");
			Optional<ButtonType> result = a.showAndWait();

			if (result.get() == ButtonType.OK) {
				nombreTextField.setText("");
				cuestionario.getCuestiones().clear();
				cuestiones.clear();
				Controlador.getInstance().action(new Contexto(Evento.leerTodasCuestiones, null));
			}
		});
		
		
		botonCrear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				//Pintar el borde en rojo si no se ha rellenado el textfield
				if (nombreTextField.getText() == null || nombreTextField.getText().isEmpty()) {
					Alert a  = new Alert(AlertType.ERROR, "Rellene los campos para crear un cuestionario.");
					a.setHeaderText("Faltan campos por rellenar");
					a.showAndWait();
					nombreTextField.setStyle("-fx-border-width: 2px; -fx-border-color: red");
				} else {
					nombreTextField.setStyle("-fx-border-color: lightgray");
					cuestionario.setNombre(nombreTextField.getText());
					Controlador.getInstance().action(new Contexto(Evento.altaCuestionario, cuestionario));
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Contexto contexto) {
		Alert a;
		
		switch(contexto.getEvento()) {
		case leerTodasCuestionesOK:
			cuestiones.addAll((List<Cuestion>) contexto.getDatos());
			break;
			
		case altaCuestionarioErrorCuestionarioYaExistente:
			 a = new Alert(AlertType.ERROR,
					"Ya existe un cuestionario con nombre \"" + cuestionario.getNombre() + "\"");
			a.setHeaderText("No se ha podido crear el cuestionario");
			a.showAndWait();
			break;
			
		case altaCuestionarioErrorCuestionarioConMenosDeDosRespuestas:
			a = new Alert(AlertType.ERROR,
					"El cuestionario debe tener como mínimo 2 cuestiones, añada más cuestiones para crearlo.");
			a.setHeaderText("No se ha podido crear el cuestionario");
			a.showAndWait();
			break;
			
		case altaCuestionarioErrorModificacionConcurrente:
			a = new Alert(AlertType.ERROR,
					"Ha habido un error al crear el cuestionario, inténtelo de nuevo.");
			a.setHeaderText("No se ha podido crear el cuestionario");
			a.showAndWait();
			break;
			
		case altaCuestionarioOK:
			a  = new Alert(AlertType.INFORMATION, "Se ha añadido el nuevo cuestionario a la base de datos.");
			a.setHeaderText("Cuestionario creado correctamente");
			a.showAndWait();
		
			//Dejar el nombre del cuestionario sin rellenar y mostrar todas las cuestiones
			//por si el usuario quiere dar de alta otro cuestionario
			nombreTextField.setText("");
			cuestionario = new Cuestionario();
			cuestiones.clear();
			Controlador.getInstance().action(new Contexto(Evento.leerTodasCuestiones, null));
			break;
			
		case errorConexionBBDD:
			a = new Alert(AlertType.ERROR,
					"No ha sido posible conectarse a la base de datos.");
			a.setHeaderText("Error de conexion");
			a.showAndWait();
			break;
			
			default:		
				break;
		}
	}

}
