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



