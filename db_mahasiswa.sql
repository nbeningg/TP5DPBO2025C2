-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Mar 2025 pada 06.35
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_mahasiswa`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `prodi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `jenis_kelamin`, `prodi`) VALUES
(1, '2303558', 'Dewi Yanti', 'Perempuan', 'Pendidikan Ilmu Komputer'),
(2, '2311697', 'Dinda Natania Sugara', 'Perempuan', 'Pendidikan Ilmu Komputer'),
(3, '2308372', 'Muhammad Ridho Fajri', 'Laki-laki', 'Pendidikan Ilmu Komputer'),
(4, '2305056', 'Syifa Nur Azizah Suhud', 'Perempuan', 'Pendidikan Ilmu Komputer'),
(5, '2305551', 'Taufiq Nurrohman', 'Laki-laki', 'Pendidikan Ilmu Komputer'),
(6, '2300498', 'Alvyn Hadrian Nugraha', 'Laki-laki', 'Pendidikan Ilmu Komputer'),
(7, '2309131', 'Alya Nurul Hanifah', 'Perempuan', 'Pendidikan Ilmu Komputer'),
(8, '2300408', 'Cynthia Hasna Mazaya', 'Perempuan', 'Pendidikan Ilmu Komputer'),
(9, '2300134', 'Samsul Maarif Aripin', 'Laki-laki', 'Pendidikan Ilmu Komputer'),
(10, '2301333', 'Zaenal Rifa\'i Saepudin', 'Laki-laki', 'Pendidikan Ilmu Komputer'),
(11, '2308138', 'Alifa Salsabila', 'Perempuan', 'Ilmu Komputer'),
(12, '2311399', 'Faisal Nur Qolbi', 'Laki-laki', 'Ilmu Komputer'),
(13, '2304017', 'Naeya Adeani Putri', 'Perempuan', 'Ilmu Komputer'),
(14, '2308355', 'Raffi Adzril Alfaiz', 'Laki-laki', 'Ilmu Komputer'),
(15, '2309209', 'Safira Aliyah Azmi', 'Perempuan', 'Ilmu Komputer'),
(16, '2300492', 'Ahmad Izzuddin Azzam', 'Laki-laki', 'Ilmu Komputer'),
(17, '2300484', 'Julian Dwi Satrio', 'Laki-laki', 'Ilmu Komputer'),
(18, '2312120', 'Natasha Adinda Cantika', 'Perempuan', 'Ilmu Komputer'),
(19, '2305309', 'Rasendriya Andhika', 'Laki-laki', 'Ilmu Komputer'),
(20, '2305274', 'Zakiyah Hasanah', 'Perempuan', 'Ilmu Komputer'),
(26, '2301410', 'Nuansa Bening Aura Jelita', 'Perempuan', 'Ilmu Komputer');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
