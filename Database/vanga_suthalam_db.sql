-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: vanga_suthalam_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boats`
--

DROP TABLE IF EXISTS `boats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boats` (
  `boat_id` int NOT NULL AUTO_INCREMENT,
  `captain_id` int NOT NULL,
  `boat_name` varchar(100) NOT NULL,
  `boat_number` varchar(50) NOT NULL,
  `boat_type` varchar(50) DEFAULT NULL,
  `capacity` int NOT NULL,
  `engine_details` varchar(150) DEFAULT NULL,
  `safety_equipment` varchar(255) DEFAULT NULL,
  `registration_details` varchar(150) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'AVAILABLE',
  PRIMARY KEY (`boat_id`),
  UNIQUE KEY `boat_number` (`boat_number`),
  KEY `captain_id` (`captain_id`),
  CONSTRAINT `boats_ibfk_1` FOREIGN KEY (`captain_id`) REFERENCES `captains` (`captain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boats`
--

LOCK TABLES `boats` WRITE;
/*!40000 ALTER TABLE `boats` DISABLE KEYS */;
INSERT INTO `boats` VALUES (1,1,'Vanga Explorer','TN-RMD-001','Motor Boat',10,'Diesel Engine','Life Jackets, First Aid Kit, Fire Extinguisher','TN Marine Registration 001','ASSIGNED'),(4,3,'Sea Pearl','TN-RMD-002','Motor Boat',8,'Diesel Engine','Life Jackets, First Aid Kit, Fire Extinguisher','TN Marine Registration 002','ASSIGNED'),(5,3,'Ocean Star','TN-RMD-009','Motor Boat',10,'Diesel Engine','Life Jackets, First Aid Kit, Fire Extinguisher','TN Marine Registration 009','AVAILABLE'),(6,3,'Sea Pearl','TN-RMD-010','Passenger Boat',8,'Marine Diesel Engine','Life Jackets, First Aid Kit, Fire Extinguisher','TN-RMD-REG-010','AVAILABLE');
/*!40000 ALTER TABLE `boats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `destination_id` int NOT NULL,
  `package_id` int NOT NULL,
  `boat_id` int DEFAULT NULL,
  `booking_date` date NOT NULL,
  `start_time` time NOT NULL,
  `number_of_people` int NOT NULL,
  `fishing_required` tinyint(1) DEFAULT '0',
  `food_required` tinyint(1) DEFAULT '0',
  `total_amount` decimal(10,2) NOT NULL,
  `booking_status` varchar(30) DEFAULT 'PENDING',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `approval_id` int DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `customer_id` (`customer_id`),
  KEY `destination_id` (`destination_id`),
  KEY `package_id` (`package_id`),
  KEY `boat_id` (`boat_id`),
  KEY `fk_booking_island_approval` (`approval_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`destination_id`),
  CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`package_id`) REFERENCES `trip_packages` (`package_id`),
  CONSTRAINT `bookings_ibfk_4` FOREIGN KEY (`boat_id`) REFERENCES `boats` (`boat_id`),
  CONSTRAINT `fk_booking_island_approval` FOREIGN KEY (`approval_id`) REFERENCES `island_approvals` (`approval_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,1,1,1,1,'2026-09-15','08:00:00',2,1,1,5000.00,'PENDING','2026-09-11 04:11:25',NULL),(2,1,1,1,1,'2026-09-20','08:00:00',2,1,1,13000.00,'PENDING','2026-09-11 07:08:52',NULL),(3,1,1,1,1,'2026-10-05','09:00:00',3,1,1,13500.00,'CONFIRMED','2026-09-11 08:00:32',NULL),(4,1,1,1,1,'2026-10-15','09:00:00',2,1,0,5000.00,'CONFIRMED','2026-09-11 08:21:44',NULL),(5,9,5,1,1,'2026-09-15','09:00:00',2,1,1,5000.00,'PENDING','2026-09-12 04:09:31',NULL),(6,9,1,1,1,'2026-09-15','09:00:00',2,1,1,5000.00,'PENDING','2026-09-12 06:48:37',NULL),(8,9,5,2,1,'2026-09-15','09:00:00',2,1,1,13000.00,'PENDING','2026-09-12 07:06:17',NULL),(9,9,1,1,1,'2026-09-20','09:00:00',2,1,1,5000.00,'CONFIRMED','2026-09-12 07:17:11',NULL),(10,9,1,1,1,'2026-09-20','09:00:00',2,1,1,5000.00,'CONFIRMED','2026-09-12 07:27:28',NULL),(11,9,9,2,1,'2026-09-21','09:00:00',2,1,1,13000.00,'PENDING','2026-09-12 07:33:59',NULL),(12,9,1,1,1,'2026-09-20','09:00:00',2,1,1,5000.00,'CONFIRMED','2026-09-12 07:44:04',NULL),(13,9,8,2,1,'2026-09-21','09:00:00',2,1,1,13000.00,'PENDING','2026-09-12 07:48:13',NULL),(14,9,1,1,1,'2026-09-20','09:00:00',2,1,1,7000.00,'PENDING','2026-09-12 07:58:57',NULL),(15,9,1,1,1,'2026-09-20','09:00:00',2,1,1,7000.00,'PENDING','2026-09-12 08:08:07',NULL),(16,9,1,1,1,'2026-09-20','09:00:00',2,0,0,5000.00,'PENDING','2026-09-12 08:10:08',NULL),(17,14,1,1,4,'2026-09-17','09:00:00',1,1,1,3500.00,'COMPLETED','2026-09-14 17:55:28',NULL),(18,14,2,1,NULL,'2026-09-17','09:00:00',1,1,1,3500.00,'CONFIRMED','2026-09-14 18:21:00',NULL),(19,11,5,5,NULL,'2026-09-21','09:00:00',2,1,1,18000.00,'CONFIRMED','2026-09-15 03:51:23',9),(20,11,5,5,NULL,'2026-09-21','09:00:00',5,1,1,45000.00,'CONFIRMED','2026-09-15 04:29:56',10),(21,11,5,5,NULL,'2026-09-25','09:00:00',8,1,1,72000.00,'PENDING','2026-09-15 05:05:58',11),(22,11,5,5,NULL,'2026-09-29','09:00:00',5,1,1,45000.00,'CONFIRMED','2026-09-15 05:54:22',12),(23,11,5,5,NULL,'2026-10-01','09:00:00',5,1,1,45000.00,'CONFIRMED','2026-09-15 06:42:45',13),(24,11,5,5,NULL,'2026-10-05','09:00:00',5,1,1,45000.00,'CONFIRMED','2026-09-15 08:02:22',15),(25,15,5,5,NULL,'2026-10-08','09:00:00',8,0,0,64000.00,'CONFIRMED','2026-09-15 09:26:17',16),(26,13,6,5,NULL,'2026-10-22','09:00:00',5,1,1,45000.00,'CONFIRMED','2026-09-15 17:12:23',18),(27,18,5,5,NULL,'2026-10-21','09:00:00',2,0,0,16000.00,'CONFIRMED','2026-09-16 09:08:33',19),(28,18,5,5,1,'2026-10-23','09:00:00',3,1,1,27000.00,'IN_PROGRESS','2026-09-16 09:21:32',20),(29,20,7,5,5,'2026-09-20','09:00:00',8,0,0,64000.00,'COMPLETED','2026-09-20 03:02:35',21),(30,21,8,5,NULL,'2026-09-21','09:00:00',8,0,0,64000.00,'PENDING','2026-09-20 07:58:04',22),(31,21,8,5,6,'2026-09-21','09:00:00',8,1,1,72000.00,'COMPLETED','2026-09-20 07:58:27',22);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `captains`
--

DROP TABLE IF EXISTS `captains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `captains` (
  `captain_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `experience_years` int DEFAULT NULL,
  `qualification` varchar(150) DEFAULT NULL,
  `verification_status` varchar(20) DEFAULT 'PENDING',
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`captain_id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `captains`
--

LOCK TABLES `captains` WRITE;
/*!40000 ALTER TABLE `captains` DISABLE KEYS */;
INSERT INTO `captains` VALUES (1,'Arun','9876543211','arun@gmail.com','12345',8,'Boat Handling and Marine Safety','PENDING','ACTIVE','2026-09-11 03:56:02'),(3,'Karthik','9876543212','karthik@gmail.com','12345',6,'Marine Safety and Boat Handling','PENDING','ACTIVE','2026-09-11 04:44:31'),(4,'Suresh','9876543298','suresh98@gmail.com','suresh123',8,'Marine Safety and Boat Handling','PENDING','ACTIVE','2026-09-11 07:52:40'),(5,'Ramesh Kumar','9876543216','rameshkumar16@gmail.com','Ramesh@123',8,'Certified Boat Captain','PENDING','ACTIVE','2026-09-11 08:16:56'),(6,'Mani Kumar','9876543222','manikumar22@gmail.com','Mani@123',6,'Certified Boat Captain','PENDING','ACTIVE','2026-09-11 08:47:31');
/*!40000 ALTER TABLE `captains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Raju','9876543210','raju@gmail.com','12345','Ramanathapuram','ACTIVE','2026-09-11 03:51:02'),(4,'John','9876543211','john@gmail.com','12346','Ramanathapuram','ACTIVE','2026-09-11 04:38:24'),(5,'Vijay Kumar','9876543299','vijaykumar99@gmail.com','vijay123','Rameswaram','ACTIVE','2026-09-11 07:51:31'),(6,'Arun Kumar','9876543215','arunkumar15@gmail.com','Arun@123','Rameswaram','ACTIVE','2026-09-11 08:15:32'),(7,'Naveen Kumar','9876543221','naveenkumar21@gmail.com','Naveen@123','Rameswaram','ACTIVE','2026-09-11 08:45:17'),(9,'Ramesh Kumar','9000012345','ramesh2026@test.com','Ramesh@123','Rameswaram','ACTIVE','2026-09-12 02:51:31'),(11,'Ramu Kumar','8428104545','ramu2026@vanga.com','Ramu@456','Mandapam, Ramanathapuram','ACTIVE','2026-09-13 19:24:31'),(12,'ragu','1234567890','ragu481@gmail.com','ragu@1234','Ramanathapuram','ACTIVE','2026-09-13 19:27:00'),(13,'Surya Surya','9042889350','suryaraju481@vanga.com','S$*j6NgaXavdP75','Ramnathapuram','ACTIVE','2026-09-14 06:08:02'),(14,'Vasi','9056768797','vas123@vanga.com','!hU3NU8-HvzE$A5','Ramanathapuram','ACTIVE','2026-09-14 06:44:08'),(15,'Raju Test Customer','9000000001','rajutest2026@vanga.com','Bin9tUsG6vZ6Akd','Ramanathapuram','ACTIVE','2026-09-15 09:23:44'),(17,'Raju E2E Test','9000000011','rajue2ee2026@vanga.com','ucu6ei5f9waz7Ny','Chennai','ACTIVE','2026-09-15 11:51:41'),(18,'Mullai','9787776757','Mullai@vanga.com','54Nnpm5VNDRaZcP','Tanjoore','ACTIVE','2026-09-16 09:04:57'),(19,'Kumar Test','9878675645','kumartest2026@vanga.com','VzjteNt6qneXc-D','Ramanathapuram','ACTIVE','2026-09-16 09:16:53'),(20,'Ram','8767564534','Ram123@vanga.com','mnrDQDQ2udYvdr2','Ramanathapuram','ACTIVE','2026-09-20 02:59:14'),(21,'Bala','9343546576','Bala123@vanga.com','wxNi.8tTN7XU!#u','Ramanathapuram','ACTIVE','2026-09-20 07:55:21');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destinations`
--

DROP TABLE IF EXISTS `destinations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destinations` (
  `destination_id` int NOT NULL AUTO_INCREMENT,
  `destination_name` varchar(100) NOT NULL,
  `location` varchar(150) NOT NULL,
  `destination_type` varchar(50) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `distance_km` decimal(6,2) DEFAULT NULL,
  `landing_allowed` tinyint(1) DEFAULT '0',
  `trip_allowed` tinyint(1) DEFAULT '1',
  `status` varchar(20) DEFAULT 'ACTIVE',
  PRIMARY KEY (`destination_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinations`
--

LOCK TABLES `destinations` WRITE;
/*!40000 ALTER TABLE `destinations` DISABLE KEYS */;
INSERT INTO `destinations` VALUES (1,'Rameswaram Sea Route','Rameswaram','Sea Exploration','Approved sea exploration route around Rameswaram.',15.50,0,1,'ACTIVE'),(2,'Dhanushkodi Sea Route','Rameswaram','Sea Exploration','Sea exploration route around the Rameswaram and Dhanushkodi area.',18.50,0,1,'ACTIVE'),(3,'Pamban Marine Explorer','Rameswaram','Marine Exploration','Marine exploration route near the Pamban area.',15.00,0,1,'ACTIVE'),(4,'Rameswaram Sea Explorer','Rameswaram','Sea Exploration','Approved marine exploration route near Rameswaram.',20.00,0,1,'ACTIVE'),(5,'Mulli Theevu','Ramanathapuram','Island Explorer','Protected island exploration subject to official approval.',10.00,0,0,'ACTIVE'),(6,'Desert Island','Ramanathapuram','Island Explorer','Protected island exploration subject to official approval.',12.00,0,0,'ACTIVE'),(7,'Appa Theevu','Ramanathapuram','Island Explorer','Protected island exploration subject to official approval.',15.00,0,0,'ACTIVE'),(8,'Valai Theevu','Ramanathapuram','Island Explorer','Protected island exploration subject to official approval.',14.00,0,0,'ACTIVE'),(9,'Muyal Theevu','Ramanathapuram','Island Explorer','Protected island exploration subject to official approval.',16.00,0,0,'ACTIVE');
/*!40000 ALTER TABLE `destinations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `captain_rating` int DEFAULT NULL,
  `boat_rating` int DEFAULT NULL,
  `food_rating` int DEFAULT NULL,
  `safety_rating` int DEFAULT NULL,
  `experience_rating` int DEFAULT NULL,
  `overall_rating` int DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  `feedback_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`feedback_id`),
  KEY `booking_id` (`booking_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `feedback_chk_1` CHECK ((`captain_rating` between 1 and 5)),
  CONSTRAINT `feedback_chk_2` CHECK ((`boat_rating` between 1 and 5)),
  CONSTRAINT `feedback_chk_3` CHECK ((`food_rating` between 1 and 5)),
  CONSTRAINT `feedback_chk_4` CHECK ((`safety_rating` between 1 and 5)),
  CONSTRAINT `feedback_chk_5` CHECK ((`experience_rating` between 1 and 5)),
  CONSTRAINT `feedback_chk_6` CHECK ((`overall_rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,2,1,5,4,5,5,5,5,'Excellent sea exploration experience. Captain was helpful and safety was good.','2026-09-11 07:33:42'),(2,2,1,5,5,4,5,5,5,'Excellent trip. Captain was helpful and the safety arrangements were very good.','2026-09-11 08:05:35'),(3,2,1,5,5,4,5,5,5,'Excellent sea trip. Captain was helpful and the experience was very enjoyable.','2026-09-11 08:35:39'),(4,3,1,5,5,5,5,5,5,'YELLARUM VANGA SEMAYA SUTHUROM','2026-09-12 04:26:14'),(5,3,9,5,5,5,5,5,4,'YELLERUM VANGA SUTHALAM','2026-09-12 06:14:46'),(6,5,9,5,5,5,5,5,5,'WOW!!!','2026-09-12 07:11:19'),(7,9,9,5,5,5,5,5,5,'5','2026-09-12 07:21:27'),(8,10,9,5,5,5,5,5,5,'Vanga Inga','2026-09-12 07:28:51'),(9,12,9,5,5,5,5,5,5,'Super!!!!!!!!!!!!!!!','2026-09-12 07:45:09'),(10,12,9,5,5,5,5,5,5,'Super!!!!!!!','2026-09-12 07:55:14'),(11,23,11,5,5,5,5,5,5,'Vanga Suthalam','2026-09-15 06:44:09'),(12,24,11,5,5,5,5,5,5,'VANGA SUTHALAM','2026-09-15 08:03:31'),(13,26,13,5,5,5,5,5,5,'ELLARUM VANGA SEMAYA SUTHUROM','2026-09-16 08:42:07'),(14,26,13,5,5,5,5,5,5,'ELLARUM VANGA SEMMAYA SUTHUROM','2026-09-16 08:45:02'),(15,29,20,5,5,5,5,5,5,'SUPER PROOOO!!!!!!','2026-09-20 03:11:14'),(16,31,21,5,5,5,5,5,5,'Very Super','2026-09-20 08:00:05');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `island_approvals`
--

DROP TABLE IF EXISTS `island_approvals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `island_approvals` (
  `approval_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int DEFAULT NULL,
  `destination_id` int NOT NULL,
  `certificate_number` varchar(100) NOT NULL,
  `approved_by` varchar(100) NOT NULL,
  `approval_start_date` date NOT NULL,
  `approval_end_date` date NOT NULL,
  `approval_status` varchar(20) DEFAULT 'PENDING',
  `remarks` varchar(500) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`approval_id`),
  KEY `booking_id` (`booking_id`),
  KEY `destination_id` (`destination_id`),
  CONSTRAINT `island_approvals_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`),
  CONSTRAINT `island_approvals_ibfk_2` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`destination_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `island_approvals`
--

LOCK TABLES `island_approvals` WRITE;
/*!40000 ALTER TABLE `island_approvals` DISABLE KEYS */;
INSERT INTO `island_approvals` VALUES (1,2,1,'DEMO-FO-001',' DEMO Forest Officer','2026-09-15','2026-09-16','PENDING','2 day island approval','2026-09-12 03:25:35'),(2,3,5,'FO-2026-001','Forest Officer','2026-09-15','2026-09-16','PENDING','Approved for permitted island exploration','2026-09-12 06:22:26'),(3,3,5,'FO-TEST-002','Forest Officer','2026-09-15','2026-09-16','APPROVED','Approved for permitted island exploration','2026-09-12 06:26:41'),(4,4,5,'CERT-2026-001','Forest Officer','2026-09-15','2026-09-16','APPROVED','Approved for permitted island exploration','2026-09-12 07:08:20'),(5,12,8,'CERT-2026-002','Forest Officer','2026-09-21','2026-09-22','APPROVED','Approved for permitted island exploration','2026-09-12 07:51:20'),(6,NULL,6,'TEST-CERT-001','Forest Officer','2026-09-15','2026-09-16','APPROVED','Development testing','2026-09-14 17:31:28'),(7,NULL,5,'CERT-2026-001','Forest Officer','2026-09-15','2026-09-16','APPROVED','Demo island approval for Vanga Suthalam project','2026-09-15 03:29:48'),(8,NULL,5,'TEST-CERT-002','Forest Officer','2026-09-18','2026-09-19','APPROVED','Demo island approval for Vanga Suthalam project','2026-09-15 03:40:36'),(9,NULL,5,'TEST-CERT-004','Forest Officer','2026-09-20','2026-09-21','APPROVED','Aproval','2026-09-15 03:50:53'),(10,NULL,5,'TEST-CERT-005','Forest Officer','2026-09-22','2026-09-23','APPROVED','Approval','2026-09-15 04:29:31'),(11,NULL,5,'TEST-CERT-007','Forest Officer','2026-09-25','2026-09-26','APPROVED','Approval','2026-09-15 05:05:29'),(12,NULL,5,'TEST-CERT-007','Forest Officer','2026-09-29','2026-09-30','APPROVED','Approval','2026-09-15 05:53:51'),(13,NULL,5,'TEST-CERT-007','Forest Officer','2026-10-01','2026-10-02','APPROVED','Approval','2026-09-15 06:42:15'),(14,NULL,5,'TEST-CERT-008','Forest Officer','2026-10-03','2026-10-04','APPROVED','Approval Success','2026-09-15 07:41:51'),(15,NULL,5,'TEST-CERT-009','Forest Officer','2026-10-05','2026-10-06','APPROVED','Approval','2026-09-15 08:01:55'),(16,NULL,5,'TEST-CERT-008','Forest Officer','2026-10-07','2026-10-08','APPROVED','Approval','2026-09-15 09:25:44'),(17,NULL,5,'TEST-CERT-008','Forest Officer','2026-10-16','2026-10-17','APPROVED','Approval Success','2026-09-15 11:53:32'),(18,NULL,6,'TEST-CERT-001','Forest Officer','2026-10-22','2026-10-23','APPROVED','Approval Confirm','2026-09-15 17:11:32'),(19,NULL,5,'TEST-CERT-011','Forest Officer','2026-10-21','2026-10-22','APPROVED','Approval Confirm','2026-09-16 09:08:06'),(20,NULL,5,'TEST-CERT-012','Forest Officer','2026-10-23','2026-10-24','APPROVED','Approval Confirm','2026-09-16 09:19:16'),(21,NULL,7,'TEST-CERT-015','Forest Officer','2026-09-20','2026-09-21','APPROVED','Approval Required','2026-09-20 03:02:05'),(22,NULL,8,'TEST-CERT-016','Forest Officer','2026-09-21','2026-09-22','APPROVED','Approval Executed','2026-09-20 07:57:45');
/*!40000 ALTER TABLE `island_approvals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_method` varchar(30) NOT NULL,
  `transaction_reference` varchar(100) DEFAULT NULL,
  `payment_status` varchar(30) DEFAULT 'PENDING',
  `payment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,13000.00,'UPI','VANGA-TXN-1001','PENDING','2026-09-11 07:15:37'),(2,2,1300.00,'UPI','VANGA-TXN-2005','PENDING','2026-09-11 08:03:17'),(3,2,1300.00,'UPI','VANGA-TXN-2005','PENDING','2026-09-11 08:03:20'),(4,2,1300.00,'UPI','VANGA-TXN-2005','PENDING','2026-09-11 08:03:28'),(5,2,5000.00,'UPI','VANGA-TXN-1002','PENDING','2026-09-11 08:22:37'),(6,1,50000.00,' UPI','DEMO-UPI-001','PENDING','2026-09-11 09:05:13'),(7,3,5000.00,'UPI','UPI20260912001','SUCCESS','2026-09-12 04:16:58'),(8,3,5000.00,'UPI','UPI20260912002','SUCCESS','2026-09-12 04:21:54'),(9,4,5000.00,'CASH','TXN-20260912-001','SUCCESS','2026-09-12 06:50:53'),(10,4,13000.00,'CASH','UPI20260915001','SUCCESS','2026-09-12 07:10:18'),(11,9,5000.00,'UPI','UPI20260920001','SUCCESS','2026-09-12 07:19:18'),(12,10,5000.00,'	UPI','UPI20260920001','SUCCESS','2026-09-12 07:28:05'),(13,12,5000.00,'UPI','UPI20260920001','SUCCESS','2026-09-12 07:44:36'),(14,12,13000.00,'UPI','UPI20260921001','SUCCESS','2026-09-12 07:52:29'),(15,18,3500.00,'UPI','TEST-UPI-001','CONFIRMED','2026-09-14 18:23:33'),(16,19,18000.00,'UPI','VSUPI202609150001','CONFIRMED','2026-09-15 03:52:25'),(17,20,45000.00,'UPI','VSUPI202609150001','CONFIRMED','2026-09-15 04:30:54'),(18,22,45000.00,'UPI','VSUPI202609150001','CONFIRMED','2026-09-15 05:54:27'),(19,23,45000.00,'UPI','VSUPI202609150001','CONFIRMED','2026-09-15 06:43:34'),(20,24,45000.00,'UPI','VSUPI202609150001','CONFIRMED','2026-09-15 08:03:08'),(21,25,64000.00,'UPI','VS17TXN20267','CONFIRMED','2026-09-15 09:26:46'),(22,26,45000.00,'UPI','RGTHUDD23FB56','CONFIRMED','2026-09-15 17:12:38'),(23,17,3500.00,'UPI','VANGA-TEST-017','CONFIRMED','2026-09-16 08:26:32'),(24,27,16000.00,'UPI','RTH565898DGDJ','CONFIRMED','2026-09-16 09:08:43'),(25,28,27000.00,'UPI','TRGSJKJSHKS8','CONFIRMED','2026-09-16 09:22:43'),(26,29,64000.00,'UPI','TSHGSHJGHJS87','CONFIRMED','2026-09-20 03:02:48'),(27,31,72000.00,'UPI','DHDIUHD65','CONFIRMED','2026-09-20 07:58:37');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `safety_checks`
--

DROP TABLE IF EXISTS `safety_checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `safety_checks` (
  `safety_check_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int NOT NULL,
  `captain_approved` tinyint(1) DEFAULT '0',
  `boat_available` tinyint(1) DEFAULT '0',
  `passenger_capacity_ok` tinyint(1) DEFAULT '0',
  `life_jackets_available` tinyint(1) DEFAULT '0',
  `emergency_equipment_available` tinyint(1) DEFAULT '0',
  `communication_equipment_available` tinyint(1) DEFAULT '0',
  `weather_clearance` tinyint(1) DEFAULT '0',
  `safety_status` varchar(30) DEFAULT 'PENDING',
  `checked_by` varchar(100) DEFAULT NULL,
  `checked_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`safety_check_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `safety_checks_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `safety_checks`
--

LOCK TABLES `safety_checks` WRITE;
/*!40000 ALTER TABLE `safety_checks` DISABLE KEYS */;
INSERT INTO `safety_checks` VALUES (1,2,1,1,1,1,1,1,1,'PENDING','Admin',NULL),(2,2,1,1,1,1,1,1,1,'PENDING','Admin',NULL),(3,2,1,1,1,1,1,1,1,'PENDING','Admin',NULL),(4,3,1,1,1,1,1,1,1,'PENDING','true',NULL),(5,9,1,1,1,1,1,1,1,'PENDING','Safety Officer',NULL),(6,10,1,1,1,1,1,1,1,'PENDING','saftey ofiicer',NULL),(7,10,1,1,1,1,1,1,1,'PENDING','saftey officer',NULL),(8,12,1,1,1,1,1,1,1,'PENDING','Saftey officer',NULL),(9,12,1,1,1,1,1,1,1,'PENDING','true',NULL),(10,12,1,1,1,1,1,1,1,'PENDING','Safety Officer',NULL),(11,5,1,1,1,1,1,1,1,'APPROVED','forest officer','2026-09-16 08:12:55'),(12,17,1,1,1,1,1,1,1,'APPROVED','Saftey Officer','2026-09-16 08:27:23'),(13,28,1,1,1,1,1,1,1,'APPROVED','Saftey Officer','2026-09-16 09:46:32'),(14,29,1,1,1,1,1,1,1,'APPROVED','Karthik','2026-09-20 03:10:17'),(15,31,1,1,1,1,1,1,1,'APPROVED','Karthik','2026-09-20 07:59:34');
/*!40000 ALTER TABLE `safety_checks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_packages`
--

DROP TABLE IF EXISTS `trip_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip_packages` (
  `package_id` int NOT NULL AUTO_INCREMENT,
  `package_name` varchar(100) NOT NULL,
  `duration_days` int NOT NULL,
  `duration_nights` int DEFAULT '0',
  `description` varchar(500) DEFAULT NULL,
  `base_price_per_person` decimal(10,2) NOT NULL,
  `fishing_included` tinyint(1) DEFAULT '0',
  `food_included` tinyint(1) DEFAULT '0',
  `status` varchar(20) DEFAULT 'ACTIVE',
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_packages`
--

LOCK TABLES `trip_packages` WRITE;
/*!40000 ALTER TABLE `trip_packages` DISABLE KEYS */;
INSERT INTO `trip_packages` VALUES (1,'Sea Explorer - 1 Day',1,0,'One day sea exploration experience with fishing and food.',2500.00,1,1,'ACTIVE'),(2,'Marine Adventure - 2 Days',2,1,'Two day marine exploration with fishing and food.',6500.00,1,1,'ACTIVE'),(3,'Rameswaram Ocean Explorer',1,0,'One day sea exploration experience with food and fishing.',4500.00,1,1,'ACTIVE'),(4,'Rameswaram Sea Adventure',1,0,'One day sea exploration with fishing experience.',2500.00,1,0,'ACTIVE'),(5,'Premium Island Adventure',2,1,'Premium 2-day island exploration experience in Ramanathapuram and Rameswaram. Subject to applicable official approval and safety clearance.',8000.00,1,1,'ACTIVE');
/*!40000 ALTER TABLE `trip_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trips`
--

DROP TABLE IF EXISTS `trips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trips` (
  `trip_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int NOT NULL,
  `captain_id` int NOT NULL,
  `boat_id` int NOT NULL,
  `trip_start_datetime` datetime DEFAULT NULL,
  `trip_end_datetime` datetime DEFAULT NULL,
  `trip_status` varchar(30) DEFAULT 'BOOKED',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`trip_id`),
  KEY `booking_id` (`booking_id`),
  KEY `captain_id` (`captain_id`),
  KEY `boat_id` (`boat_id`),
  CONSTRAINT `trips_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`),
  CONSTRAINT `trips_ibfk_2` FOREIGN KEY (`captain_id`) REFERENCES `captains` (`captain_id`),
  CONSTRAINT `trips_ibfk_3` FOREIGN KEY (`boat_id`) REFERENCES `boats` (`boat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trips`
--

LOCK TABLES `trips` WRITE;
/*!40000 ALTER TABLE `trips` DISABLE KEYS */;
INSERT INTO `trips` VALUES (1,2,3,1,'2026-09-20 08:00:00','2026-09-21 17:00:00','BOOKED','2026-09-11 07:29:54'),(2,2,3,1,'2026-10-05 09:00:00','2026-10-06 17:00:00','BOOKED','2026-09-11 08:05:01'),(3,2,3,1,'2026-10-05 09:00:00','2026-10-06 17:00:00','BOOKED','2026-09-11 08:05:07'),(4,2,3,1,'2026-10-15 09:00:00','2026-10-15 17:00:00','BOOKED','2026-09-11 08:28:23'),(5,3,3,1,'2026-09-15 09:00:00','2026-09-15 17:00:00','BOOKED','2026-09-12 06:20:14'),(6,9,3,1,'2026-09-20 09:00:00','2026-09-20 17:00:00','BOOKED','2026-09-12 07:23:44'),(7,10,3,1,'2026-09-20 09:00:00','2026-09-20 17:00:00','BOOKED','2026-09-12 07:30:53'),(8,12,3,1,'2026-09-20 09:00:00','2026-09-20 17:00:00','BOOKED','2026-09-12 07:46:48'),(9,12,3,1,'2026-09-21 09:00:00','2026-09-22 17:00:00','BOOKED','2026-09-12 07:54:07'),(10,17,1,1,'2026-09-16 13:57:26','2026-09-16 14:07:06','COMPLETED','2026-09-15 09:08:25'),(11,17,3,4,NULL,NULL,'BOOKED','2026-09-16 09:33:13'),(12,28,1,1,'2026-09-16 15:15:40',NULL,'IN_PROGRESS','2026-09-16 09:44:08'),(13,29,3,5,'2026-09-20 08:40:22','2026-09-20 08:40:26','COMPLETED','2026-09-20 03:09:37'),(14,31,3,6,'2026-09-20 13:29:37','2026-09-20 13:29:41','COMPLETED','2026-09-20 07:59:05');
/*!40000 ALTER TABLE `trips` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-20 17:33:00
