-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 31 May 2025, 10:35:22
-- Sunucu sürümü: 10.4.28-MariaDB
-- PHP Sürümü: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `moodtracker`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `moods`
--

CREATE TABLE `moods` (
  `id` int(11) NOT NULL,
  `mood` varchar(50) NOT NULL,
  `note` text DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `moods`
--

INSERT INTO `moods` (`id`, `mood`, `note`, `timestamp`) VALUES
(1, 'Happy', 'Test', '2025-05-26 23:06:55'),
(2, 'Sad', 'Test', '2025-05-26 23:07:52'),
(3, 'Sad', 'Sad', '2025-05-26 23:11:44'),
(4, 'Sad', 'Sad', '2025-05-26 23:11:55');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `moods`
--
ALTER TABLE `moods`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `moods`
--
ALTER TABLE `moods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
