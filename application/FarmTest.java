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
    farm.milks = new ArrayList<Milk>();
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  /**
   * Tests getMilk for three milks
   */
  @Test
  void testGetMilk() {
    Farm test = new Farm();
    test.milks.add(new Milk(10, "01/01/2019"));
    Milk getMilk = test.getMilk("01/01/2019");
    assertTrue(getMilk.getWeight() == 10);
    test.milks.add(new Milk(20, "1/2/2019"));
    getMilk = test.getMilk("01/02/2019");
    assertTrue(getMilk.getWeight() == 20);
    try {
      test.milks.add(new Milk(40, "20/10/2015"));
      getMilk = test.getMilk("20/10/2015");
      assertTrue(getMilk.getWeight() == 40);
      fail(
          "exception should have been thrown because it is an invalid date... ");
    } catch (Exception e) {

    }
  }

  /**
   * Tests addMilk for 3 milks
   */
  @Test
  void testAddMilk() {
    Milk milk1 = new Milk(200, "01/01/2019");
    farm.addMilk(200, "01/01/2019");
    assertTrue(milk1.getWeight() == farm.getMilk("01/01/2019").getWeight());
    Milk milk2 = new Milk(100, "1/02/2019");
    farm.addMilk(100, "01/02/2019");
    System.out.println(milk2.getWeight());
    System.out.println(farm.getMilk("01/02/2019").getWeight());
    assertTrue(milk2.getWeight() == farm.getMilk("01/02/2019").getWeight());

    Milk milk3 = new Milk(190, "1/03/2019");
    farm.addMilk(190, "01/03/2019");
    assertTrue(milk3.getWeight() == farm.getMilk("01/03/2019").getWeight());
  }

  @Test
  void testGetMonthTotal() {
    fail("Not yet implemented");
  }

  @Test
  void testGetYearTotal() {
    // january
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "01/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "01/" + i + "/2019");
    }
    // feb
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "02/0" + i + "/2019");
    }
    for (int i = 10; i < 28; i++) {
      farm.addMilk(1, "02/" + i + "/2019");
    }
    // march
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "03/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "03/" + i + "/2019");
    }
    // april
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "04/0" + i + "/2019");
    }
    for (int i = 10; i < 30; i++) {
      farm.addMilk(1, "04/" + i + "/2019");
    }
    // may
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "05/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "05/" + i + "/2019");
    }
    // june
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "06/0" + i + "/2019");
    }
    for (int i = 10; i < 30; i++) {
      farm.addMilk(1, "06/" + i + "/2019");
    }
    // july
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "07/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "07/" + i + "/2019");
    }
    // august
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "08/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "08/" + i + "/2019");
    }
    // september
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "09/0" + i + "/2019");
    }
    for (int i = 10; i < 30; i++) {
      farm.addMilk(1, "09/" + i + "/2019");
    }
    // october
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "10/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "10/" + i + "/2019");
    }
    // november
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "11/0" + i + "/2019");
    }
    for (int i = 10; i < 30; i++) {
      farm.addMilk(1, "11/" + i + "/2019");
    }
    // december
    for (int i = 1; i < 10; i++) {
      farm.addMilk(1, "12/0" + i + "/2019");
    }
    for (int i = 10; i < 31; i++) {
      farm.addMilk(1, "12/" + i + "/2019");
    }
    
    int totalAmountExpected = 365;
    int expected = farm.getYearTotal("2019");
    System.out.println(expected);
    assertTrue(totalAmountExpected == expected);
  }

  @Test
  void testGetRange() {
    fail("Not yet implemented");
  }

}
