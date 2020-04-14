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
    int milkIndex = this.getIndexOfDate(startDate, date);
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
  public int getMonthTotal(int month, int year) {
    // TODO: implement
    return 0;
  }

  /**
   * Gets the total weight for the specified year
   * 
   * @param year - specified by the user
   * @return the total weight for the specified year
   */
  public int getYearTotal(int year) {
    // TODO: implment
    return 0;
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
  private int getIndexOfDate(String startDate, String date) {
    int[] dateArray = this.getDateAsInt(date);
    int[] startArray = this.getDateAsInt(startDate);
    // get the difference in year, month, and day
    int yearDifference = startArray[0] - dateArray[0];
    int monthDifference = startArray[1] - dateArray[1];
    int dayDifference = startArray[2] - dateArray[2];
    int index = yearDifference + monthDifference + dayDifference;
    return index;
  }

  /**
   * Takes in a date and returns [year, month, day] as ints
   * 
   * @param date - a date to "transform" into ints
   * @return an array [year, month, day] as ints
   */
  private int[] getDateAsInt(String date) {
    int[] dateArray = new int[3];
    // split the array up
    String[] split = date.split("/");
    // get the year
    String yearString = split[4] + split[5] + split[6] + split[7];
    int year = Integer.valueOf(yearString);
    dateArray[0] = year;
    // get the month
    String monthString = split[2] + split[3];
    int month = Integer.valueOf(monthString);
    dateArray[1] = month;
    // get the day
    String dayString = split[0] + split[1];
    int day = Integer.valueOf(dayString);
    dateArray[2] = day;
    return dateArray;
  }

}
