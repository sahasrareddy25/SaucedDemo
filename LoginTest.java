package example.AutomationCICD;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    void validLoginTest() {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.contains("inventory"));
    }

    @Test
void invalidLoginTest() {

    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.id("login-button")).click();

    String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

    assertEquals(
        "Epic sadface: Username and password do not match any user in this service",
        errorMsg
    );
}

    @Test
    void emptyTest() {

        driver.findElement(By.id("login-button")).click();

        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        assertEquals("Epic sadface: Username is required", errorMsg);
    }
    
    @Test
    void addToCart() {

        testValidLogin();
        
        // Add first product to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack"))
                .click();

        // Verify cart badge shows 1
        WebElement cartBadge =
                driver.findElement(By.className("shopping_cart_badge"));

        assertEquals("1", cartBadge.getText());
    }


    private void testValidLogin() {
		// TODO Auto-generated method stub
		
	}

	@AfterEach
    void tearDown() {
        driver.quit();
    }
}
