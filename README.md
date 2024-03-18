# parc-informatique-spring


## Requirements

For building and running the application you need:

- [JDK 21](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
- [Maven 4](https://maven.apache.org)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)

## Running the application locally

Intellij IDEA is the best IDE to run this application.

(https://www.jetbrains.com/idea/download/)

## Setting Up Database Roles

After running the application, you'll need to execute the following SQL commands to set up database roles:

```sql
INSERT INTO roles(name) VALUES('ROLE_COLLABORATEUR');
INSERT INTO roles(name) VALUES('ROLE_DSI');
```
## Signing Up a User with ROLE_COLLABORATEUR

Use application register interface to sign up a user with the ROLE_COLLABORATEUR (by default).

## Signing Up a User with ROLE_DSI

To sign up a user with the ROLE_DSI using Postman, follow these steps:

1. Open Postman and make sure your application server is running.

2. Send a POST request to the following URL: http://localhost:8090/api/auth/signup

3. In the request body, provide the following JSON payload:
```json
{
    "username": "dsi",
    "email": "dsi@fod.com",
    "password": "12345678",
    "role": ["COLLABORATEUR", "DSI"]
}
```

## Copyright

Copyright © 2024 Freedom Of Dev Services.