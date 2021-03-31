package br.cert.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.Assertions;

import org.openqa.selenium.support.PageFactory;

import br.cert.app.pages.HomePage;

public class SampleJUnitTest extends JUnitTestBase {

  private HomePage homepage;

  @BeforeEach
  public void initPageObjects() {
    homepage = PageFactory.initElements(driver, HomePage.class);
  }

  @Test
  public void testHomePageHasAHeader() {
    driver.get(baseUrl);
    Assertions.assertFalse("".equals(homepage.header.getText()));
  }
}
