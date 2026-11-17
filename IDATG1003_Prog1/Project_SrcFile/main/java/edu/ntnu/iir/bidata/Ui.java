package edu.ntnu.iir.bidata;

import static edu.ntnu.iir.bidata.Activityregister.addActivity;
import static edu.ntnu.iir.bidata.Activityregister.getActivity;
import static edu.ntnu.iir.bidata.Activityregister.getActivityIndex;
import static edu.ntnu.iir.bidata.Activityregister.getActivityLogSize;
import static edu.ntnu.iir.bidata.Activityregister.printAllActivities;
import static edu.ntnu.iir.bidata.Activityregister.sortActivityLogByNewest;

import java.time.LocalDate;
// Importing the Scanner class which I will use to get input from the user in the console.
import java.util.Scanner;

/**
 * The UI class will handle all the input from the user,
 * giving an interface as well as methods that call on other
 * classes to make the application function.
 */
public class Ui {

  /**
   * Establishing the scanner variable as a private final variable,
   * meaning it can only be set once and reused throughout the class.
   * This is to ensure that the scanner is not accidentally closed or reinitialized,
   * which would cause errors in the program.
   */
  private final Scanner scanner;

  // Establishing the variable that will be used to control the while loop in the initiate method.
  public boolean running;

  public String username;

  /**
   * The UI constructor initializes the scanner variable, allowing the program to read input from
   * the console.
   */
  public Ui() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * This method will get the input from the user and return it as a String.
   * I have decided that all user input will be in the form of strings (for names, descriptions
   * and navigation,
   * this makes it easier without needing conversions or changes
   * for the specific methods that "could" use something else).
   */
  public String getInput() {
    String input = scanner.nextLine();
    // Bellow is a test to check what input the program detected
    // System.out.println("Your input was " + choice);

    return switch (input.trim()) {
      case null -> throw new IllegalArgumentException("Input cannot be null");
      case "" -> throw new IllegalArgumentException("Input cannot be empty");
      default -> input.trim();
    };
  }

  /**
   * Setting the start of the program - and while the running value is True, the program will
   * continue to run.
   */
  public void init() {
    // Creating the test activities to fill the log with premade entries, along with test users.
    Activity.createTestActivity();
    User.createUsers();
    // This part of init() checks who the user is before starting the rest of the application,
    // letting them log inn and setting the variable to be used throughout the program to set
    // access and user-specific info.
    boolean checkuser = true;
    while (checkuser) {
      System.out.println("Please enter your password");
      String password = getInput();
      if (User.users.containsKey(password)) {
        this.username = User.users.get(password);
        System.out.println("Welcome, " + username + "!");
        checkuser = false;
      } else {
        System.out.println("Invalid password. Please try again.");
      }
    }
    // Once user is set, the rest of the application can be initialized and run, using a while loop
    // that ensures the entirety will stay running until the quit function is called. This also
    // brings the user back to the starting menu whenever a function is completed so they don't
    // get stuck or have to restart the program.
    running = true;
    while (running) {
      start();

    }
  }

  /**
   * This method will display the main menu to the user and prompt them to choose an option.
   * Every other function in the program will, through calls across the UI, be accessed from this
   * method.
   */
  public void start() {
    System.out.println("------------------------------------");
    System.out.println("Welcome to the Activity Log.");
    System.out.println("Please choose what to do:");
    System.out.println("1. Log a new activity");
    System.out.println("2. Print log History");
    System.out.println("3. Search by Date");
    System.out.println("4. Print detailed activity");
    System.out.println("5. Delete existing activity");
    System.out.println("6. Exit Program");
    System.out.println("------------------------------------");

    try {
      baseMenu(getInput());
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
      System.out.println("Please try again.");
    }
  }

  /**
   * This method will handle the user's choice from the main menu and call the appropriate method
   * based on their selection.
   */
  public void baseMenu(String choice) {
    switch (choice) {
      case "1":
        // Handle choice 1 - log a new activity. Uses the logActivity method, calling to the
        // dedicated function.
        logActivity();
        break;
      case "2":
        // Handle choice 2 - print all activities. Needs to call the function from Register
        printAllActivities();
        break;
      case "3":
        // Handle choice 3 - search by date. Needs to call the function from Register
        dateSearch();
        break;
      case "4":
        // Handle choice 4 - print detailed activity.
        printSpecificActivity();
        break;
      case "5":
        // Handle choice 5 - delete existing activity. Needs to call the function from Register
        deleteActivity();
        break;
      case "6":
        // Handle choice 6 - exit program. Needs to call the function from UI to close the program.
        quitProg();
        break;
      default:
        System.out.println("Invalid input, please use the provided numbers.");
        break;
    }
  }

  /**
   * The method to actually log an activity - case 1 - that needs to ask for input for each field.
   * Prompts the user to set each input, then uses those to create the activity object on its
   * own with the provided
   * values and prints it (placeholder to show that it works).
   */
  public void logActivity() {
    boolean validInputs = false;
    while (!validInputs) {
      try {

        System.out.println("Please enter the name of the activity you want to log:");
        String activityName = getInput();


        System.out.println("Please enter a description for the activity:");
        String activityDescription = getInput();

        String activityAuthor = this.username;

        // When all  parameters are set correctly, the activity object is created.
        Activity activity = new Activity(activityName, activityDescription, activityAuthor, null);
        // The program can then add the new activity to the log.
        addActivity(activity);

        // Followed by sorting the log using the method from Activityregister.
        sortActivityLogByNewest();

        // The code below is simply to check that everything worked correctly; The new activity will
        // be at the "end" of the log, so printing that entry of the array should
        // give the same information as the activity object that was just created.
        System.out.println("Number of entries in log is " + getActivityLogSize());
        System.out.println("You logged the following activity:");
        // The logged activity will be index 0 in the log because it was sorted earlier, and always
        // sorts for the newest thing to be at the top of the log.
        Activity.printActivity(getActivity(0));
        System.out.println("Activity logged successfully!");


        validInputs = true;
      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Please try again.");
      }
    }
  }

  /**
   * This version of the print method is not static, which lets it take inn the getInput method
   * and use it to determine which specific activity the user wants to see.
   */
  public void printSpecificActivity() {
    boolean validInput = false;
    while (!validInput) {
      System.out.println("Please enter the index of the activity you want to see");
      try {
        int index = Integer.parseInt(getInput());
        if (index < 0 || index >= getActivityLogSize()) {
          printAllActivities();
          throw new IllegalArgumentException("Invalid input: Index out of bounds, please enter"
              + "the index of an existing activity from the list above.");
        }
        Activity.printActivity(getActivity(index));
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Please try again.");
      }
    }
  }

  /**
   * Creating a method that lets you quit the program, activated by case 5 from the menu.
   */
  public void quitProg() {
    System.out.println("Are you sure you want to quit? (y/n)");
    String input = getInput();
    if (input.equalsIgnoreCase("y")) {
      running = false;
      System.out.println("Exiting program...");
    } else if (input.equalsIgnoreCase("n")) {
      System.out.println("Returning to main menu...");

    } else {
      System.out.println("Invalid input, returning to main menu...");
    }
  }

  /**
   * This method will look through the activity register and print all activities from a given date.
   * Because the user has to give input for each part of the date, this method needs to be
   * partially in the Ui class so it can call the non-static getInput method, before passing the
   * completed date to the searchByDate method in the Activityregister.
   */
  public void dateSearch() {
    // Asking the user for input for the year,
    // making it an int and checking to make sure it's valid.
    System.out.println("Please enter the year you want to search for (YYYY):");
    boolean validInputs = false;
    while (!validInputs) {
      try {
        String yearstr = getInput();

        int yearInt = Integer.parseInt(yearstr);
        if (yearInt < 2000 || yearInt > 2030) {
          throw new IllegalArgumentException("Invalid input: Year must be between 2000 and 2030");
        }
        // Asking the user for the month and making sure it's a valid input.
        // The month has to be between January(1) and December(12).
        System.out.println("Please enter the month you want to search for (MM):");
        String month = getInput();

        int monthInt = Integer.parseInt(month);
        if (monthInt < 1 || monthInt > 12) {
          throw new IllegalArgumentException("Invalid input: Month must be between 1 and 12");
        }

        // Asking for the day - here the program could check for which month was given
        // to determine number of days. Might do that but haven't yet.
        System.out.println("Please enter the day you want to search for (DD):");
        String day = getInput();
        int dayInt = Integer.parseInt(day);

        if (dayInt < 1 || dayInt > 31) {
          throw new IllegalArgumentException("Invalid input: Day must be between 1 and 31");
        }

        // Once all inputs are valid, the int values are entered into the LocalDateTime format
        // so it can be used to search the register, using a more suitable datatype.
        LocalDate trueDate = LocalDate.of(yearInt, monthInt, dayInt);
        Activityregister.searchBydate(trueDate);

      } catch (NumberFormatException e) {
        System.out.println("Invalid input: Please enter a valid number");
      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
      }

      validInputs = true;
    }
  }

  /**
   * This method will be able to delete existing activities from the log.
   * Can be expanded upon to give only specific access to certain users,
   * but for now it will be open to all. As this method requires user input, it is largely in the
   * Ui class before calling the actual delete function from the Activityregister class.
   */
  public void deleteActivity() {
    boolean validInputs = false;
    while (!validInputs) {
      try {
        System.out.println("Which activity would you like to delete?");
        System.out.print("Enter the index of the activity to delete: ");
        int index = Integer.parseInt(getInput());
        if (index < 0 || index >= Activityregister.getActivityLogSize()) {
          Activityregister.printAllActivities();
          throw new IllegalArgumentException("Invalid input: Index out of bounds, please enter"
              + "the index of an existing activity from the list above.");

        }
        System.out.println("Are you sure you want to delete this activity? (y/n)");
        String confirmation = getInput();
        if (confirmation.equalsIgnoreCase("y")) {
          System.out.println("Deleting activity....");
          Activityregister.deleteActivity(Activityregister.getActivity(index));
          System.out.println("Activity deleted successfully.");
          validInputs = true;
        } else if (confirmation.equalsIgnoreCase("n")) {
          System.out.println("Deletion cancelled, returning to main menu...");
          validInputs = true;
        } else {
          throw new IllegalArgumentException("Invalid input: Please enter 'y' or 'n'");

        }
        {

        }

      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }

  }

}