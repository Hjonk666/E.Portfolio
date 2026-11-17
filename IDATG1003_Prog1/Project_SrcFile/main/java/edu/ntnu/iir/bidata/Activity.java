package edu.ntnu.iir.bidata;

// Class for constructing the object that will hold all necessary values of the Activity
// logged in the application.

// Importing the LocalDateTime class from the java.time package to handle
// date and time, preventing users from setting invalid times.
// The log will only show when the activity was logged, not when it was actually done.

import static edu.ntnu.iir.bidata.Activityregister.addActivity;
import static edu.ntnu.iir.bidata.Activityregister.getActivityIndex;
import static edu.ntnu.iir.bidata.Activityregister.sortActivityLogByNewest;

import java.time.LocalDate;
import java.time.LocalDateTime;
// Also importing the Formatter function so that I can display time in my preferred format.
import java.time.format.DateTimeFormatter;

/**
 * Objects hold four details; The name of the activity, a description of it,
 * the date/time it took place and the name of the author.
 */
public class Activity {

  /**
   * Establishing each of the parameters as private variables to be used in the constructor and
   * getters.
   **/
  private final String name;
  private final String description;
  private final String dateTime;
  private final String author;

  private final LocalDateTime dateTimeObject;
  private final LocalDate date;
  // Creating a formatter to format the date/time in a specific pattern for display purposes.
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  /**
   * Constructor for the Activity class, taking in four parameters to set the values of the
   * private variables. The Ui class does not give you the chance to set the date parameter,
   * as this should always be set to current time and date: the parameter only exists so that
   * they can be set manually within other methods, namely the createTestActivity method.
   **/
  public Activity(String name, String description, String author, LocalDateTime date) {

    // Implementing a switch/case statement that will check if any of the
    // parameters are null or empty, and if so, will return an error message and ask to try again.
    // This is to ensure that all necessary information is provided when creating an Activity
    // object.
    switch (name) {
      case null:
        throw new IllegalArgumentException("Name cannot be null");
      case "":
        throw new IllegalArgumentException("Name cannot be empty");
      default:
        break;
    }
    switch (description) {
      case null:
        throw new IllegalArgumentException("Description cannot be null");
      case "":
        throw new IllegalArgumentException("Description cannot be empty");
      default:
        break;
    }

    switch (author) {
      case null:
        throw new IllegalArgumentException("Author cannot be null");
      case "":
        throw new IllegalArgumentException("Author cannot be empty");
      default:
        break;
    }

    this.author = author;
    this.name = name;
    this.description = description;

    // DateTime has to be set differently as the LocalDateTime gives
    // its own datatype and has to be converted into a string, which is done with the formatter.
    // The Ui won't give an input, automatically setting date to null which works 'normally', while
    // giving the option to set manually for testing.
    if (date == null) {
      this.dateTimeObject = LocalDateTime.now();
      this.dateTime = this.dateTimeObject.format(formatter);
      this.date = LocalDate.now();
    } else {
      this.dateTimeObject = date;
      this.dateTime = this.dateTimeObject.format(formatter);
      this.date = this.dateTimeObject.toLocalDate();
    }
  }

  /**
   * This method creates four custom test activities - the only instances where you are supposed
   * to manually set the date/time. This lets you test and troubleshoot, making sure the
   * register works properly and sorts in the way you wanted it to when using the menu.
   */
  public static void createTestActivity() {
    Activity activity4 =
        new Activity("Test Activity4", "This is a test activity.", "Test Author",
            LocalDateTime.of(2026, 4, 4, 10, 0, 0));
    addActivity(activity4);
    Activity activity1 =
        new Activity("Test Activity1", "This is a test activity.", "Test Author",
            LocalDateTime.of(2026, 1, 1, 12, 0, 0));
    addActivity(activity1);
    Activity activity2 =
        new Activity("Test Activity2", "This is a test activity.", "Test Author",
            LocalDateTime.of(2026, 2, 2, 12, 0, 0));
    addActivity(activity2);
    Activity activity3 =
        new Activity("Test Activity3", "This is a test activity.", "Test Author",
            LocalDateTime.of(2026, 3, 3, 12, 0, 0));
    addActivity(activity3);
    sortActivityLogByNewest();

  }

  /**
   * Getters for each of the private variables, allowing access to their values outside the class.
   */
  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  /*
   * getDateTime method returns the converted version of date and time,
   * making it easier to read and understand for the user.
   */
  public String getDateTime() {
    return dateTime;
  }

  public String getAuthor() {
    return author;
  }

  // This getter is specifically for use when sorting, using the "pure" localdatetime to see which
  // object in the registry is younger or older.
  public LocalDateTime getSpecificDateTime() {
    return dateTimeObject;
  }

  // Needed a getter for specifically the date so I can search by it.
  public LocalDate getSpecificDate() {
    return date;
  }
  // End of Getters

  /**
   * Creating a method specifically for printing out the information stored in the Activity object.
   * This means I won't have to laboriously create "get" calls for every other method that needs it.
   * This version of the method is static so that it can be called from any class that uses
   * the activity object, without needing to create an instance of the Ui class.
   */
  public static void printActivity(Activity activity) {
    System.out.println("------------------------------------");
    System.out.println("Printing information for " + activity.getName() + ":");
    System.out.println("Description: " + activity.getDescription());
    System.out.println("Owner: " + activity.getAuthor());
    System.out.println("Date/Time logged: " + activity.getDateTime());
    System.out.println("Logged at index position: " + getActivityIndex(activity));
  }

  /**
   * A separate function to print only the name and author of the activity, letting the
   * searchByDate method print a more compact version of the log, while still giving the user
   * the option to see the full information by using the printActivity method.
   */
  public static void printshort(Activity activity) {
    System.out.println("------------------------------------");
    System.out.println(
        activity.getName() + " | Logged by: " + activity.getAuthor() + " | Index position: "
            + getActivityIndex(activity));
  }

  public static String printShortTest(Activity activity) {
    return activity.getName() + " | Logged by: " + activity.getAuthor() + " | Index position: "
        + getActivityIndex(activity);
  }
}
