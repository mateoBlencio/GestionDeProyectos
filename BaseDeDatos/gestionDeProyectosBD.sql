-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: gestionproyectos
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `nro_cliente` int NOT NULL AUTO_INCREMENT,
  `nombre_cliente` varchar(100) NOT NULL,
  `telefono` int DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`nro_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Cliente A',123456789,'clienteA@mail.com'),(2,'Cliente B',987654321,'clienteB@mail.com'),(3,'Cliente C',111222333,'clienteC@mail.com'),(4,'Cliente D',1234563339,'clienteD@mail.com');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `nro_empleado` int NOT NULL AUTO_INCREMENT,
  `nombre_empleado` varchar(100) NOT NULL,
  `nro_rol_empleado` int DEFAULT NULL,
  `mail` varchar(100) NOT NULL,
  `nombre_usuario` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`nro_empleado`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  KEY `nro_rol_empleado` (`nro_rol_empleado`),
  CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`nro_rol_empleado`) REFERENCES `rol_empleado` (`numero_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'Juan Pérez',1,'abc@gmail.com','usuario1','$2a$10$x3GFSmvEzKDiuos8K5qILe8XyYMVccRwzj/EBZH6PxDb.3fJrWv3e'),(2,'Ana Rodríguez',2,'abc@gmail.com','usuario2','$2a$10$KYsp0G4ztjKZ7U80kpxQyeE/K.DAZrdGKOmWiejmuhtjJMMunwgKe'),(3,'Pedro Gómez',3,'abc@gmail.com','usuario3','$2a$10$a3hLfApSIqtZi2ss89zufuI3leKXXdhQ0dpxjoxwvCgBt2Vtj.WRq'),(4,'María López',4,'abc@gmail.com','usuario4','$2a$10$3BVSSlaoDLqvtiRKVNr5WOOTwDFB0noV.3ZJYl8JL14sv7vy1QFq.'),(5,'Carlos Martínez',5,'abc@gmail.com','usuario5','$2a$10$3BVSSlaoDLqvtiRKVNr5WOOTwDFB0noV.3ZJYl8JL14sv7vy1QFq.');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado_x_proyecto`
--

DROP TABLE IF EXISTS `empleado_x_proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado_x_proyecto` (
  `nro_empleado` int NOT NULL,
  `nro_proyecto` int NOT NULL,
  `lider_proyecto` int NOT NULL,
  PRIMARY KEY (`nro_empleado`,`nro_proyecto`),
  KEY `nro_proyecto` (`nro_proyecto`),
  KEY `lider_proyecto` (`lider_proyecto`),
  CONSTRAINT `empleado_x_proyecto_ibfk_1` FOREIGN KEY (`nro_empleado`) REFERENCES `empleado` (`nro_empleado`),
  CONSTRAINT `empleado_x_proyecto_ibfk_2` FOREIGN KEY (`nro_proyecto`) REFERENCES `proyecto` (`nro_proyecto`),
  CONSTRAINT `empleado_x_proyecto_ibfk_3` FOREIGN KEY (`lider_proyecto`) REFERENCES `empleado` (`nro_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado_x_proyecto`
--

LOCK TABLES `empleado_x_proyecto` WRITE;
/*!40000 ALTER TABLE `empleado_x_proyecto` DISABLE KEYS */;
INSERT INTO `empleado_x_proyecto` VALUES (1,1,1),(2,1,1),(3,2,3),(4,2,3),(2,9,5),(5,3,5);
/*!40000 ALTER TABLE `empleado_x_proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado` (
  `nro_estado` int NOT NULL AUTO_INCREMENT,
  `nombre_estado` varchar(100) NOT NULL,
  `descripcion` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`nro_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Pendiente','Tarea por hacer'),(2,'Progreso','Tarea en curso'),(3,'Finalizado','Tarea completada');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyecto` (
  `nro_proyecto` int NOT NULL AUTO_INCREMENT,
  `estado` int NOT NULL,
  `fecha_inicio` datetime(6) DEFAULT NULL,
  `fecha_limite` datetime(6) DEFAULT NULL,
  `fecha_reentrega` datetime(6) DEFAULT NULL,
  `precio_proyecto` int NOT NULL,
  `nro_cliente` int NOT NULL,
  `nombre_proyecto` varchar(250) NOT NULL,
  PRIMARY KEY (`nro_proyecto`),
  KEY `estado` (`estado`),
  KEY `nro_cliente` (`nro_cliente`),
  CONSTRAINT `proyecto_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estado` (`nro_estado`),
  CONSTRAINT `proyecto_ibfk_2` FOREIGN KEY (`nro_cliente`) REFERENCES `cliente` (`nro_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
INSERT INTO `proyecto` VALUES (1,2,'2023-01-01 00:00:00.000000','2023-02-01 00:00:00.000000',NULL,5000,1,'Proyecto A'),(2,2,'2023-02-01 00:00:00.000000','2023-03-01 00:00:00.000000',NULL,7000,2,'Proyecto B'),(3,3,'2023-03-01 00:00:00.000000','2023-04-01 00:00:00.000000',NULL,6000,3,'Proyecto C'),(6,3,'2023-02-28 00:00:00.000000','2023-03-09 00:00:00.000000',NULL,15000,1,'Proyecto D'),(9,1,'2024-01-10 10:57:44.482062','2020-09-09 00:00:00.000000',NULL,1234567,1,'proyectoPrueba');
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_cambios_estado`
--

DROP TABLE IF EXISTS `registro_cambios_estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_cambios_estado` (
  `nro_proyecto` int NOT NULL,
  `nro_estado` int NOT NULL,
  `fecha_cambio` datetime(6) NOT NULL,
  `nro_estado_nuevo` int NOT NULL,
  PRIMARY KEY (`nro_proyecto`,`nro_estado`,`fecha_cambio`),
  KEY `nro_estado` (`nro_estado`),
  KEY `nro_estado_nuevo` (`nro_estado_nuevo`),
  CONSTRAINT `nro_estado_nuevo` FOREIGN KEY (`nro_estado_nuevo`) REFERENCES `estado` (`nro_estado`),
  CONSTRAINT `registro_cambios_estado_ibfk_1` FOREIGN KEY (`nro_proyecto`) REFERENCES `proyecto` (`nro_proyecto`),
  CONSTRAINT `registro_cambios_estado_ibfk_2` FOREIGN KEY (`nro_estado`) REFERENCES `estado` (`nro_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_cambios_estado`
--

LOCK TABLES `registro_cambios_estado` WRITE;
/*!40000 ALTER TABLE `registro_cambios_estado` DISABLE KEYS */;
INSERT INTO `registro_cambios_estado` VALUES (1,1,'2023-01-05 00:00:00.000000',2),(1,1,'2024-01-09 12:10:19.273139',2),(1,2,'2023-01-10 00:00:00.000000',3),(2,2,'2023-02-10 00:00:00.000000',3),(6,2,'2023-12-31 12:23:41.321498',3);
/*!40000 ALTER TABLE `registro_cambios_estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol_empleado`
--

DROP TABLE IF EXISTS `rol_empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol_empleado` (
  `numero_rol` int NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(50) NOT NULL,
  `precio_por_hora` float NOT NULL,
  PRIMARY KEY (`numero_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol_empleado`
--

LOCK TABLES `rol_empleado` WRITE;
/*!40000 ALTER TABLE `rol_empleado` DISABLE KEYS */;
INSERT INTO `rol_empleado` VALUES (1,'Desarrollador',25),(2,'Diseñador',30),(3,'Gerente',40),(4,'Tester',20),(5,'Analista',35);
/*!40000 ALTER TABLE `rol_empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarea`
--

DROP TABLE IF EXISTS `tarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarea` (
  `nro_tarea` int NOT NULL,
  `nro_proyecto` int NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `prioridad` int NOT NULL,
  `estado` int NOT NULL,
  `fecha_finalizacion` datetime(6) DEFAULT NULL,
  `encargado` int NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`nro_tarea`,`nro_proyecto`),
  KEY `nro_proyecto` (`nro_proyecto`),
  KEY `estado` (`estado`),
  KEY `fk_encargado` (`encargado`),
  CONSTRAINT `fk_encargado` FOREIGN KEY (`encargado`) REFERENCES `empleado` (`nro_empleado`),
  CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`nro_proyecto`) REFERENCES `proyecto` (`nro_proyecto`),
  CONSTRAINT `tarea_ibfk_2` FOREIGN KEY (`estado`) REFERENCES `estado` (`nro_estado`),
  CONSTRAINT `tarea_chk_1` CHECK ((`prioridad` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarea`
--

LOCK TABLES `tarea` WRITE;
/*!40000 ALTER TABLE `tarea` DISABLE KEYS */;
INSERT INTO `tarea` VALUES (1,1,'Desarrollo de la aplicación',3,1,NULL,1,'2023-01-01 00:00:00.000000'),(1,2,'Diseño de interfaz',2,2,NULL,3,'2023-01-01 00:00:00.000000'),(1,3,'Pruebas de calidad',4,3,NULL,5,'2023-01-01 00:00:00.000000'),(1,9,'Diseño de interfaz',3,1,NULL,2,'2024-01-11 10:51:21.949011'),(2,1,'Revisión de código',1,1,NULL,2,'2023-01-01 00:00:00.000000'),(2,2,'Implementación de funciones',5,2,NULL,4,'2023-01-01 00:00:00.000000');
/*!40000 ALTER TABLE `tarea` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-13 13:08:31
