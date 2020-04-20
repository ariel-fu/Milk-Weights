package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class models an Annual Report requested by the user
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class AnnualReport extends ReportBase {

  private HashMap<String, Farm> farms; // hash map of all farms for the
                                       // user-specified year
  private int year; // user-specified year

  /**
   * Constructor, takes two arguments
   * 
   * @param farms - HashMap of all farms
   * @param year  to take data from
   */
  public AnnualReport(HashMap<String, Farm> farms, Year year) {
    this.farms = farms;
    this.year = year.getYear();
  }

  /**
   * @return list of percents of each farm compared to total amount
   */
  public List<Double> getPercents() {
    List<Double> percents = new ArrayList<Double>();
    double milkSum = 0;
    Farm currFarm;
    Iterator farmIt = farms.entrySet().iterator();
    while (farmIt.hasNext()) {
      currFarm = ((Map.Entry<String, Farm>) farmIt.next()).getValue();
      milkSum += currFarm.getYearTotal(year);
      percents.add((double) currFarm.getYearTotal(year));
    }
    for (int i = 0; i < percents.size(); i++) {
      percents.set(i, percents.get(i) / milkSum);
    }
    return percents;
  }

  /**
   * @return average of all milk amounts
   */
  public double getAvg() {
    double milkSum = 0;
    Farm currFarm;
    Iterator farmIt = farms.entrySet().iterator();
    while (farmIt.hasNext()) {
      currFarm = ((Map.Entry<String, Farm>) farmIt.next()).getValue();
      milkSum += currFarm.getYearTotal(year);
    }
    return milkSum / farms.size();
  }

  /**
   * @return minimum of all milk amounts
   */
  public double getMin() {
    double minMilk = 1000;
    Farm currFarm;
    Iterator farmIt = farms.entrySet().iterator();
    while (farmIt.hasNext()) {
      currFarm = ((Map.Entry<String, Farm>) farmIt.next()).getValue();
      if (currFarm.getYearTotal(year) < minMilk) {
        minMilk = currFarm.getYearTotal(year);
      }
    }
    return minMilk;
  }

  /**
   * @return maximum of all milk amounts
   */
  public double getMax() {
    double maxMilk = 0;
    Farm currFarm;
    Iterator farmIt = farms.entrySet().iterator();
    while (farmIt.hasNext()) {
      currFarm = ((Map.Entry<String, Farm>) farmIt.next()).getValue();
      if (currFarm.getYearTotal(year) > maxMilk) {
        maxMilk = currFarm.getYearTotal(year);
      }
    }
    return maxMilk;
  }


}
