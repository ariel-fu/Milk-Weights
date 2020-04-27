
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
public class FarmReport extends ReportBase {
  private Farm farm; // user-specified farm
  private int year; // user-specified year

  /**
   * Constructor that sets the farm and the year to the user-specified values
   * 
   * @param farm the farm the user wants to see
   * @param year specific year of the farm
   */
  public FarmReport(Farm farm, int year) {
    this.farm = farm;
    this.year = year;
  }

  // TODO: delete the note
  /*
   * Note that it does not include methods such as getMin, getMax, getAverage,
   * getPercentages, etc. It will work because we have runReport() and since
   * each report uses different calculations, that method has to be different
   * for each report. However, each getMin, getMax, etc will do the same thing -
   * return its corresponding variable.
   */

  /**
   * Runs the report - finds the min, max, average and calculates the
   * percentages
   */
  @Override
  public void runReport() {
    String[] months = new DateFormatSymbols().getMonths();
    // sets the max to the first month's total, 0 if the month does not exist
    this.max = farm.getMonthTotal(1, year);
    // also set the min to the first month's total
    this.min = max;
    this.percents = new ArrayList<Double>();
    // labels: January, ..., December
    this.percentLabels = new ArrayList<String>();

    // finds the min and the max
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
    double total = 0;
    int size = percents.size();
    // gets the total size
    for (int i = 0; i < size; i++) {
      total += percents.get(i);
    }
    for (int i = 0; i < size; i++) {
      percents.set(i, percents.get(i) / total);
    }
    // calculates average? - should be a list?
    this.average = total / size;
  }

}
