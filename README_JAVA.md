# Playwright Java Examples

This directory contains example test cases for Playwright using the Java programming language.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Project Structure

```
Playwright-Examples/
├── pom.xml                                    # Maven project configuration
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── playwright/
│                   └── PlaywrightExampleTest.java  # Example tests
└── README_JAVA.md                            # This file
```

## Setup

### 1. Install Dependencies

The project uses Maven for dependency management. Install the required dependencies by running:

```bash
mvn clean install
```

### 2. Install Playwright Browsers

After installing dependencies, you need to install the Playwright browsers:

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

Or use the Playwright installer:

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

## Running the Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=PlaywrightExampleTest
```

### Run Specific Test Method

```bash
mvn test -Dtest=PlaywrightExampleTest#testHasTitle
```

### Run Tests in UI Mode (Headed Browser)

To see the browser while tests run, modify the `launchBrowser()` method in the test class:

```java
browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
```

## Test Examples

The `PlaywrightExampleTest.java` file contains the following example tests:

### 1. Basic Title Verification
- `testHasTitle()` - Verifies that the page title contains "Playwright"
- `testPageTitleIsCorrect()` - Verifies the exact page title

### 2. Navigation Tests
- `testGetStartedLink()` - Clicks "Get started" link and verifies Installation heading
- `testCanNavigateToGetStarted()` - Navigates and verifies URL changes

### 3. Search Functionality
- `testSearchFunctionality()` - Tests the search feature on the documentation site

### 4. Menu Navigation
- `testNavigationMenuFunctionality()` - Tests navigation through main menu items

### 5. Page Elements
- `testPageContentAndElements()` - Verifies main content and navigation links are visible

### 6. Form Interactions
- `testFormInteractions()` - Tests form input, filling, and validation

### 7. Advanced Features
- `testScreenshotCapability()` - Demonstrates screenshot functionality
- `testBrowserContextIsolation()` - Verifies context isolation
- `testMultipleTabs()` - Demonstrates handling multiple tabs
- `testJavaScriptExecution()` - Executes JavaScript in the browser

## Key Playwright Java Concepts

### Page Navigation

```java
page.navigate("https://playwright.dev/");
```

### Locating Elements

```java
// By text
page.locator("text=Get started").click();

// By placeholder
page.getByPlaceholder("Search docs");

// By selector
page.locator("button:has-text('Search')");

// By text content
page.getByText("Some text");
```

### Assertions

```java
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// Assert title
assertThat(page).hasTitle("Expected Title");
assertThat(page).hasTitle(Pattern.compile("Playwright"));

// Assert visibility
assertThat(element).isVisible();

// Assert URL
assertThat(page).hasURL("https://example.com");

// Assert value
assertThat(input).hasValue("test");
```

### Element Interactions

```java
// Click
locator.click();

// Fill input
locator.fill("text");

// Press key
locator.press("Enter");

// Clear input
locator.clear();
```

### JavaScript Execution

```java
// Execute JavaScript
String result = (String) page.evaluate("() => document.title");

// Execute with arguments
int count = (Integer) page.evaluate("() => document.querySelectorAll('a').length");
```

## Test Isolation

Each test gets its own isolated browser context:
- `@BeforeEach` creates a new context and page
- `@AfterEach` closes the context
- This ensures tests don't interfere with each other

## Browser Options

Configure browser launch options:

```java
browser = playwright.chromium().launch(
    new BrowserType.LaunchOptions()
        .setHeadless(true)         // Run in headless mode
        .setSlowMo(100)           // Slow down operations by 100ms
        .setChannel("chrome")     // Use specific channel
);
```

## Multiple Browsers

To test across different browsers:

```java
// Chromium
browser = playwright.chromium().launch();

// Firefox
browser = playwright.firefox().launch();

// WebKit (Safari)
browser = playwright.webkit().launch();
```

## Dependencies

The project uses the following dependencies (defined in `pom.xml`):

- **Playwright**: 1.44.0
- **JUnit 5**: 5.10.2

## Troubleshooting

### Browser Installation Issues

If you encounter issues installing browsers, try:

```bash
mvn dependency:resolve
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --force"
```

### Version Compatibility

Make sure Java version matches the configuration in `pom.xml` (currently Java 11).

Check your Java version:
```bash
java -version
```

### IDE Setup

For IntelliJ IDEA or Eclipse:
1. Import as Maven project
2. Wait for dependencies to download
3. Install browsers using Maven exec plugin
4. Run tests from IDE

## Resources

- [Playwright Java Documentation](https://playwright.dev/java/docs/intro)
- [Playwright Java API Reference](https://playwright.dev/java/docs/api/class-playwright)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Documentation](https://maven.apache.org/guides/)

## License

This project is provided as an example for learning Playwright with Java.

