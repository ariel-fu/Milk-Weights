package application;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YearTest {
  Year year;

  @BeforeEach
  void setUp() throws Exception {
    year = new Year(2019);
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  @Test
  void testGetYear() {
    assertTrue(year.getYear() == (2019));
  }

  @Test
  void testAddMilkBasic() {
    Milk milk = new Milk(10);
    year.addMilk(10, LocalDate.of(2019, 1, 1));
    System.out.println(LocalDate.of(2019, 1, 1).getDayOfYear());
    assertTrue(year.milks[1].getWeight() == 10);
  }

  @Test
  void testGetMilkBasic() {

    Milk milk = new Milk(10);
    year.addMilk(10, LocalDate.of(2019, 1, 1));
    System.out.println(year.getMilk(LocalDate.of(2019, 1, 1)).getWeight());
    assertTrue(year.getMilk(LocalDate.of(2019, 1, 1)).getWeight() == (milk)
        .getWeight());
  }

  @Test
  void testGetYearTotal() {
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 1, i));
    }
    for (int i = 1; i < 29; i++) {
      year.addMilk(1, LocalDate.of(2019, 2, i));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 3, i));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(1, LocalDate.of(2019, 4, i));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 5, i));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(1, LocalDate.of(2019, 6, i));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 7, i));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 8, i));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(1, LocalDate.of(2019, 9, i));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 10, i));
    }
    for (int i = 1; i < 31; i++) {
      year.addMilk(1, LocalDate.of(2019, 11, i));
    }
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 12, i));
    }

    int expected = 365;
    int results = year.getYearTotal();
    assertTrue(expected == results);
  }

  @Test
  void testGetMonthTotal() {
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 1, i));
    }
    int expected = 31;
    int results = year.getMonthTotal(1);
    assertTrue(expected == results);
  }

  @Test
  void testGetRange() {
    for (int i = 1; i < 32; i++) {
      year.addMilk(1, LocalDate.of(2019, 1, i));
    }
    int expected = 10;

    int results = year.getRange(LocalDate.of(2019, 1, 1),
        LocalDate.of(2019, 1, 10));
    System.out.println(results);
    assertTrue(expected == results);
  }

}
