package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * This class models a Farm
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 * 
 *
 */
public class Farm {
  private String ID;
  // TODO: change back to private after testing
  protected List<Year> milks;

  /**
   * No-arg constructor that inits the ID to empty string and the list of milk
   */
  public Farm() {
    milks = new ArrayList<Year>();
    ID = "";
  }

  /**
   * Inits the List of milk and set the ID to the input
   * 
   * @param ID - ID of the Farm
   */
  public Farm(String ID) {
    milks = new ArrayList<Year>();
    this.ID = ID;
  }

  /**
   * Gets the Milk at that date
   * 
   * @param date - MM/DD/YYYY format
   * @return the Milk at that date
   */
  public Milk getMilk(String date) {
    // get the year from the date
    String[] dateParse = date.split("/");
    // validate
    // get the year from the list of years
    // use that year to get the milk 
    return null;
  }

  /**
   * Adds a Milk to the farm
   * 
   * @param weight - weight of the Milk
   * @param date   - the date associated with the milk
   */
  public void addMilk(int weight, String date) {
    
  }

  /**
   * Gets the total weight for the specified month and year
   * 
   * @param month - month specified by user
   * @param year  - year specified by user
   * @return the total weight for the specified month and year
   */
  public int getMonthTotal(String month, String year) {
    // TODO: implement
    return 0;
  }

  /**
   * Gets the total weight for the specified year
   * 
   * @param year - specified by the user
   * @return the total weight for the specified year
   * @throws IllegalArgumentException - if the input is not valid
   */
  public int getYearTotal(String year) throws IllegalArgumentException {
    return 0;
  }



  /**
   * Gets the total weight for a date range
   * 
   * @param startDate - start date specified by the user
   * @param endDate   - end date specified by the user
   * @return the total weight of milk between the start date and the end date
   */
  public int getRange(String startDate, String endDate) {
    // TODO: implement (obviously)
    return 0;
  }
  
  private boolean validInput(String[] dateParse) {
    // three parts - day, month, and year
    if(dateParse.length != 3) {
      return false;
    }
    // TODO: finish implementation
    return true;
  }



}
