package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The activityRegister class is where any object of the activity class will be stored,
 * so that users can more easily access and view the logged activities.
 */
public class Activityregister {

  /**
   * The Activity array will store activity objects.
   * An array works best here, as other collection types
   * may not provide the necessary functionality for this use case - namely storing full objects.
   */
  private static ArrayList<Activity> activityLog = new ArrayList<>();

  /**
   * This method will go through the entire array and print any entry that contains the desired
   * date. This might not be the most efficient way
   * (needing to check the whole array) but it will do for this assignment.
   * Does risk people inputting the wrong date format, but that is handled in the Ui class.
   *
   * @param date The date to search for in the activity log.
   */
  public static void searchBydate(LocalDate date) {
    System.out.println("Searching for activities on " + date);
    // For each entry in the log...
    for (Activity activity : activityLog) {
      // If said entry's date matches the input date...
      if (activity
          .getSpecificDate()
          .equals(date)) {
        // Calls the printActivity method from the Ui class.
        Activity.printActivity(activity);

      }
    }
  }

  /**
   * This method is used for testing purposes, allowing the test class to
   * access the searchByDate method.
   */
  public static Activity searchByDateTest(LocalDate date) {
    // For each entry in the log...
    for (Activity activity : activityLog) {
      if (activity
          .getSpecificDate()
          .equals(date)) {
        return activity;
      }
    }
    return null; // Return null if no matching activity is found
  }

  /**
   * This method will remove an activity from the log, allowing users to delete entries.
   */
  public static void deleteActivity(Activity activity) {
    activityLog.remove(activity);
  }

  /**
   * This method will return the activity at the given index in the activity log.
   */
  public static Activity getActivity(int index) {
    return activityLog.get(index);
  }

  /**
   * This method will return the index of the given activity in the activity log.
   */
  public static int getActivityIndex(Activity activity) {
    return activityLog.indexOf(activity);
  }

  /**
   * This method will sort the activity log by the newest entries first, using the LocalDateTime
   * object stored in the activity class to determine which entry is newer or older.
   */
  public static void sortActivityLogByNewest() {
    activityLog.sort(Comparator
        .comparing(Activity::getSpecificDateTime)
        .reversed());
  }

  /**
   * This method will return the size of the activity log, allowing other classes to know how many
   * entries are stored in the log.
   */
  public static int getActivityLogSize() {
    return activityLog.size();
  }

  /**
   * This method simply adds an activity to the log.
   *
   * @param activity The activity to be added to the log.
   */
  public static void addActivity(Activity activity) {
    activityLog.add(activity);
  }

  /**
   * This method goes through the entire log and prints every entry. Due to
   * how the log is sorted, this will always result in chronological order from newest to oldest.
   * The if statement is used to check whether the date of the entry changes from the last one, and
   * if so, will change the printed header to create neat sections for each day.
   */
  public static void printAllActivities() {
    // Making sure the log is sorted before printing
    sortActivityLogByNewest();

    // The entry date is checked against the first entry,
    // which is extracted using substring 0 to 10 which contains only the date, not the time.
    String currentDate = getActivity(0)
        .getSpecificDate()
        .toString();
    System.out.println("Activities from " + currentDate + ":");

    for (Activity activity : activityLog) {

      if (activity
          .getDateTime()
          .substring(0, 10)
          .equals(currentDate)) {
        Activity.printshort(activity);
      } else {
        currentDate = activity
            .getSpecificDate()
            .toString();
        System.out.println("------------------------------------");
        System.out.println("Activities from " + currentDate + ":");
        Activity.printshort(activity);
      }
    }
  }



}

