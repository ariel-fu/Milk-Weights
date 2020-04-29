package application;

import java.time.LocalDate;

/**
 * This class holds one year worth of milk. Note, this HashMap does not insert
 * in a sorted order. Since it uses the String date to map each Milk object, it
 * is a O(1) look-up :)
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class Year {

  private int year; // represents what year it is
  private Milk[] milks; // holds the milks for one year

  /**
   * Constructor that sets up the Year
   * 
   * @param year - represent the year
   */
  public Year(int year) {
    this.year = year;
    // set initial capacity to 366 because of stupid leap years
    milks = new Milk[367];
    for (int i = 0; i < milks.length; i++) {
      milks[i] = new Milk();
    }
  }

  /**
   * Returns which year this is
   * 
   * @return the year
   */
  public int getYear() {
    return this.year;
  }

  /**
   * Adds a milk to the year
   * 
   * @param milk - new milk to add to the year
   */
  public void addMilk(int weight, LocalDate date) {
    milks[date.getDayOfYear()] = new Milk(weight);
  }

  /**
   * Gets the milk given the date
   * 
   * @param milkDate - date of the milk
   * @return Milk object given the date
   */
  public Milk getMilk(LocalDate date) {
    return milks[date.getDayOfYear()];
  }

  /**
   * Gets the weight of a year worth of milk
   * 
   * @return the whole year's milk weight
   */
  public int getYearTotal() {
    int yearTotal = 0;
    // get the year as an int

    for (int i = 0; i < milks.length; i++) {
      yearTotal += milks[i].getWeight();
    }

    return yearTotal;
  }

  /**
   * Gets the total milk weight for a given month
   * 
   * @param month - user given month
   * @return total milk weight
   */
  public int getMonthTotal(int month) {
    int monthTotal = 0;

    LocalDate firstDate = LocalDate.of(this.year, month, 1);
    int max = firstDate.lengthOfMonth();
    int firstDay = firstDate.getDayOfYear();
    // add all the days in the month
    for (int i = 0; i < max; i++) {
      // get the days in the month
      monthTotal += milks[firstDay + i].getWeight();
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
  public int getRange(LocalDate day1, LocalDate day2) {

    int totalWeight = 0;

    // while the curr date is not the last date, add up the weights
    for (int i = day1.getDayOfYear(); i < day2.getDayOfYear() + 1; i++) {
      totalWeight += milks[i].getWeight();
    }
    return totalWeight;
  }

  /**
   * Gets the max weight from this year
   * 
   * @return max weight, or 0 if the year is empty
   */
  public int getMaxWeightYear() {
    int max = 0;
    for (int i = 0; i < milks.length; i++) {
      int currWeight = milks[i].getWeight();
      if (currWeight > max) {
        max = currWeight;
      }
    }
    return max;
  }

  /**
   * Gets the min weight from this year
   * 
   * @return the min weight
   */
  public int getMinWeightYear() {
    int min = 1000000;
    for (int i = 0; i < milks.length; i++) {
      int currWeight = milks[i].getWeight();
      if (currWeight < min) {
        min = currWeight;
      }
    }
    return min;
  }

  /**
   * Gets the average for the year
   * 
   * @return the average as a double
   */
  public double getAvgWeightYear() {
    double avg = 0;
    for (int i = 0; i < milks.length; i++) {
      int currWeight = milks[i].getWeight();
      avg += currWeight;
    }
    return avg / milks.length;
  }

  /**
   * Gets the min weight of a month
   * 
   * @param month - specified month
   * @return the min weight of a specified month
   */
  public int getMinWeightMonth(int month) {
    int minMonthWeight = 1000000;

    LocalDate firstDate = LocalDate.of(this.year, month, 1);
    int max = firstDate.lengthOfMonth();
    int firstDay = firstDate.getDayOfYear();

    // add all the days in the month
    for (int i = 0; i < max + 1; i++) {
      int currWeight = milks[firstDay + i].getWeight();
      // if the current weight is less than the curr min, that will be the new
      // min
      if (currWeight < minMonthWeight) {
        minMonthWeight = currWeight;
      }
    }
    // return total
    return minMonthWeight;
  }

  /**
   * Get the max weight of a month
   * 
   * @param month - month to get the max weight
   * @return gets the max weight of a month
   */
  public int getMaxWeightMonth(int month) {
    int maxMonthWeight = 0;

    LocalDate firstDate = LocalDate.of(this.year, month, 1);
    int max = firstDate.lengthOfMonth();
    int firstDay = firstDate.getDayOfYear();

    for (int i = 0; i < max + 1; i++) {
      int currWeight = milks[firstDay + i].getWeight();
      // if the current is greater than the previous max, set the new max to the
      // current weight
      if (currWeight > maxMonthWeight) {
        maxMonthWeight = currWeight;
      }
    }
    // return total
    return maxMonthWeight;
  }

  /**
   * Gets the average weight of a user-specified month
   * 
   * @param month - the user-specified month
   * @return the average of that month
   */
  public double getAvgWeightMonth(int month) {
    double avg = 0;

    LocalDate firstDate = LocalDate.of(this.year, month, 1);
    int max = firstDate.lengthOfMonth();
    int firstDay = firstDate.getDayOfYear();
    // add all the days in the month
    for (int i = 0; i < max + 1; i++) {
      int currWeight = milks[firstDay + i].getWeight();
      avg += currWeight;
    }
    return avg / milks.length;
  }

}
