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


