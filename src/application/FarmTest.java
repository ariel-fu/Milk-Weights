package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class! feeling lazy after math class, add ur own names as authors!
 * 
 * @author Ariel
 *
 */
class FarmTest {
  private Farm farm;

  @BeforeEach
  void setUp() throws Exception {
    farm = new Farm("1");
    ArrayList<Milk> milks = new ArrayList<Milk>();
    milks.add(new Milk(10, "01/01/2019"));
    farm.milks = milks;
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  @Test
  void testGetMilk() {
    Milk getMilk = farm.getMilk("01/01/2019");
    assertTrue(getMilk.getWeight() == 10);
    farm.milks.add(new Milk(20, "1/2/2019"));
    getMilk = farm.getMilk("01/02/2019");
    assertTrue(getMilk.getWeight() == 20);
    try {
    farm.milks.add(new Milk(40, "20/10/2015"));
    getMilk = farm.getMilk("20/10/2015");
    assertTrue(getMilk.getWeight() == 40);
    fail("exception should have been thrown because it is an invalid date... ");
    } catch(Exception e) {
      
    }
  }

  @Test
  void testAddMilk() {
    fail("Not yet implemented");
  }

  @Test
  void testGetMonthTotal() {
    fail("Not yet implemented");
  }

  @Test
  void testGetYearTotal() {
    fail("Not yet implemented");
  }

  @Test
  void testGetRange() {
    fail("Not yet implemented");
  }

}


