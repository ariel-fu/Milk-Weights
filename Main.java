package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	Button save = new Button("Save");// Saved screen
	Button remove = new Button("Remove");// Removed screen
	Scene choice;
	CSVFile csv = new CSVFile();
	String fileName = "";
	HashMap<String, Farm> hashMap = null;

	// Gets the sixth scene, Edit data
	Scene sixthScene = null;
	// Gets the fifth scene, Data Range Report
	Scene fifthScene = null;
	// Gets the forth scene, Monthly report
	Scene forthScene = null;
	// Gets the third scene, Annual Report
	Scene thirdScene = null;
	// Gets the second scene, farm report
	Scene secondScene = null;

	Button farm = new Button("Farm Report");
	Button month = new Button("Month Report");
	Button annual = new Button("Annual Report");
	Button data = new Button("Data Range Report");
	Button edit = new Button("Edit Data");
	boolean flag = true;

	ArrayList<Farm> allFarms;

	ComboBox<Farm> farmCombo;

	@Override
	public void start(Stage arg0) throws Exception {
		// The saved and removed scene after editing
		savedScene(arg0);
		removedScene(arg0);

		BorderPane root = new BorderPane();

		// Title and welcome sign
		Text text = new Text();
		text.setText("Milk Weights");
		text.setFill(Color.BLACK);
		text.setStyle("-fx-font: 40 arial;");
		Label lable2 = new Label("Welcome! Start by entering the file name below, click Submit,"
				+ "then click Continue");

		// Type input and submit
		Button continueButton = new Button("Continue");
		TextField textField = new TextField();
		Button button = new Button("Submit");
		button.setOnAction(e -> {
			fileName = textField.getText();
			choiceScene(arg0, continueButton);
		});

		VBox vBoxFirstScene = new VBox();
		vBoxFirstScene.getChildren().addAll(text, lable2, textField, button, continueButton);
		vBoxFirstScene.setAlignment(Pos.CENTER);

		// Return first scene
		root.setCenter(vBoxFirstScene);

		// The first scene
		Scene mainScene = new Scene(root, 450, 200);
		arg0.setTitle("Milk Weights");
		arg0.setScene(mainScene);
		arg0.show();

	}

	/**
	 * Returns the choice scene
	 * 
	 * @param arg0        - the arg
	 * @param secondScene - the second scene
	 * @return the first scene, input file name
	 * @throws InterruptedException
	 */
	public Scene choiceScene(Stage arg0, Button continueButton) {
		continueButton.setOnAction(e -> arg0.setScene(choice));

		// Choices scene
		HBox hBox = new HBox();
		hBox.getChildren().addAll(farm, month, annual, data, edit);
		VBox vBox = new VBox();
		Button close = new Button("Close");
		vBox.getChildren().addAll(new Label("Choose one: "), hBox, close);
		choice = new Scene(vBox, 500, 100);

		// Which button or choice in combo box was pressed
		farm.setOnAction(e -> arg0.setScene(getSecondScene(arg0, choice)));
		farmCombo.setOnAction(e -> {
			for (int i = 0; i < hashMap.size(); i++) {
				if (farmCombo.getValue() == allFarms.get(i)) {
					arg0.setScene(
							this.getSecondSceneOnceAFarmIsChosen(arg0, farmCombo, allFarms.get(i)));
				}
			}
		});
		edit.setOnAction(e -> arg0.setScene(getSixthScene(arg0, choice)));
		data.setOnAction(e -> arg0.setScene(getFifthScene(arg0, choice)));
		month.setOnAction(e -> arg0.setScene(getForthScene(arg0, choice)));
		annual.setOnAction(e -> arg0.setScene(getThirdScene(arg0, choice)));
		close.setOnAction(e -> arg0.close());
		return choice;
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

	public PieChart getPieChartWithFarm() {
		int numOfFarms = 5; // Need to figure out how to get the number of farms
		// Need to figure out how to get what farm they and how much percent
		//List<Double> percent = farmReport.getPercents();
		PieChart.Data pieChart = new PieChart.Data("Farm", 200);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++) {// NEED TO CHANGE SIZE
			pieChartData = FXCollections.observableArrayList(pieChart);
		}
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

	public BarChart getBarChart(Farm chosenFarm) {
		// Bar Chart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Milk Weights");
		BarChart barChart = new BarChart(xAxis, yAxis);
		XYChart.Series dataSeries1 = new XYChart.Series();
		dataSeries1.setName("Milk Weights");

		// Making the bar chart from info from FarmReport
		FarmReport farmReport = new FarmReport(chosenFarm, 2019);//CHANGE YEAR MAYBE
		dataSeries1.getData().add(new XYChart.Data("Min", farmReport.getMin()));
		dataSeries1.getData().add(new XYChart.Data("Average", farmReport.getAvg()));
		dataSeries1.getData().add(new XYChart.Data("Max", farmReport.getMax()));
		barChart.getData().add(dataSeries1);
		return barChart;
	}

	public Button goBack(Stage arg0) {
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> arg0.setScene(choice));
		return goBack;
	}

	public PieChart getPieChartWithMonth(Farm chosenFarm) {
		FarmReport farmReport = new FarmReport(chosenFarm, 2019);
		List<Double> percentage = farmReport.getPercents();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Jan", percentage.get(0)), new PieChart.Data("Feb", percentage.get(1)),
				new PieChart.Data("Mar", percentage.get(2)), new PieChart.Data("Apr", percentage.get(3)),
				new PieChart.Data("May", percentage.get(4)), new PieChart.Data("Jun", percentage.get(5)),
				new PieChart.Data("Jul", percentage.get(6)), new PieChart.Data("Aug", percentage.get(7)),
				new PieChart.Data("Sep", percentage.get(8)), new PieChart.Data("Oct", percentage.get(9)),
				new PieChart.Data("Nov", percentage.get(10)), new PieChart.Data("Dec", percentage.get(11)));
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
		// Scene sixthScene;

		Text sixthText = new Text();
		sixthText.setText("Edit your farm data");
		sixthText.setFill(Color.BLACK);
		sixthText.setStyle("-fx-font: 20 arial;");

		Text input = new Text();
		input.setText("Input your data:");
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
		sixthBox.getChildren().addAll(sixthText, input, farm, calendar, inputWeight, saveRemove,
				goBack(arg0));

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
		// Scene fifthScene;
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
		// Scene forthScene;
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
		charts.getChildren().addAll(getBarChart(new Farm("NOT IT")), pieChart1, pieChart2);//CHANGE! THE NEW FARM PART

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
		// Scene thirdScene;
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
		hashMap = csv.parseFile(fileName);
		int numOfFarms = hashMap.size();
		allFarms = new ArrayList<Farm>();
		for (int i = 0; i < numOfFarms; i++) {
			allFarms.add(hashMap.get(i));
		}

		// ComboBox
		farmCombo = new ComboBox<Farm>();
		for (int i = 0; i < numOfFarms; i++) {
			farmCombo = new ComboBox<Farm>(FXCollections.observableArrayList(allFarms));
		}

		Text secondText = new Text();
		secondText.setText("Farm Report");
		secondText.setFill(Color.BLACK);
		secondText.setStyle("-fx-font: 20 arial;");

		Label farm = new Label("Farm     ");

		Label year = new Label("          Year     ");
		ComboBox<String> yearCombo = 
				new ComboBox<String>(
						FXCollections.observableArrayList("Year"));//CHANGE MAYBE
//		Button reload = new Button("Reload"); I DON'T THINK THIS IS NEEDED, CAUSE IT RESETS EVERYTIME COMBOBOX IS CHANGED.
		HBox hBoxAll = new HBox();
		hBoxAll.getChildren().addAll(farm, farmCombo, year, yearCombo);

		// Go back to choices
		VBox secondVBox = new VBox();
		secondVBox.getChildren().addAll(secondText, hBoxAll, goBack(arg0));

		// Return second scene
		secondScene = new Scene(secondVBox, 1000, 1500);
		return secondScene;
	}

	public Scene getSecondSceneOnceAFarmIsChosen(Stage arg0, ComboBox<Farm> farmCombo,
			Farm chosenFarm) {
		// Pie Chart
		PieChart chart = this.getPieChartWithMonth(chosenFarm);
		chart.setTitle("Month Percentage");
		HBox charts = new HBox();
		charts.getChildren().addAll(this.getBarChart(chosenFarm), chart);
		
		Text secondText = new Text();
		secondText.setText("Farm Report");
		secondText.setFill(Color.BLACK);
		secondText.setStyle("-fx-font: 20 arial;");

		Label farm = new Label("Farm     ");

		Label year = new Label("          Year     ");
		ComboBox<String> yearCombo = 
				new ComboBox<String>(
						FXCollections.observableArrayList(String.valueOf("2019")));//CHANGE MAYBE
		Label space = new Label("          ");
		Button reload = new Button("Reload");
		HBox hBoxSpace = new HBox();
		hBoxSpace.getChildren().addAll(space, reload);
		HBox hBoxAll = new HBox();
		hBoxAll.getChildren().addAll(farm, farmCombo, year, yearCombo, hBoxSpace);

		// Go back to choices
		VBox secondVBox = new VBox();
		secondVBox.getChildren().addAll(secondText, hBoxAll, charts, goBack(arg0));

		// Return second scene
		secondScene = new Scene(secondVBox, 1000, 1500);
		return secondScene;
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
