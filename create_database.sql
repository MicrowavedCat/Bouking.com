DROP DATABASE Boukings;
DROP DATABASE SNCF;

CREATE DATABASE Boukings;
USE Boukings;
DROP TABLE Bookings, Users;
CREATE TABLE Users (
    mail VARCHAR(64) PRIMARY KEY,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    password VARCHAR(64)
);
CREATE TABLE Bookings (
    booking_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(64),
    ticket_id INTEGER,
    web_service VARCHAR(32)
);
INSERT INTO Users VALUES ("louka.doz@ensiie.fr", "Louka", "DOZ", "louka");
INSERT INTO Users VALUES ("julien.carcau@ensiie.fr", "Julien", "CARCAU", "julien");
INSERT INTO Bookings (user_id, ticket_id, web_service) VALUES ("louka.doz@ensiie.fr", 4789, "localhost:8081");
INSERT INTO Bookings (user_id, ticket_id, web_service) VALUES ("julien.carcau@ensiie.fr", 1456, "localhost:8081");

CREATE DATABASE SNCF;
USE SNCF;
DROP TABLE Travels, Tickets;
CREATE TABLE Travels (
    train_id INTEGER PRIMARY KEY,
    departure_station VARCHAR(32),
    arrival_station VARCHAR(32),
    departure_date BIGINT,
    arrival_date BIGINT,
    base_price INTEGER,
    nb_first_class INTEGER,
    nb_business_class INTEGER,
    nb_standard_class INTEGER
);
CREATE TABLE Tickets (
    ticket_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    train_id INTEGER,
    flexible BOOLEAN,
    price INTEGER,
    class VARCHAR(8),
    CONSTRAINT chk_class CHECK (class IN ('FIRST', 'BUSINESS', 'STANDARD')),
    FOREIGN KEY (train_id) REFERENCES Travels (train_id) ON DELETE CASCADE
);
INSERT INTO Travels VALUES (4789, "Paris", "Marseille", "1631428200", "1631473980", 154, 62, 88, 156);
INSERT INTO Travels VALUES (1456, "Lille", "Toulouse", "1651255800", "1651278540", 230, 0, 3, 24);
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (4789, false, 154, "STANDARD");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 284, "BUSINESS");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 305, "FIRST");

CREATE USER 'wsclient' IDENTIFIED BY 'wsclient';
GRANT ALL PRIVILEGES ON Boukings.* TO 'wsclient'@'%';
GRANT ALL PRIVILEGES ON SNCF.* TO 'wsclient'@'%';
