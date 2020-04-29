package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class models a reader for CSV files. Should be able to parse and send
 * back usable information to Main class
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 * 
 *
 */
public class CSVFile {

  private HashMap<String, Farm> map; // stores the map of Farms that will be
                                     // parsed from a file

  /**
   * Inits the hash map
   */
  public CSVFile() {
    map = new HashMap<String, Farm>();
  }

  /**
   * Parses the file
   * 
   * @param fileName - file given by the user, hopefully
   * @return a HashMap with all the farms and their corresponding data added.
   * @throws IOException
   */
  public HashMap<String, Farm> parseFile(FileReader fileName)
      throws IOException {
    BufferedReader scnr = new BufferedReader(fileName);
    HashMap<String, Farm> hm = new HashMap<String, Farm>();
    String next = scnr.readLine(); // skip the header
    // set next to the next line and check if it is null
    while ((next = scnr.readLine()) != null) {
      // nothing else to get!
      if (next.equals("")) {
        break;
      }

      // CSV, split by a comma
      String[] split = next.split(",");
      boolean validDate = false;

      // validate the date
      LocalDate date = null;
      while (!validDate && next != null) {
        split = next.split(",");
        String currDate = split[0];
        String[] parseDate = currDate.split("-");
        try {
          int year = Integer.valueOf(parseDate[0]);
          int month = Integer.valueOf(parseDate[1]);
          int day = Integer.valueOf(parseDate[2]);
          date = LocalDate.of(year, month, day);
          // quit the loop
          validDate = true;
        } catch (Exception e) {
          // get the next line
          next = scnr.readLine();
        }
      }

      // validate the weight
      String strWeight = split[2];
      int weight;
      try {
        weight = Integer.valueOf(strWeight);
      } catch (NumberFormatException e) {
        // if the weight is invalid, set it to a default of 0 for them to come
        // back to fix.
        weight = 0;
      }

      String ID = split[1];
      Farm farm;
      // if the hashmap does not contain this current key
      if (!hm.containsKey(ID)) {
        // will accept any String ID
        farm = new Farm(ID);
        hm.put(ID, farm);
      }
      // else get it from the map
      farm = hm.get(ID);
      farm.addMilk(weight, date);

    }
    scnr.close();
    // return the hashmap
    return hm;
  }

  /**
   * Writes to the file when prompted by the user. Writes the report data
   * 
   * @param reportName        - name of report: FarmReport, MonthlyReport,
   *                          AnnualReport, or DateRangeReport
   * @param min               - the min of that report
   * @param max               - max of report
   * @param average           - average of report
   * @param percentages       - list of percentages used in the pie chart
   * @param percentagesLables - list of labels associated with the percentagesS
   * @throws IOException - if the file is not found, which it won't be.
   * 
   */
  public void writeToAFile(String reportName, double min, double max,
      double average, List<Double> percentages, List<String> percentagesLabels)
      throws IOException {
    PrintWriter csvWriter = new PrintWriter("reportData");
    csvWriter.write("Name, min, max, average");

    // create a string to write onto the csv file
    String dataString = reportName + "\r\n" + "min: " + min + "\r\n" + "max: "
        + max + "\r\n" + "average: " + average + "";
    csvWriter.write("\r\n");
    csvWriter.write(dataString);
    csvWriter.write("\r\n");
    // create a list that associates a percentage with its label
    String typeOfPercentage = this.getTypeOfPercentage(reportName);
    csvWriter.write("Percents, " + typeOfPercentage);
    csvWriter.write("\r\n");
    List<List<String>> percentsData = new ArrayList<List<String>>();
    for (int i = 0; i < percentages.size(); i++) {
      List<String> currData = Arrays.asList("" + (percentages.get(i) * 100),
          percentagesLabels.get(i));
      percentsData.add(currData);
    }

    for (List<String> currPercent : percentsData) {
      csvWriter.write("" + currPercent);
      csvWriter.write("\r\n");
    }

    // flush and close the PrintWriter
    csvWriter.flush();
    csvWriter.close();
  }

  /**
   * Gets the type of percentage for a specified report
   * 
   * @param reportName - specified report
   * @return a String that describes what kind of percentage the report has
   */
  private String getTypeOfPercentage(String reportName) {
    if (reportName.equalsIgnoreCase("Farm Report")) {
      return "Month";
    } else if (reportName.equalsIgnoreCase("Annual Report")) {
      return "Year";
    } else {
      return "FarmID";
    }
  }
}