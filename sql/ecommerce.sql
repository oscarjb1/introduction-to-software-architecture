CREATE DATABASE security;
use security;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `rol` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `users` VALUES ('oscar','oscar_jb1@hotmail.com','1234','ADMIN');


CREATE DATABASE webhook;
use webhook;
CREATE TABLE `listeners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endpoint` varchar(500) NOT NULL,
  `event_type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `listeners` VALUES (1,'http://localhost:9092','NEW_SALES');


CREATE DATABASE crm;
use crm;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image` varchar(1500) DEFAULT NULL,
  `name` varchar(200) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `products` VALUES (1,'https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg','Batman',200),(2,'https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg','Batman Returns',300),(3,'https://m.media-amazon.com/images/M/MV5BNDdjYmFiYWEtYzBhZS00YTZkLWFlODgtY2I5MDE0NzZmMDljXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg','Batman Forever',400),(4,'https://m.media-amazon.com/images/M/MV5BMGQ5YTM1NmMtYmIxYy00N2VmLWJhZTYtN2EwYTY3MWFhOTczXkEyXkFqcGdeQXVyNTA2NTI0MTY@._V1_SX300.jpg','Batman & Robin',500),(5,'https://m.media-amazon.com/images/M/MV5BMTcyNTEyOTY0M15BMl5BanBnXkFtZTgwOTAyNzU3MDI@._V1_SX300.jpg','The Lego Batman Movie',600),(6,'https://m.media-amazon.com/images/M/MV5BOTM3MTRkZjQtYjBkMy00YWE1LTkxOTQtNDQyNGY0YjYzNzAzXkEyXkFqcGdeQXVyOTgwMzk1MTA@._V1_SX300.jpg','Batman: The Animated Series',700),(7,'https://m.media-amazon.com/images/M/MV5BYTdlODI0YTYtNjk5ZS00YzZjLTllZjktYmYzNWM4NmI5MmMxXkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg','Batman: Under the Red Hood',800),(8,'https://m.media-amazon.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg','Batman: The Dark Knight Returns, Part 1',900);

CREATE TABLE `sales_orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_email` varchar(100) NOT NULL,
  `customer_name` varchar(250) NOT NULL,
  `ref_number` varchar(255) DEFAULT NULL,
  `regist_date` datetime NOT NULL,
  `status` varchar(10) NOT NULL,
  `total` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `order_lines` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `fk_product` bigint(20) NOT NULL,
  `fk_sale_order` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnue3qiiwor2oqx9e184aatb1` (`fk_product`),
  KEY `FK63cvmlmv87jkrq37gdbdmvv8e` (`fk_sale_order`),
  CONSTRAINT `FK63cvmlmv87jkrq37gdbdmvv8e` FOREIGN KEY (`fk_sale_order`) REFERENCES `sales_orders` (`id`),
  CONSTRAINT `FKqnue3qiiwor2oqx9e184aatb1` FOREIGN KEY (`fk_product`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pay_date` datetime DEFAULT NULL,
  `payment_method` varchar(20) NOT NULL,
  `fk_saleorder` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpjwk9dbpcllwnpx0y875qcqjk` (`fk_saleorder`),
  CONSTRAINT `FKpjwk9dbpcllwnpx0y875qcqjk` FOREIGN KEY (`fk_saleorder`) REFERENCES `sales_orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

