package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main class that runs UI
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class Main extends Application {

	Button save = new Button("Save");//Saved screen
	Button remove = new Button("Remove");//Removed screen
	Scene choice;

	@Override
	public void start(Stage arg0) throws Exception {
		// The saved and removed scene after editing
		savedScene(arg0);
		removedScene(arg0);

		// The first scene
		BorderPane root = this.getFirstScene(arg0, choicesScene(arg0));
		Scene mainScene = new Scene(root, 300, 200);
		arg0.setTitle("Milk Weights");
		arg0.setScene(mainScene);
		arg0.show();
	}

	public Scene savedScene(Stage arg0) {
		// Saved
		Text saved = new Text();
		saved.setText("Saved!");
		saved.setFill(Color.BLACK);
		saved.setStyle("-fx-font: 100 arial;");
		Button continueButton = new Button("Continue");
		continueButton.setOnAction(e -> arg0.setScene(choice));
		VBox box = new VBox();
		box.getChildren().addAll(saved, continueButton);
		box.setAlignment(Pos.CENTER);
		Scene savedScene = new Scene(box, 300, 200);
		this.save.setOnAction(e -> arg0.setScene(savedScene));
		return savedScene;
	}

	public Scene removedScene(Stage arg0) {
		// Removed
		Text removed = new Text();
		removed.setText("Removed!");
		removed.setFill(Color.BLACK);
		removed.setStyle("-fx-font: 100 arial;");
		Button buttonContinue = new Button("Continue");
		buttonContinue.setOnAction(e -> arg0.setScene(choice));
		VBox removedBox = new VBox();
		removedBox.getChildren().addAll(removed, buttonContinue);
		removedBox.setAlignment(Pos.CENTER);
		Scene removedScene = new Scene(removedBox, 500, 200);
		this.remove.setOnAction(e -> arg0.setScene(removedScene));
		return removedScene;
	}

	public Scene choicesScene(Stage arg0) {
		// Choices scene
		Button farm = new Button("Farm Report");
		Button month = new Button("Month Report");
		Button annual = new Button("Annual Report");
		Button data = new Button("Data Range Report");
		Button edit = new Button("Edit Data");
		HBox hBox = new HBox();
		hBox.getChildren().addAll(farm, month, annual, data, edit);
		VBox vBox = new VBox();
		Button close = new Button("Close");
		vBox.getChildren().addAll(new Label("Choose one: "), hBox, close);
		choice = new Scene(vBox, 500, 100);

		// Gets the sixth scene, Edit data
		Scene sixthScene = getSixthScene(arg0, choice);
		// Gets the fifth scene, Data Range Report
		Scene fifthScene = getFifthScene(arg0, choice);
		// Gets the forth scene, Monthly report
		Scene forthScene = getForthScene(arg0, choice);
		// Gets the third scene, Annual Report
		Scene thirdScene = getThirdScene(arg0, choice);
		// Gets the second scene, farm report
		Scene secondScene = getSecondScene(arg0, choice);

		// Which choice was pressed
		farm.setOnAction(e -> arg0.setScene(secondScene));
		month.setOnAction(e -> arg0.setScene(forthScene));
		annual.setOnAction(e -> arg0.setScene(thirdScene));
		data.setOnAction(e -> arg0.setScene(fifthScene));
		edit.setOnAction(e -> arg0.setScene(sixthScene));
		close.setOnAction(e -> arg0.close());
		return choice;
	}
	
	public PieChart getPieChartWithFarm() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Farm1", 10), new PieChart.Data("Farm2", 20),
				new PieChart.Data("Farm3", 30), new PieChart.Data("Farm4", 40));
		PieChart chart = new PieChart(pieChartData);
		return chart;
	}

	public VBox getTable() {
		// Table for farm, percentage, and weight
		TableView<String> table = new TableView<String>();
		TableColumn<String, String> farmTable = new TableColumn<String, String>("Farm");
		TableColumn<String, String> percent = new TableColumn<String, String>("Total percentage");
		TableColumn<String, String> totle = new TableColumn<String, String>("Total Weight");
		farmTable.setMinWidth(100);
		percent.setMinWidth(100);
		totle.setMinWidth(100);
		table.getColumns().addAll(farmTable, percent, totle);
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(table);
		return vbox;
	}

	public BarChart getBarChart() {
		// Bar Chart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Milk Weights");
		BarChart barChart = new BarChart(xAxis, yAxis);
		XYChart.Series dataSeries1 = new XYChart.Series();
		dataSeries1.setName("Milk Weights");
		dataSeries1.getData().add(new XYChart.Data("Min", 100));
		dataSeries1.getData().add(new XYChart.Data("Average", 200));
		dataSeries1.getData().add(new XYChart.Data("Max", 300));
		dataSeries1.getData().add(new XYChart.Data("Total", 400));
		barChart.getData().add(dataSeries1);
		return barChart;
	}

	public Button goBack(Stage arg0) {
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> arg0.setScene(choice));
		return goBack;
	}
	
	public PieChart getPieChartWithMonth() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Jan", 5), new PieChart.Data("Feb", 10),
				new PieChart.Data("Mar", 5), new PieChart.Data("Apr", 15),
				new PieChart.Data("May", 10), new PieChart.Data("Jun", 5),
				new PieChart.Data("Jul", 15), new PieChart.Data("Aug", 5),
				new PieChart.Data("Sep", 10), new PieChart.Data("Oct", 5),
				new PieChart.Data("Nov", 10), new PieChart.Data("Dec", 10));
		PieChart chart = new PieChart(pieChartData);
		return chart;
	}
	/**
	 * Returns the fifth scene, Edit data
	 * 
	 * @param arg0   - arg0
	 * @param choice - the scene with choices
	 * @return the fifth scene, Edit data
	 */
	public Scene getSixthScene(Stage arg0, Scene choice) {
		Scene sixthScene;

		Text sixthText = new Text();
		sixthText.setText("Edit your farm data");
		sixthText.setFill(Color.BLACK);
		sixthText.setStyle("-fx-font: 20 arial;");

		Text input = new Text();
		input.setText("Input your data");
		input.setFill(Color.BLACK);
		input.setStyle("-fx-font: 10 arial;");

		HBox farm = new HBox();
		Label farmLabel = new Label("Farm:     ");
		ComboBox<String> farmCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Farm1", "Farm2", "Farm3"));
		farm.getChildren().addAll(farmLabel, farmCombo);

		Label data = new Label("Data:     ");
		TilePane r = new TilePane();
		// create a date picker
		DatePicker d = new DatePicker();
		// add button and label
		r.getChildren().add(d);
		VBox calendar = new VBox();
		calendar.getChildren().addAll(data, r);

		Label milk = new Label("Milk weight:     ");
		TextField textField = new TextField();
		HBox inputWeight = new HBox();
		inputWeight.getChildren().addAll(milk, textField);

		Label spacing = new Label("          ");
		HBox saveRemove = new HBox();
		saveRemove.getChildren().addAll(this.save, spacing, this.remove);

		VBox sixthBox = new VBox();
		sixthBox.getChildren().addAll(sixthText, input, farm, calendar, inputWeight, 
				saveRemove, goBack(arg0));

		// Return second scene
		sixthScene = new Scene(sixthBox, 250, 200);
		return sixthScene;
	}

	/**
	 * Returns the fifth scene, Data Range Report.
	 * 
	 * @param arg0   - arg
	 * @param choice - the scene with choices
	 * @return the fifth scene, Data Range Report
	 */
	public Scene getFifthScene(Stage arg0, Scene choice) {
		Scene fifthScene;
		Text fifthText = new Text();
		fifthText.setText("Data Range Report");
		fifthText.setFill(Color.BLACK);
		fifthText.setStyle("-fx-font: 20 arial;");

		// ComboBox
		Label data = new Label("Data Range:");
		TilePane r = new TilePane();

		// create a date picker
		DatePicker d = new DatePicker();
		// add button and label
		r.getChildren().add(d);
		Button reload = new Button("Reload");
		HBox hBoxAll = new HBox();
		VBox calendar = new VBox();
		hBoxAll.getChildren().addAll(r);
		calendar.getChildren().addAll(data, hBoxAll);

		PieChart pieChart = getPieChartWithFarm();
		pieChart.setTitle("Annual Percentage");
		HBox charts = new HBox();
		charts.getChildren().addAll(getTable(), pieChart);
		
		// Go back to choices
		VBox fifthBox = new VBox();
		fifthBox.getChildren().addAll(fifthText, calendar, reload, charts, goBack(arg0));

		// Return fifth scene
		fifthScene = new Scene(fifthBox, 1000, 1500);
		return fifthScene;
	}

	/**
	 * Returns the forth scene, monthly report.
	 * 
	 * @param arg0   - arg
	 * @param choice - scene with choices
	 * @return the forth scene, monthly report
	 */
	public Scene getForthScene(Stage arg0, Scene choice) {
		Scene forthScene;
		Text forthText = new Text();
		forthText.setText("Monthly Report");
		forthText.setFill(Color.BLACK);
		forthText.setStyle("-fx-font: 20 arial;");

		// ComboBox
		Label farm = new Label("Month     ");
		ComboBox<String> farmCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Farm1", "Farm2", "Farm3"));
		Label year = new Label("          Year     ");
		ComboBox<String> yearCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Year1", "Year2", "Year3"));
		Label space = new Label("          ");
		Button reload = new Button("Reload");
		HBox hBoxSpace = new HBox();
		hBoxSpace.getChildren().addAll(space, reload);
		HBox hBoxAll = new HBox();
		hBoxAll.getChildren().addAll(farm, farmCombo, year, yearCombo, hBoxSpace);

		PieChart pieChart1 = getPieChartWithFarm();
		PieChart pieChart2 = getPieChartWithFarm();
		pieChart1.setTitle("Annual Percentage");
		pieChart2.setTitle("Monthly Percentage");
		HBox charts = new HBox();
		charts.getChildren().addAll(getBarChart(), pieChart1, pieChart2);
		
		Label spacing = new Label(
				"                                                                      ");
		HBox tableSpace = new HBox();
		tableSpace.getChildren().addAll(getTable(), spacing);
		VBox forthBox = new VBox();

		// Makes the forthScene
		forthBox.getChildren().addAll(forthText, hBoxAll, charts, tableSpace, goBack(arg0));

		// Return second scene
		forthScene = new Scene(forthBox, 1000, 1000);
		return forthScene;
	}

	/**
	 * Returns the third scene, annual report.
	 * 
	 * @param arg0   - arg
	 * @param choice - scene with choices
	 * @return the third scene, annual report
	 */
	public Scene getThirdScene(Stage arg0, Scene choice) {
		Scene thirdScene;
		Text thirdText = new Text();
		thirdText.setText("Annual Report");
		thirdText.setFill(Color.BLACK);
		thirdText.setStyle("-fx-font: 20 arial;");

		// ComboBox
		Label year = new Label("          Year     ");
		ComboBox<String> yearCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Year1", "Year2", "Year3"));
		Label space = new Label("          ");
		Button reload = new Button("Reload");
		HBox hBoxSpace = new HBox();
		hBoxSpace.getChildren().addAll(space, reload);
		HBox hBoxAll = new HBox();
		hBoxAll.getChildren().addAll(year, yearCombo, hBoxSpace);

		PieChart chart = getPieChartWithFarm();
		chart.setTitle("Month Percentage");

		HBox charts = new HBox();
		charts.getChildren().addAll(getTable(), chart);

		VBox thirdBox = new VBox();

		// Makes the third scene
		thirdBox.getChildren().addAll(thirdText, hBoxAll, charts, goBack(arg0));

		// Return the third scene
		thirdScene = new Scene(thirdBox, 1000, 1500);
		return thirdScene;
	}

	/**
	 * Returns the second scene, farm report.
	 * 
	 * @param arg0   - arg
	 * @param choice - scene of choices
	 * @return the second scene, farm report
	 */
	public Scene getSecondScene(Stage arg0, Scene choice) {
		// SECOND SCENE
		Scene secondScene;
		Text secondText = new Text();
		secondText.setText("Farm Report");
		secondText.setFill(Color.BLACK);
		secondText.setStyle("-fx-font: 20 arial;");

		// ComboBox
		Label farm = new Label("Farm     ");
		ComboBox<String> farmCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Farm1", "Farm2", "Farm3"));
		Label year = new Label("          Year     ");
		ComboBox<String> yearCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Year1", "Year2", "Year3"));
		Label space = new Label("          ");
		Button reload = new Button("Reload");
		HBox hBoxSpace = new HBox();
		hBoxSpace.getChildren().addAll(space, reload);
		HBox hBoxAll = new HBox();
		hBoxAll.getChildren().addAll(farm, farmCombo, year, yearCombo, hBoxSpace);

		// Pie Chart
		PieChart chart =  this.getPieChartWithMonth();
		chart.setTitle("Month Percentage");
		HBox charts = new HBox();
		charts.getChildren().addAll(this.getBarChart(), chart);

		// Go back to choices
		VBox secondVBox = new VBox();
		secondVBox.getChildren().addAll(secondText, hBoxAll, charts, goBack(arg0));

		// Return second scene
		secondScene = new Scene(secondVBox, 1000, 1500);
		return secondScene;
	}

	/**
	 * Returns the first scene, input file name
	 * 
	 * @param arg0        - the arg
	 * @param secondScene - the second scene
	 * @return the first scene, input file name
	 */
	public BorderPane getFirstScene(Stage arg0, Scene choice) {
		// FIRST SCENE
		BorderPane root = new BorderPane();

		// Title and welcome sign
		Text text = new Text();
		text.setText("Milk Weights");
		text.setFill(Color.BLACK);
		text.setStyle("-fx-font: 40 arial;");
		Label lable2 = new Label("Welcome! Start by entering the file name below:");

		// Type input and submit
		TextField textField = new TextField();
		Button button = new Button("Submit");
		button.setOnAction(e -> arg0.setScene(choice));

		VBox vBox = new VBox();
		vBox.getChildren().addAll(text, lable2, textField, button);
		vBox.setAlignment(Pos.CENTER);

		// Return first scene
		root.setCenter(vBox);
		return root;
	}

	/**
	 * Main method to launch the application.
	 * 
	 * @param args - argument
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
