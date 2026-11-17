package edu.ntnu.iir.bidata;

import java.util.HashMap;

/**
 * The User class will handle which user is currently logged in, working with the Ui and Group
 * classes to create Activities in the appropriate log that the user belongs to.
 */
public class User {
  private String username;
  private String password;

  // A HashMap to store the users and their passwords.
  public static HashMap<String, String> users = new HashMap<>();

  /**
   * Creating a public method to add more users to the application. This will then go on to work
   * with the Group class to determine which users have access to which activity logs.
   */
  public static void addUser(String password, String username) {
    users.put(password, username);
  }

  /**
   * This method is there to ensure the program has a set amount of users to work
   * with from the start.
   */
  public static void createUsers() {
    addUser("admin", "admin");
    addUser("Pass123", "Zako");
    addUser("Pass456", "Zeko");
    addUser("Pass789", "Ziko");
  }

  /**
   * This method is used to get the username of a user based on their password.
   *
   * @param password The hashmap key to get username.
   * @return The username returned from the hashmap.
   */
  public static String getUser(String password) {
    return users.get(password);
  }

}
