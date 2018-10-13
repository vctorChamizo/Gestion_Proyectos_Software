package presentacion.vistas.cuestionario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import launcher.Main;
import negocio.cuestion.Cuestion;
import negocio.cuestionario.Cuestionario;
import negocio.respuesta.Respuesta;
import presentacion.controlador.Contexto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Evento;
import presentacion.vistas.GUI;

public class GUIResolverCuestionario extends Scene implements GUI {
	
	private Cuestionario cuestionario;
	private int numCuestionesRespondidas;
	private Button siguiente;

	private Integer indiceCuestionActual;
	private final Integer TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION = 40;
	
	/**
	 * Mapa que almacena si la cuestión con un determinado índice ha sido ya respondida o no
	 */
	private Map<Integer, Boolean> cuestionesRespondidas;
	
	/**
	 * Almacena la respuesta a cada cuestion mapeadas por el índice de la cuestión en el cuestionario
	 */
	private Map<Integer, Respuesta> respuestas;

	private GridPane panelCuestion ;
	private Text esCorrecta;

	private Object root;
	private Long currentTimeMillis;


	private Button explicacion;

	private GridPane barraInferior;

	
	public GUIResolverCuestionario(Object root) {
		super((BorderPane) root, 800, 500);
		
		ImageView inicio = new ImageView(new Image("main/resources/logo/logo.png"));

		inicio.setOpacity(0.5);
		
		this.getStylesheets().add("presentacion/vistas/css/style.css");
		
		siguiente = new Button("Siguiente");
		siguiente.setOnAction((event) -> {
			++numCuestionesRespondidas;
			if (numCuestionesRespondidas == cuestionario.getCuestiones().size())
				Controlador.getInstance().action(new Contexto(Evento.finalizarCuestionario, null));
			else Controlador.getInstance().action(new Contexto(Evento.siguienteCuestion, null));
		}); 
		siguiente.setId("siguienteCuestionButton");
		
		((BorderPane) root).setCenter(inicio);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Resolver Cuestionario");
	}

	@Override
	public void actualizar(Contexto contexto) {
		
		switch (contexto.getEvento()) {
		case resolverCuestionario:
			cuestionario = (Cuestionario) contexto.getDatos();
			numCuestionesRespondidas = 0;
			this.cuestionesRespondidas = new HashMap<>();
			respuestas = new HashMap<>();
			for (int i = 0; i < cuestionario.getCuestiones().size(); ++i) {
				this.cuestionesRespondidas.put(i, false);
			}
			mostrarCuestionario(cuestionario);
			break;
			
		case siguienteCuestion:
			mostrarCuestionario(cuestionario);
			break;
			
		case finalizarCuestionario:
			mostrarResultado();
			break;
			
		case mostrarAclaracionCuestion:
			mostrarAclaracion();
			break;
			
		default:
			break;
		}
	}
	
	private void mostrarCuestionario(Cuestionario cuestionario) {
		GridPane panelCuestionario = new GridPane();
		barraInferior = new GridPane();
		
		Random rand = new Random();
		panelCuestionario.setAlignment(Pos.CENTER);
		panelCuestionario.setHgap(10);
		panelCuestionario.setVgap(10);

		panelCuestionario.add(mostrarSiguiente(), 0, 1);
		
		this.indiceCuestionActual = rand.nextInt(cuestionario.getCuestiones().size());
		
		while (this.cuestionesRespondidas.get(indiceCuestionActual)) {
			indiceCuestionActual = rand.nextInt(cuestionario.getCuestiones().size());
		}
		
		panelCuestionario.add(mostrarCuestion(cuestionario.getCuestiones().get(indiceCuestionActual)), 0, 0);
		this.cuestionesRespondidas.put(indiceCuestionActual, true);

		
		((BorderPane) this.getRoot()).setCenter(panelCuestionario);

		panelCuestionario.add(new Separator(), 0, 5);
	}
	
	private GridPane mostrarCuestion(Cuestion cuestion) {
		
		panelCuestion = new GridPane();
		panelCuestion.setAlignment(Pos.CENTER);
		panelCuestion.setHgap(10);
		panelCuestion.setVgap(10);
		
		panelCuestion.add(new Separator(), 0, 0);
		
		TextArea enunciado = new TextArea(cuestion.getEnunciado());
		enunciado.setFont(new Font("", 18));
		enunciado.setPrefWidth(450);
		enunciado.setPrefHeight(200);
		enunciado.setWrapText(true);
		enunciado.setEditable(false);
		enunciado.setFocusTraversable(false);
		
		panelCuestion.add(enunciado, 0, 1);
	
		List<Button> botones = new ArrayList<>();
		for (int index = 0; index < cuestion.getRespuestas().size(); ++index) {
			String enunciadoDeLaRespuesta = cuestion.getRespuestas().get(index).getEnunciado();
			Button button = new Button();
			if (enunciadoDeLaRespuesta.length() > TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION) {
				Tooltip tooltip = new Tooltip(enunciadoDeLaRespuesta);
				tooltip.setWrapText(true);
				tooltip.setPrefWidth(200);
				tooltip.setId("tooltip");
				button.setTooltip(tooltip);
				enunciadoDeLaRespuesta = enunciadoDeLaRespuesta.substring(0, TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION) + "...";
			}
			
			button.setMaxWidth(40);
			button.setText(enunciadoDeLaRespuesta);
			botones.add(index, button);
			botones.get(index).setMinWidth(200);
			GridPane.setHalignment(botones.get(index), HPos.CENTER);
			panelCuestion.add(botones.get(index), 0, index + 2);
			//
			
			botones.get(index).setOnAction((event) -> {

				
			
				if(System.currentTimeMillis() - this.currentTimeMillis < cuestion.getTMinimoMs()) {
					Alert a = new Alert(AlertType.INFORMATION, "Lee la pregunta y piensa antes de responderla.", ButtonType.OK);
					a.setHeaderText("¡No tan rápido!");
					a.show();
				} else {
					//getResultado();
					getEleccion(botones);
					siguiente.setVisible(true);
					explicacion.setVisible(true);
				}
			});
		}
		
		panelCuestion.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
		});
		
		panelCuestion.add(new Separator(), 0, cuestion.getRespuestas().size() + 2);
		Label posicion = new Label((numCuestionesRespondidas + 1) + " / " + cuestionario.getCuestiones().size());

		esCorrecta = new Text("");
		esCorrecta.setFont(Font.font ("Verdana", 14));
		esCorrecta.setWrappingWidth(300);
		
		barraInferior.add(posicion, 0, 0);
		barraInferior.add(esCorrecta, 0, 1);
	
		currentTimeMillis = System.currentTimeMillis();

		GridPane.setHalignment(posicion, HPos.CENTER);
		GridPane.setHalignment(esCorrecta, HPos.CENTER);
		GridPane.setMargin(esCorrecta, new Insets(10, 0, 0, 0));
		barraInferior.setAlignment(Pos.CENTER);

		panelCuestion.add(barraInferior, 0, cuestion.getRespuestas().size() + 3);
		
		return panelCuestion;		
	}
	
	private void getEleccion(List<Button> botones) {
		String respuesta = new String();
		Cuestion cuestion = cuestionario.getCuestiones().get(this.indiceCuestionActual);
		for (int index = 0; index < botones.size(); ++index) {

			respuesta = botones.get(index).getText();
			if (botones.get(index).isArmed()) {
				
				botones.get(index).setStyle("-fx-base: rgb(0, 0, 255);");
				
				Respuesta r = cuestion.getRespuestas().get(index);
				respuestas.put(this.indiceCuestionActual, r);

				if (respuesta.equals(getSolucion(cuestion))){
					esCorrecta.setText("¡Tu respuesta es correcta!");
				} else{
					botones.get(index).setStyle("-fx-base: rgb(255, 0, 0);");
					esCorrecta.setText("Te has equivocado, la respuesta correcta es: " + getSolucion(cuestion));
				}
			} else {
				botones.get(index).setStyle("");
			}
			
			botones.get(index).setDisable(true);
			
			
			if (respuesta.equals(getSolucion(cuestion))) {
				botones.get(index).setStyle("-fx-base: rgb(0, 255, 0);");
			}
			
		}
	}
	
	private GridPane mostrarSiguiente() {
		if (numCuestionesRespondidas == cuestionario.getCuestiones().size() - 1) {
			siguiente.setText("Finalizar cuestionario");
		} else {
			siguiente.setText("Siguiente");
		}

		siguiente.setMinWidth(100);
		siguiente.setVisible(false);
	
		ImageView info = new ImageView(new Image(this.getClass().getResource("/main/resources/game/info.png").toExternalForm()));
		info.setFitHeight(25);
		info.setFitWidth(25);
		info.setPreserveRatio(true);
		
		explicacion = new Button(null, info);
		explicacion.setPadding(Insets.EMPTY);
		explicacion.setStyle("-fx-background-color: rgb(37, 183, 211);");
		explicacion.setVisible(false);
		explicacion.setOnAction((event) -> {
			Controlador.getInstance().action(new Contexto(Evento.mostrarAclaracionCuestion, null));
		});
		
		GridPane siguienteBox = new GridPane();
		siguienteBox.setAlignment(Pos.CENTER);
		siguienteBox.setHgap(10);
		siguienteBox.setVgap(10);
		
		if (!cuestionario.getCuestiones().get(numCuestionesRespondidas).getExplicacion().equalsIgnoreCase(""))
			siguienteBox.add(explicacion, 2, 0);
		siguienteBox.add(siguiente, 3, 0);
		
		barraInferior.add(siguienteBox, 0, 3);
		GridPane.setMargin(siguiente, new Insets(10, 0, 10, 0));
		GridPane.setHalignment(siguiente, HPos.CENTER);
		
		return barraInferior;
	}
	
	private void mostrarResultado() {
		GridPane panelResultado = new GridPane();
		panelResultado.setAlignment(Pos.CENTER);
		panelResultado.setHgap(10);
		panelResultado.setVgap(10);
		
		//Nombre del cuestionario
		Label nombreCuestionario = new Label(cuestionario.getNombre());
		nombreCuestionario.setFont(new Font("Baskerville", 30));
		nombreCuestionario.setStyle("-fx-font-weight: bold;");
		GridPane.setHalignment(nombreCuestionario, HPos.CENTER);
		panelResultado.add(nombreCuestionario, 0, 0);
		
		//Nota obenida en el cuestioanrio
		Label notaCuestionario = getNota();
		notaCuestionario.setFont(new Font("Baskerville", 35));
		GridPane.setHalignment(notaCuestionario, HPos.CENTER);
		panelResultado.add(notaCuestionario, 0, 1);
		
		panelResultado.add(new Separator(), 0, 2);
		
		//Tabla de aciertos y fallos según la respuesta
		ScrollPane cuestiones = new ScrollPane();
		cuestiones.setStyle("-fx-padding: 10px;");
		cuestiones.setContent(getResultado());
		cuestiones.setFitToWidth(true);
		cuestiones.setMinViewportWidth(400);
		panelResultado.add(cuestiones, 0, 3);
		
		panelResultado.add(new Separator(), 0, 4);
		
		((BorderPane) this.getRoot()).setCenter(panelResultado);
		
	}
	
	private GridPane getResultado() {
		
		GridPane panelResultado = new GridPane();
		panelResultado.setAlignment(Pos.CENTER);
		panelResultado.setHgap(20);
		panelResultado.setVgap(10);
		
		Label lCuestion = new Label("CUESTIÓN");
		lCuestion.setTextFill(Color.ROYALBLUE);
		lCuestion.setStyle("-fx-font-weight: bold;");
		GridPane.setHalignment(lCuestion, HPos.CENTER);
		panelResultado.add(lCuestion, 0, 0);
		
		Label lSolucion = new Label("SOLUCIÓN");
		lSolucion.setTextFill(Color.ROYALBLUE);
		lSolucion.setStyle("-fx-font-weight: bold;");
		GridPane.setHalignment(lSolucion, HPos.CENTER);
		panelResultado.add(lSolucion, 1, 0);
		
		Label lRespuesta = new Label("RESPUESTA");
		lRespuesta.setTextFill(Color.ROYALBLUE);
		lRespuesta.setStyle("-fx-font-weight: bold;");
		GridPane.setHalignment(lRespuesta, HPos.CENTER);
		panelResultado.add(lRespuesta, 2, 0);
		
		for (int index = 0; index < cuestionario.getCuestiones().size(); ++index) {
			Cuestion cuestion = cuestionario.getCuestiones().get(index);
			String enunciado = cuestion.getEnunciado();
			Label cuestionLabel = new Label();
			
			if (enunciado.length() > TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION) {
				Tooltip tooltip = new Tooltip(enunciado);
				tooltip.setWrapText(true);
				tooltip.setPrefWidth(200);
				tooltip.setId("tooltip");
				cuestionLabel.setTooltip(tooltip);
				enunciado = enunciado.substring(0, TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION) + "...";
			}
			
			cuestionLabel.setText(enunciado);
			GridPane.setHalignment(cuestionLabel, HPos.CENTER);
			panelResultado.add(cuestionLabel, 0, index + 1);
			
			String solucion = getSolucion(cuestion);
			Label solucionLabel = new Label();
			
			if (solucion.length() > TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION) {
				Tooltip tooltip = new Tooltip(enunciado);
				tooltip.setWrapText(true);
				tooltip.setPrefWidth(200);
				tooltip.setId("tooltip");
				solucionLabel.setTooltip(tooltip);
				solucion = solucion.substring(0, TAM_MAX_DEL_ENUNCIADO_AL_MOSTRAR_SOLUCION) + "...";
			}
			
			solucionLabel.setText(solucion);
			GridPane.setHalignment(solucionLabel, HPos.CENTER);
			panelResultado.add(solucionLabel, 1, index + 1);

			ImageView imagenRespuesta = new ImageView(getImagenRespuesta(index));
			imagenRespuesta.setFitHeight(20);
			imagenRespuesta.setFitWidth(20);
			imagenRespuesta.setPreserveRatio(true);
			GridPane.setHalignment(imagenRespuesta, HPos.CENTER);
			panelResultado.add(imagenRespuesta, 2, index + 1);

			Respuesta respuesta = respuestas.get(index);
			
		
			if (!respuesta.esCorrecta()) {
				cuestion.sumarVecesFallada();
			}
			
			cuestion.sumarVecesContestada();
			Controlador.getInstance().action(new Contexto(Evento.actualizarCuestion, cuestion));

		}
		
		return panelResultado;	
	}
	
	private String getSolucion(Cuestion cuestion) {
		String solucion = new String();
		
		for (int index = 0; index < cuestion.getRespuestas().size(); ++index) {
			if (cuestion.getRespuestas().get(index).esCorrecta())
				solucion = cuestion.getRespuestas().get(index).getEnunciado();
		}
		
		return solucion;
	}


	private Image getImagenRespuesta(int indiceCuestionEnCuestionario) {
		if (respuestas.get(indiceCuestionEnCuestionario).esCorrecta()) {
			return new Image(this.getClass().getResource("/main/resources/game/correct.png").toExternalForm());
		} else {
			return new Image(this.getClass().getResource("/main/resources/game/error.png").toExternalForm());
		}
	}

	
	private Label getNota() {

		double aciertos = 0;
		for (int i = 0; i < cuestionario.getCuestiones().size(); ++i)
			if(respuestas.get(i).esCorrecta())
				aciertos++;
		
		double nota = (aciertos / (double) cuestionario.getCuestiones().size()) * 10;
		String aux = new String();
		if (((aciertos * 10) % cuestionario.getCuestiones().size()) == 0 )
			aux += (int) nota;
		else aux = String.format("%.2f", nota);
		Label lNota = new Label(aux + " / 10");
		lNota.setStyle("-fx-font-weight: bold;");
		
		if(nota >= 7.50)
			lNota.setTextFill(Color.LIMEGREEN);
		
		else if(nota < 7.50 && nota >= 5.00)
			lNota.setTextFill(Color.GOLD);
		
		else if(nota < 5.00 && nota >= 2.50)
			lNota.setTextFill(Color.DARKORANGE);
		
		else lNota.setTextFill(Color.RED);
		
		DropShadow sombra = new DropShadow();
		sombra.setRadius(4);
		sombra.setSpread(1);
		sombra.setColor(Color.BLACK);
		lNota.setEffect(sombra);
		
		return lNota;
	}

	
	private void mostrarAclaracion() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Explicación");
		alert.setHeaderText(cuestionario.getCuestiones().get(numCuestionesRespondidas).getEnunciado());
		alert.setContentText(cuestionario.getCuestiones().get(numCuestionesRespondidas).getExplicacion());

		alert.showAndWait();
	}

}
