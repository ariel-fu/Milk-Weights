package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class models a report for a date range.
 * 
 * @author Ariel, Prasun, Harry, Alex, Catherine
 *
 */
public class DateRangeReport extends ReportBase {

  private HashMap<String, Farm> farms; // all the farms
  private LocalDate day1; // day1 of the date range
  private LocalDate day2; // day2 of the date range
  private ArrayList<Double> totalPerFarm; // list of total milk weights over a
  // date range for each farm

  /**
   * Constructor that takes in the map of Farms and two dates
   * 
   * @param farms - HashMap of farms
   * @param day1  - LocalDate 1 - start date
   * @param day2  - LocalDate 2 - end date
   */
  public DateRangeReport(HashMap<String, Farm> farms, LocalDate day1,
      LocalDate day2) {
    this.farms = farms;
    this.day1 = day1;
    this.day2 = day2;
    totalPerFarm = new ArrayList<>();

    // initialize first because when doing set will get index 0 out of
    // bound of length 0
    for (int i = 0; i < farms.size(); i++) {
      totalPerFarm.add(null);
    }
  }

  /**
   * The total milk weight per farm
   * 
   * @return a list of doubles that has the total milk weight per farm
   */
  public ArrayList<Double> getTotalPerFarm() {
    return this.totalPerFarm;
  }

  /*
   * Note that it does not include methods such as getMin, getMax, getAverage,
   * getPercentages, etc. It will work because we have runReport() and since
   * each report uses different calculations, that method has to be different
   * for each report. However, each getMin, getMax, etc will do the same thing -
   * return its corresponding variable.
   */
  /**
   * Runs the report - min, max (pretty sure we don't need this, but we are
   * over-achievers), average, list of Farm IDs, list of total milk weight over
   * date range for each farm, list of total milk weight over a date range for a
   * farm/total milk over date range for every farm.
   */
  @Override
  public void runReport() {
    // a list of Farm IDs, same order as the percentages
    this.percentLabels = new ArrayList<String>();
    // percent of each farm milk divided by total farm milk for a given year
    this.percents = new ArrayList<Double>();
    int total = 0;

    // Prasun: first gets the farms from hashMaps
    Collection<Farm> collection = farms.values();
    // iterate through the hashmap to get the total farm milk for a given year
    Iterator farmIt = collection.iterator();

    while (farmIt.hasNext()) {
      // get the next farm
      Farm currFarm = (Farm) farmIt.next();
      // get the year total of the current farm
      double currMonthTotal = 0;
      int year1 = day1.getYear();
      int year2 = day2.getYear();
      if (currFarm.getYearTotal(year1) != -1
          && currFarm.getYearTotal(year2) != -1) {
        currMonthTotal = currFarm.getRangeTotal(day1, day2);
      }
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

      // add the currFarm's total over the date range to the list
      totalPerFarm.set(i, currFarmTotal);

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
