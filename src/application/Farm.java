package application;

import java.util.ArrayList;
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
  protected List<Year> years;

  /**
   * No-arg constructor that inits the ID to empty string and the list of milk
   */
  public Farm() {
    years = new ArrayList<Year>();
    ID = "";
  }

  /**
   * Inits the List of milk and set the ID to the input
   * 
   * @param ID - ID of the Farm
   */
  public Farm(String ID) {
    years = new ArrayList<Year>();
    this.ID = ID;
  }

  /**
   * Gets the Milk at that date
   * 
   * @param date - MM/DD/YYYY format
   * @return the Milk at that date
   */
  public Milk getMilk(String date) {
    // get the year from the date
    String[] dateParse = date.split("/");
    // validate
    if (!this.validInput(dateParse)) {
      return null;
    } else if (!this.validateYear(dateParse[2])) {
      return null;
    } else {
      // fortunately, we will never run into a NullPointerException because we
      // already validated the year, and it is in the list of years :)
      Year yearOfMilk = null;
      // get the year from the list of years
      for (int i = 0; i < years.size(); i++) {
        if (years.get(i).getYear().equals(dateParse[2])) {
          yearOfMilk = years.get(i);
        }
      }
      // use that year to get the milk
      return yearOfMilk.getMilk(date);
    }
  }

  /**
   * Adds a Milk to the farm
   * 
   * @param weight - weight of the Milk
   * @param date   - the date associated with the milk
   */
  public void addMilk(int weight, String date) {
    // validate year
    String[] dateParse = date.split("/");
    // if invalid input, do nothing?? or throw exception, consult team
    if (!this.validInput(dateParse)) {
      return;
    } else {
      // fortunately, we will never run into a NullPointerException because we
      // already validated the year, and it is in the list of years :)
      Year yearOfMilk = null;
      // get the year from the list of years
      for (int i = 0; i < years.size(); i++) {
        if (years.get(i).getYear().equals(dateParse[2])) {
          yearOfMilk = years.get(i);
        }
      }
      // not in the list...
      if (yearOfMilk == null) {
        // create a new year and add to the list of years
        yearOfMilk = new Year(dateParse[2]);
        years.add(yearOfMilk);
      }
      // add the milk, and done
      yearOfMilk.addMilk(new Milk(weight, date));

      return;

    }
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
   */
  public int getYearTotal(String year) {
    // validate input
    if (!this.validateYear(year)) {
      return -1;
    }
    // fortunately, we will never run into a NullPointerException because we
    // already validated the year, and it is in the list of years :)
    Year yearOfMilk = null;
    // get the year from the list of years
    for (int i = 0; i < years.size(); i++) {
      if (years.get(i).getYear().equals(year)) {
        yearOfMilk = years.get(i);
      }
    }

    // use the year to get the total for that year
    return yearOfMilk.getYearTotal();
  }

  /**
   * Gets the total weight for a date range
   * 
   * @param startDate - start date specified by the user
   * @param endDate   - end date specified by the user
   * @return the total weight of milk between the start date and the end date
   */
  public int getRange(String startDate, String endDate) {
    // validate both start and end dates
    if (!this.validInput(startDate.split("/"))) {
      return -1;
    } else if (!this.validInput(endDate.split("/"))) {
      return -1;
    } else {
      // get year and validate (both years are the same bc implementing for one)
      String[] getYear = startDate.split("/");
      if (!this.validateYear(getYear[2])) {
        return -1;
      }
      Year year = null;
      for (int i = 0; i < years.size(); i++) {
        if (years.get(i).getYear().equals(year)) {
          year = years.get(i);
        }
      }
      // return the total weight between days
      return year.getRange(startDate, endDate);
    }

  }

  /**
   * Validates the date
   * 
   * @param dateParse - a String array in the format [mm, dd, yyyy]
   * @return true if the date is valid, false otherwise
   */
  private boolean validInput(String[] dateParse) {
    // two parts - day and month
    if (dateParse.length != 3) {
      return false;
    }
    // check the day
    String day = dateParse[1];
    if (Integer.valueOf(day) > 31) {
      return false;
    }
    // check the month
    String month = dateParse[0];
    if (Integer.valueOf(month) > 12) {
      return false;
    }
    return true;
  }

  /**
   * Validates the year
   * 
   * @param input - year
   * @return true if the year is already in the
   */
  private boolean validateYear(String input) {
    // check if the year given by the date is one of the years for this farm.
    for (Year year : years) {
      if (year.getYear().equals(input)) {
        return true;
      }
    }
    return false;
  }

}
