package edu.ntnu.iir.bidata;

import static edu.ntnu.iir.bidata.Activityregister.searchByDateTest;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class if for testing the Activity register class,
 * making sure it functions as it's supposed to.
 */
public class RegisterTest {

  /**
   * Starts up the activitylog so it contains the entries needed to test.
   */
  @BeforeEach
  public void setUp() {
    // Clear the activity log before each test
    Activity.createTestActivity();
  }

  @Test
  public void testSearchByDate() {
    // Test searching for activities on a specific date
    LocalDate testDate = LocalDate.of(2026, 1, 1);
    Activityregister.searchBydate(testDate);

    // Check if the activity log contains the expected activity
    assert searchByDateTest(testDate) != null;
    assert searchByDateTest(testDate)
        .getName()
        .equals("Test Activity1");
    assert searchByDateTest(testDate)
        .getSpecificDate()
        .equals(testDate);

  }

}
