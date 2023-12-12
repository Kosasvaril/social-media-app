## Introduction
The Social Media Application is a Spring Boot-based platform that allows users to create profiles, post updates, follow other users, and send messages. The system also integrates with a cloud storage service, such as Amazon S3, to store user-generated content.

## Prerequisites
Before you begin, ensure you have the following items:

- Java Development Kit (JDK) 17
- Gradle
- Amazon S3 account (for cloud storage)


## Installation

### Database
This application using an H2 database, with JPA.
This is an example, on how to set up your properties files. 

#### info.properties
    jwt.secret=your secret key
I would advise you to use a hashed key, for 
security, since this app uses JWT.

#### application.properties
    spring.datasource.url=your databes url
    spring.datasource.driverClassName=org.h2.Driver
    spring.jpa.defer-datasource-initialization=true
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    spring.h2.console.enabled=true
    baseDirPath=resources
    spring.datasource.username=admin
    spring.datasource.password=
    spring.jpa.show-sql=true
    debug=true
    server.port=your preferred port

### Cloud Storage Integration
You should set up an AWS account, and create an S3 bucket.
In this setup guide it is assumed, that you familiar with aws services.
Using AWS Toolkit, you can easily connect to your account, and
everything you need.

### Copy code
    git clone https://github.com/your-username/social-media-app.git

### Postman
If you would like to try the application, I'd suggest Postman
for testing the endpoints, since there is no Frontend.
You may look for endpoints in the controller package, after you
start the app you should do the following:
- Start with 'Timeline' page. The page calls all created users
and posts, so you can have an understanding of the data structures.
- Next you can register a new account or login 
with an existing one. (You can find the database here: localhost:your-preffered-port/h2-console.
For example: localhost:8081/h2-console/).
- Now almost every other page needs a Bearer token. The response of
the login contains a JSON Web Token, you can copy that, and under the
authorization tab in Postman pick Bearer token option and insert yours.
- You will need to follow the instructions to access the endpoints.