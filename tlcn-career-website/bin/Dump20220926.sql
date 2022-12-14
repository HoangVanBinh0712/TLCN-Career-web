-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: careerwebsite
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_confirm` bit(1) DEFAULT NULL,
  `name` varchar(120) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `avatar_id` bigint DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c0r9atamxvbhjjvy5j8da1kam` (`email`),
  KEY `FK3d2i4to51p7w2kaoudn9ev03f` (`avatar_id`),
  KEY `FK4ey6tpx7rtrtkm2eufmfnbmlf` (`city_id`),
  CONSTRAINT `FK3d2i4to51p7w2kaoudn9ev03f` FOREIGN KEY (`avatar_id`) REFERENCES `media_resource` (`id`),
  CONSTRAINT `FK4ey6tpx7rtrtkm2eufmfnbmlf` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,_binary '','thu duc',NULL,'thebest11447@gmail.com',_binary '','Hoang Van Binh','$2a$10$BpGcCwmcnlUHhgrsleKGMuXGPJFXAtcxncZhrpD/ah/tRfyR4cRQK','0337445596',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_p738egrkomomgourst3hqfipb` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'ho-chi-minh','TP H??? Ch?? Minh'),(2,'binh-duong','B??nh D????ng'),(3,'dong-nai','?????ng Nai'),(4,'an-giang','An Giang'),(5,'ba-ria-vung-tau','B?? R???a ??? V??ng T??u'),(6,'bac-giang','B???c Giang'),(7,'bac-kan','B???c K???n'),(8,'bac-lieu','B???c Li??u'),(9,'bac-ninh','B???c Ninh'),(10,'ben-tre','B???n Tre'),(11,'binh-dinh','B??nh ?????nh'),(12,'binh-phuoc','B??nh Ph?????c'),(13,'binh-thuan','B??nh Thu???n'),(14,'ca-mau','C?? Mau'),(15,'can-tho','C???n Th??'),(16,'cao-bang','Cao B???ng '),(17,'da-nang','???? N???ng'),(18,'dak-lak','?????k L???k'),(19,'dak-nong','?????k N??ng'),(20,'dien-bien','??i???n Bi??n'),(21,'dong-thap','?????ng Th??p'),(22,'gia-lai','Gia Lai'),(23,'ha-giang','H?? Giang'),(24,'ha-nam','H?? Nam'),(25,'ha-noi','H?? N???i '),(26,'ha-tinh','H?? T??nh'),(27,'hai-duong','H???i D????ng'),(28,'hai-phong','H???i Ph??ng'),(29,'hau-giang','H???u Giang'),(30,'hoa-binh','H??a B??nh'),(31,'hung-yen','H??ng Y??n'),(32,'khanh-hoa','Kh??nh H??a'),(33,'kien-giang','Ki??n Giang'),(34,'kon-tum','Kon Tum'),(35,'lai-chau','Lai Ch??u'),(36,'lam-dong','L??m ?????ng'),(37,'lang-son','L???ng S??n'),(38,'lao-cai','L??o Cai'),(39,'long-an','Long An'),(40,'nam-dinh','Nam ?????nh'),(41,'nghe-an','Ngh??? An'),(42,'ninh-binh','Ninh B??nh'),(43,'ninh-thuan','Ninh Thu???n'),(44,'phu-tho','Ph?? Th???'),(45,'phu-yen','Ph?? Y??n'),(46,'quang-binh','Qu???ng B??nh'),(47,'quang-nam','Qu???ng Nam'),(48,'quang-ngai','Qu???ng Ng??i'),(49,'quang-ninh','Qu???ng Ninh'),(50,'quang-tri','Qu???ng Tr???'),(51,'soc-trang','S??c Tr??ng'),(52,'son-la','S??n La'),(53,'tay-ninh','T??y Ninh'),(54,'thai-binh','Th??i B??nh'),(55,'thai-nguyen','Th??i Nguy??n'),(56,'thanh-hoa','Thanh H??a'),(57,'thua-thien-hue','Th???a Thi??n Hu???'),(58,'tien-giang','Ti???n Giang'),(59,'tra-vinh','Tr?? Vinh'),(60,'tuyen-quang','Tuy??n Quang'),(61,'vinh-long','V??nh Long'),(62,'vinh-phuc','V??nh Ph??c'),(63,'yen-bai','Y??n B??i');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cvsubmit`
--

DROP TABLE IF EXISTS `cvsubmit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cvsubmit` (
  `media_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `match_percent` bigint DEFAULT NULL,
  PRIMARY KEY (`media_id`,`post_id`,`user_id`),
  KEY `FKqku9e8075xelp7tm8ilymcu1x` (`post_id`),
  KEY `FKt99c8dc7ivt24v2lef9neah3j` (`media_id`,`user_id`),
  CONSTRAINT `FKqku9e8075xelp7tm8ilymcu1x` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKt99c8dc7ivt24v2lef9neah3j` FOREIGN KEY (`media_id`, `user_id`) REFERENCES `profile` (`media_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cvsubmit`
--

LOCK TABLES `cvsubmit` WRITE;
/*!40000 ALTER TABLE `cvsubmit` DISABLE KEYS */;
INSERT INTO `cvsubmit` VALUES (13,2,1,'2022-08-31 16:53:12.402000',50),(13,3,1,'2022-08-31 16:53:16.769000',7),(17,1,2,'2022-08-31 16:53:53.706000',28),(17,2,2,'2022-08-31 16:53:55.032000',20),(17,3,2,'2022-08-31 16:53:52.256000',45),(24,1,1,'2022-09-06 21:48:15.349000',55);
/*!40000 ALTER TABLE `cvsubmit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employer`
--

DROP TABLE IF EXISTS `employer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_confirm` bit(1) DEFAULT NULL,
  `name` varchar(120) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `employee` bigint DEFAULT NULL,
  `avatar_id` bigint DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  `field_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kde6jr4yp7v8fc949kh1tgyvp` (`email`),
  KEY `FK9lri5v2cof2l56nmqw6jy3pga` (`avatar_id`),
  KEY `FKnopx5tkgjmuiy628hawfjbl99` (`city_id`),
  KEY `FKbjyob07n475lihd1erldl9c6j` (`field_id`),
  CONSTRAINT `FK9lri5v2cof2l56nmqw6jy3pga` FOREIGN KEY (`avatar_id`) REFERENCES `media_resource` (`id`),
  CONSTRAINT `FKbjyob07n475lihd1erldl9c6j` FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
  CONSTRAINT `FKnopx5tkgjmuiy628hawfjbl99` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employer`
--

LOCK TABLES `employer` WRITE;
/*!40000 ALTER TABLE `employer` DISABLE KEYS */;
INSERT INTO `employer` VALUES (1,_binary '','Th??? ?????c',NULL,'cnttcompany@gmail.com',_binary '\0','C??ng ty CNTT TP H??? Ch?? Minh','$2a$10$HEWcKxY9m4zr6oOqNuSZkug3vOcED5Y/4cyC2TvRfXKFYRTO8MhDW','0904730050',100,22,1,44),(2,_binary '','Ba ????nh',NULL,'contructcompay@gmail.com',_binary '\0','X??y d???ng','$2a$10$HEWcKxY9m4zr6oOqNuSZkug3vOcED5Y/4cyC2TvRfXKFYRTO8MhDW','0810810810',100,23,6,15),(3,_binary '','C???u long',NULL,'salecompany@gmail.com',_binary '\0','C??ng ty b??n h??ng','$2a$10$HEWcKxY9m4zr6oOqNuSZkug3vOcED5Y/4cyC2TvRfXKFYRTO8MhDW','0987123458',100,21,5,26);
/*!40000 ALTER TABLE `employer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bpg2hvntg0iitx56dwrfu9ryo` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field`
--

LOCK TABLES `field` WRITE;
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
INSERT INTO `field` VALUES (4,'businessdevelopment','Business Development'),(5,'aviation','Aviation'),(6,'banking','Banking'),(7,'publicrelations','Bublic Relations'),(8,'chef','Chef'),(9,'accountant','Accountant'),(10,'teacher','Teacher'),(11,'consultant','Consultant'),(12,'digitalmedia','Digitalmedia'),(13,'apparel','Apparel'),(14,'javadeveloper','Java Developer'),(15,'construction','Construction'),(16,'testing','Testing'),(17,'finance','Finance'),(18,'agriculture','Agriculture'),(19,'devopsengineer','Devops Engineer'),(20,'pythondeveloper','Python Developer'),(21,'webdesigning','Web Designing'),(22,'hr','HR'),(23,'hadoop','Hadoop'),(24,'blockchain','Blockchain'),(25,'mechanicalengineer','Mechanical Engineer'),(26,'sales','Sales'),(27,'etldeveloper','Etl Developer'),(28,'operationsmanager','Operations Manager'),(29,'datascience','Data Science'),(30,'arts','Arts'),(31,'automobile','Automobile'),(32,'database','Database'),(33,'healthandfitness','Health and Fitness'),(34,'pmo','PMO'),(35,'electricalengineering','Electrical Engineering'),(36,'dotnetdeveloper','Dot Net Developer'),(37,'businessanalyst','Business Analyst'),(38,'automationtesting','Automation Testing'),(39,'networksecurityengineer','Network Security Engineer'),(40,'civilengineer','Civil Engineer'),(41,'sapdeveloper','SAP Developer'),(42,'bpo','BPO'),(43,'advocate','Advocate'),(44,'engineering','Engineering');
/*!40000 ALTER TABLE `field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_resource`
--

DROP TABLE IF EXISTS `media_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `public_id` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `url` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_resource`
--

LOCK TABLES `media_resource` WRITE;
/*!40000 ALTER TABLE `media_resource` DISABLE KEYS */;
INSERT INTO `media_resource` VALUES (2,'izslkumdlbefdy4immla','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661823419/izslkumdlbefdy4immla.png'),(7,'bmejppt91gy8cw3yc5yr','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661825343/bmejppt91gy8cw3yc5yr.pdf'),(8,'qos4lxt2sqbiztiejjrm','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661828465/qos4lxt2sqbiztiejjrm.png'),(9,'wbbhoubd8o26pyunhskw','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661828761/wbbhoubd8o26pyunhskw.png'),(10,'olk02lwqqjtcqgw6nsml','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661828796/olk02lwqqjtcqgw6nsml.png'),(13,'xfw2vy2x5umoobluqatv','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661855157/xfw2vy2x5umoobluqatv.pdf'),(14,'wms2n0tuis6wwxeq9qww','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661857116/wms2n0tuis6wwxeq9qww.pdf'),(17,'tksiuixdbqcudvuujnb6','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661938642/tksiuixdbqcudvuujnb6.pdf'),(21,'rkzwag8ak0jmgwggc7iz','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661941543/rkzwag8ak0jmgwggc7iz.jpg'),(22,'fmpg8vkqvglagihk0cdl','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661941582/fmpg8vkqvglagihk0cdl.jpg'),(23,'j1clradpfcsemmecel9r','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1661941720/j1clradpfcsemmecel9r.jpg'),(24,'roydzvsgcvllvk1gst8j','image','https://res.cloudinary.com/dwezyh13q/image/upload/v1662475651/roydzvsgcvllvk1gst8j.pdf');
/*!40000 ALTER TABLE `media_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accepted_date` datetime(6) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `description` longtext,
  `expiration_date` datetime(6) NOT NULL,
  `location` varchar(200) NOT NULL,
  `recruit` bigint DEFAULT NULL,
  `salary` bigint NOT NULL,
  `salary_type` varchar(255) NOT NULL,
  `title` longtext,
  `accepted_by` bigint DEFAULT NULL,
  `avatar_id` bigint DEFAULT NULL,
  `city` bigint DEFAULT NULL,
  `author` bigint DEFAULT NULL,
  `field` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfgna97yh882924tk04695xq9y` (`accepted_by`),
  KEY `FKn0yjnh7nawba0pauh69o6sxgf` (`avatar_id`),
  KEY `FKgujw94bnvv7w9j8eyd4mymrtf` (`city`),
  KEY `FKjt5qelg59dl30kurr2nb7ljf8` (`author`),
  KEY `FKvuyp3gdvyjcdoy88c49fmadu` (`field`),
  CONSTRAINT `FKfgna97yh882924tk04695xq9y` FOREIGN KEY (`accepted_by`) REFERENCES `admin` (`id`),
  CONSTRAINT `FKgujw94bnvv7w9j8eyd4mymrtf` FOREIGN KEY (`city`) REFERENCES `city` (`id`),
  CONSTRAINT `FKjt5qelg59dl30kurr2nb7ljf8` FOREIGN KEY (`author`) REFERENCES `employer` (`id`),
  CONSTRAINT `FKn0yjnh7nawba0pauh69o6sxgf` FOREIGN KEY (`avatar_id`) REFERENCES `media_resource` (`id`),
  CONSTRAINT `FKvuyp3gdvyjcdoy88c49fmadu` FOREIGN KEY (`field`) REFERENCES `field` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'2022-08-30 10:12:36.120000','2022-08-30 10:58:44.476000','Java Software Engineer responsibilities include: ??? Analyzing user and system requirements ??? Designing flowcharts to illustrate software solutions ??? Writing efficient code based on feature specifications Job brief We are looking for a Java Software Engineer to help us build functional products and applications. Java Software Engineer responsibilities include defining software requirements, writing clean and efficient code for various applications and running tests to improve system functionality. If you have hands-on experience with programming in Java and are interested in agile methodologies, we???d like to meet you. Ultimately, you???ll implement and maintain Java components and frameworks throughout the software development life cycle. Responsibilities ??? Analyze user and system requirements ??? Design flowcharts to illustrate software solutions ??? Write efficient code based on feature specifications ??? Develop user interfaces ??? Prioritize and execute tasks in the software development life cycle ??? Design database architecture ??? Test and debug Java applications ??? Validate software functionality and security Requirements ??? Work experience as a Java Software Engineer, Java Developer or similar role ??? Experience building Java EE applications ??? Experience in JSP/Servlet, Hibernate, JDBC, Swing ??? Familiarity with Object-Oriented Design (OOD) ??? Well verse with web Technologies such as HTML, CSS, Ajax, Javascript ??? Good knowledge of popular Java frameworks like JSF and Spring MVC ??? Experience with test-driven development ??? Problem-solving skills ??? BSc in Computer Science, Engineering or relevant field','2022-12-15 07:00:00.000000','H???i Ph??ng 01',50,13,'MILLION','Qu???n l?? d??? ??n ph???n m???m',1,8,1,1,14),(2,'2022-08-30 10:12:36.120000','2022-08-30 10:58:44.476000','CAREER OBJECTIVE Having 10 year of experience as a Python Developer. Having experience in developing web applications using Python and Django Knowledge on front end designing HTML, CSS. Through knowledge of Object Oriented Programming. Exceptional ability to work independently with a team of Python coders. Ability to learn and adapt quickly and to correctly apply new tools and technologies. Self-starter who can prioritize in order to meet deadlines. Capable of working alone as well as a team player with strong interpersonal skills. PROFESSIONAL EXPERIENCE Python Developer Company Name, Location ??? August 2017 to Present Description: Transport management system is a system used to manage Driving Licenses that are to be maintained in Transport Authority of Oregon State in U.S. This application deals with Issue, Renewal and Cancellation of Driving Licenses, Issuing, Renewal ID Cards, Customer Management, Transaction and Financial Management and Insurance Management etc. Roles& Responsibilities: Monitoring and finding the issues in the platform. Developing and improving the performance issues in the platform. Performing code coverage and standardization. Preparing user interfaces for the application. Ensuring that the applications being developed can be used by non-technical person. Getting thoroughly involved in the programming of web based applications. Involved in writing test cases script using python. Python Developer Company Name, Location ??? 2016 Roles& Responsibilities: Developed SQL Queries, Stored Procedures, and Triggers Using Oracle, SQL, PL/SQL. Responsible for debugging and troubleshooting the web application. Supported user groups by handling target-related software issues/service requests, identifying/fixing bugs. Involved in Agile Methodologies and SCRUM Process. Worked through the entire lifecycle of the projects including Design, Development, and Deployment, Testing and Implementation and support. EDUCATIONAL QUALIFICATIONS Course (Stream)/ Examination Institution/University/School Year of Passing Performance BCA SRM College of Engineering and Technology ??? Chennai 2016 90% HSC K.C.S Higher Secondary School 2013 84% SSLC D.L.S Higher Secondary School 2011 80% ADDITIONAL INFORMATION Technical Skills:- Operating System: Windows Languages: Python DBMS: MySQL Web Technologies: HTML, CSS. Web Framework: Django. Editors: IDLE, notepad++ Methodologies Agile, Waterfall Operating Systems Window, Linux(Ubuntu, Fedora) Testing tools Unittest, pytest Messaging Queue???s RabbitMQ DECLARATION: I hereby declare that all the information given above are true to the best of my knowledge and belief.','2022-12-15 07:00:00.000000','H???i Ph??ng 01',50,13,'MILLION','Qu???n l?? d??? ??n ph???n m???m',1,9,2,1,14),(3,'2022-08-30 10:12:36.120000','2022-08-30 10:58:44.476000','??o polo \n????? ???????: ??en / Tr???ng /N??u\n???????? ????????:  nh?? video\n???? ????: FREESIZE form r???ng.T??? 40-55KG (m???c r???ng tho???i m??i), t??? 55-65KG (m???c r???ng v???a)\n               D??i : 68-70cm\n               R???ng : 58-59cm\n               Form r???ng. \n???????? ??????: Vi???t Nam\n\n??????????? ??????? ?????? ????? Qu???n\n??? ?????i v???i l???n gi???t ?????u ti??n, b???n n??n v?? b???ng tay v?? kh??ng n??n s??? d???ng x?? ph??ng c?? ch???t t???y. ?????c bi???t kh??ng n??n gi???t chung v???i c??c lo???i qu???n ??o tr???ng hay d??? ra m??u. H???n ch??? gi???t b???ng m??y ????? ??o ???????c b???n h??n\n\n??? ??????? ????? ????????\n??? Shop cam k???t c??? v??? CH???T LI???U c??ng nh?? H??NH ???NH (????ng v???i nh???ng g?? ???????c n??u b???t trong ph???n m?? t??? s???n ph???m)\n??? S???n ph???m ???????c ?????i tr??? h??ng l???i trong v??ng 3 ng??y k??? t??? khi nh???n ???????c h??ng. ?????i tr??? trong tr?????ng h???p h??ng l???i ???????ng may, sai m??u s???c, sai m???u, l???i do nh?? s???n xu???t, v?? C?? Video Quay Qu?? Tr??nh Nh???n H??ng V?? B??c H??ng.\n\n#aoni #aoninam #aoninu #aosweater #aosweaternam #aosweaternuformrong #aosweaterformrong #aosweaterunisex #aonikhongmu #aonibong #aonicotton #aonitaydai #aonitaybong #aonidoi #aonidep #aosweaterdoi #aosweaternamhanquoc #aonihanquoc #aosweaterphoichanvay #trend #unisex  #setni #boni #boaoquanni','2022-12-15 07:00:00.000000','H???i Ph??ng 01',50,13,'MILLION','Qu???n l?? d??? ??n ph???n m???m',1,10,3,1,26),(5,NULL,'2022-08-31 17:44:33.042000','M?? t??? c??ng vi???c\n- T?? v???n h??? tr??? kh??ch h??ng qua ??i???n tho???i s???n ph???m v?? d???ch v??? c???a c??ng ty (Thi???t b??? gia d???ng ??i???n t???).\n\n- Ch???t ????n, theo d??i ????n h??ng.\n\n- ???????c cung c???p trang thi???t b???\n\n- L??m vi???c t???i v??n ph??ng.\n\n- Th???i gian l??m vi???c: 8h15-17h30 ( ngh??? tr??a 12h-13h30), 1 tu???n off 1 ng??y\n\nY??u c???u c??ng vi???c\n- Ch??m ch???, trung th???c, vui v???, h??a ?????ng, nhanh nh???n\n\n- ????? Tu???i t??? 18-30 tu???i ( 2003-1992)\n\nQuy???n l???i\n- THU NH???P ???N ?????NH: 8-15 tri???u (l????ng c???ng + T??? 7% hoa h???ng +40K/????n h??ng)+ th?????ng n??ng\n\n- ???????c l??m vi???c trong m??i tr?????ng chuy??n nghi???p, n??ng ?????ng, c?? ch??? linh ho???t\n\n- ???????c ????o t???o\n\n- ???????c tham gia ????ng BHXH, BHYT, BHTN\n\n- ???????c h?????ng c??c quy???n l???i theo quy ?????nh c???a b??? lu???t lao ?????ng v?? theo quy ch??? c???a c??ng ty.\n\n- KH??NG Y??U C???U KINH NGHI???M','2022-08-31 07:00:00.000000','L???u 6 T??a Nh?? Th??i An, 2290 Qu???c L??? 1A, P.Trung M??? T??y,Q12, Tp.H??? Ch?? Minh.',10,15,'MILLION','Nh??n Vi??n T?? V???n L??m Vi???c T???i V??n Ph??ng L????ng Tr??n 15 Tri???u',NULL,NULL,1,2,11),(6,NULL,'2022-09-26 22:55:39.257000','C??ng ty TNHH Panel Plus Vi???t Nam - chuy??n s???n xu???t, ph??n ph???i s???n ph???m g??? c??ng nghi???p c???a Th??i Lan: MDF, MFC, PB, HDF..., c???n tuy???n G???P v??? tr?? nh??n vi??n marketing th???c hi???n:\n\nTheo d??i v?? b??o c??o hi???u su???t c???a t???t c??? c??c chi???n d???ch markting online v?? offline.\nPh??t tri???n chi???n l?????c Marketing online v?? l??n n???i dung qu???ng c??o tr??n t???t c??? c??c n???n t???ng tr???c tuy???n.\nTh???c hi???n marketing theo xu h?????ng th??? tr?????ng, ph??n t??ch ?????i th??? c???nh tranh v?? hi???u bi???t s??u s???c th??? tr?????ng ????? ph??t tri???n chi???n l?????c.\nKh??m ph?? c??c kh??ch h??ng m???i / ngu???n ti???m n??ng ????? ph??t tri???n thi???t k??? m???i c???a s???n ph???m.\nChi ti???t c??ng vi???c trao ?????i khi ph???ng v???n.','2025-12-12 07:00:00.000000','??dasda',12,12,'MILLION','213213',NULL,NULL,1,1,17),(7,NULL,'2022-09-26 22:57:17.781000','M?? t??? c??ng vi???c\n L???p k??? ho???ch Digital Marketing, tri???n khai, theo d??i v?? b??o c??o chi???n d???ch qu???ng c??o s???n ph???m v?? h??nh ???nh th????ng hi???u tr??n c??c n???n t???ng.\nX??y d???ng v?? qu???n l?? t???i ??u h??a c??c k??nh qu???ng c??o tr???c tuy???n (Google Ads, Facebook, Youtube, Zalo, Email Marketing...).\nL???p k??? ho???ch, tri???n khai chi???n d???ch qu???ng b?? v?? x??y d???ng th????ng hi???u qua SEO/SEM, Online Advertising, Affiliate Marketing, ... - bao g???m th??? nghi???m, tri???n khai v?? ????nh gi?? k???t qu???.\nL???p k??? ho???ch, ??i???u ph???i, ph???i h???p v???i Content Marketing ????? ph??t tri???n c??c k??nh Marketing Online ?????m b???o c??c k???t qu??? ???? ?????t ra.\nPh??n t??ch v?? b??o c??o hi???u qu??? ho???t ?????ng truy???n th??ng Digital Marketing, t??? ???? ????a ra ????? xu???t n??ng cao ch???t l?????ng truy???n th??ng Digital Marketing.\nUpdate c??c xu h?????ng truy???n th??ng m???i ph?? h???p v???i th????ng hi???u.\nC??c c??ng vi???c kh??c theo y??u c???u c???a qu???n l?? tr???c ti???p v?? c??ng ty.\nY??u c???u ???ng vi??n\nC?? ??t nh???t 2 n??m kinh nghi???m trong v??? tr?? t????ng ??????ng (l??nh v???c truy???n th??ng, qu???ng c??o, digital marketing).\nAm hi???u v??? Digital Marketing v?? c??c c??ng c??? Google Ads, Facebook Ads, Viral Video, E-Marketing???, c?? kinh nghi???m qu???n l?? website.\nC?? kinh nghi???m x??y d???ng v?? qu???n l?? n???i dung qu???ng c??o (?? t?????ng v??? n???i dung, tr??nh b??y n???i dung, ?? t?????ng h??nh ???nh & video...).\nC?? k??? n??ng t???ng h???p, th??nh th???o ph??n t??ch data v?? report, c?? kh??? n??ng t?? duy l??m vi???c ?????c l???p v?? teamwork.\nTrung th???c, nhanh nh???n, c???u ti???n, nghi??m t??c trong c??ng vi???c.\nB???ng c???p: ?????i h???c\n????? tu???i: Kh??ng gi???i h???n\nH??nh th???c: Nh??n vi??n ch??nh th???c.\nQuy???n l???i\n L????ng 15tr-20tr + KPIs ( C?? kinh nghi???m ch???y ads facebook ads, google ads, ??? )\n Th???i gian l??m vi???c: Th??? 2 ??? Th??? 6 : 9h-18h ; 2 ng??y th??? 7 xen k??? trong th??ng\n ?????a ??i???m l??m vi???c: 237 Nguy???n Tr??i, Ph?????ng Nguy???n C?? Trinh, Qu???n 1\n X??t t??ng l????ng 6 th??ng/l???n , c?? l??? tr??nh th??ng ti???n r?? r??ng ???????c tham gia c??c ho???t ?????ng chung c???a c??ng ty: team building, happy hour...\n ???????c th???a s???c s??ng t???o trong c??ng vi???c\n????ng BHXH theo quy ?????nh lu???t lao ?????ng\n M??i tr?????ng l??m vi???c tr??? trung, n??ng ?????ng v?? s??ng t???o\n L????ng theo th???a thu???n Th???i gian l??m vi???c: Th??? 2 ??? Th??? 6 : 9h-18h ; 2 ng??y th??? 7 xen k??? trong th??ng.','2025-12-12 07:00:00.000000','319B2 L?? Th?????ng Ki???t , Ph?????ng 15, Qu???n 11, Th??nh ph??? H??? Ch?? Minh, Vi???t Nam\n',23,12,'MILLION','Nh??n Vi??n Digital Marketing',NULL,NULL,2,1,17);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `media_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`media_id`,`user_id`),
  KEY `FKawh070wpue34wqvytjqr4hj5e` (`user_id`),
  CONSTRAINT `FKawh070wpue34wqvytjqr4hj5e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqitv6qsbqikwwp1h8sgku01vb` FOREIGN KEY (`media_id`) REFERENCES `media_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (7,1,1,'CV01'),(13,1,1,'cv11'),(14,1,1,'cv112'),(17,2,0,'12a'),(24,1,1,'acac');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_confirm` bit(1) DEFAULT NULL,
  `name` varchar(120) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `avatar_id` bigint DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FK64ydoqa8wkadupx8aci0k4v2h` (`avatar_id`),
  KEY `FK29eqyw0gxw5r4f1ommy11nd9i` (`city_id`),
  CONSTRAINT `FK29eqyw0gxw5r4f1ommy11nd9i` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `FK64ydoqa8wkadupx8aci0k4v2h` FOREIGN KEY (`avatar_id`) REFERENCES `media_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '','D?? An',NULL,'user01@example.com',_binary '\0','string','$2a$10$CeP74h.HW08civAbYVh2k.UXQIhzSYiDAj2Ew9fth01b6/DYLGhua','0615843974','2003-06-08 07:00:00.000000','MALE',2,38),(2,_binary '','string',NULL,'user02@example.com',_binary '\0','User 02','$2a$10$q.o2i/DjXLMc4si3gGUmWe9ssiWELoSOEboIsY54DYL3E0ADHWhoa','0615843174',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-26 22:59:39
