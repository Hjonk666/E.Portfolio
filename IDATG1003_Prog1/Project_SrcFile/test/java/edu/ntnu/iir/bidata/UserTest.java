package edu.ntnu.iir.bidata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserTest {
  @BeforeEach
  public void setup() {
    User.createUsers();
  }

  @Test
  public void testBaseUsers(){
    assert User.getUser("Pass123").equals("Zako");
    assert User.getUser("Pass456").equals("Zeko");
    assert User.getUser("Pass789").equals("Ziko");
    assert User.getUser("admin").equals("admin");
  }

  @Test
  public void testWrongPassword() {
    assert User.getUser("wrongpassword") == null;
  }

  @Test
    public void testUserCreation() {
    User.addUser("password123", "testuser");

    assert User.getUser("password123").equals("testuser");
    }

  @Test
  public void testUserLogin() {
    User.createUsers();
    String username = User.getUser("Pass123");
    assert username.equals("Zako");
  }
}
