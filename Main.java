package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
	  public class TableInner {
	    private final SimpleStringProperty name;
	    private final SimpleStringProperty percent;
	    private final SimpleStringProperty weight;

	    public TableInner(String farmName, String percent, String weight) {
	      this.name = new SimpleStringProperty(farmName);
	      this.percent = new SimpleStringProperty(percent);
	      this.weight = new SimpleStringProperty(weight);
	    }

	    public String getName() {
	      return name.get();
	    }

	    public void setName(String name) {
	      this.name.set(name);
	    }

	    public String getPercent() {
	      return percent.get();
	    }

	    public void setPercent(String percent) {
	      this.percent.set(percent);
	    }

	    public String getWeight() {
	      return weight.get();
	    }

	    public void setDataSensorTwo(String weight) {
	      this.weight.set(weight);
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
	  private ArrayList<Farm> allFarms = new ArrayList<Farm>();

	  // Combo box of all farms, user chooses which farm they want to look at
	  ComboBox<String> farmCombo = new ComboBox<String>(
	      FXCollections.observableArrayList());
	  Button farmReportSubmitButton = new Button("Submit and show");

	  // Combo box of all months, user chooses which month to look at
	  ComboBox<String> monthCombo = new ComboBox<String>(
	      FXCollections.observableArrayList());
	  Button monthReportSubmitButton = new Button("Submit and show");

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
	    Label lable2 = new Label(
	        "Welcome! Start by entering the file name below, click Submit,"
	            + "then click Continue");

	    // A place for the user to type the file name and submit
	    Button continueButton = new Button("Continue");
	    TextField textField = new TextField();
	    Button button = new Button("Submit");

	    // If the submit button pressed, then save the file name put in by the user
	    // and
	    // go to choiceScene method, where the choice
	    // scene and all other scenes are made
	    button.setOnAction(e -> {
	      fileName = textField.getText();
	      try {
	        choiceScene(arg0, continueButton);
	      } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	      }
	    });

	    // All this makes the first scene
	    VBox vBoxFirstScene = new VBox();
	    vBoxFirstScene.getChildren().addAll(text, lable2, textField, button,
	        continueButton);
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
	   * @throws IOException
	   * @throws InterruptedException
	   */
	  public void choiceScene(Stage arg0, Button continueButton)
	      throws IOException {
	    // Choices scene
	    HBox hBox = new HBox();
	    hBox.getChildren().addAll(farm, month, annual, data);
	    VBox vBox = new VBox();
	    Button close = new Button("Close");
	    vBox.getChildren().addAll(new Label("Choose one: "), hBox, close);
	    choice = new Scene(vBox, 500, 100);

	    continueButton.setOnAction(e -> arg0.setScene(choice));

	    hashMap = csv.parseFile(new FileReader(fileName));
	    Collection<Farm> collection = hashMap.values();
	    allFarms = new ArrayList<Farm>(collection);

	    // Which button or choice in combo box was pressed
	    farm.setOnAction(e -> arg0.setScene(getSecondScene(arg0)));

	    // ARIEL: eaiser way to get farm from ID
	    Farm farm = hashMap.get(farmCombo.getValue());

	    farmReportSubmitButton.setOnAction(e -> {
	      for (int i = 0; i < hashMap.size(); i++) {
	        if (farmCombo.getValue() == allFarms.get(i).getID()) {
	          arg0.setScene(
	//TODO - pass Year as part of the user inputs
	              this.getSecondSceneOnceAFarmIsChosen(arg0, allFarms.get(i),
	                  2019));
	        }
	      }
	    });
	    month.setOnAction(e -> arg0.setScene(getForthScene(arg0, choice)));
	    monthReportSubmitButton.setOnAction(e -> {
	      if (getMonthNum(monthCombo.getValue()) == 1) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "January"));
	      } else if (getMonthNum(monthCombo.getValue()) == 2) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "February"));
	      } else if (getMonthNum(monthCombo.getValue()) == 3) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "March"));
	      } else if (getMonthNum(monthCombo.getValue()) == 4) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "April"));
	      } else if (getMonthNum(monthCombo.getValue()) == 5) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "May"));
	      } else if (getMonthNum(monthCombo.getValue()) == 6) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "June"));
	      } else if (getMonthNum(monthCombo.getValue()) == 7) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "July"));
	      } else if (getMonthNum(monthCombo.getValue()) == 8) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "August"));
	      } else if (getMonthNum(monthCombo.getValue()) == 9) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "September"));
	      } else if (getMonthNum(monthCombo.getValue()) == 10) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "October"));
	      } else if (getMonthNum(monthCombo.getValue()) == 11) {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "November"));
	      } else {
	        arg0.setScene(this.getForthSceneOnceAMonthIsChosen(arg0, "December"));
	      }
	    });
	    annual.setOnAction(e -> arg0.setScene(getThirdScene(arg0, choice)));
	    data.setOnAction(e -> arg0.setScene(getFifthScene(arg0, choice)));
	    submitDate.setOnAction(e -> arg0.setScene(
	        this.getFifthSceneAfterPickingDate(arg0, d.getValue(), d2.getValue())));
	    close.setOnAction(e -> arg0.close());
	  }

	  public Scene fileNotFoundScene(Stage arg0) {
	    Text secondText = new Text();
	    secondText.setText("File: " + fileName
	        + " not found. Please go back and change your stupid file name!");
	    secondText.setFill(Color.BLACK);
	    secondText.setStyle("-fx-font: 30 arial;");
	    HBox hBox = new HBox();
	    hBox.getChildren().addAll(secondText, new Label("     "), goBack(arg0));
	    hBox.setAlignment(Pos.CENTER);
	    Scene fileNotFoundScene = new Scene(hBox, 1000, 300);
	    return fileNotFoundScene;
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

	    ArrayList<String> id = new ArrayList<String>();
	    for (int i = 0; i < allFarms.size(); i++) {
	      id.add(allFarms.get(i).getID());
	    }
	    // ComboBox
	    farmCombo = new ComboBox<String>();
	    for (int i = 0; i < hashMap.size(); i++) {
	      farmCombo = new ComboBox<String>(FXCollections.observableArrayList(id));
	    }

	    Text secondText = new Text();
	    secondText.setText("Farm Report");
	    secondText.setFill(Color.BLACK);
	    secondText.setStyle("-fx-font: 20 arial;");

	    Label farm = new Label("Farm     ");

	    Label year = new Label("     Year: 2019");// CHANGE MAYBE
	    HBox hBoxAll = new HBox();
	    hBoxAll.getChildren().addAll(farm, farmCombo, year);

	    // Go back to choices
	    VBox secondVBox = new VBox();
	    secondVBox.getChildren().addAll(secondText, hBoxAll, farmReportSubmitButton,
	        goBack(arg0));

	    // Return second scene
	    secondScene = new Scene(secondVBox, 1000, 1500);
	    return secondScene;
	  }

	  public Scene getSecondSceneOnceAFarmIsChosen(Stage arg0, Farm chosenFarm,
	      int year) {

	    // ARIEL: if reportbase 2.0 passes, this goes back to farmreport
	    FarmReport2 report = new FarmReport2(chosenFarm, year);
	    report.runReport();

	    // Pie Chart
	    PieChart chart = this.getPieChartWithMonth(report);
	    chart.setTitle("Month Percentage");
	    HBox charts = new HBox();
	    // ARIEL: haha i changed some stuff plz dont get mad prasun
	    charts.getChildren().addAll(this.getBarChart(report), chart);

	    Text secondText = new Text();
	    secondText.setText("Farm Report");
	    secondText.setFill(Color.BLACK);
	    secondText.setStyle("-fx-font: 20 arial;");

	    Label farm = new Label("Farm     ");

	    Label yearLabel = new Label("     Year: 2019");// CHANGE MAYBE
	    Label space = new Label("          ");
	    HBox hBoxSpace = new HBox();
	    hBoxSpace.getChildren().addAll(space);
	    HBox hBoxAll = new HBox();
	    hBoxAll.getChildren().addAll(farm, farmCombo, yearLabel, hBoxSpace);

	    // Go back to choices
	    VBox secondVBox = new VBox();
	    secondVBox.getChildren().addAll(secondText, hBoxAll, farmReportSubmitButton,
	        charts, goBack(arg0));

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
	    Label year = new Label("          Year:    ");
	    Label year2019 = new Label("2019");// MAYBE CHANGE YEAR
	    // Button reload = new Button("Reload"); PORBABLY DON'T NEED THIS
	    HBox hBoxSpace = new HBox();
	    HBox hBoxAll = new HBox();
	    hBoxAll.getChildren().addAll(year, year2019, hBoxSpace);

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
	      monthCombo = new ComboBox<String>(
	          FXCollections.observableArrayList(months));
	    }

	    Text forthText = new Text();
	    forthText.setText("Monthly Report");
	    forthText.setFill(Color.BLACK);
	    forthText.setStyle("-fx-font: 20 arial;");

	    // Makes the forthScene
	    VBox forthBox = new VBox();
	    forthBox.getChildren().addAll(forthText, monthCombo,
	        monthReportSubmitButton, goBack(arg0));

	    // Return second scene
	    forthScene = new Scene(forthBox, 1000, 1000);
	    return forthScene;
	  }

	  public Scene getForthSceneOnceAMonthIsChosen(Stage arg0, String month) {
	    // ComboBox
	    Label monthLabel = new Label("Month     ");
	    HBox hBox = new HBox();
	    hBox.getChildren().addAll(monthLabel, monthCombo);

	    // ARIEL: making this compile dont mind me
	    MonthlyReport report = new MonthlyReport(hashMap, new Year(2019), 12);
	    report.runReport();
	    // haha this won't work bc i don't have a second monthlyreport example...
	    // don't mind the compile error.
	    PieChart pieChart2 = getPieChartWithFarmForMonths(report);
	    pieChart2.setTitle("Monthly Percentage");
	    HBox charts = new HBox();
	    // ARIEL: has sleep deprivation and needs coffee ... anyways this
	    // getBarChartForMonthReport can be used
	    // for all reports that need a bar chart bc it takes in any class that
	    // extends ReportBase - FarmReport, MonthlyReport, AnnualReport, and
	    // DateRangeReport when we make the class
	    charts.getChildren().addAll(getBarChartForMonthReport(month), pieChart2);
	    Label spacing = new Label(
	        "                                                 ");
	    HBox tableSpace = new HBox();
	    tableSpace.getChildren().addAll(getTableForthScene(month), spacing);

	    Text forthText = new Text();
	    forthText.setText("Monthly Report");
	    forthText.setFill(Color.BLACK);
	    forthText.setStyle("-fx-font: 20 arial;");

	    HBox hbox = new HBox();
	    hbox.getChildren().addAll(tableSpace, goBack(arg0));

	    // Makes the forthScene
	    VBox forthBox = new VBox();
	    forthBox.getChildren().addAll(forthText, hBox, monthReportSubmitButton,
	        charts, hbox);

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
	    Label space2 = new Label("     ");
	    calendar.getChildren().addAll(data, hBoxAll, space, hBoxAll2, space2,
	        submitDate);

	    // Go back to choices
	    VBox fifthBox = new VBox();
	    fifthBox.getChildren().addAll(fifthText, calendar, goBack(arg0));

	    // Return fifth scene
	    fifthScene = new Scene(fifthBox, 1000, 1500);
	    return fifthScene;
	  }

	  public Scene getFifthSceneAfterPickingDate(Stage arg0, LocalDate date1,
	      LocalDate date2) {
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
	    Label space2 = new Label("     ");
	    calendar.getChildren().addAll(data, hBoxAll, space, hBoxAll2, space2,
	        submitDate);

	    // Go back to choices
	    VBox fifthBox = new VBox();
	    fifthBox.getChildren().addAll(fifthText, calendar,
	        getTableForFifthScene(date1, date2), goBack(arg0));

	    // Return fifth scene
	    fifthScene = new Scene(fifthBox, 1000, 1500);
	    return fifthScene;
	  }

	  public VBox getTableForFifthScene(LocalDate date1, LocalDate date2) {
	    // Table for farm, percentage, and weight
	    final TableView<TableInner> table = new TableView<>();
	    table.setItems(getListForTableFifthScene(date1, date2));
	    TableColumn<TableInner, String> farmTable = new TableColumn<>("Farm");
	    TableColumn<TableInner, String> percent = new TableColumn<>(
	        "Total percentage");
	    TableColumn<TableInner, String> totle = new TableColumn<>("Total Weight");
	    farmTable.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("name"));
	    percent.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("percent"));
	    totle.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("weight"));
	    table.getColumns().addAll(farmTable, percent, totle);
	    farmTable.setMinWidth(200);
	    percent.setMinWidth(200);
	    totle.setMinWidth(200);
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(table);
	    return vbox;
	  }

	  private ObservableList<TableInner> getListForTableFifthScene(LocalDate date1,
	      LocalDate date2) {
	    ObservableList<TableInner> tableInner = FXCollections.observableArrayList();
	    int startDate = date1.getMonthValue();
	    int endDate = date2.getMonthValue();
	    for (int i = 0; i < allFarms.size(); i++) {
	      double percentForYear = 0.0;
	      FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
	      for (int j = startDate; j < endDate; j++) {
	        percentForYear += farmReport.getPercents().get(j);
	      }

	      String percentDouble = Double.toString(percentForYear);
	      String weightInt = Double
	          .toString(allFarms.get(i).getRangeTotal(date1, date2));
	      tableInner.add(
	          new TableInner(allFarms.get(i).getID(), percentDouble, weightInt));
	    }
	    return tableInner;
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
	    dataSeries1.getData()
	        .add(new XYChart.Data("Average", monthlyReport.getAvg()));
	    dataSeries1.getData().add(new XYChart.Data("Max", monthlyReport.getMax()));
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

	  public PieChart getPieChartWithFarmForMonths(ReportBase2 report) {// IDK IF IT
	                                                                    // WORKS
	    // OR NOT (probs yes) goo prasun :)
	    // ADD JAVADOCS DEAR GOD DUDE good code tho :) really appreciate the effort
	    // into the font. 120/10 approval.
	    // gets the labels of the percentages, conventiently in an ArrayList :)
	    List<String> listOfLabels = report.getPercentLabels();
	    // gets the percentages of the files into a list
	    List<Double> listOfPercentages = report.getPercents();
	    // BOOM, now you have a list of labels, a list of percentages, and one less
	    // for loop!

	    // ARIEL: this looks good (and uber complicated), but i made a new method
	    // for u to use prasun, look above
	    int num = allFarms.size();
	    ArrayList<String> listOfData = new ArrayList<String>();
	    ArrayList<Double> listOfData2 = new ArrayList<Double>();
	    // to the best of my knowledge, which is a little, you probs won't need
	    // this...??
	    // int chosenMonth = getMonthNum(month);

	    ArrayList<PieChart.Data> finalArray = new ArrayList<PieChart.Data>();
	    // highlight the area below that is commented out, press ctrl then a "/" to
	    // get rid of block comments (cuz the new code might not be what you want)
//	    for (int i = 0; i < num; i++) {
//	      FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
//	      listOfData.add(allFarms.get(i).getID());
//	      listOfData2.add(farmReport.getPercents().get(chosenMonth - 1));
//	    }
//	    for (int i = 0; i < num; i++) {
//	      PieChart.Data pieChart = new PieChart.Data(listOfData.get(i),
//	          listOfData2.get(i));
//	      finalArray.add(pieChart);
//	    }

	    // NEW CODE: (from ARIEL), works the same way with one less for loop. Plus
	    // as our GUIer, you should call methods left and right and not worry about
	    // what is in them. tell us backenders what you need and we'll get it for
	    // you
	    // sincerely, the backenders
	    for (int i = 0; i < num; i++) {
	      PieChart.Data pieChart = new PieChart.Data(listOfLabels.get(i),
	          listOfPercentages.get(i));
	      finalArray.add(pieChart);
	    }
	    ObservableList<PieChart.Data> pieChartData = FXCollections
	        .observableArrayList(finalArray);
	    PieChart chart = new PieChart(pieChartData);
	    return chart;
	  }

	  public VBox getTableForthScene(String chosenMonth) {
	    // Table for farm, percentage, and weight
	    final TableView<TableInner> table = new TableView<>();
	    table.setItems(getListForTableForthScene(chosenMonth));
	    TableColumn<TableInner, String> farmTable = new TableColumn<>("Farm");
	    TableColumn<TableInner, String> percent = new TableColumn<>(
	        "Total percentage");
	    TableColumn<TableInner, String> totle = new TableColumn<>("Total Weight");
	    farmTable.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("name"));
	    percent.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("percent"));
	    totle.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("weight"));
	    table.getColumns().addAll(farmTable, percent, totle);
	    farmTable.setMinWidth(200);
	    percent.setMinWidth(200);
	    totle.setMinWidth(200);
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(table);
	    return vbox;
	  }

	  private ObservableList<TableInner> getListForTableForthScene(
	      String chosenMonth) {
	    ObservableList<TableInner> tableInner = FXCollections.observableArrayList();
	    MonthlyReport monthly = new MonthlyReport(hashMap, new Year(2019),
	        getMonthNum(chosenMonth));
	    for (int i = 0; i < allFarms.size(); i++) {
	      FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
	      String percentDouble = Double
	          .toString(monthly.getPercents().get(getMonthNum(chosenMonth)));
	      String weightInt = Double.toString(
	          allFarms.get(i).getMonthTotal(getMonthNum(chosenMonth), 2019));
	      tableInner.add(
	          new TableInner(allFarms.get(i).getID(), percentDouble, weightInt));
	      System.out.println(allFarms.get(i).getID());
	      System.out.println(percentDouble);
	      System.out.println(weightInt);
	    }
	    return tableInner;
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
	      pieChart = new PieChart.Data(allFarms.get(i).getID(), percentTotle);
	      listOfData.add(pieChart);
	      percentTotle = 0.0;
	    }
	    ObservableList<PieChart.Data> pieChartData = FXCollections
	        .observableArrayList(listOfData);
	    PieChart chart = new PieChart(pieChartData);
	    return chart;
	  }

	  public VBox getTable() {
	    // Table for farm, percentage, and weight
	    final TableView<TableInner> table = new TableView<>();
	    table.setItems(getListForTable());
	    TableColumn<TableInner, String> farmTable = new TableColumn<>("Farm");
	    TableColumn<TableInner, String> percent = new TableColumn<>(
	        "Total percentage");
	    TableColumn<TableInner, String> totle = new TableColumn<>("Total Weight");
	    farmTable.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("name"));
	    percent.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("percent"));
	    totle.setCellValueFactory(
	        new PropertyValueFactory<TableInner, String>("weight"));
	    table.getColumns().addAll(farmTable, percent, totle);
	    farmTable.setMinWidth(200);
	    percent.setMinWidth(200);
	    totle.setMinWidth(200);
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(table);
	    return vbox;
	  }

	  private ObservableList<TableInner> getListForTable() {
	    ObservableList<TableInner> tableInner = FXCollections.observableArrayList();
	    for (int i = 0; i < allFarms.size(); i++) {
	      double percentForYear = 0.0;
	      FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
	      for (int j = 0; j < farmReport.getPercents().size(); j++) {
	        percentForYear += farmReport.getPercents().get(j);
	      }
	      String percentDouble = Double.toString(percentForYear);
	      String weightInt = Double.toString(allFarms.get(i).getYearTotal(2019));
	      tableInner.add(
	          new TableInner(allFarms.get(i).getID(), percentDouble, weightInt));
	    }
	    return tableInner;
	  }

	  public BarChart getBarChart(ReportBase2 report) {
	    // Bar Chart
	    CategoryAxis xAxis = new CategoryAxis();
	    xAxis.setLabel("");
	    NumberAxis yAxis = new NumberAxis();
	    yAxis.setLabel("Milk Weights");
	    BarChart barChart = new BarChart(xAxis, yAxis);
	    XYChart.Series dataSeries1 = new XYChart.Series();
	    dataSeries1.setName("Milk Weights");

	    // Making the bar chart from info from FarmReport
	    dataSeries1.getData().add(new XYChart.Data("Min", report.getMin()));
	    dataSeries1.getData().add(new XYChart.Data("Average", report.getAvg()));
	    dataSeries1.getData().add(new XYChart.Data("Max", report.getMax()));
	    barChart.getData().add(dataSeries1);
	    return barChart;
	  }

	  public Button goBack(Stage arg0) {
	    Button goBack = new Button("Go Back");
	    goBack.setOnAction(e -> arg0.setScene(choice));
	    return goBack;
	  }

	  public PieChart getPieChartWithMonth(ReportBase2 report) {
	    List<Double> percentage = report.getPercents();
	    // ARIEL: THIS IS BROKEN, i would 10/10 recommend fixing thx
	    ObservableList<PieChart.Data> pieChartData = ObservableList<PieChart.Data>();

	    // ARIEL: add both the label and the percentage from the ReportBase class
	    for (int i=0; i<percentage.size(); i++) {
	        pieChartData.add( new PieChart.Data(report.percentLabels().get(i), percentage.get(i)));
	    }
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