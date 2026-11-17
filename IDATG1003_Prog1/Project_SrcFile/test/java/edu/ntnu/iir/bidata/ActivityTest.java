package edu.ntnu.iir.bidata;

import org.junit.jupiter.api.Test;

/**
 * Class for testing the Activity class, making sure it functions as it's supposed to,
 * and that the error handling works as intended.
 */
public class ActivityTest {
  // Class for testing the Activity class, making sure it functions as it's supposed to,
  // and that the error handling works as intended.

  @Test
  public void testActivityCreation() {
    // Creating a new Activity object with valid parameters.
    Activity activity =
        new Activity("Test Activity", "This is a test activity.", "Test Author", null);

    // Testing that valid parameters worked.
    assert activity
        .getName()
        .equals("Test Activity");
    assert activity
        .getDescription()
        .equals("This is a test activity.");
    assert activity
        .getAuthor()
        .equals("Test Author");
  }

  @Test
  public void testActivityCreationWithNullName() {
    try {
      new Activity(null, "This is a test activity.", "Test Author", null);
      assert false; // Should not reach here
    } catch (IllegalArgumentException e) {
      assert e
          .getMessage()
          .equals("Name cannot be null");
    }
  }

  @Test
  public void testActivityCreationWithEmptyName() {
    try {
      new Activity("", "This is a test activity.", "Test Author", null);
      assert false; // Should not reach here
    } catch (IllegalArgumentException e) {
      assert e
          .getMessage()
          .equals("Name cannot be empty");
    }
  }

  @Test
  public void testActivityCreationWithNullDescription() {
    try {
      new Activity("Test Activity", null, "Test Author", null);
      assert false; // Should not reach here
    } catch (IllegalArgumentException e) {
      assert e
          .getMessage()
          .equals("Description cannot be null");
    }
  }

  @Test
  public void testActivityWithEmptyDescription() {
    try {
      new Activity("Test Activity", "", "Test Author", null);
      assert false; // Should not reach here
    } catch (IllegalArgumentException e) {
      assert e
          .getMessage()
          .equals("Description cannot be empty");
    }
  }

  @Test
  public void testprintshort() {
    Activity activity =
        new Activity("Test Activity",
            "This is a test activity.", "Test Author", null);
    String expectedOutput = "Test Activity | Logged by: Test Author | Index position: ";
    assert Activity
        .printShortTest(activity)
        .substring(0, expectedOutput.length())
        .equals(expectedOutput);
  }


}