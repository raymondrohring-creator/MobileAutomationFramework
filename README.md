# 📱 Mobile Automation Framework (Appium + Java + TestNG)

A production-ready **Mobile Test Automation Framework** built using **Appium 2**, **Java**, and **TestNG**, supporting:

* ✅ Native Android testing
* ✅ Hybrid (WebView) testing
* ✅ Parallel execution on multiple emulators
* ✅ Thread-safe driver management
* ✅ Retry mechanism
* ✅ Screenshot capture on failure
* ✅ Structured logging with Log4j2
* ✅ Page Object Model (POM) architecture

---

# 🚀 Tech Stack

| Tool           | Purpose                   |
| -------------- | ------------------------- |
| Java 21        | Programming language      |
| Appium 2       | Mobile automation engine  |
| Selenium 4     | WebDriver protocol        |
| TestNG         | Test execution framework  |
| UiAutomator2   | Android automation driver |
| Log4j2         | Logging                   |
| Maven          | Dependency management     |
| Android Studio | Emulator management       |

---

# 🏗 Framework Architecture

```
MobileAutomationFramework/
│
├── src/test/java
│   ├── testBase
│   │     └── BaseClass.java
│   │
│   ├── pageObjects
│   │     ├── HomePage.java
│   │     ├── ProductsPage.java
│   │     ├── CartPage.java
│   │     └── WebviewPage.java
│   │
│   ├── testCases
│   │     ├── TC01_LaunchApp.java
│   │     ├── TC02_...
│   │     └── TC06_TermsAndConditions.java
│   │
│   └── utilities
│         └── RetryAnalyzer.java
│
├── src/test/resources
│     ├── config.properties
│     └── General-Store.apk
│
├── screenshots/
├── testng.xml
└── pom.xml
```

---

# 🧠 Framework Design Highlights

## 1️⃣ Thread-Safe Driver (Parallel Ready)

Uses:

```java
private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
```

Each device session runs independently without collision.

---

## 2️⃣ Parameterized Multi-Device Execution

Devices are configured via `testng.xml`:

```xml
<test name="Android_Emulator_5554">
    <parameter name="deviceName" value="emulator-5554"/>
    <parameter name="systemPort" value="8200"/>
    <parameter name="os" value="Android"/>
</test>

<test name="Android_Emulator_5556">
    <parameter name="deviceName" value="emulator-5556"/>
    <parameter name="systemPort" value="8201"/>
    <parameter name="os" value="Android"/>
</test>
```

Supports true parallel execution across multiple Android emulators.

---

## 3️⃣ Page Object Model (POM)

Each screen is separated into its own class:

* Encapsulation of locators
* Reusable methods
* Cleaner test cases
* Better maintainability

Example:

```java
HomePage hp = new HomePage(getDriver());
hp.enterName("Ray");
hp.clickLetsShop();
```

---

## 4️⃣ Logging with Log4j2

Structured logging levels:

* INFO → Test flow
* DEBUG → Step-level details
* ERROR → Failures
* WARN → Retry attempts

Example log output:

```
INFO  Test Setup Started
DEBUG Device Name: emulator-5554
INFO  Driver initialized successfully
ERROR Assertion Failed in TC05_CompletePurchase
```

---

## 5️⃣ Retry Mechanism

Implements TestNG `IRetryAnalyzer`.

Automatically retries failed tests (configurable retry count).

---

## 6️⃣ Screenshot Capture on Failure

Automatically captures timestamped screenshots:

```
/screenshots/TC05_CompletePurchase_20260213160523.png
```

Useful for debugging parallel failures.

---

## 7️⃣ Hybrid (WebView) Support

Supports:

* Context switching
* Chromedriver integration
* Native → WebView → Native transitions

Example:

```java
Set<String> contexts = driver.getContextHandles();
driver.context("WEBVIEW");
```

---

# ⚙️ Configuration

## config.properties

```
appium.server.url=http://127.0.0.1:4723
appName=General-Store.apk
chromedriver.path=/absolute/path/to/chromedriver
```

Device-specific configuration is handled via `testng.xml`.

---

# 🖥 Running Tests

## 1️⃣ Start Appium Server

```bash
appium
```

## 2️⃣ Start Emulators

Ensure both devices are running:

```bash
adb devices
```

Expected:

```
emulator-5554 device
emulator-5556 device
```

## 3️⃣ Run Test Suite

Right-click:

```
testng.xml → Run As → TestNG Suite
```

Or via Maven:

```bash
mvn clean test
```

---

# 🔁 Parallel Execution Strategy

* Unique `systemPort` per device
* Explicit `setUdid()`
* ThreadLocal driver
* Suite-level parallel configuration:

```xml
<suite parallel="tests" thread-count="2">
```

---

# 🧪 Test Coverage Examples

* App Launch Validation
* Country Selection
* Add to Cart Flow
* Complete Purchase (Hybrid WebView)
* Terms & Conditions Alert Validation
* Context Switching Validation

---

# 📸 Example Failure Output

If a test fails:

* Logs show failure reason
* Screenshot is captured
* Test retries automatically
* Final status reported via TestNG

---

# 🛠 Future Improvements (Roadmap)

* ExtentReports integration
* CI/CD integration (GitHub Actions)
* iOS support
* Device farm integration (BrowserStack/Sauce Labs)
* Environment-based configuration (QA/Stage/Prod)
* Dockerized Appium grid

---

# 🎯 Why This Framework Matters

This project demonstrates:

* Enterprise-grade mobile automation architecture
* Parallel device execution
* Hybrid app handling
* Proper logging & error handling
* Clean separation of concerns
* Scalable design principles

---

# 👨‍💻 Author

Raymond Rohring
Senior Quality Engineer | Mobile & Automation Specialist

