 package gl4.tp2;
import io.github.bonigarcia.wdm.WebDriverManager;
	import org.junit.jupiter.api.AfterEach;
	import org.junit.jupiter.api.BeforeAll;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
    import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;

	import org.junit.jupiter.params.ParameterizedTest;
	import org.junit.jupiter.params.provider.ValueSource;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import java.util.ArrayList;
	
import java.util.List;

	public class ToDo {
		JavascriptExecutor javascript;
		WebDriver driver;
	   
	    @BeforeAll
	    public static void initializeDriver() {
	    	WebDriverManager.chromedriver().setup();
	    }

	    @BeforeEach
	    public void prepareDriver(){
	        driver = new ChromeDriver();
	        javascript = (JavascriptExecutor) driver;
	    }

	    @Test
	    public void testCase() throws InterruptedException {
	    	
	    	driver.get("https://todomvc.com");
	    	selectFramework("Backbone.js");
	    	
	    	List <String> toDos = new ArrayList<String>();
	    	toDos.add("Buy groceries");
	    	toDos.add("Read book");
	    	toDos.add("Go to market");	    	
	    	addActions(toDos);
	    	
	    	Thread.sleep(2000);
	    	checkAction();
	    	
	    	Thread.sleep(1000);
	    	assertLeft(2);   
	    }
	    
	    @ParameterizedTest
	    @ValueSource(strings = {"Dojo",
	    		"Vue.js"})
	    public void testCases(String link) throws InterruptedException {
	    	
	    	driver.get("https://todomvc.com");
	    	selectFramework(link);
	    	
	    	List <String> toDos = new ArrayList<String>();
	    	toDos.add("Buy groceries");
	    	toDos.add("Read book");
	    	toDos.add("Go to market");	
	    	addActions(toDos);
	    	
	    	Thread.sleep(2000);
	    	checkAction();
	    	
	    	Thread.sleep(2000);
	    	assertLeft(2);
	       
	    }
	    
	    
	    private void addActions(List <String> actions) throws InterruptedException {
	    	for(String action : actions) {
	    		Thread.sleep(2000);
	    		WebElement webElement = driver.findElement(By.className("new-todo"));
		    	webElement.sendKeys(action);
		    	webElement.sendKeys(Keys.RETURN);
	    	}
	    	
	    }
	    
	    private void checkAction() {	    	
	    	WebElement element = driver.findElement(By.cssSelector("li:nth-child(1) .toggle"));
	    	element.click();
	    }
	    
	    private void assertLeft(int expectedItemLeft) {
	    	WebElement webElement = driver.findElement(By.xpath("//footer/*/span | //footer/span"));
	    	if(expectedItemLeft == 1 ) {
	    		String test = String.format("$d item left", expectedItemLeft);
	    		ExpectedConditions.textToBePresentInElement(webElement, test);
	    	} else {
	    		String test = String.format("$d items left", expectedItemLeft);
	    		ExpectedConditions.textToBePresentInElement(webElement, test);
	    	}
	    }

		
		private void selectFramework(String link) {
	    	WebElement element = driver.findElement(By.linkText(link));
	        element.click();
	    }
	

		@AfterEach
	    public void quitDriver() throws InterruptedException {
	        driver.quit();
	    }
		
	}