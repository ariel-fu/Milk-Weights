package application;

/**
 * This class (obviously) models a Milk (weight) from the Farm
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class Milk {
  private int weight; // weight of the milk
  private String date; // date associated with the milk

  /**
   * No-arg constructor that sets the weight to 0
   */
  public Milk() {

  }

  /**
   * Constructor that sets the weight to the input
   * 
   * @param weight - milk weight
   */
  public Milk(int weight, String date) {
    this.weight = weight;
    this.date = date;
  }

  /**
   * Set the weight to the input
   * 
   * @param weight - new weight
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }

  /**
   * Gets the weight
   * 
   * @return the weight of the milk
   */
  public int getWeight() {
    return weight;
  }

  /**
   * Returns the date associated with the Milk
   * 
   * @return a date associated with the milk
   */
  public String getDate() {
    return this.date;
  }

  /**
   * Compares them and returns 0 if they are the same date
   * 
   * @param other - a String that can be used to compare dates with this Milk
   * @return 0 if they are the same date, a negative if this one is less than
   *         other, and a positive if this is greater than other.
   */
  public int compareTo(String other) {
    return this.getDate().compareTo(other);
  }
}
