DROP DATABASE Boukings;
DROP DATABASE SNCF;
DROP DATABASE TrainTrain;

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
INSERT INTO Bookings (user_id, ticket_id, web_service) VALUES ("julien.carcau@ensiie.fr", 8492, "localhost:8081");
INSERT INTO Bookings (user_id, ticket_id, web_service) VALUES ("julien.carcau@ensiie.fr", 3441, "localhost:8081");
INSERT INTO Bookings (user_id, ticket_id, web_service) VALUES ("louka.doz@ensiie.fr", 5113, "localhost:8081");
INSERT INTO Bookings (user_id, ticket_id, web_service) VALUES ("louka.doz@ensiie.fr", 7941, "localhost:8081");

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
INSERT INTO Travels VALUES (4789, "Paris", "Marseille", "1631428200000", "1631473980000", 154, 62, 88, 156);
INSERT INTO Travels VALUES (1456, "Lille", "Toulouse", "1651255800000", "1651278540000", 230, 0, 3, 24);
INSERT INTO Travels VALUES (5113, "Paris", "Évry", "1631881320000", "1631883480000", 50, 561, 10, 2);
INSERT INTO Travels VALUES (3441, "Bois-le-Roi", "Paris", "1632080100000", "1632081600000", 220, 0, 6, 254);
INSERT INTO Travels VALUES (7941, "Melun", "Fontainebleau", "1631761200000", "1631762100000", 126, 0, 65, 156);
INSERT INTO Travels VALUES (8492, "South Park", "Évry", "60000", "4102444800000", 9999, 0, 0, 9999);
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (4789, false, 154, "STANDARD");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 284, "BUSINESS");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 305, "FIRST");

CREATE DATABASE Transgabonais;
USE Transgabonais;
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
INSERT INTO Travels VALUES (4789, "Paris", "Lille", "1631428200000", "1631473980000", 154, 62, 88, 156);
INSERT INTO Travels VALUES (1456, "Marseille", "Toulouse", "1651255800000", "1651278540000", 150, 0, 3, 24);
INSERT INTO Travels VALUES (5113, "Paris", "Paris", "1631881320000", "1631883480000", 506, 561, 10, 20);
INSERT INTO Travels VALUES (3441, "Bois-le-Roi", "Évry", "1632080100000", "1632081600000", 20, 0, 60, 254);
INSERT INTO Travels VALUES (7941, "Melun", "Évry", "1631761200000", "1631762100000", 162, 0, 65, 23);
INSERT INTO Travels VALUES (8492, "South Park", "Fontainebleau", "60000", "4102444800000", 9999, 6, 9), 9999);
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (4789, false, 154, "STANDARD");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 284, "BUSINESS");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 305, "FIRST");

CREATE DATABASE Camrail;
USE Camrail;
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
INSERT INTO Travels VALUES (4789, "Paris", "Lille", "1631468200000", "1631493980000", 154, 62, 88, 156);
INSERT INTO Travels VALUES (1456, "Marseille", "Toulouse", "1691255800000", "1771278540000", 150, 0, 3, 24);
INSERT INTO Travels VALUES (5113, "Paris", "Paris", "1631881111000", "1631889999000", 506, 561, 10, 20);
INSERT INTO Travels VALUES (3441, "Bois-le-Roi", "Évry", "1632082100000", "1632081900000", 20, 0, 60, 254);
INSERT INTO Travels VALUES (7941, "Melun", "Évry", "1631761200000", "1631762100000", 162, 0, 65, 23);
INSERT INTO Travels VALUES (8492, "South Park", "Fontainebleau", "60000", "4102444800000", 9999, 6, 9), 9999);
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (4789, false, 154, "STANDARD");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 284, "BUSINESS");
INSERT INTO Tickets (train_id, flexible, price, class) VALUES (1456, true, 305, "FIRST");

CREATE USER 'wsclient' IDENTIFIED BY 'wsclient';
GRANT ALL PRIVILEGES ON Boukings.* TO 'wsclient'@'%';
GRANT ALL PRIVILEGES ON SNCF.* TO 'wsclient'@'%';
GRANT ALL PRIVILEGES ON Transgabonais.* TO 'wsclient'@'%';
GRANT ALL PRIVILEGES ON Camrail.* TO 'wsclient'@'%';
