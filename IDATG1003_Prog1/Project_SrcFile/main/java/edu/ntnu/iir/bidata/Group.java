package edu.ntnu.iir.bidata;

/**
 * The Group class will handle the groups of Users that exist, allowing the program to limit
 * which activity logs are in use by who, and which users have access to which logs.
 */
public class Group {
  private String groupName;
  private String groupPassword;

  /**
   * Creating a constructor for the Group class, taking in two parameters to set the values of the
   * private variables.
   */
  public Group(String groupName, String groupPassword) {
    this.groupName = groupName;
    this.groupPassword = groupPassword;
  }

}
