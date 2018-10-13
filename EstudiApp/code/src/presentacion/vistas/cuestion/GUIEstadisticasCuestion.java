package presentacion.vistas.cuestion;

import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import launcher.Main;
import negocio.cuestion.Cuestion;
import presentacion.controlador.Contexto;
import presentacion.vistas.GUI;

public class GUIEstadisticasCuestion extends Scene implements GUI {
	
	private GridPane mainPanel;
	private ObservableList<Cuestion> cuestiones;
	private ListView<Cuestion> lista;
	private PieChart chart;
	private ObservableList<PieChart.Data> pieChartData;
	
	public GUIEstadisticasCuestion(Object root) {
		
		super((BorderPane) root, 800, 500);
		
		mainPanel = new GridPane();
		initGUI(root);
		
		this.getStylesheets().add("presentacion/vistas/css/style.css");
		
		((BorderPane) root).setCenter(mainPanel);
		Main.getStage().setScene(this);
		Main.getStage().setTitle("EstudiApp - Estadísticas de cuestiones");
	}

	private void initGUI(Object root) {
		setBottom(root);
		setCenter(root);
	}

	private void setCenter(Object root) {
	
		this.chart = new PieChart();
		this.pieChartData = FXCollections.observableArrayList();
		
		GridPane.setMargin(chart, new Insets(10, 0, 15, 0));
		mainPanel.getChildren().add(chart);
	}

	private void setBottom(Object root) {
		this.lista = new ListView<>();
		cuestiones = FXCollections.observableArrayList();
		Label label = new Label("Seleccione la cuestión");
		
		lista.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Cuestion cuestionSeleccionada = lista.getSelectionModel().getSelectedItem();
			
				if (cuestionSeleccionada != null) {
					if (cuestionSeleccionada.getVecesContestada() > 0) {
						int numeroDeVecesRespondida = cuestionSeleccionada.getVecesContestada();
						int numeroDeFallos = cuestionSeleccionada.getVecesFallada();
						int numeroDeAciertos = numeroDeVecesRespondida - numeroDeFallos;
						
						pieChartData.clear();
						PieChart.Data numeroDeFallosData = new PieChart.Data(String.valueOf(numeroDeFallos), numeroDeFallos);
						PieChart.Data numeroDeAciertosData = new PieChart.Data(String.valueOf(numeroDeAciertos), numeroDeAciertos);
						
						pieChartData.add(numeroDeFallosData);
						pieChartData.add(numeroDeAciertosData);
						
						chart.setData(pieChartData);
						
						Set<Node> legendLabels = chart.lookupAll("Label.chart-legend-item");
						int i = 0;
						
						for (Node legendLabel : legendLabels) {
							Label label = (Label) legendLabel;
							if (i == 0) {
								label.setText("Número de fallos");
							} else if (i == 1) {
								label.setText("Número de aciertos");
							}
							++i;
							
							label.setFont(new Font("Times New Roman", 18));
						}
						chart.autosize();
						
															
					} else {
						chart.getData().clear();
						Alert a = new Alert(AlertType.ERROR, "La cuestión aún no ha sido respondida en ningún cuestionario.");
						a.setTitle("Error");
						a.setHeaderText("No es posible mostrar estadísticas de la cuestión seleccionada");
						a.showAndWait();
					}
				}
			}
		});
		
		lista.setItems(cuestiones);
		label.setFont(new Font("Times New Roman", 20));
		StackPane stackPane = new StackPane();

		StackPane.setAlignment(label, Pos.TOP_CENTER);
		stackPane.getChildren().add(label);

		StackPane.setMargin(lista, new Insets(50, 30, 50, 80));
		StackPane.setMargin(label, new Insets(10, 25, 0, 80));
		lista.setMaxWidth(200);
		lista.setMinWidth(200);
		stackPane.getChildren().add(lista);
		
		this.mainPanel.add(stackPane, 1, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Contexto contexto) {
		switch (contexto.getEvento()) {
		case leerTodasCuestionesOK:
			cuestiones.addAll((List<Cuestion>) contexto.getDatos());
			break;
			
		case leerTodasCuestionesNoExisteNinguna:
			Alert a = new Alert(AlertType.ERROR, "No hay cuestiones guardadas.");
			a.setTitle("Error: No hay cuestiones guardadas");
			a.setHeaderText("Ha ocurrido un error");
			a.showAndWait();
			break;
		default:
			break;
		}
	}
}
