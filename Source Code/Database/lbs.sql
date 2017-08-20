-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 15, 2014 at 08:19 PM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lbs`
--

-- --------------------------------------------------------

--
-- Table structure for table `checkin`
--

CREATE TABLE IF NOT EXISTS `checkin` (
  `checkinId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(15) NOT NULL,
  `placeId` varchar(2) NOT NULL,
  `ulat` double NOT NULL,
  `ulong` double NOT NULL,
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`checkinId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=51 ;

--
-- Dumping data for table `checkin`
--

INSERT INTO `checkin` (`checkinId`, `userId`, `placeId`, `ulat`, `ulong`, `ctime`) VALUES
(42, 'jx', '1', 24.771, 67.099, '2014-05-15 16:28:58'),
(43, 'osman', '2', 24.8766, 67.063, '2014-05-15 16:31:02'),
(44, 'osman', '2', 24.8766, 67.063, '2014-05-15 16:34:18'),
(45, 'jx', '2', 24.8766, 67.063, '2014-05-15 16:34:32'),
(46, 'jx', '2', 24.8766, 67.063, '2014-05-15 16:34:53'),
(47, 'jx', '2', 24.8766, 67.063, '2014-05-15 16:34:58'),
(48, 'jx', '3', 24.75704, 67.095866, '2014-05-15 17:38:02'),
(49, 'jx', '2', 24.8766, 67.06268, '2014-05-15 19:05:26'),
(50, 'osman', '2', 24.8766, 67.062, '2014-05-15 19:06:30');

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE IF NOT EXISTS `place` (
  `pId` int(2) NOT NULL,
  `pName` varchar(30) NOT NULL,
  `pLat` double NOT NULL,
  `pLong` double NOT NULL,
  PRIMARY KEY (`pId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `place`
--

INSERT INTO `place` (`pId`, `pName`, `pLat`, `pLong`) VALUES
(1, 'DHA Golf Club', 24.7717335, 67.0998575),
(2, 'Dolmen Mall Tariq Road', 24.8766065, 67.0626819),
(3, 'Kolachi', 24.7570425, 67.0958665),
(4, 'Espresso', 24.8016955, 67.0302915),
(5, 'BAR BQ Tonight', 24.8157771, 67.0216012),
(6, 'Gloria Jeans', 24.8614622, 67.0099387),
(7, 'The Forum', 24.8785663, 67.0640988),
(8, 'Ginsoy Extreme', 24.8614622, 67.0099387),
(9, 'Sattar Buksh', 24.8666081, 67.0646173),
(10, 'Southend Club', 24.7941877, 67.0544453),
(11, 'Ocean Tower', 24.8239128, 67.0358475),
(12, 'Fast Nuces Karachi', 24.8569755, 67.2643035);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userId` varchar(15) NOT NULL,
  `password` varchar(10) NOT NULL,
  `lastLogin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` int(11) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `password`, `lastLogin`, `isActive`) VALUES
('jx', 'jx1', '2014-05-15 19:08:40', 0),
('osman', 'abc123', '2014-05-15 19:07:04', 0),
('Sony ST23i', '1234', '2014-05-15 08:14:02', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
