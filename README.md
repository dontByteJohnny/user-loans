# user-loans
user loans api

API for loans. You can create a user and then create a loan. 
You can access to user data a their loans, in case it has loans. 
You can get all the loans paginated with size(mandatory), page(mandatory) and userId(not mandatory).
You can delete loans and delete a user with all the loans associated to him.

USER Services:

    [POST] /users 
    body e.g.:
    {
        "email": "JohnIM@gmail.com",
        "firstName" : "John",
        "lastName" : "I"
    }

    [GET] /users/{id} 

    [DELETE] /users/{id}

LOAN Services:

    [POST] /loans 
    body e.g.:
    {
        "total": 149999.99,
        "userId": 1
    }

    [GET] /loans?page={pageNumber}&size={pageSize}&userId={userId}
    query e.g.:
    /loans?page=1&size=5&userId=1

    [DELETE] /loans/{loanId}


APP is deployed on Heroku servers. Its available in:

    base url: https://user-loan.herokuapp.com/
    user url: https://user-loan.herokuapp.com/users
    loans url: https://user-loan.herokuapp.com/loans

Its an Springboot app with Java 8, Hibernate, JPA, JUnit, Lombok, etc.

POSTMAN
is available online in: 
    
    https://www.getpostman.com/collections/6b8e72af195e6ccc8f2e

or in the folder "postman" on the path of the project

