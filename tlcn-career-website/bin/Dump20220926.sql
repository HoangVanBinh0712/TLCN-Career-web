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
INSERT INTO `city` VALUES (1,'ho-chi-minh','TP Hồ Chí Minh'),(2,'binh-duong','Bình Dương'),(3,'dong-nai','Đồng Nai'),(4,'an-giang','An Giang'),(5,'ba-ria-vung-tau','Bà Rịa – Vũng Tàu'),(6,'bac-giang','Bắc Giang'),(7,'bac-kan','Bắc Kạn'),(8,'bac-lieu','Bạc Liêu'),(9,'bac-ninh','Bắc Ninh'),(10,'ben-tre','Bến Tre'),(11,'binh-dinh','Bình Định'),(12,'binh-phuoc','Bình Phước'),(13,'binh-thuan','Bình Thuận'),(14,'ca-mau','Cà Mau'),(15,'can-tho','Cần Thơ'),(16,'cao-bang','Cao Bằng '),(17,'da-nang','Đà Nẵng'),(18,'dak-lak','Đắk Lắk'),(19,'dak-nong','Đắk Nông'),(20,'dien-bien','Điện Biên'),(21,'dong-thap','Đồng Tháp'),(22,'gia-lai','Gia Lai'),(23,'ha-giang','Hà Giang'),(24,'ha-nam','Hà Nam'),(25,'ha-noi','Hà Nội '),(26,'ha-tinh','Hà Tĩnh'),(27,'hai-duong','Hải Dương'),(28,'hai-phong','Hải Phòng'),(29,'hau-giang','Hậu Giang'),(30,'hoa-binh','Hòa Bình'),(31,'hung-yen','Hưng Yên'),(32,'khanh-hoa','Khánh Hòa'),(33,'kien-giang','Kiên Giang'),(34,'kon-tum','Kon Tum'),(35,'lai-chau','Lai Châu'),(36,'lam-dong','Lâm Đồng'),(37,'lang-son','Lạng Sơn'),(38,'lao-cai','Lào Cai'),(39,'long-an','Long An'),(40,'nam-dinh','Nam Định'),(41,'nghe-an','Nghệ An'),(42,'ninh-binh','Ninh Bình'),(43,'ninh-thuan','Ninh Thuận'),(44,'phu-tho','Phú Thọ'),(45,'phu-yen','Phú Yên'),(46,'quang-binh','Quảng Bình'),(47,'quang-nam','Quảng Nam'),(48,'quang-ngai','Quảng Ngãi'),(49,'quang-ninh','Quảng Ninh'),(50,'quang-tri','Quảng Trị'),(51,'soc-trang','Sóc Trăng'),(52,'son-la','Sơn La'),(53,'tay-ninh','Tây Ninh'),(54,'thai-binh','Thái Bình'),(55,'thai-nguyen','Thái Nguyên'),(56,'thanh-hoa','Thanh Hóa'),(57,'thua-thien-hue','Thừa Thiên Huế'),(58,'tien-giang','Tiền Giang'),(59,'tra-vinh','Trà Vinh'),(60,'tuyen-quang','Tuyên Quang'),(61,'vinh-long','Vĩnh Long'),(62,'vinh-phuc','Vĩnh Phúc'),(63,'yen-bai','Yên Bái');
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
INSERT INTO `employer` VALUES (1,_binary '','Thủ đức',NULL,'cnttcompany@gmail.com',_binary '\0','Công ty CNTT TP Hồ Chí Minh','$2a$10$HEWcKxY9m4zr6oOqNuSZkug3vOcED5Y/4cyC2TvRfXKFYRTO8MhDW','0904730050',100,22,1,44),(2,_binary '','Ba đình',NULL,'contructcompay@gmail.com',_binary '\0','Xây dựng','$2a$10$HEWcKxY9m4zr6oOqNuSZkug3vOcED5Y/4cyC2TvRfXKFYRTO8MhDW','0810810810',100,23,6,15),(3,_binary '','Cửu long',NULL,'salecompany@gmail.com',_binary '\0','Công ty bán hàng','$2a$10$HEWcKxY9m4zr6oOqNuSZkug3vOcED5Y/4cyC2TvRfXKFYRTO8MhDW','0987123458',100,21,5,26);
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
INSERT INTO `post` VALUES (1,'2022-08-30 10:12:36.120000','2022-08-30 10:58:44.476000','Java Software Engineer responsibilities include: • Analyzing user and system requirements • Designing flowcharts to illustrate software solutions • Writing efficient code based on feature specifications Job brief We are looking for a Java Software Engineer to help us build functional products and applications. Java Software Engineer responsibilities include defining software requirements, writing clean and efficient code for various applications and running tests to improve system functionality. If you have hands-on experience with programming in Java and are interested in agile methodologies, we’d like to meet you. Ultimately, you’ll implement and maintain Java components and frameworks throughout the software development life cycle. Responsibilities • Analyze user and system requirements • Design flowcharts to illustrate software solutions • Write efficient code based on feature specifications • Develop user interfaces • Prioritize and execute tasks in the software development life cycle • Design database architecture • Test and debug Java applications • Validate software functionality and security Requirements • Work experience as a Java Software Engineer, Java Developer or similar role • Experience building Java EE applications • Experience in JSP/Servlet, Hibernate, JDBC, Swing • Familiarity with Object-Oriented Design (OOD) • Well verse with web Technologies such as HTML, CSS, Ajax, Javascript • Good knowledge of popular Java frameworks like JSF and Spring MVC • Experience with test-driven development • Problem-solving skills • BSc in Computer Science, Engineering or relevant field','2022-12-15 07:00:00.000000','Hải Phòng 01',50,13,'MILLION','Quản lý dự án phần mềm',1,8,1,1,14),(2,'2022-08-30 10:12:36.120000','2022-08-30 10:58:44.476000','CAREER OBJECTIVE Having 10 year of experience as a Python Developer. Having experience in developing web applications using Python and Django Knowledge on front end designing HTML, CSS. Through knowledge of Object Oriented Programming. Exceptional ability to work independently with a team of Python coders. Ability to learn and adapt quickly and to correctly apply new tools and technologies. Self-starter who can prioritize in order to meet deadlines. Capable of working alone as well as a team player with strong interpersonal skills. PROFESSIONAL EXPERIENCE Python Developer Company Name, Location – August 2017 to Present Description: Transport management system is a system used to manage Driving Licenses that are to be maintained in Transport Authority of Oregon State in U.S. This application deals with Issue, Renewal and Cancellation of Driving Licenses, Issuing, Renewal ID Cards, Customer Management, Transaction and Financial Management and Insurance Management etc. Roles& Responsibilities: Monitoring and finding the issues in the platform. Developing and improving the performance issues in the platform. Performing code coverage and standardization. Preparing user interfaces for the application. Ensuring that the applications being developed can be used by non-technical person. Getting thoroughly involved in the programming of web based applications. Involved in writing test cases script using python. Python Developer Company Name, Location – 2016 Roles& Responsibilities: Developed SQL Queries, Stored Procedures, and Triggers Using Oracle, SQL, PL/SQL. Responsible for debugging and troubleshooting the web application. Supported user groups by handling target-related software issues/service requests, identifying/fixing bugs. Involved in Agile Methodologies and SCRUM Process. Worked through the entire lifecycle of the projects including Design, Development, and Deployment, Testing and Implementation and support. EDUCATIONAL QUALIFICATIONS Course (Stream)/ Examination Institution/University/School Year of Passing Performance BCA SRM College of Engineering and Technology – Chennai 2016 90% HSC K.C.S Higher Secondary School 2013 84% SSLC D.L.S Higher Secondary School 2011 80% ADDITIONAL INFORMATION Technical Skills:- Operating System: Windows Languages: Python DBMS: MySQL Web Technologies: HTML, CSS. Web Framework: Django. Editors: IDLE, notepad++ Methodologies Agile, Waterfall Operating Systems Window, Linux(Ubuntu, Fedora) Testing tools Unittest, pytest Messaging Queue’s RabbitMQ DECLARATION: I hereby declare that all the information given above are true to the best of my knowledge and belief.','2022-12-15 07:00:00.000000','Hải Phòng 01',50,13,'MILLION','Quản lý dự án phần mềm',1,9,2,1,14),(3,'2022-08-30 10:12:36.120000','2022-08-30 10:58:44.476000','Áo polo \n??̀? ??̆́?: Đen / Trắng /Nâu\n???̂́? ???̣̂?:  như video\n???? ?́?: FREESIZE form rộng.Từ 40-55KG (mặc rộng thoải mái), từ 55-65KG (mặc rộng vừa)\n               Dài : 68-70cm\n               Rộng : 58-59cm\n               Form rộng. \n???̂́? ??̛́: Việt Nam\n\n??̛?̛́?? ??̂̃? ??́?? ??̉? Quản\n• Đối với lần giặt đầu tiên, bạn nên vò bằng tay và không nên sử dụng xà phòng có chất tẩy. Đặc biệt không nên giặt chung với các loại quần áo trắng hay dễ ra màu. Hạn chế giặt bằng máy để áo được bền hơn\n\n??? ??̂́? ??̉? ???̂̉?\n• Shop cam kết cả về CHẤT LIỆU cũng như HÌNH ẢNH (đúng với những gì được nêu bật trong phần mô tả sản phẩm)\n• Sản phẩm được đổi trả hàng lỗi trong vòng 3 ngày kể từ khi nhận được hàng. Đổi trả trong trường hợp hàng lỗi đường may, sai màu sắc, sai mẫu, lỗi do nhà sản xuất, và Có Video Quay Quá Trình Nhận Hàng Và Bóc Hàng.\n\n#aoni #aoninam #aoninu #aosweater #aosweaternam #aosweaternuformrong #aosweaterformrong #aosweaterunisex #aonikhongmu #aonibong #aonicotton #aonitaydai #aonitaybong #aonidoi #aonidep #aosweaterdoi #aosweaternamhanquoc #aonihanquoc #aosweaterphoichanvay #trend #unisex  #setni #boni #boaoquanni','2022-12-15 07:00:00.000000','Hải Phòng 01',50,13,'MILLION','Quản lý dự án phần mềm',1,10,3,1,26),(5,NULL,'2022-08-31 17:44:33.042000','Mô tả công việc\n- Tư vấn hỗ trợ khách hàng qua điện thoại sản phẩm và dịch vụ của công ty (Thiết bị gia dụng điện tử).\n\n- Chốt đơn, theo dõi đơn hàng.\n\n- Được cung cấp trang thiết bị\n\n- Làm việc tại văn phòng.\n\n- Thời gian làm việc: 8h15-17h30 ( nghỉ trưa 12h-13h30), 1 tuần off 1 ngày\n\nYêu cầu công việc\n- Chăm chỉ, trung thực, vui vẻ, hòa đồng, nhanh nhẹn\n\n- Độ Tuổi từ 18-30 tuổi ( 2003-1992)\n\nQuyền lợi\n- THU NHẬP ỔN ĐỊNH: 8-15 triệu (lương cứng + TỪ 7% hoa hồng +40K/đơn hàng)+ thưởng nóng\n\n- Được làm việc trong môi trường chuyên nghiệp, năng động, cơ chế linh hoạt\n\n- Được đào tạo\n\n- Được tham gia đóng BHXH, BHYT, BHTN\n\n- Được hưởng các quyền lợi theo quy định của bộ luật lao động và theo quy chế của công ty.\n\n- KHÔNG YÊU CẦU KINH NGHIỆM','2022-08-31 07:00:00.000000','Lầu 6 Tòa Nhà Thái An, 2290 Quốc Lộ 1A, P.Trung Mỹ Tây,Q12, Tp.Hồ Chí Minh.',10,15,'MILLION','Nhân Viên Tư Vấn Làm Việc Tại Văn Phòng Lương Trên 15 Triệu',NULL,NULL,1,2,11),(6,NULL,'2022-09-26 22:55:39.257000','Công ty TNHH Panel Plus Việt Nam - chuyên sản xuất, phân phối sản phẩm gỗ công nghiệp của Thái Lan: MDF, MFC, PB, HDF..., cần tuyển GẤP vị trí nhân viên marketing thực hiện:\n\nTheo dõi và báo cáo hiệu suất của tất cả các chiến dịch markting online và offline.\nPhát triển chiến lược Marketing online và lên nội dung quảng cáo trên tất cả các nền tảng trực tuyến.\nThực hiện marketing theo xu hướng thị trường, phân tích đối thủ cạnh tranh và hiểu biết sâu sắc thị trường để phát triển chiến lược.\nKhám phá các khách hàng mới / nguồn tiềm năng để phát triển thiết kế mới của sản phẩm.\nChi tiết công việc trao đổi khi phỏng vấn.','2025-12-12 07:00:00.000000','ádasda',12,12,'MILLION','213213',NULL,NULL,1,1,17),(7,NULL,'2022-09-26 22:57:17.781000','Mô tả công việc\n Lập kế hoạch Digital Marketing, triển khai, theo dõi và báo cáo chiến dịch quảng cáo sản phẩm và hình ảnh thương hiệu trên các nền tảng.\nXây dựng và quản lý tối ưu hóa các kênh quảng cáo trực tuyến (Google Ads, Facebook, Youtube, Zalo, Email Marketing...).\nLập kế hoạch, triển khai chiến dịch quảng bá và xây dựng thương hiệu qua SEO/SEM, Online Advertising, Affiliate Marketing, ... - bao gồm thử nghiệm, triển khai và đánh giá kết quả.\nLập kế hoạch, điều phối, phối hợp với Content Marketing để phát triển các kênh Marketing Online đảm bảo các kết quả đã đặt ra.\nPhân tích và báo cáo hiệu quả hoạt động truyền thông Digital Marketing, từ đó đưa ra đề xuất nâng cao chất lượng truyền thông Digital Marketing.\nUpdate các xu hướng truyền thông mới phù hợp với thương hiệu.\nCác công việc khác theo yêu cầu của quản lý trực tiếp và công ty.\nYêu cầu ứng viên\nCó ít nhất 2 năm kinh nghiệm trong vị trí tương đương (lĩnh vực truyền thông, quảng cáo, digital marketing).\nAm hiểu về Digital Marketing và các công cụ Google Ads, Facebook Ads, Viral Video, E-Marketing…, có kinh nghiệm quản lý website.\nCó kinh nghiệm xây dựng và quản lý nội dung quảng cáo (ý tưởng về nội dung, trình bày nội dung, ý tưởng hình ảnh & video...).\nCó kỹ năng tổng hợp, thành thạo phân tích data và report, có khả năng tư duy làm việc độc lập và teamwork.\nTrung thực, nhanh nhẹn, cầu tiến, nghiêm túc trong công việc.\nBằng cấp: Đại học\nĐộ tuổi: Không giới hạn\nHình thức: Nhân viên chính thức.\nQuyền lợi\n Lương 15tr-20tr + KPIs ( Có kinh nghiệm chạy ads facebook ads, google ads, … )\n Thời gian làm việc: Thứ 2 – Thứ 6 : 9h-18h ; 2 ngày thứ 7 xen kẽ trong tháng\n Địa điểm làm việc: 237 Nguyễn Trãi, Phường Nguyễn Cư Trinh, Quận 1\n Xét tăng lương 6 tháng/lần , có lộ trình thăng tiến rõ ràng Được tham gia các hoạt động chung của công ty: team building, happy hour...\n Được thỏa sức sáng tạo trong công việc\nĐóng BHXH theo quy định luật lao động\n Môi trường làm việc trẻ trung, năng động và sáng tạo\n Lương theo thỏa thuận Thời gian làm việc: Thứ 2 – Thứ 6 : 9h-18h ; 2 ngày thứ 7 xen kẽ trong tháng.','2025-12-12 07:00:00.000000','319B2 Lý Thường Kiệt , Phường 15, Quận 11, Thành phố Hồ Chí Minh, Việt Nam\n',23,12,'MILLION','Nhân Viên Digital Marketing',NULL,NULL,2,1,17);
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
INSERT INTO `user` VALUES (1,_binary '','Dĩ An',NULL,'user01@example.com',_binary '\0','string','$2a$10$CeP74h.HW08civAbYVh2k.UXQIhzSYiDAj2Ew9fth01b6/DYLGhua','0615843974','2003-06-08 07:00:00.000000','MALE',2,38),(2,_binary '','string',NULL,'user02@example.com',_binary '\0','User 02','$2a$10$q.o2i/DjXLMc4si3gGUmWe9ssiWELoSOEboIsY54DYL3E0ADHWhoa','0615843174',NULL,NULL,NULL,NULL);
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
