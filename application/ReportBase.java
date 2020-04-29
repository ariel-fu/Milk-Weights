
package application;

import java.util.List;

/**
 * Abstract type for the reports. Include basic functions - clear, reload,
 * display, etc.
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public abstract class ReportBase {

  /**
   * This method calculate how much percent does each month contributes towards
   * the entire year
   * 
   * @return an arrayList of percentages of each month
   */
  List<Double> getPercents() {
    return this.percents;
  }

  List<String> getPercentLabels() {
    return this.percentLabels;
  }

  /**
   * This method gets the overall average milk weight for the whole year
   * 
   * @return the average weight of milk for the whole year
   */
  double getAvg() {
    return this.average;
  }

  /**
   * This method returns the month with the lowest yield of milk
   */
  double getMin() {
    return this.min;
  }

  /**
   * This method returns the month with the most yield of milk
   * 
   * @return the month that had the most yield of milk
   **/
  double getMax() {
    return this.max;
  }

  // variables that hold the average, min, max, list of percents, and percent
  // labels needed in Main
  protected double average;
  protected double min;
  protected double max;
  protected List<Double> percents;
  protected List<String> percentLabels;

  // abstract class
  public abstract void runReport();
}
