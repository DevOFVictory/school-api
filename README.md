# School API
## an open source API for [IServ](https://iserv.de) and [Units](https://www.untis.at) written in Java

### This project can either be used as Java libary for accessing units and iserv data, or can be executed as HTTP web API and access data through json.

#### Features:
 - Login with your **IServ account**
 - Request a given amount of outstanding **exercises** from iserv
 - Send mails via **SMTP**
 - Read mails via **IMAP**
 - Get all **examens** from calender
 - **Session** system
 - Runnable as **web API** to use it in **JSON** format in **any language**
 - Request **timetable as image** from "Units Vertretungsplan"
 - Request your peronal public profile

#### How to setup:
##### As java dependency / libary:
 1. Download the latest release
 2. Unzip it and add the .jar file as dependency in your Java or Kotlin project

##### As stand-alone http web API:
 1. Download the latest release
 2. Move the .jar file inside the .zip to a new folder
 3. Create a `start.sh` or `start.bat` file in the same folder, depending on your OS
 4. Write `java -jar <jarname>.jar <port>` into the file (replace both variables)
 5. (I'ld recommend to use `screen` for linux to organize the console)

#### How to use:
##### Coming soon...

#### How to contribute:
Feel free to create a pull request, as long the code style  is respected.

#### Contributers:
 - [DevOFVictory](https://github.com/DevOFVictory) 
