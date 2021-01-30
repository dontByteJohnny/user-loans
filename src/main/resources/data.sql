DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS loan;

create table USER
(
    ID INT auto_increment,
    FIRST_NAME VARCHAR,
    LAST_NAME VARCHAR,
    EMAIL VARCHAR,
    constraint USER_PK
        primary key (ID)
);

create table LOAN
(
    ID INT auto_increment,
    USER_ID INT,
    TOTAL DECIMAL,
    constraint LOAN_PK
        primary key (ID),
    constraint LOAN_USER_ID_FK
        foreign key (USER_ID) references USER (ID)
);

INSERT INTO USER(ID, FIRST_NAME, LAST_NAME, EMAIL) VALUES
(9999, 'John', 'Lewis', 'johnlewis@gmail.com');

INSERT INTO LOAN(ID, USER_ID, TOTAL) VALUES
(10000, 9999, 1500.50);

INSERT INTO LOAN(ID, USER_ID, TOTAL) VALUES
(10001, 9999, 605000.99);