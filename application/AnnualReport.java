
package application;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
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

	// TODO: delete the note
	/*
	 * Note that it does not include methods such as getMin, getMax, getAverage,
	 * getPercentages, etc. It will work because we have runReport() and since each
	 * report uses different calculations, that method has to be different for each
	 * report. However, each getMin, getMax, etc will do the same thing - return its
	 * corresponding variable.
	 */

	/**
	 * Runs the report - finds the min, max, average, and calculates the percentages
	 */
	@Override
	public void runReport() {
		String[] months = new DateFormatSymbols().getMonths();
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
			double currYearTotal = currFarm.getYearTotal(year);
			// add to the total farm milk
			total += currYearTotal;
			percents.add(currYearTotal);
		}

		this.max = percents.get(0);
		this.min = max;

		// find the min and the max. also calculate the percents list
		for (int i = 0; i < percents.size(); i++) {
			double currFarmTotal = percents.get(i);
			// set the curr pos in the position list to the curr farm's year total/the
			// total milk for a year
			percents.set(i, currFarmTotal / total);
			// see if this milk weight is the new max
			if (currFarmTotal > max) {
				this.max = currFarmTotal;
			}
			// see if this milk weight is the new min
			if (currFarmTotal < min) {
				this.min = currFarmTotal;
			}
			// add this to the list of months
			percentLabels.add(months[i]);
		}
		// return the average farm milk weight for that year
		this.average = total / percents.size();

	}

}
