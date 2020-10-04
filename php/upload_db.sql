-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 04, 2020 at 07:55 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `upload_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `media`
--

CREATE TABLE `media` (
  `id` int(128) NOT NULL,
  `file_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `media`
--

INSERT INTO `media` (`id`, `file_path`) VALUES
(1, 'image/skitch.png'),
(2, 'http://192.168.0.103/imgupload/image/image/skitch.png'),
(3, 'http://192.168.0.103/imgupload/image/WP_20150712_00s5.jpg'),
(4, 'http://192.168.0.103/imgupload/image/konbert-export-8ab02d0991f44.sql'),
(5, 'http://192.168.0.103/imgupload/image/Second Screening_Android Developer Recruitment Test.pdf'),
(6, 'http://192.168.0.103/imgupload/image/TUTORIAL Flash Bios VGA No Display (RX550 Pulse).mp4'),
(7, 'http://192.168.0.103/imgupload/image/TUTORIAL Flash Bios VGA No Display (RX550 Pulse).mp4'),
(8, 'http://192.168.0.103/imgupload/image/TUTORIAL Flash Bios VGA No Display (RX550 Pulse).mp4'),
(9, 'http://192.168.0.103/imgupload/image/image (1).jpeg'),
(10, 'http://192.168.0.103/imgupload/image/download.png'),
(11, 'http://192.168.0.103/imgupload/image/TUTORIAL Flash Bios VGA No Display (RX550 Pulse).mp4'),
(12, 'http://192.168.0.103/imgupload/image/TUTORIAL Flash Bios VGA No Display (RX550 Pulse).mp4');

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `id` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `photo` varchar(511) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `name`, `email`, `photo`) VALUES
(1, 'itsme2', 'itsme2@mail.com', 'http://192.168.0.103/imgupload/image/1.pdf'),
(2, 'rrr', 'rr@r.com', 'http://192.168.0.103/imgupload/image/IMG_rrr.jpg'),
(3, 'itsme2', 'itsme2@mail.com', 'http://192.168.0.103/imgupload/image/3.pdf'),
(4, 'itsme2', 'itsme2@mail.com', 'http://192.168.0.103/imgupload/image/4.zip'),
(5, 'itsme2', 'itsme2@mail.com', 'http://192.168.0.103/imgupload/image/IMG_itsme2.pdf'),
(6, 'itsme2', 'itsme2@mail.com', 'image/IMG_itsme2.pdf'),
(7, 'itsme2', 'itsme2@mail.com', 'image/IMG_itsme2.jpeg'),
(8, 'any', 'any@gmail.com', 'http://192.168.0.103/imgupload/image/8.jpg'),
(9, 'any', 'any@gmail.com', 'http://192.168.0.103/imgupload/image/9.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
