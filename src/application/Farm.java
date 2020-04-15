package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * This class models a Farm
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 * 
 *
 */
public class Farm {
  private String ID;
  // TODO: change back to private after testing
  protected List<Milk> milks;

  /**
   * No-arg constructor that inits the ID to empty string and the list of milk
   */
  public Farm() {
    milks = new ArrayList<Milk>();
    ID = "";
  }

  /**
   * Inits the List of milk and set the ID to the input
   * 
   * @param ID - ID of the Farm
   */
  public Farm(String ID) {
    milks = new ArrayList<Milk>();
    this.ID = ID;
  }

  /**
   * Gets the Milk at that date
   * 
   * @param date - MM/DD/YYYY format
   * @return the Milk at that date
   */
  public Milk getMilk(String date) {
    int milkIndex = this.getIndexOfDate(date);
    return milks.get(milkIndex);
  }

  /**
   * Adds a Milk to the farm
   * 
   * @param weight - weight of the Milk
   * @param date   - the date associated with the milk
   */
  public void addMilk(int weight, String date) {
    milks.add(new Milk(weight, date));
  }

  /**
   * Gets the total weight for the specified month and year
   * 
   * @param month - month specified by user
   * @param year  - year specified by user
   * @return the total weight for the specified month and year
   */
  public int getMonthTotal(String month, String year) {
    // TODO: implement
    return 0;
  }

  /**
   * Gets the total weight for the specified year
   * 
   * @param year - specified by the user
   * @return the total weight for the specified year
   * @throws IllegalArgumentException - if the input is not valid
   */
  public int getYearTotal(String year) throws IllegalArgumentException {
    if (!validInput(year)) {
      throw new IllegalArgumentException("Year is not within range!");
    }
    // get the indexes of the start and end index
    String startString = "01/01/" + year;
    int startIndex = this.getIndexOfDate(startString);
    String indexOfEnd = "12/31/" + year;
    int endIndex = this.getIndexOfDate(indexOfEnd);

    int totalMilkWeight = 0; // stores total weight
    // add milk weights from Jan 1 until Dec 31
    for (int i = startIndex; i < endIndex; i++) {
      totalMilkWeight += milks.get(i).getWeight();
    }
    return totalMilkWeight;
  }

  /**
   * Validates the input that is either a year or a month
   * 
   * @param input - either a month or a year
   * @return true if the input is within a valid rangeSSS
   */
  private boolean validInput(String input) {
    // split the string and validate based on whether it is an month input or a
    // year input
    String[] parseInput = input.split("");
    if (parseInput.length == 4) {
      // a year -> convert to int and verify that it is less than the last
      // input's year
      int year = Integer.valueOf(input);
      String lastYear = milks.get(milks.size() - 1).getDate();
      int[] getYear = this.parseDate(lastYear);
      // get the last year from the milk list
      int lastKnownYear = Integer.valueOf(getYear[2]);
      if (year > lastKnownYear) {
        return false;
      } else {
        return true;
      }
    } else if (parseInput.length == 2) {
      // a month -> convert to int and verify that it is between 1 and 12
      int month = Integer.valueOf(input);
      if (month > 1 && month < 12) {
        return true;
      } else {
        return false;
      }
    } else {
      // not valid, return false
      return false;
    }

  }

  /**
   * Gets the total weight for a date range
   * 
   * @param startDate - start date specified by the user
   * @param endDate   - end date specified by the user
   * @return the total weight of milk between the start date and the end date
   */
  public int getRange(String startDate, String endDate) {
    // TODO: implement (obviously)
    return 0;
  }

  /**
   * Private method that gets the index in the List of Milks based on the date
   * given
   * 
   * @param date - date of the index to be returned
   * @return an index that corresponds to the date
   */
  private int getIndexOfDate(String date) {
    // if there are multiple milk objects in the list already, should not return
    // 0... unless, of course, it is the first milk object
    if (milks.size() > 1) {
      String startDate = milks.get(0).getDate();
      int[] dateArray = this.parseDate(date);
      int[] startArray = this.parseDate(startDate);
      // get the difference in year, month, and day between the date the user
      // chose and the first date registered for this farm.
      int yearDifference = dateArray[2] - startArray[2];
      int monthDifference = dateArray[1] - startArray[1];
      int dayDifference = dateArray[0] - startArray[0];
      int index = yearDifference + monthDifference + dayDifference;
      return index;
    } else {
      return 0; // only one object in the list
    }
  }

  /**
   * Takes in a date and returns [year, month, day] as ints
   * 
   * @param date - a date to "transform" into ints
   * @return an array [day, month, year] as ints
   */
  private int[] parseDate(String date) {
    int[] dateArray = new int[3];
    // split the array up
    String[] split = date.split("/");
    // get the day's int counter-part
    int day = Integer.valueOf(split[0]);
    dateArray[0] = day;
    // get the month's int counter-part
    int month = Integer.valueOf(split[1]);
    dateArray[1] = month;
    // get the year's int counter-part
    int year = Integer.valueOf(split[2]);
    dateArray[2] = year;
    return dateArray;
  }

}
