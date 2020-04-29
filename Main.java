package application;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
  /**
   * Stores the farm, percent of total weight, and the total weight so it can be
   * used to make a table.
   * 
   * @author prasunguragain
   *
   */
  public class TableInner {
    // Farm name
    private final SimpleStringProperty name;

    // Total percentage of weight for a farm
    private final SimpleStringProperty percent;

    // Total weight for a farm
    private final SimpleStringProperty weight;

    /**
     * Inner class constructor.
     * 
     * @param farmName - farm name
     * @param percent  - total percentage of weight for a farm
     * @param weight   - total weight for a farm
     */
    public TableInner(String farmName, String percent, String weight) {
      this.name = new SimpleStringProperty(farmName);
      this.percent = new SimpleStringProperty(percent);
      this.weight = new SimpleStringProperty(weight);
    }

    // Gets the farm name
    public String getName() {
      return name.get();
    }

    // Gets the total percentage of weight for a farm
    public String getPercent() {
      return percent.get();
    }

    // Gets the total weight for a farm
    public String getWeight() {
      return weight.get();
    }
  }

  // Scene
  // The scene with the choices
  Scene choiceScene;

  // The CSVFile object
  CSVFile csv = new CSVFile();

  // The file name given by the user
  String fileName = "";

  // hashMap full of all farms
  HashMap<String, Farm> hashMap = null;

  // The Data Range Report scene
  Scene dataRangeReportScene = null;
  // The Monthly report scene
  Scene monthlyReportScene = null;
  // The Annual Report scene
  Scene annualReportScene = null;
  // The farm report scene
  Scene farmReportScene = null;

  // Buttons that will be used to change to a specified scene chosen by the user
  Button farmButton = new Button("Farm Report");
  Button monthButton = new Button("Month Report");
  Button annualButton = new Button("Annual Report");
  Button dataRangeButton = new Button("Data Range Report");

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
  Button submitDateForDataRangeReport = new Button("Submit Date");

  // Stores the first date picked by the user
  DatePicker firstDatePickedForDataRangeReport;

  // Stores the second date picked by the user
  DatePicker secondDatePickedForDataRangeReport;

  Button saveFileFarmReport = new Button("Save to the file");
  Button saveFileAnnualReport = new Button("Save to the file");
  Button saveFileMonthlyReport = new Button("Save to the file");
  Button saveFileDataRangeReport = new Button("Save to the file");

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
    // Makes the choice scene, for user to pick which data to look at
    HBox hBox = new HBox();
    hBox.getChildren().addAll(farmButton, monthButton, annualButton,
        dataRangeButton);
    VBox vBox = new VBox();
    Button close = new Button("Close");
    HBox closeAndSave = new HBox();
    closeAndSave.getChildren().addAll(close);
    vBox.getChildren().addAll(new Label("Choose one: "), hBox, closeAndSave);
    choiceScene = new Scene(vBox, 500, 100);

    // If the continue button was pressed in the welcome scene (first scene, the
    // main scene), then the scene changes to the choice scene
    continueButton.setOnAction(e -> arg0.setScene(choiceScene));

    try {
      // try to parse the file
      hashMap = csv.parseFile(new FileReader(fileName));
      // break out of the loop
    } catch (IOException e) {
      // show that it was an invalid message
      JOptionPane.showMessageDialog(null, "Invalid File name!");
      return;// leave
      // show an alert message to the user
    }
    Collection<Farm> collection = hashMap.values();
    allFarms = new ArrayList<Farm>(collection);

    // If the user decides to look at the farm reports
    farmButton.setOnAction(e -> arg0.setScene(getFarmReportScene(arg0)));

    // Once the user picks a farm in the farm report scene and clicks submit,
    // the
    // scene will change to a similar scene but now with the bar chart and pie
    // chart
    // of the farm that was picked
    farmReportSubmitButton.setOnAction(e -> {
      for (int i = 0; i < hashMap.size(); i++) {
        if (farmCombo.getValue() == allFarms.get(i).getID()) {
          arg0.setScene(this.getFarmReportSceneAfterUserChosesAFarm(arg0,
              allFarms.get(i)));
        }
      }
    });

    // If the user decides to look at the monthly report
    monthButton.setOnAction(
        e -> arg0.setScene(getMonthlyReportScene(arg0, choiceScene)));

    // Once the user picks a month in the monthly report scene and click submit,
    // the scene will change to a similar scene but now with the bar change, pie
    // chart, and table of the month that was picked
    monthReportSubmitButton.setOnAction(e -> {
      if (getMonthNum(monthCombo.getValue()) == -1) {

      } else if (getMonthNum(monthCombo.getValue()) == 1) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "January"));
      } else if (getMonthNum(monthCombo.getValue()) == 2) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "February"));

      } else if (getMonthNum(monthCombo.getValue()) == 3) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "March"));
      } else if (getMonthNum(monthCombo.getValue()) == 4) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "April"));
      } else if (getMonthNum(monthCombo.getValue()) == 5) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "May"));
      } else if (getMonthNum(monthCombo.getValue()) == 6) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "June"));
      } else if (getMonthNum(monthCombo.getValue()) == 7) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "July"));
      } else if (getMonthNum(monthCombo.getValue()) == 8) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "August"));
      } else if (getMonthNum(monthCombo.getValue()) == 9) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "September"));
      } else if (getMonthNum(monthCombo.getValue()) == 10) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "October"));
      } else if (getMonthNum(monthCombo.getValue()) == 11) {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "November"));
      } else {
        arg0.setScene(
            this.getMonthlyReportSceneAfterUserChosesAMonth(arg0, "December"));
      }
    });

    // If the user decides to look at the annual report
    annualButton.setOnAction(
        e -> arg0.setScene(getAnnualReportScene(arg0, choiceScene)));

    // If the user decides to look at the data range report
    dataRangeButton.setOnAction(
        e -> arg0.setScene(getDataRangeReportScene(arg0, choiceScene)));

    // Once the user picks 2 dates in the data range report class and clicks
    // submit,
    // the scene will change to a similar scene but now with a table of the
    // month
    // that was
    // picked
    submitDateForDataRangeReport.setOnAction(
        e -> arg0.setScene(this.getDataRangeReportSceneAfterPickingDate(arg0,
            firstDatePickedForDataRangeReport.getValue(),
            secondDatePickedForDataRangeReport.getValue())));

    // In the choice scene, if the user clicks the close button, the program
    // stops
    close.setOnAction(e -> arg0.close());
  }

  // TODO: If the user types a wrong file, SHOULD WE DO SOMETHING??? I made a
  // scene for that but don't know how to use it
//	public Scene fileNotFoundScene(Stage arg0) {
//		Text secondText = new Text();
//		secondText.setText("File: " + fileName
//				+ " not found. Please go back and change your stupid file name!");
//		secondText.setFill(Color.BLACK);
//		secondText.setStyle("-fx-font: 30 arial;");
//		HBox hBox = new HBox();
//		hBox.getChildren().addAll(secondText, new Label("     "), goBack(arg0));
//		hBox.setAlignment(Pos.CENTER);
//		Scene fileNotFoundScene = new Scene(hBox, 1000, 300);
//		return fileNotFoundScene;
//	}

  /**
   * Returns the farm report scene.
   * 
   * @param arg0   - arg
   * @param choice - scene of choices
   * @return the second scene, farm report
   */
  public Scene getFarmReportScene(Stage arg0) {
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
    farmReportScene = new Scene(secondVBox, 1000, 1500);
    return farmReportScene;
  }

  /**
   * Returns the farm report scene with bar chart and pie chart.
   * 
   * @param arg0       - arg
   * @param chosenFarm - the farm chosen by the user
   * @return the farm report scene with bar chart and pie chart
   */
  public Scene getFarmReportSceneAfterUserChosesAFarm(Stage arg0,
      Farm chosenFarm) {
    // Pie Chart
    PieChart chart = this.getPieChartForFarmReport(chosenFarm);
    chart.setTitle("Month Percentage");
    HBox charts = new HBox();
    charts.getChildren()
        .addAll(this.getBarChartForFarmReportScene(chosenFarm, arg0), chart);

    Text secondText = new Text();
    secondText.setText("Farm Report");
    secondText.setFill(Color.BLACK);
    secondText.setStyle("-fx-font: 20 arial;");

    Label farm = new Label("Farm     ");

    Label year = new Label("     Year: 2019");// CHANGE MAYBE
    Label space = new Label("          ");
    HBox hBoxSpace = new HBox();
    hBoxSpace.getChildren().addAll(space);
    HBox hBoxAll = new HBox();
    hBoxAll.getChildren().addAll(farm, farmCombo, year, hBoxSpace);

    HBox goBackAndSave = new HBox();
    goBackAndSave.getChildren().addAll(goBack(arg0), this.saveFileFarmReport);

    // Go back to choices
    VBox secondVBox = new VBox();
    secondVBox.getChildren().addAll(secondText, hBoxAll, farmReportSubmitButton,
        charts, goBackAndSave);

    // Return second scene
    farmReportScene = new Scene(secondVBox, 1000, 1500);
    return farmReportScene;
  }

  /**
   * Scene that lets the user know the file is saved and saved the file given by
   * the user.
   * 
   * @param arg -arg
   * @return the scene that says "Saved!"
   */
  private Scene getSaveFileScene(Stage arg) {
    Scene saveFileScene = null;
    Text secondText = new Text();
    secondText.setText("SAVED!");
    secondText.setFill(Color.BLACK);
    secondText.setStyle("-fx-font: 50 arial;");
    VBox vbox = new VBox();
    vbox.getChildren().addAll(secondText, goBack(arg));
    vbox.setAlignment(Pos.CENTER);
    saveFileScene = new Scene(vbox, 200, 300);
    return saveFileScene;

  }

  /**
   * Returns the pie chart for the farm report scene.
   * 
   * @param chosenFarm - the month chosen by the user
   * @return the pie chart for the farm report scene
   */
  public PieChart getPieChartForFarmReport(Farm chosenFarm) {
    FarmReport farmReport = new FarmReport(chosenFarm, 2019);
    farmReport.runReport();
    List<Double> percentage = farmReport.getPercents();
    ObservableList<PieChart.Data> pieChartData = FXCollections
        .observableArrayList(new PieChart.Data("Jan", percentage.get(0)),
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
   * Returns a bar chart for the farm report scene.
   * 
   * @param chosenFarm - the farm chosen by the user
   * @return a bar chart for the farm report scene
   */
  public BarChart getBarChartForFarmReportScene(Farm chosenFarm, Stage arg0) {
    // Bar Chart
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Milk Weights");
    BarChart barChart = new BarChart(xAxis, yAxis);
    XYChart.Series dataSeries1 = new XYChart.Series();
    dataSeries1.setName("Milk Weights");

    // Making the bar chart from info from FarmReport
    FarmReport farmReport = new FarmReport(chosenFarm, 2019);
    farmReport.runReport();
    dataSeries1.getData().add(new XYChart.Data("Min", farmReport.min));
    dataSeries1.getData().add(new XYChart.Data("Average", farmReport.average));
    dataSeries1.getData().add(new XYChart.Data("Max", farmReport.max));
    barChart.getData().add(dataSeries1);

    saveFileFarmReport.setOnAction(e -> {
      try {
        csv.writeToAFile("Farm Report", farmReport.min, farmReport.max,
            farmReport.average, farmReport.percents, farmReport.percentLabels);
        System.out.println(
            farmReport.min + " " + farmReport.max + " " + farmReport.average
                + " " + farmReport.percents + " " + farmReport.percentLabels);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      arg0.setScene(getSaveFileScene(arg0));
    });

    return barChart;
  }

  /**
   * Returns the annual report scene.
   * 
   * @param arg0   - arg
   * @param choice - scene with choices
   * @return annual report scene
   */
  public Scene getAnnualReportScene(Stage arg0, Scene choice) {
    // Scene thirdScene;
    Text thirdText = new Text();
    thirdText.setText("Annual Report");
    thirdText.setFill(Color.BLACK);
    thirdText.setStyle("-fx-font: 20 arial;");

    // ComboBox
    Label year = new Label("          Year:    ");
    Label year2019 = new Label("2019");
    HBox hBoxSpace = new HBox();
    HBox hBoxAll = new HBox();
    hBoxAll.getChildren().addAll(year, year2019, hBoxSpace);

    PieChart chart = getPieChartForAnnualReportScene();
    chart.setTitle("Month Percentage");

    HBox charts = new HBox();
    charts.getChildren().addAll(getTableForAnnualReportScene(), chart);

    VBox thirdBox = new VBox();

    HBox goBackAndSave = new HBox();
    goBackAndSave.getChildren().addAll(goBack(arg0), this.saveFileAnnualReport);

    // Makes the third scene
    thirdBox.getChildren().addAll(thirdText, hBoxAll, charts, goBackAndSave);

    saveFileAnnualReport.setOnAction(e -> {
      AnnualReport annualReportSave = new AnnualReport(hashMap, new Year(2019));
      annualReportSave.runReport();
      try {
        csv.writeToAFile("Annual Report", annualReportSave.min,
            annualReportSave.max, annualReportSave.average,
            annualReportSave.percents, annualReportSave.percentLabels);
        System.out.println(annualReportSave.min + " " + annualReportSave.max
            + " " + annualReportSave.average + " " + annualReportSave.percents
            + " " + annualReportSave.percentLabels);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      arg0.setScene(getSaveFileScene(arg0));
    });

    // Return the third scene
    annualReportScene = new Scene(thirdBox, 1000, 1500);
    return annualReportScene;
  }

  /**
   * Returns the pie chart for the annual report scene.
   * 
   * @return the pie chart for the annual report scene
   */
  public PieChart getPieChartForAnnualReportScene() {
    int num = allFarms.size();
    PieChart.Data pieChart = null;
    ArrayList<PieChart.Data> listOfData = new ArrayList<PieChart.Data>();
    Double percentTotle = 0.0;
    for (int i = 0; i < num; i++) {
      // FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
      AnnualReport annualReport = new AnnualReport(hashMap, new Year(2019));
      annualReport.runReport();
      percentTotle += annualReport.getPercents().get(i);
      pieChart = new PieChart.Data(allFarms.get(i).getID(), percentTotle);
      listOfData.add(pieChart);
      percentTotle = 0.0;
    }
    ObservableList<PieChart.Data> pieChartData = FXCollections
        .observableArrayList(listOfData);
    PieChart chart = new PieChart(pieChartData);
    return chart;
  }

  /**
   * Returns a table for the annual report scene.
   * 
   * @return a table for the annual report scene
   */
  public VBox getTableForAnnualReportScene() {
    // Table for farm, percentage, and weight
    final TableView<TableInner> table = new TableView<>();
    table.setItems(getListForTableForAnnualReportScene());
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

  /**
   * Helper method for getTableForAnnualReportScene() to return a list of info
   * to put on the table.
   * 
   * @return a list of info to put on the table.
   */
  private ObservableList<TableInner> getListForTableForAnnualReportScene() {
    ObservableList<TableInner> tableInner = FXCollections.observableArrayList();
    for (int i = 0; i < allFarms.size(); i++) {
      double percentForYear = 0.0;
      AnnualReport annualReport = new AnnualReport(hashMap, new Year(2019));
      annualReport.runReport();
      percentForYear += annualReport.getPercents().get(i);
      String percentDouble = Double.toString(percentForYear);
      String weightInt = Double.toString(allFarms.get(i).getYearTotal(2019));
      tableInner.add(
          new TableInner(allFarms.get(i).getID(), percentDouble, weightInt));
    }
    return tableInner;
  }

  /**
   * Returns the monthly report scene.
   * 
   * @param arg0   - arg
   * @param choice - scene with choices
   * @return monthly report scene
   */
  public Scene getMonthlyReportScene(Stage arg0, Scene choice) {
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
    monthlyReportScene = new Scene(forthBox, 1000, 1000);
    return monthlyReportScene;
  }

  /**
   * Returns the monthly report scene with bar chart, pie chart and table
   * 
   * @param arg0        - arg
   * @param chosenMonth - chosenMonth
   * @return
   */
  public Scene getMonthlyReportSceneAfterUserChosesAMonth(Stage arg0,
      String chosenMonth) {
    // ComboBox
    Label monthLabel = new Label("Month     ");
    HBox hBox = new HBox();
    hBox.getChildren().addAll(monthLabel, monthCombo);

    PieChart pieChart2 = getPieChartForMonthlyReportScene(chosenMonth);
    pieChart2.setTitle("Monthly Percentage");
    HBox charts = new HBox();
    charts.getChildren().addAll(getBarChartForMonthReportScene(chosenMonth),
        pieChart2);
    Label spacing = new Label(
        "                                                 ");
    HBox tableSpace = new HBox();
    tableSpace.getChildren().addAll(getTableForMonthlyReportScene(chosenMonth),
        spacing);

    Text forthText = new Text();
    forthText.setText("Monthly Report");
    forthText.setFill(Color.BLACK);
    forthText.setStyle("-fx-font: 20 arial;");

    HBox goBackAndSave = new HBox();
    goBackAndSave.getChildren().addAll(goBack(arg0),
        this.saveFileMonthlyReport);

    HBox hbox = new HBox();
    hbox.getChildren().addAll(tableSpace, goBackAndSave);

    // Makes the forthScene
    VBox forthBox = new VBox();
    forthBox.getChildren().addAll(forthText, hBox, monthReportSubmitButton,
        charts, hbox);

    saveFileMonthlyReport.setOnAction(e -> {
      MonthlyReport monthlyReportSave = new MonthlyReport(hashMap,
          new Year(2019), getMonthNum(chosenMonth));
      monthlyReportSave.runReport();
      try {
        csv.writeToAFile("Monthly Report", monthlyReportSave.min,
            monthlyReportSave.max, monthlyReportSave.average,
            monthlyReportSave.percents, monthlyReportSave.percentLabels);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      arg0.setScene(getSaveFileScene(arg0));
    });

    // Return second scene
    monthlyReportScene = new Scene(forthBox, 1000, 1000);
    return monthlyReportScene;
  }

  /**
   * Helper method for getMonthlyReportSceneAfterUserChosesAMonth() to return a
   * bar chart
   * 
   * @param chosenMonth - the most chosen by the user
   * @return a bar chart
   */
  public BarChart getBarChartForMonthReportScene(String chosenMonth) {
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Milk Weights");
    BarChart barChart = new BarChart(xAxis, yAxis);
    XYChart.Series dataSeries1 = new XYChart.Series();
    dataSeries1.setName("Milk Weights");

    MonthlyReport monthlyReport = new MonthlyReport(hashMap, new Year(2019),
        getMonthNum(chosenMonth));
    monthlyReport.runReport();
    // Making the bar chart from info from Monthly Report
    dataSeries1.getData().add(new XYChart.Data("Min", monthlyReport.min));
    dataSeries1.getData()
        .add(new XYChart.Data("Average", monthlyReport.average));
    dataSeries1.getData().add(new XYChart.Data("Max", monthlyReport.max));
    barChart.getData().add(dataSeries1);
    return barChart;
  }

  /**
   * Helper method for getMonthlyReportSceneAfterUserChosesAMonth() to return a
   * pie chart.
   * 
   * @param chosenMonth - the month chosen by the user
   * @return a pie chart
   */
  public PieChart getPieChartForMonthlyReportScene(String chosenMonth) {
    int num = allFarms.size();
    ArrayList<String> listOfData = new ArrayList<String>();
    ArrayList<Double> listOfData2 = new ArrayList<Double>();
    ArrayList<PieChart.Data> finalArray = new ArrayList<PieChart.Data>();
    for (int i = 0; i < num; i++) {
      // FarmReport farmReport = new FarmReport(allFarms.get(i), 2019);
      MonthlyReport monthlyReport = new MonthlyReport(hashMap, new Year(2019),
          getMonthNum(chosenMonth));
      monthlyReport.runReport();
      listOfData.add(allFarms.get(i).getID());
      listOfData2.add(monthlyReport.getPercents().get(i));
    }
    for (int i = 0; i < num; i++) {
      PieChart.Data pieChart = new PieChart.Data(listOfData.get(i),
          listOfData2.get(i));
      finalArray.add(pieChart);
    }
    ObservableList<PieChart.Data> pieChartData = FXCollections
        .observableArrayList(finalArray);
    PieChart chart = new PieChart(pieChartData);
    return chart;
  }

  /**
   * Return the table for the monthly report scene table.
   * 
   * @param chosenMonth - the month chosen by the user
   * @return the table
   */
  public VBox getTableForMonthlyReportScene(String chosenMonth) {
    // Table for farm, percentage, and weight
    final TableView<TableInner> table = new TableView<>();
    table.setItems(getListForTableForMonthlyReportScene(chosenMonth));
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

  /**
   * Helper method for getTableForMonthlyReportScene() to return a list of info
   * to put in the table
   * 
   * @param chosenMonth - the month chosen by the user
   * @return a list of info to pul in the table
   */
  private ObservableList<TableInner> getListForTableForMonthlyReportScene(
      String chosenMonth) {
    ObservableList<TableInner> tableInner = FXCollections.observableArrayList();
    MonthlyReport monthly = new MonthlyReport(hashMap, new Year(2019),
        getMonthNum(chosenMonth));
    monthly.runReport();
    for (int i = 0; i < allFarms.size(); i++) {
      String percentDouble = Double.toString(monthly.getPercents().get(i));
      String weightInt = Double.toString(
          allFarms.get(i).getMonthTotal(getMonthNum(chosenMonth), 2019));
      tableInner.add(
          new TableInner(allFarms.get(i).getID(), percentDouble, weightInt));
    }
    return tableInner;
  }

  /**
   * Returns Data Range Report scene.
   * 
   * @param arg0   - arg
   * @param choice - the scene with choices
   * @return data Range Report scene
   */
  public Scene getDataRangeReportScene(Stage arg0, Scene choice) {
    // Scene dataRangeReportScene;
    Text fifthText = new Text();
    fifthText.setText("Data Range Report");
    fifthText.setFill(Color.BLACK);
    fifthText.setStyle("-fx-font: 20 arial;");

    // ComboBox
    Label data = new Label("Data Range:     ");
    TilePane r = new TilePane();
    TilePane r2 = new TilePane();
    firstDatePickedForDataRangeReport = new DatePicker();
    secondDatePickedForDataRangeReport = new DatePicker();
    r.getChildren().add(firstDatePickedForDataRangeReport);
    r2.getChildren().add(secondDatePickedForDataRangeReport);
    HBox hBoxAll = new HBox();
    HBox hBoxAll2 = new HBox();
    VBox calendar = new VBox();
    hBoxAll.getChildren().addAll(r);
    hBoxAll2.getChildren().addAll(r2);
    Label space = new Label("     ");
    Label space2 = new Label("     ");
    calendar.getChildren().addAll(data, hBoxAll, space, hBoxAll2, space2,
        submitDateForDataRangeReport);

    // Go back to choices
    VBox fifthBox = new VBox();
    fifthBox.getChildren().addAll(fifthText, calendar, goBack(arg0));

    // Return fifth scene
    dataRangeReportScene = new Scene(fifthBox, 1000, 1500);
    return dataRangeReportScene;
  }

  /**
   * Return the data range report scene with table
   * 
   * @param arg0  - arg
   * @param date1 - first date picked by the user
   * @param date2 - second date picked by the user
   * @return the data range report scene with table
   */
  public Scene getDataRangeReportSceneAfterPickingDate(Stage arg0,
      LocalDate date1, LocalDate date2) {
    Text fifthText = new Text();
    fifthText.setText("Data Range Report");
    fifthText.setFill(Color.BLACK);
    fifthText.setStyle("-fx-font: 20 arial;");

    // Date range
    Label dateRange = new Label("Date Range:     ");
    Label to = new Label(" - ");
    String firstDate = date1.toString();
    Label firstDateLabel = new Label(firstDate);
    String secondDate = date2.toString();
    Label secondDateLabel = new Label(secondDate);
    HBox firstToSecondDate = new HBox();
    firstToSecondDate.getChildren().addAll(dateRange, firstDateLabel, to,
        secondDateLabel);

//		Label data = new Label("Data Range:     ");
//		TilePane r = new TilePane();
//		TilePane r2 = new TilePane();
//		DatePicker d = new DatePicker();
//		DatePicker d2 = new DatePicker();
//		r.getChildren().add(d);
//		r2.getChildren().add(d2);
//		HBox hBoxAll = new HBox();
//		HBox hBoxAll2 = new HBox();
//		VBox calendar = new VBox();
//		hBoxAll.getChildren().addAll(r);
//		hBoxAll2.getChildren().addAll(r2);
//		Label space = new Label("     ");
//		Label space2 = new Label("     ");
//		calendar.getChildren().addAll(data, hBoxAll, space, hBoxAll2, space2,
//				submitDateForDataRangeReport);

    HBox goBackAndSave = new HBox();
    goBackAndSave.getChildren().addAll(goBack(arg0),
        this.saveFileDataRangeReport);

    Label spaceBetweenTableAndText = new Label();
    Label note = new Label(
        "NOTE: If you want to look at report of different dates, please go back and come "
            + "back to data range report to pick different dates.");
    // Go back to choices
    VBox fifthBox = new VBox();
    fifthBox.getChildren().addAll(fifthText, firstToSecondDate,
        getTableForDataRangeReportScene(date1, date2), goBackAndSave,
        spaceBetweenTableAndText, note);

    saveFileDataRangeReport.setOnAction(e -> {
      DateRangeReport dateRangeReportSave = new DateRangeReport(hashMap, date1,
          date2);

      dateRangeReportSave.runReport();
      try {
        csv.writeToAFile("Data Range Report", dateRangeReportSave.min,
            dateRangeReportSave.max, dateRangeReportSave.average,
            dateRangeReportSave.percents, dateRangeReportSave.percentLabels);
      } catch (IOException e1) {
        // won't happen bc we create a file and write to it
      }
      arg0.setScene(getSaveFileScene(arg0));
    });

    // Return fifth scene
    dataRangeReportScene = new Scene(fifthBox, 1000, 1500);
    return dataRangeReportScene;
  }

  /**
   * Returns a table for the data range report scene
   * 
   * @param date1 - first date picked by user
   * @param date2 - second date picked by user
   * @return a table for the data range report scene
   */
  public VBox getTableForDataRangeReportScene(LocalDate date1,
      LocalDate date2) {
    // Table for farm, percentage, and weight
    final TableView<TableInner> table = new TableView<>();
    table.setItems(getListForTableDataRangeReportScene(date1, date2));
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

  /**
   * Helper method for getTableForDataRangeReportScene() to return a list of
   * info to put in the table
   * 
   * @param date1 - the first date picked by user
   * @param date2 - the second date picked by user
   * @return a list of info to put in the table
   */
  private ObservableList<TableInner> getListForTableDataRangeReportScene(
      LocalDate date1, LocalDate date2) {
    ObservableList<TableInner> tableInner = FXCollections.observableArrayList();
    DateRangeReport dateRangeReport = new DateRangeReport(hashMap, date1,
        date2);
    dateRangeReport.runReport();
    for (int i = 0; i < allFarms.size(); i++) {
      String percentDouble = Double.toString(dateRangeReport.percents.get(i));
      String weightInt = Double
          .toString(dateRangeReport.getTotalPerFarm().get(i));
      tableInner.add(new TableInner(dateRangeReport.percentLabels.get(i),
          percentDouble, weightInt));
    }
//		int startDate = date1.getMonthValue();
//		int endDate = date2.getMonthValue();
//		ArrayList<Double> percentForFarm = new ArrayList<Double>();
//		for (int i = 0; i < allFarms.size(); i++) {
//			// TODO: i will be 0 in first loop, 0 month does not make sense, NEED TO FIX!
//			MonthlyReport monthlyReport = new MonthlyReport(hashMap, new Year(2019), i);
//			monthlyReport.runReport();
//			for (int j = startDate; j < endDate + 1; j++) {
//				percentForFarm.add(monthlyReport.getPercents().get(j));
//			}
//		}
//		for (int k = 0; k < allFarms.size(); k++) {
//			String percentDouble = Double.toString(percentForFarm.get(k));
//			String weightInt = Double.toString(allFarms.get(k).getRangeTotal(date1, date2));
//			tableInner.add(new TableInner(allFarms.get(k).getID(), percentDouble, weightInt));
//		}
    return tableInner;
  }

  /**
   * Returns the number associated with the month, so January = 1, February =
   * 2,...December = 12.
   * 
   * @param chosenMonth - the month chosen by the user
   * @return the number associated with the month
   */
  public int getMonthNum(String chosenMonth) {
    // if the month is null (user did not choose a month first), display an
    // error message and return -1;
    if (chosenMonth == null) {
      JOptionPane.showMessageDialog(null, "Choose a month first!");
      return -1;
    }
    // otherwise, find the month
    if (chosenMonth.contentEquals("January")) {
      return 1;
    } else if (chosenMonth.contentEquals("February")) {
      return 2;
    } else if (chosenMonth.contentEquals("March")) {
      return 3;
    } else if (chosenMonth.contentEquals("April")) {
      return 4;
    } else if (chosenMonth.contentEquals("May")) {
      return 5;
    } else if (chosenMonth.contentEquals("June")) {
      return 6;
    } else if (chosenMonth.contentEquals("July")) {
      return 7;
    } else if (chosenMonth.contentEquals("August")) {
      return 8;
    } else if (chosenMonth.contentEquals("September")) {
      return 9;
    } else if (chosenMonth.contentEquals("October")) {
      return 10;
    } else if (chosenMonth.contentEquals("November")) {
      return 11;
    } else {
      return 12;
    }
  }

  /**
   * All scene has a button to go back to the choice scene for the user to pick
   * to look at a different data. This method returns that button for all scene
   * methods.
   * 
   * @param arg0 - arg
   * @return the button that goes back to the choice scene
   */
  public Button goBack(Stage arg0) {
    Button goBack = new Button("Go Back");
    goBack.setOnAction(e -> arg0.setScene(choiceScene));
    return goBack;
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
