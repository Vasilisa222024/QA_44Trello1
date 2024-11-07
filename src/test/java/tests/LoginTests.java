package tests;

import dto.UserDTO;
import manader.ApplicationManager;
import org.testng.annotations.Test;
import pages.HomePage;

import static java.sql.DriverManager.getDriver;
import  static manader.PropertiesReader.getProperty;
public class LoginTests  extends ApplicationManager {
//    UserDTO user = UserDTO.builder()
//            .email("z0559882272@gmail.com")
//            .password("Mmar123456$")
//            .build();

    UserDTO user = UserDTO.builder()
            .email(getProperty("login.properties","email"))
            .password(getProperty("login.properties", "password"))
            .build();
    @Test
    public void loginTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin()
                .typeEmail(user)
                .typePassword(user);


    }
}
