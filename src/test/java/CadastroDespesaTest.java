import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CadastroDespesaTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--disable-dev-shm-usage", "--no-sandbox");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    @Test
    public void testCadastrarDespesa() {
        driver.get("https://dev-finance.netlify.app");

        WebElement botaoNovaTransacao = driver.findElement(By.cssSelector("a.button.new"));
        botaoNovaTransacao.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));

        WebElement inputDescricao = driver.findElement(By.id("description"));
        inputDescricao.sendKeys("Conta de Luz");

        WebElement inputValor = driver.findElement(By.id("amount"));
        inputValor.sendKeys("-150.00");

        WebElement inputData = driver.findElement(By.id("date"));
        inputData.sendKeys("2024-05-15");

        WebElement botaoSalvar = driver.findElement(By.xpath("//button[text()='Salvar']"));
        botaoSalvar.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        boolean transacaoCadastrada = driver.getPageSource().contains("Conta de Luz");
        assertTrue(transacaoCadastrada, "A transação não foi encontrada na tela após o cadastro!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
