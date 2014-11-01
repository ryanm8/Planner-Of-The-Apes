CREATE DATABASE  IF NOT EXISTS `planneroftheapes` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `planneroftheapes`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: planneroftheapes
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Due_Date` date NOT NULL,
  `Priority` enum('Low','Medium','High') NOT NULL,
  `Progress` enum('Not Started','In Progress','Completed') NOT NULL,
  `Assignee_ID` int(11) DEFAULT NULL,
  `Class` varchar(45) NOT NULL,
  `Difficulty` enum('Easy','Medium','Hard') NOT NULL,
  `Notes` varchar(127) NOT NULL,
  `Documents_id` int(11) DEFAULT NULL,
  `Type` enum('Assignment','Project','Other') NOT NULL,
  `Group_id` int(11) DEFAULT NULL,
  `Document_Path` varchar(45) NOT NULL DEFAULT 'Add New Doc?',
  `Assignment_Name` varchar(45) NOT NULL DEFAULT 'Assignment Name',
  `Starred` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `Assigee_f_key_idx` (`Assignee_ID`),
  KEY `Documents_id_idx` (`Documents_id`),
  KEY `Group_id_idx` (`Group_id`),
  CONSTRAINT `Assigee_f_key` FOREIGN KEY (`Assignee_ID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Documents_id` FOREIGN KEY (`Documents_id`) REFERENCES `documents` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Group_id` FOREIGN KEY (`Group_id`) REFERENCES `group1` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (1,'2014-10-09','High','Not Started',1,'CS 1000','Easy','This Is a Test Assignment',1,'Assignment',2,'Add New Doc?','Unfinished classwork',0),(2,'2014-10-13','Medium','In Progress',1,'CS 4984','Medium','This is a test',2,'Project',1,'Add New Doc?','My First Assignment',0),(3,'2014-10-22','Low','Completed',2,'CS 2000','Hard','Also a test',3,'Assignment',1,'Add New Doc?','Assignment Name',0),(10,'2014-10-22','Low','Completed',2,'ENG 1000','Easy','None',4,'Assignment',2,'Add New Doc?','Assignment Name',0),(11,'2014-10-30','Medium','In Progress',3,'CS 1000','Easy','None',5,'Other',3,'Add New Doc?','Assignment Name',0),(12,'2014-10-31','High','In Progress',3,'CPE 2000','Medium','Test',6,'Other',3,'Add New Doc?','Assignment Name',0),(13,'2014-11-01','High','Completed',4,'CS 2001','Medium','Blard',7,'Project',4,'Add New Doc?','Assignment Name',0),(14,'2014-11-02','Medium','Not Started',5,'CPE 1000','Easy','I eat babies',8,'Project',4,'Add New Doc?','Assignment Name',0),(15,'2014-11-03','Low','Not Started',6,'CS 4000','Hard','Notes?',9,'Assignment',NULL,'Add New Doc?','Assignment Name',0),(17,'2014-11-03','Medium','Not Started',1,'CS 4000','Hard','No Notes',10,'Assignment',NULL,'Add New Doc?','Homework 4',0),(18,'2014-10-30','Low','Completed',1,'CS 3000','Easy','Talk to Professor',10,'Project',NULL,'Add New Doc?','Project 2 Due',0),(25,'2014-10-13','Medium','Completed',1,'CS 1000','Easy','EZ A',10,'Assignment',1,'Add New Doc?','Easy Homework 1',0),(26,'2014-10-30','High','Completed',1,'CS 2000','Medium','Whats polymorphisim?',10,'Assignment',1,'Add New Doc?','Chapter reading 2.1 - 2.5',0),(27,'2014-11-02','High','Not Started',1,'CS 3000','Hard','Oh god, Its due at midnight and I havent event started',10,'Project',NULL,'Add New Doc?','Shell',0);
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document` (
  `id` int(11) NOT NULL,
  `Link` varchar(255) NOT NULL,
  KEY `Documents_key_idx` (`id`),
  CONSTRAINT `Documents_key` FOREIGN KEY (`id`) REFERENCES `documents` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document` VALUES (1,'//myserver/server/uploaded/doc1.txt'),(2,'//myserver/server/uploaded/doc2.txt'),(3,'//myserver/server/uploaded/doc3.txt'),(4,'//myserver/server/uploaded/doc4.txt'),(5,'//myserver/server/uploaded/doc5.txt'),(6,'//myserver/server/uploaded/doc6.txt'),(1,'//myserver/server/uploaded/doc1-2.txt'),(2,'//myserver/server/uploaded/doc2-2.txt'),(3,'//myserver/server/uploaded/doc3-2.txt'),(4,'//myserver/server/uploaded/doc4-2.txt');
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Document_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `Document_F_Key_idx` (`Document_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (7,NULL),(8,NULL),(9,NULL),(10,NULL),(1,1),(2,2),(3,3),(4,4),(5,5),(6,6);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group1`
--

DROP TABLE IF EXISTS `group1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group1` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GroupUser_id` int(11) NOT NULL,
  `Admin` varchar(45) NOT NULL DEFAULT 'None',
  `Name` varchar(45) NOT NULL DEFAULT 'Group',
  `User_ID` int(11) NOT NULL DEFAULT '1',
  `Group_ID` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `GroupUser_id_UNIQUE` (`GroupUser_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group1`
--

LOCK TABLES `group1` WRITE;
/*!40000 ALTER TABLE `group1` DISABLE KEYS */;
INSERT INTO `group1` VALUES (1,1,'None','Group 1',1,1),(2,2,'None','Group 1',2,1),(3,3,'None','Group 2',1,2),(4,4,'None','Group 2',3,2),(5,5,'None','Group 3',1,3),(6,6,'None','Group 3',4,3),(7,7,'None','Group 1',3,1),(8,8,'None','Group 1',4,1),(9,9,'None','Group 1',5,1),(10,10,'None','Group 1',6,1),(11,11,'None','Group 4',1,4),(12,12,'None','Group 4',4,4),(13,13,'None','Group 4',5,4);
/*!40000 ALTER TABLE `group1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupuser`
--

DROP TABLE IF EXISTS `groupuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupuser` (
  `ID` int(11) NOT NULL,
  `User_ID` int(11) NOT NULL,
  KEY `User_ID_idx` (`User_ID`),
  KEY `GroupUser_f_key_idx` (`ID`),
  CONSTRAINT `GroupUser_f_key` FOREIGN KEY (`ID`) REFERENCES `group1` (`GroupUser_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `User_ID` FOREIGN KEY (`User_ID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupuser`
--

LOCK TABLES `groupuser` WRITE;
/*!40000 ALTER TABLE `groupuser` DISABLE KEYS */;
INSERT INTO `groupuser` VALUES (1,1),(1,2),(2,3),(2,4),(3,5),(3,6),(4,7);
/*!40000 ALTER TABLE `groupuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reminders`
--

DROP TABLE IF EXISTS `reminders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reminders` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `Text` varchar(500) NOT NULL,
  `Title` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `User_ID` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminders`
--

LOCK TABLES `reminders` WRITE;
/*!40000 ALTER TABLE `reminders` DISABLE KEYS */;
/*!40000 ALTER TABLE `reminders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL DEFAULT 'password',
  `FirstName` varchar(45) NOT NULL DEFAULT 'John',
  `LastName` varchar(45) NOT NULL DEFAULT 'Doe',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'bggreen','bggreen@vt.edu','password','Brian','Green'),(2,'chrisvt','chrisvt@vt.edu','password','John','Doe'),(3,'derikc7','derikc7@vt.edu','password','John','Doe'),(4,'ryanm8','ryanm8@vt.edu','password','John','Doe'),(5,'ryan1025','ryan1025@vt.edu','password','John','Doe'),(6,'km419','km419@vt.edu','password','John','Doe'),(7,'test','bggreen08@gmail.com','password','John','Doe');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'planneroftheapes'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-31 21:49:53
