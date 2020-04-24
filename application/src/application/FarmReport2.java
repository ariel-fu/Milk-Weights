package application;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * This class models a report about a specific farm and year, both are given by
 * the user
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class FarmReport2 extends ReportBase2 {
  private Farm farm; // user-specified farm
  private int year; // user-specified year

  /**
   * Constructor that sets the farm and the year to the user-specified values
   * 
   * @param farm the farm the user wants to see
   * @param year specific year of the farm
   */
  public FarmReport2(Farm farm, int year) {
    this.farm = farm;
    this.year = year;
  }

  /**
   * This method calculate how much percent does each month contributes towards
   * the entire year
   * 
   * @return an arrayList of percentages of each month
   */
  @Override
  List<Double> getPercents() {
    return this.percents;
  }

  /**
   * This method gets the overall average milk weight for the whole year
   * 
   * @return the average weight of milk for the whole year
   */
  @Override
  double getAvg() {
    return this.average;
  }

  /**
   * This method returns the month with the lowest yield of milk
   */
  @Override
  double getMin() {

    return this.min;
  }

  /**
   * This method returns the month with the most yield of milk
   * 
   * @return the month that had the most yield of milk
   **/
  @Override
  double getMax() {
    return this.max;
  }

  @Override
  public void runReport() {
    String[] months = new DateFormatSymbols().getMonths();
    this.max = farm.getMonthTotal(1, year);
    this.min = max;
    this.percents = new ArrayList<Double>();
    this.percentLabels = new ArrayList<String>();

    for (int x = 1; x < 13; x++) {
      double monthMilk = farm.getMonthTotal(x, year);
      if (monthMilk > max)
        max = monthMilk;

      if (monthMilk < min) {
        min = monthMilk;
      }

      this.percents.add(monthMilk);
      this.percentLabels.add(months[x - 1]);
    }

    // calculate % & average based on a list of doubles
    // helper function if this is done in multiple classes
    double sum = 0;
    int size = percents.size();
    for (int i = 0; i < size; i++) {
      sum += percents.get(i);
    }
    for (int i = 0; i < size; i++) {
      percents.set(i, percents.get(i) / sum);
    }
    this.average = sum / size;
  }

}
