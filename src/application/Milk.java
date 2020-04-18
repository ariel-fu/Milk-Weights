package application;

/**
 * This class (obviously) models a Milk (weight) from the Farm
 * 
 * @author Alex, Ariel, Catherine, Harry, Prasun
 *
 */
public class Milk {
  private int weight; // weight of the milk

  /**
   * No-arg constructor that sets the weight to 0
   */
  public Milk() {
    this.weight = 0;
  }

  /**
   * Constructor that sets the weight to the input
   * 
   * @param weight - milk weight
   */
  public Milk(int weight) {
    this.weight = weight;

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

}
