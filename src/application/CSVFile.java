package application;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class models a reader for CSV files. Should be able to parse and send
 * back usable information to Main class
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 * 
 *
 */
public class CSVFile extends FarmFile {

  private HashMap<String, Farm> map;

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
   */
  public HashMap<String, Farm> parseFile(String fileName) {
    Scanner scnr = new Scanner(fileName);
    HashMap<String, Farm> hm = new HashMap<String, Farm>();
    while (scnr.hasNext()) {
      String next = scnr.next();
      // CSV, split by a comma
      String[] split = next.split(",");
      boolean validDate = false;

      // validate the date
      LocalDate date = null;
      while (!validDate && scnr.hasNext()) {
        String currDate = split[0];
        try {
          date = LocalDate.parse(currDate);
          // quit the loop
          validDate = true;
        } catch (DateTimeException e) {
          // get the next line
          next = scnr.next();
          split = next.split(",");
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

      // will accept any String ID
      String ID = split[1];
      Farm farm = new Farm(ID);
      farm.addMilk(weight, date);
      hm.put(ID, farm);
    }
    scnr.close();
    // return the hashmap
    return hm;
  }

  // TODO: figure out how to write to a CSV file...
}