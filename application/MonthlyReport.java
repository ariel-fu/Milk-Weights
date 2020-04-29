
package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class models a monthly report per request from the user, should handle
 * data sorting
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class MonthlyReport extends ReportBase {

  private HashMap<String, Farm> farms; // stores the farm for the user-specified
                                       // month
  private int year; // user-specified year
  private int month; // user-specified month

  /**
   * Constructor with three parameters
   * 
   * @param farms - HashMap of all data
   * @param year  to take month from
   * @param month to take data from
   */
  public MonthlyReport(HashMap<String, Farm> farms, Year year, int month) {
    this.farms = farms;
    this.year = year.getYear();
    this.month = month;
  }

  /*
   * Note that it does not include methods such as getMin, getMax, getAverage,
   * getPercentages, etc. It will work because we have runReport() and since
   * each report uses different calculations, that method has to be different
   * for each report. However, each getMin, getMax, etc will do the same thing -
   * return its corresponding variable.
   */

  /**
   * Runs the report - finds the min, max, average, and calculates the
   * percentages
   */
  @Override
  public void runReport() {
    // a list of Farm IDs, same order as the percentages
    this.percentLabels = new ArrayList<String>();
    // percent of each farm milk divided by total farm milk for a given year
    this.percents = new ArrayList<Double>();
    int total = 0;

    // first gets the farms from hashMaps
    Collection<Farm> collection = farms.values();
    // iterate through the hashmap to get the total farm milk for a given year
    Iterator farmIt = collection.iterator();

    while (farmIt.hasNext()) {
      // get the next farm
      Farm currFarm = (Farm) farmIt.next();
      // get the year total of the current farm
      double currMonthTotal = currFarm.getMonthTotal(month, year);
      // add to the total farm milk
      total += currMonthTotal;
      percents.add(currMonthTotal);
      // add the current farm's id to the list of IDs
      percentLabels.add(currFarm.getID());
    }

    this.max = percents.get(0);
    this.min = max;

    // find the min and the max. also calculate the percents list
    for (int i = 0; i < percents.size(); i++) {
      double currFarmTotal = percents.get(i);
      // set the curr pos to the curr farm total for a given month divided by
      // the total milk weight for that month
      percents.set(i, currFarmTotal / total);
      // see if this milk weight is the new max
      if (currFarmTotal > max) {
        this.max = currFarmTotal;
      }
      // see if this milk weight is the new min
      if (currFarmTotal < min) {
        this.min = currFarmTotal;
      }

    }
    // return the average farm milk weight for that year
    this.average = total / percents.size();
  }

}
