package application;

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

  /**
   * This method calculate how much percent does each month contributes towards
   * the entire year
   * 
   * @return an arrayList of percentages of each month
   */
  @Override
  List<Double> getPercents() {
    List<Double> list = new ArrayList<Double>();
    int month = 0;
    for (int x = 1; x < 13; x++) {
      month = farm.getMonthTotal(x, year);
      list.add((double)((month) / farm.getYearTotal(year)));
    }

    return list;
  }

  /**
   * This method gets the overall average milk weight for the whole year
   * 
   * @return the average weight of milk for the whole year
   */
  @Override
  double getAvg() {
    return (farm.getYearTotal(year) / 12.0);
  }

  /**
   * This method returns the month with the lowest yield of milk
   */
  @Override
  double getMin() {
    double min = farm.getMonthTotal(1, year);
    for (int x = 2; x < 13; x++) {
      double month = farm.getMonthTotal(x, year);
      if (month < min)
        min = month;
    }
    return min;
  }

  /**
   * This method returns the month with the most yield of milk
   * 
   * @return the month that had the most yield of milk
   **/
  @Override
  double getMax() {
    double max = farm.getMonthTotal(1, year);
    for (int x = 2; x < 13; x++) {
      double month = farm.getMonthTotal(x, year);
      if (month > max)
        max = month;
    }
    return max;
  }

@Override
boolean validateInputs() {
	// TODO Auto-generated method stub
	return false;
}

}
