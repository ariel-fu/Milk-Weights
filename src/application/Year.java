package application;

import java.util.HashMap;

/**
 * This class holds one year worth of milk. Note, this HashMap does not insert
 * in a sorted order. Since it uses the String date to map each Milk object, it
 * is a O(1) look-up :)
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class Year {
  // TODO: change back to private
  protected HashMap<String, Milk> yearOfMilk; // holds one year worth of milk
  private String year; // represents what year it is

  /**
   * Constructor that sets up the Year
   * 
   * @param year - represent the year
   */
  public Year(String year) {
    this.year = year;
    this.yearOfMilk = new HashMap<String, Milk>(366);
  }

  /**
   * Returns which year this is
   * 
   * @return the year
   */
  public String getYear() {
    return this.year;
  }

  /**
   * Adds a milk to the year
   * 
   * @param milk - new milk to add to the year
   */
  public void addMilk(Milk milk) {
    yearOfMilk.put(milk.getDate(), milk);
  }

  /**
   * Gets the milk given the date
   * 
   * @param milkDate - date of the milk
   * @return Milk object given the date
   */
  public Milk getMilk(String milkDate) {
    return yearOfMilk.get(milkDate);
  }

  /**
   * Gets the weight of a year worth of milk
   * 
   * @return the whole year's milk weight
   */
  public int getYearTotal() {
    int yearTotal = 0;
    for (int month = 1; month < 13; month++) {
      for (int i = 1; i < this.getNumberOfDays(month) + 1; i++) {
        String currentDay = month + "/" + i + "/" + year;
        yearTotal += yearOfMilk.get(currentDay).getWeight();
      }

    }

    return yearTotal;
  }

  /**
   * Gets the total milk weight for a given weight
   * 
   * @param month - user given month
   * @return total milk weight
   */
  public int getMonthTotal(int month) {
    int monthTotal = 0;
    // add all the days in tehe month
    for (int i = 1; i < this.getNumberOfDays(month) + 1; i++) {
      String currentDay = month + "/" + i + "/" + year;
      monthTotal += yearOfMilk.get(currentDay).getWeight();
    }
    // return total
    return monthTotal;
  }

  /**
   * Gets the total milk weight between the two date ranges
   * 
   * @param day1 - starting date
   * @param day2 - last date
   * @return the total milk weight between the two date ranges
   */
  public int getRange(String day1, String day2) {
    String currDate = day1;
    int totalWeight = 0;
    // while the curr date is not the last date, add up the weights
    while (!currDate.equals(day2)) {
      // add the current Milk's weight to the total
      totalWeight += yearOfMilk.get(currDate).getWeight();
      // split the string into an array and get the next date
      String[] splitDate = currDate.split("/");
      currDate = this.getNextDate(splitDate);
    }
    return totalWeight;
  }

  private String getNextDate(String[] currDate) {
    int day = Integer.valueOf(currDate[1]);
    int month = Integer.valueOf(currDate[0]);
    int year = Integer.valueOf(currDate[2]);
    int numDaysOfMonth = this.getNumberOfDays(month);
    if (day++ > numDaysOfMonth) {
      String date = (month + 1) + "/01/" + year;
      return date;
    } else {
      String date = (month) + "/" + (day) + "/" + year;
      return date;
    }

  }

  /**
   * Helper method that returns the number of days for the given month
   * 
   * @note this assumes that the month is valid. it will be checked in the Farm
   *       class.
   * @param month - given month, from the user
   * @return number of days for the month given by the user
   */
  private int getNumberOfDays(int month) {
    if (month == 1) {
      return 31;
    } else if (month == 2) {
      // leap year!
      if (Integer.valueOf(year) % 4 == 0 && Integer.valueOf(year) % 100 != 0) {
        return 29;
      } else {
        return 28;
      }
    } else if (month == 3) {
      return 31;
    } else if (month == 4) {
      return 30;
    } else if (month == 5) {
      return 31;
    } else if (month == 6) {
      return 30;
    } else if (month == 7) {
      return 31;
    } else if (month == 8) {
      return 31;
    } else if (month == 9) {
      return 30;
    } else if (month == 10) {
      return 31;
    } else if (month == 11) {
      return 30;
    } else {
      return 31;
    }
  }

}
