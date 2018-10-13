package presentacion.vistas.cuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import launcher.Main;
import negocio.cuestion.Cuestion;
import negocio.respuesta.Respuesta;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

public class GUIAltaCuestion extends Scene implements GUI {

	private BorderPane borderPaneText;
	private ScrollPane sc;
	private TextField enunciadoTextField;
	private TextField respuestaCorrectaTextField;

	private TextField explicacionCuestionTextField;

	private TextField tMinimoTextField;

	private FlowPane respuestasAltPane;
	private List<BorderPane> respuestasAlternativas;
	private Button respuestasButton;

	private Cuestion cuestion;

	public GUIAltaCuestion(Object root) {

		super((BorderPane) root, 800, 500);

		sc = new ScrollPane();
		sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		cuestion = new Cuestion();
		respuestasAlternativas = new ArrayList<>();

		borderPaneText = new BorderPane();
		borderPaneText.setId("borderPaneText");

		sc.setContent(borderPaneText);

		BorderPane borderPaneButton = new BorderPane();
		borderPaneButton.setId("borderPaneButton");

		initGUICenter();
		initGUIBottom(borderPaneButton);

		((BorderPane) root).setCenter(sc);
		((BorderPane) root).setBottom(borderPaneButton);

		this.getStylesheets().add("presentacion/vistas/css/style.css");

		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Cuestión");

	}

	private void initGUICenter() {

		// Enunciado
		Label enunciadoLabel = new Label("Enunciado:");
		enunciadoTextField = new TextField();
		enunciadoLabel.setId("addCuestionLabel");
		enunciadoTextField.setId("addCuestionTextField");
		BorderPane enunciadoPane = new BorderPane();
		enunciadoPane.setId("enunciadoPane");
		enunciadoPane.setLeft(enunciadoLabel);
		enunciadoPane.setCenter(enunciadoTextField);

		// Respuesta correcta
		Label respuestaCorrectaLabel = new Label("Respuesta correcta:");
		respuestaCorrectaTextField = new TextField();
		respuestaCorrectaLabel.setId("addCuestionLabel");
		respuestaCorrectaTextField.setId("addCuestionTextField");
		BorderPane respuestaCorrectaPane = new BorderPane();
		respuestaCorrectaPane.setId("respuestaCorrectaPane");
		respuestaCorrectaPane.setLeft(respuestaCorrectaLabel);
		respuestaCorrectaPane.setCenter(respuestaCorrectaTextField);


		// Explicacion
		Label explicacionCuestionLabel = new Label("Explicación respuesta:");
		explicacionCuestionTextField = new TextField();
		explicacionCuestionLabel.setId("addCuestionLabel");
		explicacionCuestionTextField.setId("addCuestionTextField");
		BorderPane explicacionCuestionPane = new BorderPane();
		explicacionCuestionPane.setId("respuestaCorrectaPane");
		explicacionCuestionPane.setLeft(explicacionCuestionLabel);
		explicacionCuestionPane.setCenter(explicacionCuestionTextField);
		
		
	


		// Tiempo minimo
		Label tMinimoLabel = new Label("Tiempo mínimo de respuesta (ms):");
		tMinimoTextField = new TextField();
		tMinimoTextField.setPromptText("1200");
		tMinimoLabel.setId("addCuestionLabel");
		tMinimoTextField.setId("addCuestionTextField");

		BorderPane tMinimoPane = new BorderPane();
		tMinimoPane.setId("respuestaCorrectaPane");
		tMinimoPane.setLeft(tMinimoLabel);
		tMinimoPane.setCenter(tMinimoTextField);

		// Boton "Añadir Respuesta"
		Label respuestasLabel = new Label("Respuestas:");
		respuestasLabel.setId("addCuestionLabel");
		respuestasButton = new Button("Añadir respuesta alternativa");

		respuestasButton.setId("addRespuestasButton");
		respuestasButton.setOnAction((event) -> {

			addTextFieldRespuestaAlternativa();
		});

		BorderPane addRespuestaPane = new BorderPane();
		addRespuestaPane.setId("addRespuestaPane");
		addRespuestaPane.setLeft(respuestasLabel);

		VBox cabecera = new VBox();

		cabecera.getChildren().addAll(enunciadoPane, new Label(""), explicacionCuestionPane, new Label(""),
				tMinimoPane, new Label(""),
				respuestaCorrectaPane, new Label(""), addRespuestaPane);


		respuestasAltPane = new FlowPane();
		respuestasAltPane.setStyle("-fx-background-colot: red");

		borderPaneText.setTop(cabecera);
		borderPaneText.setLeft(respuestasAltPane);

		addTextFieldRespuestaAlternativa();
	}

	@SuppressWarnings("static-access")
	private void addTextFieldRespuestaAlternativa() {

		if (respuestasAlternativas.size() < 7) {
			respuestasButton.setDisable(false);
			
			BorderPane newRespuesta = new BorderPane();
			newRespuesta.setStyle("-fx-padding: 10px");

			TextField respuestaText = new TextField();
			respuestaText.setId("addRespuestaTextField");
			respuestaText.setPadding(new Insets(5, 5, 0, 0));
			Button borrar = new Button("X");
			
			borrar.setId("borrarRespuestaAlternativaBtn");
			
			borrar.setOnAction((event) -> {

				respuestasButton.setDisable(false);
				if (respuestasAlternativas.size() > 1) {
					respuestasAltPane.getChildren().remove(borrar.getParent());
					respuestasAlternativas.remove(borrar.getParent());
				} else {
					Alert a = new Alert(AlertType.ERROR,
							"Una cuestión debe tener al menos 2 respuestas (contando la correcta).");
					a.setHeaderText("Error al eliminar la respuesta");
					a.showAndWait();
				}
			});

			newRespuesta.setLeft(respuestaText);
			newRespuesta.setMargin(respuestaText, new Insets(5, 10, 0, 0));
			newRespuesta.setCenter(borrar);
			

			respuestasAlternativas.add(0, newRespuesta);
			respuestasAltPane.getChildren().clear();
			respuestasAltPane.getChildren().add(respuestasButton);
			for (BorderPane resp : respuestasAlternativas) {
				respuestasAltPane.getChildren().add(resp);
			}
			respuestaText.requestFocus();
			
			if (respuestasAlternativas.size() == 7) 
				respuestasButton.setDisable(true);
		} 
	}

	private void initGUIBottom(BorderPane borderPane) {

		Button createButton = new Button("Crear");
		Button cancelButton = new Button("Cancelar");
		createButton.setId("addCuestionButton");
		cancelButton.setId("cancelarAltaCuestionBtn");


		cancelButton.setOnAction((event) -> {

			Alert a = new Alert(AlertType.CONFIRMATION, "¿Está seguro que desea cancelar la cuestión?");
			a.setHeaderText("Cancelar cuestión");

			Optional<ButtonType> result = a.showAndWait();

			if (result.get() == ButtonType.OK) {
				enunciadoTextField.setText("");
				respuestaCorrectaTextField.setText("");
				respuestasAltPane.getChildren().clear();
				respuestasAlternativas.clear();
				
				addTextFieldRespuestaAlternativa();
			}
		});


		createButton.setOnAction((event) -> {

			boolean todosLosCamposRellenados = true;

			//Comprobacion que los campos esten rellenos.
			if (enunciadoTextField.getText().trim().isEmpty()) {
				enunciadoTextField.setStyle("-fx-border-width: 1px; -fx-border-color: red");
				todosLosCamposRellenados = false;
			}
			else
				enunciadoTextField.setStyle("-fx-border-color: lightgray");


			// Pintar el borde en rojo si no es correcto el tiempo minimo
			String tMinimoTxt = tMinimoTextField.getText().trim();
			Integer tiempoMinimo = null;
			if (!tMinimoTxt.equalsIgnoreCase("")) {
				try { tiempoMinimo = Integer.parseInt(tMinimoTxt); 	} catch (Exception e) { }
				if (tiempoMinimo == null) {
					tMinimoTextField.setStyle("-fx-border-width: 2px; -fx-border-color: red");
					todosLosCamposRellenados = false;
				} else {
					tMinimoTextField.setStyle("-fx-border-color: lightgray");
				}
			}
			else {
				tiempoMinimo = 0;
				tMinimoTextField.setStyle("-fx-border-color: lightgray");
			}

			// Pintar el borde en rojo si no se ha rellenado el textfield
			if (respuestaCorrectaTextField.getText().trim().isEmpty()) {
				respuestaCorrectaTextField.setStyle("-fx-border-width: 1px; -fx-border-color: red");
				todosLosCamposRellenados = false;
			}
			else
				respuestaCorrectaTextField.setStyle("-fx-border-color: lightgray");
			
			for (BorderPane respuestaAlternativaTextField : respuestasAlternativas) {
				
				if (((TextField) respuestaAlternativaTextField.getLeft()).getText().trim().isEmpty()) {
					respuestaAlternativaTextField.getLeft().setStyle("-fx-border-width: 1px; -fx-border-color: red");
					todosLosCamposRellenados = false;
				}
				else
					respuestaAlternativaTextField.getLeft().setStyle("-fx-border-color: lightgray");
			}

			
			if (todosLosCamposRellenados) {
				
				//Añadimos las respuestas a al Objeto respuesta
				List<Respuesta> respuestas = new ArrayList<>();
				respuestas.add(new Respuesta(respuestaCorrectaTextField.getText(), true));
				for (BorderPane respuestaAlternativaTextField : respuestasAlternativas) {
					respuestas.add(new Respuesta(((TextField) respuestaAlternativaTextField.getLeft()).getText(), false));
				}
				
				//Añadimos los datos de cuestion para poder crearla
				cuestion.setRespuestas(respuestas);
				cuestion.setEnunciado(enunciadoTextField.getText());

				
				if(explicacionCuestionTextField.getText().isEmpty())
					cuestion.setExplicacion("");
				else
					cuestion.setExplicacion(explicacionCuestionTextField.getText());

				//Limpiamos los campos para reiniciar la interfaz
				enunciadoTextField.setText("");
				respuestaCorrectaTextField.setText("");
				explicacionCuestionTextField.setText("");

				cuestion.setTMinimoMs(tiempoMinimo);
				
				enunciadoTextField.setText("");
				respuestaCorrectaTextField.setText("");
				tMinimoTextField.setText("");
				respuestasAltPane.getChildren().clear();
				respuestasAlternativas.clear();
				
				addTextFieldRespuestaAlternativa();
				enunciadoTextField.requestFocus();
				
				Controlador.getInstance().action(new Contexto(Evento.altaCuestion, cuestion));
			} else {
				Alert a = new Alert(AlertType.INFORMATION, "Rellene los campos para crear una cuestión.");
				a.setHeaderText("Faltan campos por rellenar");
				a.showAndWait();
			}

		});
		borderPane.setRight(createButton);
		borderPane.setLeft(cancelButton);
	}

	@Override
	public void actualizar(Contexto contexto) {
		Alert a;

		switch (contexto.getEvento()) {
		case altaCuestionErrorCuestionYaExistente:
			a = new Alert(AlertType.ERROR,
					"Ya existe una cuestión con el enunciado \"" + cuestion.getEnunciado() + "\"");
			a.setHeaderText("No se ha podido crear la cuestión");
			a.showAndWait();
			break;

		case altaCuestionErrorCuestionConMenosDeDosRespuestas:
			a = new Alert(AlertType.ERROR,
					"La cuestión debe tener como mínimo 2 respuestas, añada mas respuestas para crear la cuestión.");
			a.setHeaderText("No se ha podido crear la cuestión");
			a.showAndWait();
			break;
			
		case altaCuestionErrorCuestionTiempoMinimoNegativo:
			a = new Alert(AlertType.ERROR, "La cuestión debe tener un tiempo mínimo de respuesta positivo.");
			a.setHeaderText("No se ha podido crear la cuestión");
			a.showAndWait();
			break;

		case altaCuestionErrorModificacionConcurrente:
			a = new Alert(AlertType.ERROR, "Ha habido un error al crear la cuestión, inténtelo de nuevo.");
			a.setHeaderText("No se ha podido crear la cuestión");
			a.showAndWait();
			break;

		case altaCuestionOK:
			a = new Alert(AlertType.INFORMATION, "Se ha añadido la nueva cuestión a la base de datos.");
			a.setHeaderText("Cuestión creada correctamente");
			a.showAndWait();

			// Dejar el enunciado de la cuestion y de la respuesta sin rellenar
			// y quitar todos los textfields de respuestas alternativas
			// por si el usuario quiere dar de alta otra cuestion
			cuestion = new Cuestion();
			enunciadoTextField.setText("");
			respuestaCorrectaTextField.setText("");

			/*
			 * Iterator<Node> it =
			 * respuestaAlternativaPane.getChildren().iterator();
			 * while(it.hasNext()) { Node node = it.next(); if (!(node
			 * instanceof Button)) { it.remove(); } }
			 */
			break;

		case errorConexionBBDD:
			a = new Alert(AlertType.ERROR, "No ha sido posible conectarse a la base de datos.");
			a.setHeaderText("Error de conexión");
			a.showAndWait();
			break;

		default:
			break;
		}
	}
}