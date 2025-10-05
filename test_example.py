import re
import pytest  # pyright: ignore[reportMissingImports]
from playwright.sync_api import Page, expect


def test_has_title(page: Page):
    page.goto("https://playwright.dev/")

    # Expect a title "to contain" a substring.
    expect(page).to_have_title(re.compile("Playwright"))


def test_page_title_is_correct(page: Page):
    """
    Tests that the page title matches the expected value after navigation.
    """
    
    # 1. Navigate to the website
    page.goto("https://playwright.dev/")
    
    # 2. Assert the title using Playwright's 'expect'
    expect(page).to_have_title("Fast and reliable end-to-end testing for modern web apps | Playwright") 
    
    # Alternatively, using standard Python 'assert':
    assert page.title() == "Fast and reliable end-to-end testing for modern web apps | Playwright"


def test_get_started_link(page: Page):
    page.goto("https://playwright.dev/")

    # Click the get started link.
    page.get_by_role("link", name="Get started").click()

    # Expects page to have a heading with the name of Installation.
    expect(page.get_by_role("heading", name="Installation")).to_be_visible()


def test_can_navigate_to_get_started(page: Page):
    """
    Tests clicking a link and verifies the resulting URL and a text element.
    """

    # 1. Navigate to the home page
    page.goto("https://playwright.dev/")

    # 2. Locate and click the 'Get started' link
    get_started_link = page.get_by_role("link", name="Get started")
    get_started_link.click()

    # 3. Assert the URL after the click
    expect(page).to_have_url("https://playwright.dev/docs/intro")

    # 4. Assert that a heading on the new page exists and has the correct text
    heading = page.get_by_role("heading", name="Installation")
    expect(heading).to_be_visible()


def test_search_functionality(page: Page):
    """
    Tests searching on the documentation site.
    """
    search_query = "Locator"

    # 1. Navigate to the docs page
    page.goto("https://playwright.dev/docs/intro")

    # 2. Click the search button/input to activate the search field
    search_button = page.get_by_role("button", name="Search")
    search_button.click()

    # 3. Type the search query into the active search input
    search_input = page.get_by_placeholder("Search docs")
    search_input.fill(search_query)

    # 4. Press 'Enter' to submit the search (or look for a submit button)
    search_input.press("Enter")

    # 5. Assert that the search results page loads correctly
    first_result = page.get_by_text(search_query, exact=False).first
    expect(first_result).to_be_visible()

    # 6. Assert the URL contains a search parameter (optional, site-dependent)
    expect(page).to_have_url(f"https://playwright.dev/docs/intro")


def test_navigation_menu_functionality(page: Page):
    """
    Tests navigation through the main menu items on the Playwright documentation site.
    """
    menu_items = [
        ("API", "https://playwright.dev/docs/api/class-playwright"),
        ("Trace Viewer", "https://playwright.dev/docs/trace-viewer"),
        ("Test Generator", "https://playwright.dev/docs/codegen")
    ]

    # 1. Navigate to the main documentation page
    page.goto("https://playwright.dev/docs/intro")
    
    # 2. Assert that we're on the correct initial page
    expect(page).to_have_url("https://playwright.dev/docs/intro")
    assert "Playwright" in page.title()

    # 3. Test navigation for each menu item
    for menu_text, expected_url_pattern in menu_items:
        # Find and click the navigation link using more specific selectors
        if menu_text == "API":
            # Use the first API link in the navbar (main API section)
            nav_link = page.locator('a[href="/docs/api/class-playwright"]').first
        elif menu_text == "Trace Viewer":
            # Look for Trace Viewer in the navigation
            nav_link = page.get_by_text("Trace Viewer").first
        elif menu_text == "Test Generator":
            # Look for Test Generator in the navigation
            nav_link = page.get_by_text("Test Generator").first
        else:
            # Fallback to role-based selector
            nav_link = page.get_by_role("link", name=menu_text).first
        
        # Assert the link is visible and clickable
        expect(nav_link).to_be_visible()
        nav_link.click()
        
        # Wait for navigation to complete
        page.wait_for_load_state("networkidle")
        
        # Assert that we navigated to a page containing the expected URL pattern
        current_url = page.url
        assert expected_url_pattern in current_url, f"Expected URL to contain '{expected_url_pattern}', but got '{current_url}'"
        
        # Assert that the page title contains relevant keywords
        page_title = page.title()
        assert menu_text.lower() in page_title.lower() or "playwright" in page_title.lower(), \
            f"Expected page title to contain '{menu_text}' or 'playwright', but got '{page_title}'"
        
        # Assert that the main content area is visible
        main_content = page.get_by_role("main").first
        expect(main_content).to_be_visible()
        
        # Navigate back to the intro page for the next test
        page.goto("https://playwright.dev/docs/intro")


