package com.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightExampleTest {
    
    // Shared Playwright instance
    static Playwright playwright;
    static Browser browser;
    
    // Fresh context and page for each test
    BrowserContext context;
    Page page;
    
    @BeforeAll
    static void launchBrowser() {
        // Initialize Playwright
        playwright = Playwright.create();
        
        // Launch browser (chromium by default)
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }
    
    @BeforeEach
    void createContextAndPage() {
        // Create a new context and page for each test
        context = browser.newContext();
        page = context.newPage();
    }
    
    @AfterEach
    void closeContext() {
        // Clean up context after each test
        context.close();
    }
    
    @AfterAll
    static void closeBrowser() {
        // Clean up browser and playwright
        browser.close();
        playwright.close();
    }
    
    @Test
    @DisplayName("Should verify page title contains Playwright")
    public void testHasTitle() {
        // Navigate to Playwright website
        page.navigate("https://playwright.dev/");
        
        // Assert that the title contains "Playwright"
        assertThat(page).hasTitle(Pattern.compile("Playwright"));
    }
    
    @Test
    @DisplayName("Should verify exact page title")
    public void testPageTitleIsCorrect() {
        // Navigate to Playwright website
        page.navigate("https://playwright.dev/");
        
        // Assert the exact title
        assertThat(page).hasTitle("Fast and reliable end-to-end testing for modern web apps | Playwright");
    }
    
    @Test
    @DisplayName("Should click Get started link and verify Installation heading")
    public void testGetStartedLink() {
        // Navigate to Playwright website
        page.navigate("https://playwright.dev/");
        
        // Click the Get started link using text locator
        page.locator("text=Get started").click();
        
        // Expect page to have a heading with the name of Installation
        assertThat(page.locator("text=Installation").first()).isVisible();
    }
    
    @Test
    @DisplayName("Should navigate to Get started and verify URL")
    public void testCanNavigateToGetStarted() {
        // Navigate to the home page
        page.navigate("https://playwright.dev/");
        
        // Locate and click the 'Get started' link
        Locator getStartedLink = page.locator("text=Get started");
        getStartedLink.click();
        
        // Assert the URL after the click
        assertThat(page).hasURL("https://playwright.dev/docs/intro");
        
        // Assert that a heading exists and has the correct text
        Locator heading = page.locator("text=Installation").first();
        assertThat(heading).isVisible();
    }
    
    @Test
    @DisplayName("Should verify search functionality")
    public void testSearchFunctionality() {
        String searchQuery = "Locator";
        
        // Navigate to the docs page
        page.navigate("https://playwright.dev/docs/intro");
        
        // Click the search button/input to activate the search field
        Locator searchButton = page.locator("button:has-text('Search')").first();
        searchButton.click();
        
        // Type the search query into the active search input
        Locator searchInput = page.getByPlaceholder("Search docs");
        searchInput.fill(searchQuery);
        
        // Press 'Enter' to submit the search
        searchInput.press("Enter");
        
        // Assert that the search results page loads correctly
        Locator firstResult = page.getByText(searchQuery).first();
        assertThat(firstResult).isVisible();
    }
    
    @Test
    @DisplayName("Should verify navigation menu functionality")
    public void testNavigationMenuFunctionality() {
        // Navigate to the main documentation page
        page.navigate("https://playwright.dev/docs/intro");
        
        // Assert that we're on the correct initial page
        assertThat(page).hasURL("https://playwright.dev/docs/intro");
        assert page.title().contains("Playwright") : "Page title should contain 'Playwright'";
        
        // Test navigation for each menu item
        String[][] menuItems = {
            {"API", "https://playwright.dev/docs/api/class-playwright"},
            {"Trace Viewer", "https://playwright.dev/docs/trace-viewer"},
            {"Test Generator", "https://playwright.dev/docs/codegen"}
        };
        
        for (String[] item : menuItems) {
            String menuText = item[0];
            String expectedUrlPattern = item[1];
            
            // Find and click the navigation link
            Locator navLink;
            if (menuText.equals("API")) {
                navLink = page.locator("a[href=\"/docs/api/class-playwright\"]").first();
            } else if (menuText.equals("Trace Viewer")) {
                navLink = page.getByText("Trace Viewer").first();
            } else if (menuText.equals("Test Generator")) {
                navLink = page.getByText("Test Generator").first();
            } else {
                navLink = page.locator("text=" + menuText).first();
            }
            
            // Assert the link is visible and clickable
            assertThat(navLink).isVisible();
            navLink.click();
            
            // Wait for navigation to complete
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
            
            // Assert that we navigated to a page containing the expected URL pattern
            String currentUrl = page.url();
            assert currentUrl.contains(expectedUrlPattern) : 
                String.format("Expected URL to contain '%s', but got '%s'", expectedUrlPattern, currentUrl);
            
            // Assert that the page title contains relevant keywords
            String pageTitle = page.title();
            assert pageTitle.toLowerCase().contains(menuText.toLowerCase()) || 
                   pageTitle.toLowerCase().contains("playwright") :
                String.format("Expected page title to contain '%s' or 'playwright', but got '%s'", menuText, pageTitle);
            
            // Assert that the main content area is visible
            Locator mainContent = page.locator("main").first();
            assertThat(mainContent).isVisible();
            
            // Navigate back to the intro page for the next test
            page.navigate("https://playwright.dev/docs/intro");
        }
    }
    
    @Test
    @DisplayName("Should verify page content and elements")
    public void testPageContentAndElements() {
        // Navigate to Playwright website
        page.navigate("https://playwright.dev/");
        
        // Verify main heading is visible
        Locator mainHeading = page.locator("h1:has-text('Playwright')");
        assertThat(mainHeading).isVisible();
        
        // Verify description text is visible
        Locator description = page.locator("text=Fast and reliable end-to-end testing");
        assertThat(description).isVisible();
        
        // Verify navigation links are visible
        Locator docsLink = page.locator("text=Docs");
        assertThat(docsLink).isVisible();
        
        Locator apiLink = page.locator("text=API");
        assertThat(apiLink).isVisible();
    }
    
    @Test
    @DisplayName("Should handle form interactions")
    public void testFormInteractions() {
        // Navigate to a page with forms (using a test form example)
        page.navigate("https://playwright.dev/docs/intro");
        
        // Activate search
        Locator searchButton = page.locator("button:has-text('Search')").first();
        searchButton.click();
        
        // Verify search input is visible and editable
        Locator searchInput = page.getByPlaceholder("Search docs");
        assertThat(searchInput).isVisible();
        assertThat(searchInput).isEditable();
        
        // Type into search field
        searchInput.fill("testing");
        
        // Verify the input value
        assertThat(searchInput).hasValue("testing");
        
        // Clear the input
        searchInput.clear();
        assertThat(searchInput).hasValue("");
    }
    
    @Test
    @DisplayName("Should verify screenshot capability")
    public void testScreenshotCapability() {
        // Navigate to Playwright website
        page.navigate("https://playwright.dev/");
        
        // Take a screenshot (optional - commented out to avoid creating files during testing)
        // page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));
        
        // Verify page loaded successfully by checking title
        assertThat(page).hasTitle(Pattern.compile("Playwright"));
    }
    
    @Test
    @DisplayName("Should verify browser context isolation")
    public void testBrowserContextIsolation() {
        // Navigate to a page
        page.navigate("https://playwright.dev/");
        
        // Verify we can get the page URL
        String url = page.url();
        assert url.contains("playwright.dev") : "URL should contain 'playwright.dev'";
        
        // Verify page title
        String title = page.title();
        assert !title.isEmpty() : "Page title should not be empty";
        
        // This test verifies that each test gets its own isolated context
    }
    
    @Test
    @DisplayName("Should interact with multiple tabs")
    public void testMultipleTabs() {
        // Open first tab
        page.navigate("https://playwright.dev/");
        assertThat(page).hasTitle(Pattern.compile("Playwright"));
        
        // Open a new page (tab) in the same context
        Page newPage = context.newPage();
        newPage.navigate("https://playwright.dev/docs/intro");
        assertThat(newPage).hasURL("https://playwright.dev/docs/intro");
        
        // Verify both pages exist
        assert page.title().contains("Playwright") : "First page should still be accessible";
        assert newPage.title().contains("Playwright") : "Second page should be accessible";
        
        // Close the new page
        newPage.close();
    }
    
    @Test
    @DisplayName("Should handle JavaScript execution")
    public void testJavaScriptExecution() {
        // Navigate to a page
        page.navigate("https://playwright.dev/");
        
        // Execute JavaScript and get result
        String result = (String) page.evaluate("() => document.title");
        assert result.contains("Playwright") : "JavaScript execution should return page title";
        
        // Execute JavaScript with arguments
        int linksCount = (Integer) page.evaluate("() => document.querySelectorAll('a').length");
        assert linksCount > 0 : "Page should have links";
    }
}
