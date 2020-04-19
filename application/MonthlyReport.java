package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class models a monthly report per request from the user, should handle
 * data sorting (and display scene?)
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class MonthlyReport extends ReportBase {

	private HashMap<String, Farm> farms;
	private int year;
	private int month;
	
	/**
	 * Constructor with three parameters
	 * @param farms - HashMap of all data
	 * @param year to take month from
	 * @param month to take data from
	 */
	public MonthlyReport(HashMap<String, Farm> farms, Year year, int month) {
		this.farms = farms;
		this.year = year.getYear();
		this.month = month;
	}
	
	/**
	 * @return list of percents of each farm compared to total amount
	 */
	public List<Double> getPercents() {
		List<Double> percents = new ArrayList<Double>();
		double milkSum = 0;
		Farm currFarm = null;
		Iterator farmIt = farms.entrySet().iterator();
		while(farmIt.hasNext()) {
			currFarm = ((Map.Entry<String, Farm>)farmIt.next()).getValue();
			milkSum += currFarm.getMonthTotal(month, year);
			percents.add((double)currFarm.getMonthTotal(month, year));
		}
		for(int i = 0; i < percents.size(); i++) {
			percents.set(i, percents.get(i) / milkSum);
		}
		return percents;
	}

	/**
	 * @return average of all milk amounts
	 */
	public double getAvg() {
		double milkSum = 0;
		Farm currFarm = null;
		Iterator farmIt = farms.entrySet().iterator();
		while(farmIt.hasNext()) {
			currFarm = ((Map.Entry<String, Farm>)farmIt.next()).getValue();
			milkSum += currFarm.getAvgWeightMonth(month, year);
		}
		return milkSum / farms.size();
	}

	/**
	 * @return minimum of all milk amounts
	 */
	public double getMin() {
		double minMilk = 1000;
		Farm currFarm = null;
		Iterator farmIt = farms.entrySet().iterator();
		while(farmIt.hasNext()) {
			currFarm = ((Map.Entry<String, Farm>)farmIt.next()).getValue();
			if(currFarm.getMonthTotal(month, year) < minMilk) {
				minMilk = currFarm.getMonthTotal(month, year);
			}
		}
		return minMilk;
	}

	/**
	 * @return maximum of all milk amounts
	 */
	public double getMax() {
		double maxMilk = 0;
		Farm currFarm = null;
		Iterator farmIt = farms.entrySet().iterator();
		while(farmIt.hasNext()) {
			currFarm = ((Map.Entry<String, Farm>)farmIt.next()).getValue();
			if(currFarm.getMonthTotal(month, year) > maxMilk) {
				maxMilk = currFarm.getMonthTotal(month, year);
			}
		}
		return maxMilk;
	}

	@Override
	boolean validateInputs() {
		// TODO Auto-generated method stub
		return false;
	}

}