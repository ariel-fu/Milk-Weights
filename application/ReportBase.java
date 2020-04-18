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

  
  abstract List<Double> getPercents();
  
  abstract double getAvg();
  
  abstract double getMin();
  
  abstract double getMax();
  
  abstract boolean validateInputs();
  
  
  
  
  
}
