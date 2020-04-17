package application;

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
      // CSV - comma separated values, separate by commas
      String[] nextSplit = next.split(",");
      // default is assuming line is invalid
      boolean validLine = false;
      while (validLine == false) {
        // if the line is invalid, skip
        if (nextSplit.length != 3) {
          next = scnr.next();
          nextSplit = next.split("-");
        } else {
          validLine = true;
        }
      }
      // validate the date which will be at index 0
      String date = nextSplit[0];
      validLine = false;
      if (!validateDate(date)) {
        // skip the line if the date is not valid.
        while (validLine == false) {
          // if the line is invalid, skip
          if (nextSplit.length != 3) {
            next = scnr.next();
            nextSplit = next.split("-");
          } else {
            validLine = true;
          }
        }
      }

      // get a Farm object from the HashMap
      // ID is at index 1, will accept anything
      Farm farm = hm.get(date);
      // hashmap returns null if it does not contain that date
      if (farm == null) {
        // create a new farm to use
        // now, accepting all IDs
        farm = new Farm(nextSplit[1]);
      }
      String weightString = nextSplit[2];
      int weight;
      try {
        weight = Integer.valueOf(weightString);
      } catch (NumberFormatException e) {
        // it is okay, the user made a mistake! Don't we all? Add 0 for them.
        weight = 0;
      }
      // add the milk to the farm and add the farm to the hash map
      farm.addMilk(weight, date);
      hm.put(date, farm);
    }
    scnr.close();
    // return the hashmap
    return hm;
  }

  /**
   * Validates the date
   * 
   * @param date - date
   * @return true if the date is good to go
   */
  private boolean validateDate(String date) {
    String[] parseDate = date.split("-");
    if (parseDate.length != 3) {
      return false; // or throw exception?
    } else {
      try {
        int month = Integer.valueOf(parseDate[1]);
        int day = Integer.valueOf(parseDate[2]);
        // invalid month
        if (month > 12 || month < 1) {
          return false;
        } else if (day > 31 || day < 1) {
          return false;
        }
      } catch (NumberFormatException e) {
        // cannot cast from String to int
        return false;
      }
    }
    // everything is a-ok
    return true;
  }

}
