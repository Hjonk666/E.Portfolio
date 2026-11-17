package edu.ntnu.iir.bidata;

/**
 * The main starting point of your application. Let this class create the
 * instance of your main-class that starts your application.
 */
public class Main {

  /**
   * The main method sets up the program, using only the Ui class to make sure the program can run
   * then initialises the application.
   */
  static void main(String[] args) {

    Ui ui = new Ui();
    ui.init();
  }

}