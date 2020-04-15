package application;

/**
 * Abstract type for the reports. Include basic functions - clear, reload,
 * display, etc.
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public abstract class ReportBase {

  
  abstract List<Double> drawReport();
  
  abstract double getAvg();
  
  abstract double getMin();
  
  abstract double getMax();
  
  abstract boolean validateInputs();
  
}
