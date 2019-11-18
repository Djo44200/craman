-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le :  jeu. 14 nov. 2019 à 10:04
-- Version du serveur :  8.0.18
-- Version de PHP :  7.3.11-1~deb10u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `project_CRA`
--
CREATE DATABASE IF NOT EXISTS `project_CRA` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `project_CRA`;

-- --------------------------------------------------------

--
-- Structure de la table `Projects`
--

CREATE TABLE IF NOT EXISTS `Projects` (
  `idProjects` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`idProjects`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Projects`
--

INSERT INTO `Projects` (`idProjects`, `name`) VALUES
(1, 'Activity Report'),
(2, 'Public holiday'),
(3, 'Holidays'),
(4, 'RTT'),
(5, 'Diseases'),
(6, 'Training'),
(7, 'Meetings'),
(8, 'Internal organization'),
(9, 'DP'),
(10, 'Tools'),
(11, 'ISO'),
(12, 'Penalty'),
(13, 'SEE'),
(14, 'RD'),
(29, 'Planning Meeting'),
(30, 'Team Management'),
(31, 'Meeting'),
(32, 'Sales support'),
(33, 'RT/Maintenance Monitor QOS Server'),
(34, 'RT/Maintenance Monitor QOS GUI'),
(35, 'RT/Maintenance Expand Platform'),
(36, 'RT/Maintenance Secure QOS'),
(37, 'RT/Maintenance OptimRAN'),
(38, 'Vx Datastore / Vx Engine'),
(39, 'RT/Maintenance Vision SLA'),
(40, 'RT/Maintenance Vision ROAM'),
(41, 'RT/Maintenance Vision CARE'),
(42, 'RT/Maintenance Value DATA'),
(43, 'DEVOps - Clients'),
(44, 'GUI - Cartography Engine'),
(48, 'RTs / Maintenance'),
(51, 'Merge'),
(52, 'CFL'),
(53, 'Scanner'),
(54, 'O2875'),
(55, 'Voice Call Setup Time'),
(56, 'EIRENE criterion without odometer'),
(57, 'Https'),
(58, 'Certification CE UP_004'),
(59, 'Fault management'),
(60, 'PESQ'),
(61, 'GUI Attended'),
(62, 'Module Carto real time'),
(63, 'Release 2018 Q4'),
(64, 'DME'),
(65, '4G Service'),
(66, 'Senario triggered by GPS'),
(67, 'Industrialization UP_004'),
(68, 'LandSide redundancy'),
(69, 'Test Cypress'),
(70, 'NetWork Congestion Manager'),
(71, 'Idle Cell Reselection'),
(72, 'QUG'),
(73, 'Release 2019 Q2'),
(79, 'Refactor'),
(80, 'Jenkins'),
(82, 'Abis GPRS'),
(83, 'Export RecAlert for eRec'),
(84, 'Reporting 15 min'),
(85, 'Poc Migration JS'),
(86, 'JS Node Migration'),
(87, 'JS Calltrace Migration'),
(88, 'New ETCS variables'),
(89, 'HO RBC'),
(90, 'QATS installer'),
(91, 'CT2/CT3 train_registration'),
(92, 'CallTrace Migration Vue'),
(93, 'DDL2'),
(94, 'API CT2 CT3'),
(95, 'Merge Equipment Callflow'),
(96, 'Signalling : m_error and trip features'),
(97, 'Configurable Calltrace'),
(98, 'QUG'),
(101, 'TV - User Improvement / Tools'),
(102, 'TV - Benchmark'),
(103, 'TV - Documentation'),
(104, 'TV - Maintenance'),
(105, 'TV - Testing'),
(106, 'Thesis'),
(107, 'Support Egypt'),
(108, 'Support SEA'),
(109, 'Support DME'),
(110, 'DME UP / ref Analytics'),
(111, 'GLPI KPI'),
(112, 'GSM-R Simulator'),
(113, 'KPI-forecasting'),
(114, 'Devops RW'),
(115, 'Devops MNO'),
(116, 'Devops DT'),
(117, 'NEMOWAI'),
(119, 'Expandium Generic Probe'),
(120, 'Database Architecture'),
(121, 'Database Integration'),
(122, 'TV - Parallel and asynchronous system search'),
(123, 'TV - Automatic grouping on lookup field'),
(124, 'TV - Asynchronous and synchronous API'),
(125, 'TV - Lookups improvements');

-- --------------------------------------------------------

--
-- Structure de la table `Projects_has_Teams`
--

CREATE TABLE IF NOT EXISTS `Projects_has_Teams` (
  `Projects_idProjects` int(11) NOT NULL,
  `Teams_idTeams` int(11) NOT NULL,
  `Teams_name` varchar(255) NOT NULL,
  PRIMARY KEY (`Projects_idProjects`,`Teams_idTeams`,`Teams_name`),
  KEY `fk_Projects_has_Teams_Teams1_idx` (`Teams_idTeams`,`Teams_name`),
  KEY `fk_Projects_has_Teams_Projects1_idx` (`Projects_idProjects`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Projects_has_Teams`
--

INSERT INTO `Projects_has_Teams` (`Projects_idProjects`, `Teams_idTeams`, `Teams_name`) VALUES
(1, 1, 'DT'),
(2, 6, 'ABS'),
(3, 6, 'ABS'),
(4, 6, 'ABS'),
(5, 6, 'ABS'),
(6, 3, 'UP'),
(6, 6, 'ABS'),
(8, 5, 'EXP'),
(9, 5, 'EXP'),
(10, 5, 'EXP'),
(11, 5, 'EXP'),
(12, 5, 'EXP'),
(13, 5, 'EXP'),
(14, 7, 'RD'),
(29, 2, 'MNO'),
(29, 3, 'UP'),
(29, 4, 'RW'),
(30, 2, 'MNO'),
(30, 3, 'UP'),
(30, 4, 'RW'),
(31, 2, 'MNO'),
(31, 3, 'UP'),
(31, 4, 'RW'),
(32, 1, 'DT'),
(32, 2, 'MNO'),
(32, 3, 'UP'),
(32, 4, 'RW'),
(33, 2, 'MNO'),
(34, 2, 'MNO'),
(35, 2, 'MNO'),
(36, 2, 'MNO'),
(37, 2, 'MNO'),
(38, 2, 'MNO'),
(39, 2, 'MNO'),
(40, 2, 'MNO'),
(41, 2, 'MNO'),
(42, 2, 'MNO'),
(43, 2, 'MNO'),
(44, 2, 'MNO'),
(48, 3, 'UP'),
(48, 4, 'RW'),
(51, 3, 'UP'),
(52, 3, 'UP'),
(53, 3, 'UP'),
(54, 3, 'UP'),
(55, 3, 'UP'),
(56, 3, 'UP'),
(57, 3, 'UP'),
(58, 3, 'UP'),
(59, 3, 'UP'),
(60, 3, 'UP'),
(61, 3, 'UP'),
(62, 3, 'UP'),
(63, 3, 'UP'),
(63, 4, 'RW'),
(64, 3, 'UP'),
(65, 3, 'UP'),
(66, 3, 'UP'),
(67, 3, 'UP'),
(68, 3, 'UP'),
(69, 3, 'UP'),
(70, 3, 'UP'),
(71, 3, 'UP'),
(72, 3, 'UP'),
(73, 3, 'UP'),
(73, 4, 'RW'),
(79, 4, 'RW'),
(80, 4, 'RW'),
(82, 4, 'RW'),
(83, 4, 'RW'),
(84, 4, 'RW'),
(85, 4, 'RW'),
(86, 4, 'RW'),
(87, 4, 'RW'),
(88, 4, 'RW'),
(89, 4, 'RW'),
(90, 4, 'RW'),
(91, 4, 'RW'),
(92, 4, 'RW'),
(93, 4, 'RW'),
(94, 4, 'RW'),
(95, 4, 'RW'),
(96, 4, 'RW'),
(97, 4, 'RW'),
(98, 4, 'RW'),
(101, 1, 'DT'),
(102, 1, 'DT'),
(103, 1, 'DT'),
(104, 1, 'DT'),
(105, 1, 'DT'),
(106, 1, 'DT'),
(107, 1, 'DT'),
(108, 1, 'DT'),
(109, 1, 'DT'),
(110, 1, 'DT'),
(111, 1, 'DT'),
(112, 1, 'DT'),
(113, 1, 'DT'),
(114, 1, 'DT'),
(115, 1, 'DT'),
(116, 1, 'DT'),
(117, 1, 'DT'),
(119, 1, 'DT'),
(120, 1, 'DT'),
(121, 1, 'DT'),
(122, 1, 'DT'),
(123, 1, 'DT'),
(124, 1, 'DT'),
(125, 1, 'DT');

-- --------------------------------------------------------

--
-- Structure de la table `Records`
--

CREATE TABLE IF NOT EXISTS `Records` (
  `idRecords` int(11) NOT NULL AUTO_INCREMENT,
  `quarters` float NOT NULL,
  `date` date NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `Users_idUsers` int(11) NOT NULL,
  `Projects_idProjects` int(11) NOT NULL,
  PRIMARY KEY (`idRecords`),
  UNIQUE KEY `Record` (`date`,`Users_idUsers`,`Projects_idProjects`),
  KEY `fk_Records_Users_idx` (`Users_idUsers`) USING BTREE,
  KEY `fk_Records_Projects1_idx` (`Projects_idProjects`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Teams`
--

CREATE TABLE IF NOT EXISTS `Teams` (
  `idTeams` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`idTeams`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Teams`
--

INSERT INTO `Teams` (`idTeams`, `name`) VALUES
(1, 'DT'),
(2, 'MNO'),
(3, 'UP'),
(4, 'RW'),
(5, 'EXP'),
(6, 'ABS'),
(7, 'RD');

-- --------------------------------------------------------

--
-- Structure de la table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `idUsers` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  PRIMARY KEY (`idUsers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Projects_has_Teams`
--
ALTER TABLE `Projects_has_Teams`
  ADD CONSTRAINT `fk_Projects_has_Teams_Projects1` FOREIGN KEY (`Projects_idProjects`) REFERENCES `Projects` (`idProjects`),
  ADD CONSTRAINT `fk_Projects_has_Teams_Teams1` FOREIGN KEY (`Teams_idTeams`,`Teams_name`) REFERENCES `Teams` (`idTeams`, `name`);

--
-- Contraintes pour la table `Records`
--
ALTER TABLE `Records`
  ADD CONSTRAINT `fk_Records_Projects1` FOREIGN KEY (`Projects_idProjects`) REFERENCES `Projects` (`idProjects`),
  ADD CONSTRAINT `fk_Records_Users` FOREIGN KEY (`Users_idUsers`) REFERENCES `Users` (`idUsers`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
