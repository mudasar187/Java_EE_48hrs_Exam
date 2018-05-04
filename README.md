## Java Enterprise Edition 1 PG5100

<!--- Travis CI build status banner -->
[![Build Status](https://travis-ci.com/mudasar187/Java_EE_48hrs_Exam.svg?token=v251k9AGWGPGijfDozX8&branch=master)](https://travis-ci.com/mudasar187/Java_EE_48hrs_Exam)

## 48 Hours Exam Spring 2018

## UsedBook Application

#### Web application based on trading of used books in a university.

###### - Tools
- Spring
- Hibernate
- JPA
- Selenium
- JUnit


###### - Project structure
- Backend
- Frontend
- Report


###### - Tests
- Total coverage report 96%


###### - Functionality
- Login system
- Signup system
- User can see overview over books and see how many sellers have the book
- User can go to a detail page to see more about the book and see all sellers who have the book
- When user is logged in, user can decide to sell a book if s/he have it, or remove from the list
- When user is logged in s/he can send message to each user to contact them about the book
- Inbox system where user can see overview over incoming and outcoming messages
- A user can reply to a message in incoming messages
- A admin user can delete books and create new books
- Security for authenticating for the pages

###### - How to run application (enter commands in terminal)
- Clone repo
- From root without running tests -> mvn clean install -DskipTests
- From root with running tests -> mvn install
- To run only tests -> mvn clean verify
- To run project -> Go to frontend-module and run 'LocalApplicationRunner'

Also testing with Travis CI

###### - Deployed to [Heroku](https://www.heroku.com/) 

[https://usedbookapplication.herokuapp.com/](https://usedbookapplication.herokuapp.com/)

