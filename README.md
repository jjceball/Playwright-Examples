## Playwright Python Examples

Minimal setup to run Playwright tests with pytest.

### Prerequisites
- Python 3.9+ available as `python3`

### Setup
```bash
cd /Users/jayceballos/Documents/GitHub/Playwright-Examples
python3 -m venv .venv
source .venv/bin/activate
python -m pip install -U pip
pip install -r requirements.txt
python -m playwright install
```

### Run tests
```bash
python -m pytest -q
# or a single file
python -m pytest -q test_example.py
```

### Notes
- If browsers are missing, run `python -m playwright install` again.
- Deactivate the environment with `deactivate` when done.
