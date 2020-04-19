package application;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YearTest {
  Year year;

  @BeforeEach
  void setUp() throws Exception {
    year = new Year("2019");
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  @Test
  void testGetYear() {
    assertTrue(year.getYear().equals("2019"));
  }

  @Test
  void testAddMilkBasic() {
    Milk milk = new Milk(10, "1/1/2019");
    year.addMilk(milk);
    assertTrue(year.yearOfMilk.get("1/1/2019").equals(milk));
  }

  @Test
  void testGetMilkBasic() {

    Milk milk = new Milk(10, "1/1/2019");
    year.addMilk(milk);
    assertTrue(year.getMilk("1/1/2019").equals(milk));
  }

  @Test
  void testGetYearTotal() {
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "1/" + i + "/2019"));
    }
    for (int i = 1; i < 29; i++) {
      year.addMilk(new Milk(1, "2/" + i + "/2019"));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "3/" + i + "/2019"));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(new Milk(1, "4/" + i + "/2019"));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "5/" + i + "/2019"));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(new Milk(1, "6/" + i + "/2019"));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "7/" + i + "/2019"));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "8/" + i + "/2019"));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(new Milk(1, "9/" + i + "/2019"));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "10/" + i + "/2019"));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(new Milk(1, "11/" + i + "/2019"));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "12/" + i + "/2019"));
    }

    int expected = 365;
    int results = year.getYearTotal();
    assertTrue(expected == results);
  }

  @Test
  void testGetMonthTotal() {
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "1/" + i + "/2019"));
    }
    int expected = 31;
    int results = year.getMonthTotal(1);
    assertTrue(expected == results);
  }

  @Test
  void testGetRange() {
    for (int i = 1; i < 32; i++) {
      year.addMilk(new Milk(1, "1/" + i + "/2019"));
    }
    int expected = 10;

    int results = year.getRange("1/5/2019", "1/15/2019");
    System.out.println(results);
    assertTrue(expected == results);
  }

}
