package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * This class holds one year worth of milk
 * 
 * @author Ariel
 *
 */
public class Year {
  private HashMap<String, Milk> yearOfMilk; // holds one year worth of milk
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

  public int getYearWorth() {
    int yearWorth = 0;
    for (int i = 0; i < yearOfMilk.size(); i++) {
      yearWorth += yearOfMilk.get(i).getWeight();
    }
    return yearWorth;
  }

  public void addMilk(Milk milk) {
    yearOfMilk.put(milk.getDate(), milk);
  }

  public Milk getMilk(Milk milk) {
    return yearOfMilk.get(milk.getDate());
  }

  /**
   * Gets the total milk weight for a given weight
   * 
   * @param month - user given month
   * @return total milk weight
   */
  public int getMonth(int month) {
    int monthTotal = 0;
    // add the single digits first, need this because format is mm/dd/yyyy
    for (int i = 1; i < 10; i++) {
      String currentDay = month + "/" + "0" + i + "/" + year;
      monthTotal += yearOfMilk.get(currentDay).getWeight();
    }
    // add the double digits next!
    for (int i = 10; i < this.getNumberOfDays(month) + 1; i++) {
      String currentDay = month + "/" + "0" + i + "/" + year;
      monthTotal += yearOfMilk.get(currentDay).getWeight();
    }
    // return total
    return monthTotal;
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
