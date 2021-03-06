# What is it?

It is a sample project which contains automated Java tests for fromAtoB web-platform.
The project includes UI automated tests.

## Tools and Dependencies
* Java 1.8+
* Maven
* Selenium
* Chrome / Chrome WD
* Selenium Grid (only from docker containers)
* Docker to run Selenium Grid
* TestNG
* Allure Report

## How to run tests locally 

Dependencies:
* browser: chrome
* webdriver: chrome 
    * include the ChromeDriver location in your PATH environment variable
* maven
* allure (if you would like to see beautiful report)

From projects root execute:
```{bash}
    mvn clean test
```

### What to check

After tests finish please do either of the following to see the test report

```{bash}
    mvn allure:serve
```



## Configuration

Test resources contains following file configurations:

* {environment}.properties contains environment dependent parameters (ex: URL)
* {language}.properties contains language platform dependent parameters
* {browser}.capabilities contains browser specific parameters

Tests and environment might be configured in testng.xml file.
Current version has 4 parameters to configure test:

* environment = {prod, staging} 
* language = {en, de}
* remote = {0, 1} 
* browser = {chrome, ...}


## Run test with Selenium Grid

No webDrivers are required to be installed locally.

Required:
- Docker
- docker-compose
- set parameter remote=1 in testng.xml

```{bash}
    docker-compose up -d
    mvn clean test
```

## Run test in parallel

TestNG has parameter to run test in parallel.
Current version of framework should allow to 
run test classes in parallel if you add these parameters to suite in testng.xml:
parallel="classes" thread-count="2".

I've tried to do this but it failed.
I need more time to investigate what the problem is.

## Test cases

```{gherkin}
SCENARIO: User tries to login with wrong credentials
WHEN User logs in with wrong credentials
THEN User should see an error message

SCENARIO: User search for shortest trip from {arrivalStop} to {departureStop} in {days} days by train with {transportCompany} company
WHEN User searches for trip from {arrivalStop} to {departureStop} with arrival date in {N} date
AND User selects only trains and sorting by duration
AND User selects the first trip option for {transportCompany}
THEN User is on Overview checkout page

```
