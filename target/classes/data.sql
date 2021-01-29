DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS loan;

CREATE TABLE user (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             firstName VARCHAR(120) NOT NULL,
                             lastName VARCHAR(120) NOT NULL,
                             email VARCHAR(120) NOT NULL
);

CREATE TABLE loan (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      total DECIMAL NOT NULL,
                      userId INT,
                      FOREIGN KEY (userId) REFERENCES user(id)
);