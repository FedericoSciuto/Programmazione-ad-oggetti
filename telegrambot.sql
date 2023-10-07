-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Apr 15, 2023 alle 11:45
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `telegrambot`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `azioni`
--

CREATE TABLE `azioni` (
  `Id` int(11) NOT NULL,
  `Nome` varchar(10) NOT NULL,
  `Id_Dispositivo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `chat`
--

CREATE TABLE `chat` (
  `Id` varchar(20) NOT NULL,
  `Id_Casa` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `dispositivi`
--

CREATE TABLE `dispositivi` (
  `Id` int(11) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  `Stato` varchar(10) NOT NULL,
  `Id_Stanza` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `home`
--

CREATE TABLE `home` (
  `Id` int(11) NOT NULL,
  `Nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `stanze`
--

CREATE TABLE `stanze` (
  `Id` int(11) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Id_Casa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `azioni`
--
ALTER TABLE `azioni`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Nome` (`Nome`,`Id_Dispositivo`),
  ADD KEY `Id_Dispositivo` (`Id_Dispositivo`);

--
-- Indici per le tabelle `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Id_Casa` (`Id_Casa`);

--
-- Indici per le tabelle `dispositivi`
--
ALTER TABLE `dispositivi`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Nome` (`Nome`,`Id_Stanza`),
  ADD KEY `Id_Stanza` (`Id_Stanza`);

--
-- Indici per le tabelle `home`
--
ALTER TABLE `home`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Nome` (`Nome`);

--
-- Indici per le tabelle `stanze`
--
ALTER TABLE `stanze`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Nome` (`Nome`,`Id_Casa`),
  ADD KEY `Id_Casa` (`Id_Casa`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `azioni`
--
ALTER TABLE `azioni`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `dispositivi`
--
ALTER TABLE `dispositivi`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `home`
--
ALTER TABLE `home`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `stanze`
--
ALTER TABLE `stanze`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `azioni`
--
ALTER TABLE `azioni`
  ADD CONSTRAINT `azioni_ibfk_1` FOREIGN KEY (`Id_Dispositivo`) REFERENCES `dispositivi` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`Id_Casa`) REFERENCES `home` (`Id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `dispositivi`
--
ALTER TABLE `dispositivi`
  ADD CONSTRAINT `dispositivi_ibfk_1` FOREIGN KEY (`Id_Stanza`) REFERENCES `stanze` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `stanze`
--
ALTER TABLE `stanze`
  ADD CONSTRAINT `stanze_ibfk_1` FOREIGN KEY (`Id_Casa`) REFERENCES `home` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
