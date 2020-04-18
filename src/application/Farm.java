package application;

import java.time.LocalDate;
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
   * Inits the List of milk and set the ID to the input
   * 
   * @param ID - ID of the Farm
   */
  public Farm(String ID) {
    years = new ArrayList<Year>();
    this.ID = ID;
  }

  /**
   * Gets the ID of the Farm
   * 
   * @return
   */
  public String getID() {
    return this.ID;
  }

  /**
   * Gets the Milk at that date
   * 
   * @param date - MM/DD/YYYY format
   * @return the Milk at that date
   */
  public Milk getMilk(LocalDate date) {
    int year = date.getYear();

    Year yearObj = this.getYear(year);
    if (yearObj == null) {
      return new Milk();
    } else {
      return yearObj.getMilk(date);
    }

  }

  /**
   * Helper method that runs through the list of years to get the year specified
   * 
   * @param year - year specified
   * @return null if the year is not in the list, otherwise, returns the Year
   *         object
   */
  private Year getYear(int year) {
    for (int i = 0; i < years.size(); i++) {
      if (years.get(i).getYear() == year) {
        return years.get(i);
      }
    }
    return null;
  }

  /**
   * Adds a Milk to the farm
   * 
   * @param weight - weight of the Milk
   * @param date   - the date associated with the milk
   */
  public void addMilk(int weight, LocalDate date) {
    int yearDate = date.getYear();
    Year year = this.getYear(yearDate);
    if (year == null) {
      // if the year does not exist, create a new year and add it to the list
      year = new Year(yearDate);
      years.add(year);
    }
    // add the weight and the date to the specific year
    year.addMilk(weight, date);
    return;
  }

  /**
   * Gets the total weight for the specified month and year
   * 
   * @param month - month specified by user
   * @param year  - year specified by user
   * @return the total weight for the specified month and year, returns -1 for
   *         invalid year or month
   */
  public int getMonthTotal(int month, int year) {
    Year thisYear = this.getYear(year);
    if (thisYear == null) {
      return -1;
    } else {
      return thisYear.getMonthTotal(month);
    }
  }

  /**
   * Gets the total weight for the specified year
   * 
   * @param year - specified by the user
   * @return the total weight for the specified year, returns -1 for invalid
   *         year
   */
  public int getYearTotal(int year) {
    Year thisYear = this.getYear(year);
    if (thisYear == null) {
      return -1;
    } else {
      return thisYear.getYearTotal();
    }
  }

  /**
   * Gets the total weight for a date range
   * 
   * @param day1 - start date specified by the user
   * @param day2 - end date specified by the user
   * @return the total weight of milk between the start date and the end date,
   *         -1 if either day's year is not in the list of years
   */
  public int getRangeTotal(LocalDate day1, LocalDate day2) {
    int rangeTotal = 0;
    // see if it spans more than 1 year
    if (day1.getYear() == day2.getYear()) {
      Year year = this.getYear(day1.getYear());
      if (year == null) {
        return -1;
      } else {
        return year.getRange(day1, day2);
      }
    } else {
      // spans multiple years
      Year year1 = this.getYear(day1.getYear());
      Year year2 = this.getYear(day2.getYear());
      // if either one of the years isn't in the list, return -1.
      if (year1 == null || year2 == null) {
        return -1;
      }
      List<Year> listOfYears = new ArrayList<Year>();
      // otherwise, track how many years are between them
      for (int i = 0; i < years.size(); i++) {
        Year currentYear = years.get(i);
        // if the current year is between the start day and the end day, add it
        // to the list
        if (currentYear.getYear() > year1.getYear()
            && currentYear.getYear() < year2.getYear()) {
          listOfYears.add(years.get(i));
        }
      }

      // add the difference between the start day and the end of the year
      rangeTotal += year1.getRange(day1, LocalDate.of(year1.getYear(), 12, 31));
      for (int i = 0; i < listOfYears.size(); i++) {
        Year currYear = listOfYears.get(i);
        LocalDate firstDay = LocalDate.of(currYear.getYear(), 1, 1);
        LocalDate lastDay = LocalDate.of(currYear.getYear(), 12, 31);
        rangeTotal += currYear.getRange(firstDay, lastDay);
      }
      // add the weights between the first day of the year and day2
      rangeTotal += year2.getRange(LocalDate.of(year2.getYear(), 1, 1), day2);
      return rangeTotal;

    }
  }

}
