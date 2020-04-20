package application;

import java.time.LocalDate;
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
	// Stores the farm, percent of total weight, and the total weight so it can be
	// used to make a table
	private class TableInner {
		private Farm farmName;
		private double percent;
		private int weight;

		public TableInner(Farm farmName, double percent, int weight) {
			this.farmName = farmName;
			this.percent = percent;
			this.weight = weight;
		}
	}

	// The scene with the choices
	Scene choice;

	// The csv file
	CSVFile csv = new CSVFile();

	// The file name given by the user
	String fileName = "";

	// hashMap full of all farms
	HashMap<String, Farm> hashMap = null;

	// The Data Range Report scene
	Scene fifthScene = null;
	// The Monthly report scene
	Scene forthScene = null;
	// The Annual Report scene
	Scene thirdScene = null;
	// The farm report scene
	Scene secondScene = null;

	// Buttons that will be used to change to a specified scene chosen by the user
	Button farm = new Button("Farm Report");
	Button month = new Button("Month Report");
	Button annual = new Button("Annual Report");
	Button data = new Button("Data Range Report");
	boolean flag = true;

	// Array list of all farms
	ArrayList<Farm> allFarms;

	// Combo box of all farms, user chooses which farm they want to look at
	ComboBox<Farm> farmCombo;

	// Combo box of all months, user chooses which month to look at
	ComboBox<String> monthCombo;

	// Array list of months
	ArrayList<String> months;

	// Once the user types 2 dates in the data range report scene, they have to
	// click this button to show the result
	Button submitDate = new Button("Submit Date");

	// The first date the user chose
	LocalDate firstDate;

	// The second date the user chose
	LocalDate secondDate;

	// Stores the first date picked by the user
	DatePicker d;

	// Stores the second date picked by the user
	DatePicker d2;

	@Override
	public void start(Stage arg0) throws Exception {

		// For the first scene
		BorderPane root = new BorderPane();

		// Title and welcome sign
		Text text = new Text();
		text.setText("Milk Weights");
		text.setFill(Color.BLACK);
		text.setStyle("-fx-font: 40 arial;");
		Label lable2 = new Label("Welcome! Start by entering the file name below, click Submit,"
				+ "then click Continue");

		// A place for the user to type the file name and submit
		Button continueButton = new Button("Continue");
		TextField textField = new TextField();
		Button button = new Button("Submit");

		// If the submit button pressed, then save the file name put in by the user and
		// go to choiceScene method, where the choice
		// scene and all other scenes are made
		button.setOnAction(e -> {
			fileName = textField.getText();
			choiceScene(arg0, continueButton);
		});

		//All this makes the first scene
		VBox vBoxFirstScene = new VBox();
		vBoxFirstScene.getChildren().addAll(text, lable2, textField, button, continueButton);
		vBoxFirstScene.setAlignment(Pos.CENTER);
		root.setCenter(vBoxFirstScene);
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
	public void choiceScene(Stage arg0, Button continueButton) {

		// Choices scene
		HBox hBox = new HBox();
		hBox.getChildren().addAll(farm, month, annual, data);
		VBox vBox = new VBox();
		Button close = new Button("Close");
		vBox.getChildren().addAll(new Label("Choose one: "), hBox, close);
		choice = new Scene(vBox, 500, 100);

		continueButton.setOnAction(e -> arg0.setScene(choice));

		hashMap = csv.parseFile(fileName);
		allFarms = new ArrayList<Farm>();
		for (int i = 0; i < hashMap.size(); i++) {
			allFarms.add(hashMap.get(i));
		}

		// Which button or choice in combo box was pressed
		farm.setOnAction(e -> arg0.setScene(getSecondScene(arg0)));
		farmCombo.setOnAction(e -> {
			for (int i = 0; i < hashMap.size(); i++) {
				if (farmCombo.getValue() == allFarms.get(i)) {
					arg0.setScene(
							this.getSecondSceneOnceAFarmIsChosen(arg0, farmCombo, allFarms.get(i)));
				}
			}
		});
		month.setOnAction(e -> arg0.setScene(getForthScene(arg0, choice)));
		monthCombo.setOnAction(e -> {
			if (getMonthNum(monthCombo.getValue()) == 1) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "January"));
			} else if (getMonthNum(monthCombo.getValue()) == 2) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "February"));
			} else if (getMonthNum(monthCombo.getValue()) == 3) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "March"));
			} else if (getMonthNum(monthCombo.getValue()) == 4) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "April"));
			} else if (getMonthNum(monthCombo.getValue()) == 5) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "May"));
			} else if (getMonthNum(monthCombo.getValue()) == 6) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "June"));
			} else if (getMonthNum(monthCombo.getValue()) == 7) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "July"));
			} else if (getMonthNum(monthCombo.getValue()) == 8) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "August"));
			} else if (getMonthNum(monthCombo.getValue()) == 9) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "September"));
			} else if (getMonthNum(monthCombo.getValue()) == 10) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "October"));
			} else if (getMonthNum(monthCombo.getValue()) == 11) {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "November"));
			} else {
				arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, monthCombo, "December"));
			}
		});
		annual.setOnAction(e -> arg0.setScene(getThirdScene(arg0, choice)));
		data.setOnAction(e -> arg0.setScene(getFifthScene(arg0, choice)));
		submitDate.setOnAction(e -> arg0
				.setScene(this.getFifthSceneAfterPickingDate(arg0, d.getValue(), d2.getValue())));
		close.setOnAction(e -> arg0.close());
	}

	/**
	 * Returns the second scene, farm report.
	 * 
	 * @param arg0   - arg
	 * @param choice - scene of choices
	 * @return the second scene, farm report
	 */
	public Scene getSecondScene(Stage arg0) {
		// SECOND SCENE

		// ComboBox
		farmCombo = new ComboBox<Farm>();
		for (int i = 0; i < hashMap.size(); i++) {
			farmCombo = new ComboBox<Farm>(FXCollections.observableArrayList(allFarms));
		}

		Text secondText = new Text();
		secondText.setText("Farm Report");
		secondText.setFill(Color.BLACK);
		secondText.setStyle("-fx-font: 20 arial;");

		Label farm = new Label("Farm     ");

		Label year = new Label("          Year     ");
		ComboBox<String> yearCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Year"));// CHANGE MAYBE
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
		ComboBox<String> yearCombo = new ComboBox<String>(
				FXCollections.observableArrayList(String.valueOf("2019")));// CHANGE MAYBE
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
				FXCollections.observableArrayList("2019"));// MAYBE CHANGE YEAR
		// Button reload = new Button("Reload"); PORBABLY DON'T NEED THIS
		HBox hBoxSpace = new HBox();
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
	 * Returns the forth scene, monthly report.
	 * 
	 * @param arg0   - arg
	 * @param choice - scene with choices
	 * @return the forth scene, monthly report
	 */
	public Scene getForthScene(Stage arg0, Scene choice) {
		// Scene forthScene;

		// ComboBox
		monthCombo = new ComboBox<String>();
		months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		for (int i = 1; i < 13; i++) {
			monthCombo = new ComboBox<String>(FXCollections.observableArrayList(months));
		}

		Text forthText = new Text();
		forthText.setText("Monthly Report");
		forthText.setFill(Color.BLACK);
		forthText.setStyle("-fx-font: 20 arial;");

		// Makes the forthScene
		VBox forthBox = new VBox();
		forthBox.getChildren().addAll(forthText, monthCombo, goBack(arg0));

		// Return second scene
		forthScene = new Scene(forthBox, 1000, 1000);
		return forthScene;
	}

	public Scene getForthSceneOnceAMonthIsChosen(Stage arg0, ComboBox<String> monthCombo,
			String month) {
		// ComboBox
		Label farm = new Label("Month     ");
		ComboBox<String> farmCombo = new ComboBox<String>(
				FXCollections.observableArrayList("Farm1", "Farm2", "Farm3"));
		HBox hBoxAll = new HBox();
		hBoxAll.getChildren().addAll(farm, farmCombo);

		PieChart pieChart2 = getPieChartWithFarmForMonths(month);
		pieChart2.setTitle("Monthly Percentage");
		HBox charts = new HBox();
		charts.getChildren().addAll(getBarChartForMonthReport(month), pieChart2);
		Label spacing = new Label(
				"                                                                      ");
		HBox tableSpace = new HBox();
		tableSpace.getChildren().addAll(getTableForthScene(), spacing);

		Text forthText = new Text();
		forthText.setText("Monthly Report");
		forthText.setFill(Color.BLACK);
		forthText.setStyle("-fx-font: 20 arial;");

		// Makes the forthScene
		VBox forthBox = new VBox();
		forthBox.getChildren().addAll(forthText, monthCombo, charts, tableSpace, goBack(arg0));

		// Return second scene
		forthScene = new Scene(forthBox, 1000, 1000);
		return forthScene;
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
		Label data = new Label("Data Range:     ");
		TilePane r = new TilePane();
		TilePane r2 = new TilePane();
		d = new DatePicker();
		d2 = new DatePicker();
		r.getChildren().add(d);
		r2.getChildren().add(d2);
		HBox hBoxAll = new HBox();
		HBox hBoxAll2 = new HBox();
		VBox calendar = new VBox();
		hBoxAll.getChildren().addAll(r);
		hBoxAll2.getChildren().addAll(r2);
		Label space = new Label("     ");
		calendar.getChildren().addAll(data, hBoxAll, space, hBoxAll2, space, submitDate);

		// Go back to choices
		VBox fifthBox = new VBox();
		fifthBox.getChildren().addAll(fifthText, calendar, goBack(arg0));

		// Return fifth scene
		fifthScene = new Scene(fifthBox, 1000, 1500);
		return fifthScene;
	}

	public Scene getFifthSceneAfterPickingDate(Stage arg0, LocalDate date1, LocalDate date2) {
		Text fifthText = new Text();
		fifthText.setText("Data Range Report");
		fifthText.setFill(Color.BLACK);
		fifthText.setStyle("-fx-font: 20 arial;");

		// ComboBox
		Label data = new Label("Data Range:     ");
		TilePane r = new TilePane();
		TilePane r2 = new TilePane();
		DatePicker d = new DatePicker();
		DatePicker d2 = new DatePicker();
		r.getChildren().add(d);
		r2.getChildren().add(d2);
		HBox hBoxAll = new HBox();
		HBox hBoxAll2 = new HBox();
		VBox calendar = new VBox();
		hBoxAll.getChildren().addAll(r);
		hBoxAll2.getChildren().addAll(r2);
		Label space = new Label("     ");
		calendar.getChildren().addAll(data, hBoxAll, space, hBoxAll2, space, submitDate);

		// Go back to choices
		VBox fifthBox = new VBox();
		fifthBox.getChildren().addAll(fifthText, calendar, getTableForFifthScene(date1, date2),
				goBack(arg0));

		// Return fifth scene
		fifthScene = new Scene(fifthBox, 1000, 1500);
		return fifthScene;
	}

	public VBox getTableForFifthScene(LocalDate date1, LocalDate date2) {
		// Table for farm, percentage, and weight
		TableView<String> table = new TableView<String>();
		TableColumn<String, String> farmTable = new TableColumn<String, String>("Farm");
		TableColumn<String, String> percent = new TableColumn<String, String>("Total percentage");
		TableColumn<String, String> totle = new TableColumn<String, String>("Total Weight");
		FarmReport farmReport;
		Double percentTotle = 0.0;
		int startDate = date1.getMonthValue();
		int endDate = date2.getMonthValue();
		ObservableList data = FXCollections.observableArrayList();
		for (int i = 0; i < allFarms.size(); i++) {
			farmReport = new FarmReport(allFarms.get(i), 2019);
			for (int j = startDate; j < endDate; j++) {
				percentTotle += farmReport.getPercents().get(j);
			}
			TableInner tableInner = new TableInner(allFarms.get(i), percentTotle,
					allFarms.get(i).getRangeTotal(date1, date2));
			data.add(tableInner);
			percentTotle = 0.0;
		}
		farmTable.setMinWidth(100);
		percent.setMinWidth(100);
		totle.setMinWidth(100);
		table.getColumns().addAll(farmTable, percent, totle);
		table.setItems(data);
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(table);
		return vbox;
	}

	public BarChart getBarChartForMonthReport(String month) {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Milk Weights");
		BarChart barChart = new BarChart(xAxis, yAxis);
		XYChart.Series dataSeries1 = new XYChart.Series();
		dataSeries1.setName("Milk Weights");

		MonthlyReport monthlyReport = new MonthlyReport(hashMap, new Year(2019),
				getMonthNum(month));

		// Making the bar chart from info from Monthly Report
		dataSeries1.getData().add(new XYChart.Data("Min", monthlyReport.getMin()));
		dataSeries1.getData().add(new XYChart.Data("Average", monthlyReport.getMax()));
		dataSeries1.getData().add(new XYChart.Data("Max", monthlyReport.getAvg()));
		barChart.getData().add(dataSeries1);
		return barChart;
	}

	public int getMonthNum(String month) {
		if (month.contentEquals("January")) {
			return 1;
		} else if (month.contentEquals("February")) {
			return 2;
		} else if (month.contentEquals("March")) {
			return 3;
		} else if (month.contentEquals("April")) {
			return 4;
		} else if (month.contentEquals("May")) {
			return 5;
		} else if (month.contentEquals("June")) {
			return 6;
		} else if (month.contentEquals("July")) {
			return 7;
		} else if (month.contentEquals("August")) {
			return 8;
		} else if (month.contentEquals("September")) {
			return 9;
		} else if (month.contentEquals("October")) {
			return 10;
		} else if (month.contentEquals("November")) {
			return 11;
		} else {
			return 12;
		}
	}

	public PieChart getPieChartWithFarmForMonths(String month) {
		int num = allFarms.size();
		PieChart.Data pieChart = null;
		ArrayList<PieChart.Data> listOfData = new ArrayList<PieChart.Data>();
		Double percentTotle = 0.0;
		int chosenMonth = getMonthNum(month);
		for (int i = 0; i < num; i++) {
			FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
			pieChart = new PieChart.Data("Farm " + allFarms.get(i).getID(),
					farmReport.getPercents().get(chosenMonth));
			listOfData.add(pieChart);
			percentTotle = 0.0;
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(listOfData);
		PieChart chart = new PieChart(pieChartData);
		return chart;
	}

	public VBox getTableForthScene() {
		// Table for farm, percentage, and weight
		TableView<String> table = new TableView<String>();
		TableColumn<String, String> farmTable = new TableColumn<String, String>("Farm");
		TableColumn<String, String> percent = new TableColumn<String, String>("Total percentage");
		TableColumn<String, String> totle = new TableColumn<String, String>("Total Weight");
		FarmReport farmReport;
		Double percentTotle = 0.0;
		ObservableList data = FXCollections.observableArrayList();
		for (int i = 0; i < allFarms.size(); i++) {
			farmReport = new FarmReport(allFarms.get(i), 2019);
			for (int j = 0; j < farmReport.getPercents().size(); j++) {
				percentTotle += farmReport.getPercents().get(j);
			}
			TableInner tableInner = new TableInner(allFarms.get(i), percentTotle,
					allFarms.get(i).getYearTotal(2019));
			data.add(tableInner);
			percentTotle = 0.0;
		}
		farmTable.setMinWidth(100);
		percent.setMinWidth(100);
		totle.setMinWidth(100);
		table.getColumns().addAll(farmTable, percent, totle);
		table.setItems(data);
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(table);
		return vbox;
	}

	public PieChart getPieChartWithFarm() {
		int num = allFarms.size();
		PieChart.Data pieChart = null;
		ArrayList<PieChart.Data> listOfData = new ArrayList<PieChart.Data>();
		Double percentTotle = 0.0;
		for (int i = 0; i < num; i++) {
			FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
			for (int j = 0; j < farmReport.getPercents().size(); j++) {
				percentTotle += farmReport.getPercents().get(j);
			}
			pieChart = new PieChart.Data("Farm " + allFarms.get(i).getID(), percentTotle);
			listOfData.add(pieChart);
			percentTotle = 0.0;
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(listOfData);
		PieChart chart = new PieChart(pieChartData);
		return chart;
	}

	public VBox getTable() {
		// Table for farm, percentage, and weight
		TableView<String> table = new TableView<String>();
		TableColumn<String, String> farmTable = new TableColumn<String, String>("Farm");
		TableColumn<String, String> percent = new TableColumn<String, String>("Total percentage");
		TableColumn<String, String> totle = new TableColumn<String, String>("Total Weight");
		FarmReport farmReport;
		Double percentTotle = 0.0;
		ObservableList data = FXCollections.observableArrayList();
		for (int i = 0; i < allFarms.size(); i++) {
			farmReport = new FarmReport(allFarms.get(i), 2019);
			for (int j = 0; j < farmReport.getPercents().size(); j++) {
				percentTotle += farmReport.getPercents().get(j);
			}
			TableInner tableInner = new TableInner(allFarms.get(i), percentTotle,
					allFarms.get(i).getYearTotal(2019));
			data.add(tableInner);
			percentTotle = 0.0;
		}
		farmTable.setMinWidth(100);
		percent.setMinWidth(100);
		totle.setMinWidth(100);
		table.getColumns().addAll(farmTable, percent, totle);
		table.setItems(data);
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
		FarmReport farmReport = new FarmReport(chosenFarm, 2019);// CHANGE YEAR MAYBE
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
				new PieChart.Data("Jan", percentage.get(0)),
				new PieChart.Data("Feb", percentage.get(1)),
				new PieChart.Data("Mar", percentage.get(2)),
				new PieChart.Data("Apr", percentage.get(3)),
				new PieChart.Data("May", percentage.get(4)),
				new PieChart.Data("Jun", percentage.get(5)),
				new PieChart.Data("Jul", percentage.get(6)),
				new PieChart.Data("Aug", percentage.get(7)),
				new PieChart.Data("Sep", percentage.get(8)),
				new PieChart.Data("Oct", percentage.get(9)),
				new PieChart.Data("Nov", percentage.get(10)),
				new PieChart.Data("Dec", percentage.get(11)));
		PieChart chart = new PieChart(pieChartData);
		return chart;
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
