package com.bdb;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class loginTest {
    private WebDriver driver;

    @Test
    void invalidCredentials() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        username.sendKeys("XXXAdmin");
        password.sendKeys("admin123456");
        WebElement loginButton = driver.findElement(By.className("orangehrm-login-button"));
        loginButton.click();

        WebElement alertMessage = driver.findElement(By.cssSelector("div.oxd-alert.oxd-alert--error p.oxd-alert-content-text"));
        Assertions.assertEquals("Invalid credentials", alertMessage.getText());
    }

    @Test
    void requeridUserName() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin123456");
        WebElement loginButton = driver.findElement(By.className("orangehrm-login-button"));
        loginButton.click();

        List<WebElement> spans = driver.findElements(By.tagName("span"));
        Assertions.assertTrue(spans.size() == 1, "Se encontro mas de un span required");
        Assertions.assertEquals("Required", spans.get(0).getText());
    }

    @Test
    void requeridInputs() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        WebElement loginButton = driver.findElement(By.className("orangehrm-login-button"));
        loginButton.click();

        List<WebElement> spans = driver.findElements(By.tagName("span"));
        Assertions.assertTrue(spans.size() == 2, "No encontro o menos de los required");
        Assertions.assertEquals("Required", spans.get(0).getText());
        Assertions.assertEquals("Required", spans.get(1).getText());
    }

    @Test
    void sessionSuccess() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        username.sendKeys("Admin");
        password.sendKeys("admin123");

        WebElement loginButton = driver.findElement(By.className("orangehrm-login-button"));
        loginButton.click();

        WebElement pUserName = driver.findElement(By.className("oxd-userdropdown-name"));
        Assertions.assertTrue(pUserName.getText().equals("Tiago silva"), "No fue encontrado expect Tiago silva, got -> " + pUserName.getText());
    }

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
