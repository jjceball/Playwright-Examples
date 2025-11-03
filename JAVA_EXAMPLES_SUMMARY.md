# Playwright Java Examples - Quick Summary

This repository now includes both **Python** and **Java** examples for Playwright testing.

## What Was Added

### Java Test Examples
- **Location**: `src/test/java/com/playwright/PlaywrightExampleTest.java`
- **Framework**: JUnit 5 with Playwright for Java
- **Number of Tests**: 12 example tests

### Test Categories

#### 1. Basic Title Verification (2 tests)
- `testHasTitle()` - Verifies title contains "Playwright" using regex
- `testPageTitleIsCorrect()` - Verifies exact title match

#### 2. Navigation Tests (2 tests)
- `testGetStartedLink()` - Clicks link and verifies heading
- `testCanNavigateToGetStarted()` - Verifies URL changes after navigation

#### 3. Search Functionality (1 test)
- `testSearchFunctionality()` - Tests search with text input and results

#### 4. Menu Navigation (1 test)
- `testNavigationMenuFunctionality()` - Iterates through multiple menu items

#### 5. Page Elements (1 test)
- `testPageContentAndElements()` - Verifies headings, text, and links

#### 6. Form Interactions (1 test)
- `testFormInteractions()` - Tests input fields, filling, and clearing

#### 7. Advanced Features (4 tests)
- `testScreenshotCapability()` - Demonstrates screenshot functionality
- `testBrowserContextIsolation()` - Verifies test isolation
- `testMultipleTabs()` - Handles multiple browser tabs
- `testJavaScriptExecution()` - Executes JavaScript in browser

## Quick Start

### Prerequisites
- Java 11+
- Maven 3.6+

### Setup
```bash
# Install dependencies
mvn clean install

# Install Playwright browsers
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

### Run Tests
```bash
# Run all Java tests
mvn test

# Run specific test class
mvn test -Dtest=PlaywrightExampleTest

# Run specific test method
mvn test -Dtest=PlaywrightExampleTest#testHasTitle
```

### View Browser (Non-Headless)
Modify line 26 in `PlaywrightExampleTest.java`:
```java
browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
```

## Key Features Demonstrated

### Element Locators
- Text locators: `page.locator("text=Get started")`
- Placeholder locators: `page.getByPlaceholder("Search docs")`
- CSS selectors: `page.locator("button:has-text('Search')")`
- Filtered locators: `.filter(new Locator.FilterOptions().setHasText("value"))`

### Assertions
- Title: `assertThat(page).hasTitle("title")`
- Visibility: `assertThat(element).isVisible()`
- URL: `assertThat(page).hasURL("url")`
- Value: `assertThat(input).hasValue("value")`

### Interactions
- Navigation: `page.navigate("url")`
- Clicking: `locator.click()`
- Typing: `locator.fill("text")`
- Keys: `locator.press("Enter")`

### Advanced
- JavaScript execution: `page.evaluate("() => document.title")`
- Multiple tabs: `Page newPage = context.newPage()`
- Load states: `page.waitForLoadState(LoadState.NETWORKIDLE)`

## Project Structure

```
Playwright-Examples/
├── pom.xml                                    # Java/Maven config
├── requirements.txt                           # Python dependencies
├── README.md                                  # Python examples
├── README_JAVA.md                             # Java documentation
├── JAVA_EXAMPLES_SUMMARY.md                   # This file
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── playwright/
│                   └── PlaywrightExampleTest.java  # All Java tests
├── test_example.py                            # Python tests
└── tests/
    └── example.spec.ts                        # TypeScript tests
```

## Language Comparison

| Feature | Python | TypeScript | Java |
|---------|--------|------------|------|
| Framework | pytest-playwright | @playwright/test | JUnit 5 |
| Setup | `pip install` | `npm install` | `mvn install` |
| Async Support | Yes | Yes | No (Sync) |
| Browser Install | `playwright install` | `npx playwright install` | `mvn exec:java` |
| Test Structure | Functions | Functions | Methods |

## Next Steps

1. Run the tests to see them in action
2. Modify tests to match your application
3. Add more test cases as needed
4. Configure CI/CD with Maven

## Resources

- [Full Java Documentation](README_JAVA.md)
- [Playwright Java Docs](https://playwright.dev/java/docs/intro)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Guide](https://maven.apache.org/guides/)

## Dependencies

```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.44.0</version>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
</dependency>
```

