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
  private List<Milk> milks;

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
  public Milk getMilk(String startDate, String date) {
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
    String indexOfEnd = "12/31" + year;
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
      // get the last year from the milk list
      int lastKnownYear = Integer
          .valueOf(milks.get(milks.size() - 1).getDate());
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
    String startDate = milks.get(0).getDate();
    int[] dateArray = this.parseDate(date);
    int[] startArray = this.parseDate(startDate);
    // get the difference in year, month, and day
    int yearDifference = startArray[2] - dateArray[2];
    int monthDifference = startArray[1] - dateArray[1];
    int dayDifference = startArray[0] - dateArray[0];
    int index = yearDifference + monthDifference + dayDifference;
    return index;
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
    // get the year
    String yearString = split[4] + split[5] + split[6] + split[7];
    int year = Integer.valueOf(yearString);
    dateArray[2] = year;
    // get the month
    String monthString = split[2] + split[3];
    int month = Integer.valueOf(monthString);
    dateArray[1] = month;
    // get the day
    String dayString = split[0] + split[1];
    int day = Integer.valueOf(dayString);
    dateArray[0] = day;
    return dateArray;
  }

}
