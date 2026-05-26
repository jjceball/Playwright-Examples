import { test, expect, type Page } from '@playwright/test';

const ASSURED_URL = 'https://www.assured.com/';

async function openScheduleDemoFromHeader(page: Page) {
  await page.goto(ASSURED_URL, { waitUntil: 'domcontentloaded' });
  await page.getByRole('button', { name: /schedule a demo/i }).first().click();
  await expect(page.getByRole('heading', { name: /ready to get in touch/i })).toBeVisible();
}

async function openScheduleDemoFromFooter(page: Page) {
  await page.goto(ASSURED_URL, { waitUntil: 'domcontentloaded' });
  const footer = page.locator('footer');
  await footer.scrollIntoViewIfNeeded();
  await footer.getByRole('button', { name: /schedule a demo/i }).click();
  await expect(page.getByRole('heading', { name: /ready to get in touch/i })).toBeVisible();
}

test.describe('assured interview', () => {
  test('positive - schedule a demo from header submits successfully', async ({ page }) => {
    await openScheduleDemoFromHeader(page);

    await page.getByPlaceholder('First name').fill('Playwright');
    await page.getByPlaceholder('Last name').fill('Test');
    await page
      .getByPlaceholder('Work email')
      .fill(`playwright.test+${Date.now()}@example.com`);
    await page.getByPlaceholder('Phone number').fill('4155550100');

    await page.getByRole('button', { name: /^submit$/i }).click();

    await expect(
      page.getByRole('heading', { name: /thank you for reaching out/i }),
    ).toBeVisible({ timeout: 15_000 });
    await expect(
      page.getByText(/we'll be in touch soon/i),
    ).toBeVisible();
  });

  test('negative - schedule a demo from header rejects invalid email', async ({ page }) => {
    await openScheduleDemoFromHeader(page);

    await page.getByPlaceholder('Work email').fill('not-an-email');
    await page.getByRole('button', { name: /^submit$/i }).click();

    await expect(page.getByText('Please enter a valid email')).toBeVisible();
    await expect(
      page.getByRole('heading', { name: /thank you for reaching out/i }),
    ).not.toBeVisible();
  });

  test('footer - getting started is easy and schedule a demo at page bottom', async ({
    page,
  }) => {
    await page.goto(ASSURED_URL, { waitUntil: 'domcontentloaded' });

    const footer = page.locator('footer');
    await footer.scrollIntoViewIfNeeded();

    await expect(footer.getByText('Getting started is easy.')).toBeVisible();
    await expect(
      footer.getByRole('button', { name: /schedule a demo/i }),
    ).toBeVisible();
  });

  test('negative - schedule a demo from footer rejects invalid email', async ({ page }) => {
    await openScheduleDemoFromFooter(page);

    await page.getByPlaceholder('Work email').fill('not-an-email');
    await page.getByRole('button', { name: /^submit$/i }).click();

    await expect(page.getByText('Please enter a valid email')).toBeVisible();
    await expect(
      page.getByRole('heading', { name: /thank you for reaching out/i }),
    ).not.toBeVisible();
  });
});
