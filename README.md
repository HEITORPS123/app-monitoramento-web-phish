# app-monitoramento-web-phish

## About the Project
Java based open source application that uses selenium webdriver, headless firefox and browsermob-proxy to track content and metadata from phishing websites.

## Built with
- Java
- Browsermob
- Selenium Webdriver
- Geckodriver
- Firefox Headless

## Getting started
1. Download a version of firefox that supports headless mode.
2. Download geckodriver.
3. Build dependencies using maven on an IDE like Eclipse.
4. Download Java.
5. Run in command-line like the following:  
**java -jar 'name_of_project.jar' 'number_of_concurrent_browser_instances' 'page_timeout' acesso 'window_timeout' 'max_number_of_requests'**

## License
Distributed under MIT license.

## Features
Customizable parameters as of now are:

**Number of concurrent browser instances**

**Page timeout**

**Time window for request limiting**

**Request limit per defined time window**
