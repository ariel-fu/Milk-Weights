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
  // TODO: change to private
  protected Milk[] milks; // holds the milks for one year

  /**
   * Constructor that sets up the Year
   * 
   * @param year - represent the year
   */
  public Year(int year) {
    this.year = year;
    // set initial capacity to 366 because of stupid leap years
    milks = new Milk[366];
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
   * Gets the total milk weight for a given weight
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
    for (int i = 0; i < max + 1; i++) {
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

}
