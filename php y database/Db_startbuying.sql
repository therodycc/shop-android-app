-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 28, 2020 at 04:17 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id15395402_startbuying`
--

-- --------------------------------------------------------

--
-- Table structure for table `categoryProduct`
--

CREATE TABLE `categoryProduct` (
  `id_category` int(11) NOT NULL,
  `name_category` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description_category` text COLLATE utf8_unicode_ci NOT NULL,
  `id_company` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `categoryProduct`
--

INSERT INTO `categoryProduct` (`id_category`, `name_category`, `description_category`, `id_company`) VALUES
(11, 'Carniceria', 'Cerdos\nVaca\nRojiza', 5),
(12, 'Categorias primeros productos', 'prueba', 6),
(13, 'New2', 'prueba numerous 3', 6),
(14, 'ok', '12', 6),
(16, 'category_editadsa', 'this test', 5),
(19, 'base', 'para todos', 6),
(22, 'add product castegory', 'ssssss', 5),
(24, 'Tennis', 'Addidas', 7),
(28, 'Laptop12', 'Adomicilio', 5),
(29, 'Amor y Paz', 'Funcionalidades paciencia', 5),
(31, 'Zapatos para niños', 'zapatos importados desde China.', 9),
(33, 'carne', 'carne', 10),
(34, 'Pasta', 'Parmesano', 10),
(35, 'Ropa para hombres', '-ropa de hombres', 11);

-- --------------------------------------------------------

--
-- Table structure for table `companyUser`
--

CREATE TABLE `companyUser` (
  `id_company` int(11) NOT NULL,
  `image_company` text COLLATE utf8_unicode_ci NOT NULL,
  `name_company` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `social_company` text COLLATE utf8_unicode_ci NOT NULL,
  `phone_company` varchar(17) COLLATE utf8_unicode_ci NOT NULL,
  `country_company` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `addres_company` text COLLATE utf8_unicode_ci NOT NULL,
  `status_company` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `fk_id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `companyUser`
--

INSERT INTO `companyUser` (`id_company`, `image_company`, `name_company`, `social_company`, `phone_company`, `country_company`, `addres_company`, `status_company`, `fk_id_usuario`) VALUES
(5, '229823807photo1605459235.jpeg', 'Company', 'SocialNetwork', '(465) 223-2133', 'Antigua y Barbuda', 'ct.Soy nacio y creia, sector: villa mella, st.B/#12', 'true', 11),
(6, '248784306photo1605459983.jpeg', 'Dominicana Live', 'www.ok.com', '(809) 222-2311', 'Dominica', 'ct.Puerto Plata, sector: caoba, st.22/#221', 'true', 11),
(7, '1054838125photo1605467924.jpeg', 'Tennis Ad', 'wwwwwwwwwnetwok', '(111) 111-1111', 'Argentina', 'ct.kilo, sector: acan, st.poe/#2', 'true', 11),
(9, 'prueba', 'esto es una prueba', 'www.prueba.com', '', '', 'calle villa maria', 'true', 11),
(10, '833035820photo1606502900.jpeg', 'Test Finish', 'www.test.finish.com', '(899) 111-1222', 'Alemania', 'ct.Chinatown, sector: Tiradente, st.01st/#1009', 'true', 11),
(11, '1392191007photo1606578355.jpeg', 'Hollister', 'www.hollister.com', '(809) 234-5986', 'Estado Unidos', 'ct.Santo Domingo, sector: Vista Bella, st.20/#1', 'true', 18);

-- --------------------------------------------------------

--
-- Table structure for table `detalleOrder_consumer`
--

CREATE TABLE `detalleOrder_consumer` (
  `fk_id_product` int(11) NOT NULL,
  `fk_id_order` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(13,2) NOT NULL,
  `quantify` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `detalleOrder_consumer`
--

INSERT INTO `detalleOrder_consumer` (`fk_id_product`, `fk_id_order`, `price`, `quantify`) VALUES
(3, 'shwb1isdn', 230.00, 1),
(9, 'shwb1isdn', 200.00, 1),
(8, 'shwb1isdn', 320.00, 1),
(9, 'H0FCrwdGbn9u', 200.00, 1),
(8, 'H0FCrwdGbn9u', 320.00, 1),
(3, 'v7dANIar0sln', 250.00, 1),
(20, 'v7yVuxdTpo7Q', 370.00, 1),
(22, 'Duk9XfnEdyWJ', 2000.00, 1),
(21, 'Duk9XfnEdyWJ', 1800.00, 1);

-- --------------------------------------------------------

--
-- Table structure for table `orderUser`
--

CREATE TABLE `orderUser` (
  `id_orders` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `order_date` date NOT NULL DEFAULT current_timestamp(),
  `order_status` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'pending',
  `order_comment` text COLLATE utf8_unicode_ci NOT NULL,
  `order_subTotal` decimal(13,2) NOT NULL,
  `fk_id_user` int(11) NOT NULL,
  `fk_id_company` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orderUser`
--

INSERT INTO `orderUser` (`id_orders`, `order_date`, `order_status`, `order_comment`, `order_subTotal`, `fk_id_user`, `fk_id_company`) VALUES
('Duk9XfnEdyWJ', '2020-11-28', 'paid', 'very good product, I would like to try them ...', 3800.00, 16, 11),
('H0FCrwdGbn9u', '2020-11-22', 'paid', 'very good product, I would like to try them ...', 520.00, 16, 5),
('shwb1isdn', '2020-11-22', 'paid', 'me gustaria probar tus productos algún dia', 12000.00, 16, 5),
('v7dANIar0sln', '2020-11-25', 'paid', 'very good product, I would like to try them ...', 250.00, 16, 6),
('v7yVuxdTpo7Q', '2020-11-27', 'pending', 'Me gustan tus productos, me gustaria, probarlo love ants posibles', 370.00, 16, 10);

-- --------------------------------------------------------

--
-- Table structure for table `Producto`
--

CREATE TABLE `Producto` (
  `id_product` int(11) NOT NULL,
  `name_product` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `image_product` text COLLATE utf8_unicode_ci NOT NULL,
  `descripcion_product` text COLLATE utf8_unicode_ci NOT NULL,
  `price_product` decimal(10,0) NOT NULL,
  `fk_id_category` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Producto`
--

INSERT INTO `Producto` (`id_product`, `name_product`, `image_product`, `descripcion_product`, `price_product`, `fk_id_category`) VALUES
(3, ' primer_product', '1719409599photo1605573099.jpeg', 'uno de Los mejor productos', 250, 12),
(8, 'name', 'image', 'si ', 320, 16),
(9, 'Cerdo azado', '2006867120photo1605812997.jpeg', 'Barato you rico\nend nutrientes\n*favorite', 200, 11),
(10, ' CEL', '1518227152photo1605805942.jpeg', 'd', 100, 22),
(11, ' produt edit', '1355825593photo1605811223.jpeg', 'productos prueba edit\nand delete', 220, 16),
(12, ' tennis', '1438669615photo1606326154.jpeg', 'tela', 450, 24),
(13, ' tennis', '55059124photo1606326154.jpeg', 'tela', 450, 24),
(16, 'VideoFuncionamiento33', '661905246photo1606342374.jpeg', 'video de funcionamiento de last app', 200, 28),
(18, 'Adiddas Crosh 24', '540663287photo1606497841.jpeg', 'BIOGATEO\nExterior: serraje\nPlantilla extraíble de piel antibacterias\nForro: piel microperforada\nTirador trasero\nEstabilizador lateral patentado Natural Motion: mejora su equilibrio\nExtra ligereza\nMovimiento natural\nHíper-ventilación\nGran resistencia\nMulti-flexibilidad\nConfeccionado en materiales naturales: sin níquel ni cromo', 3870, 31),
(19, ' Jordan', '1880212831photo1606501225.jpeg', '20\ntenis', 220, 31),
(20, 'Carne Molida', '1839041130photo1606503050.jpeg', '-carne molida 23kg', 370, 34),
(21, ' Poloche Hollister Rojo', '649781381photo1606578505.jpeg', 'de tell fina', 1800, 35),
(22, 'Poloche blancos XL', '520883665photo1606578557.jpeg', 'importados desde china', 2000, 35);

-- --------------------------------------------------------

--
-- Table structure for table `typeUser`
--

CREATE TABLE `typeUser` (
  `id_type` int(11) NOT NULL,
  `name_type` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `typeUser`
--

INSERT INTO `typeUser` (`id_type`, `name_type`) VALUES
(1, 'Supplier'),
(2, 'Consumer');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `name_user` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `password_user` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `firtName_user` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `lastName_user` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `address_user` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `postalCode_user` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `type_user` int(11) NOT NULL,
  `status_user` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `phone_user` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `email_usuario` varchar(40) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `name_user`, `password_user`, `firtName_user`, `lastName_user`, `address_user`, `postalCode_user`, `type_user`, `status_user`, `phone_user`, `email_usuario`) VALUES
(11, 'superman23', 'ZXLG648V', 'Juan', 'Perez', 'Republica Dominicana, Santo Domingo Norte; Vista Bella, st. B /#6', '20111', 1, 'true', '+1 829-232-8099', 'dalvingamer@gmail.com'),
(12, 'Batman', '321', 'Esmil', 'Paredes', 'Inglaterra, Londres; Los Alcarrizo, st. Ot /#22', '20111', 1, 'true', '+1 809-565-2333', 'dmolinapeguero@gmail.com'),
(16, 'esme', '123', 'Esmeralda', 'Tavarez', 'Mexico, ciudad de mexico; lormino, st. 23 /#1', '22222', 2, 'true', '+1 809-556-2133', 'esmeralda@gmail.com'),
(17, 'asadas', '1234', 'Dalvindsdasddaasdda', 'Molina', 'd\ndasdad, sd; d, st. asd /#32', '3', 1, 'true', '+1 332-377-3293', 'das'),
(18, 'estudiante', '12345', 'itla', 'estudiante', 'Rep. Dom, Santo Domingo; Villa Mella, st. Vista Bella /#1', '20111', 1, 'true', '+1 809-652-3211', 'itlaprueba@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categoryProduct`
--
ALTER TABLE `categoryProduct`
  ADD PRIMARY KEY (`id_category`),
  ADD KEY `fk_company_category` (`id_company`);

--
-- Indexes for table `companyUser`
--
ALTER TABLE `companyUser`
  ADD PRIMARY KEY (`id_company`),
  ADD KEY `fk_idUser_company` (`fk_id_usuario`);

--
-- Indexes for table `detalleOrder_consumer`
--
ALTER TABLE `detalleOrder_consumer`
  ADD KEY `detalleorder_idorder` (`fk_id_order`),
  ADD KEY `detalleorder_idproduct` (`fk_id_product`);

--
-- Indexes for table `orderUser`
--
ALTER TABLE `orderUser`
  ADD PRIMARY KEY (`id_orders`),
  ADD KEY `order_iduser` (`fk_id_user`),
  ADD KEY `order_idcompany` (`fk_id_company`);

--
-- Indexes for table `Producto`
--
ALTER TABLE `Producto`
  ADD PRIMARY KEY (`id_product`),
  ADD KEY `fk_category_product` (`fk_id_category`);

--
-- Indexes for table `typeUser`
--
ALTER TABLE `typeUser`
  ADD PRIMARY KEY (`id_type`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD KEY `typeuser_idtype` (`type_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categoryProduct`
--
ALTER TABLE `categoryProduct`
  MODIFY `id_category` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `companyUser`
--
ALTER TABLE `companyUser`
  MODIFY `id_company` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `Producto`
--
ALTER TABLE `Producto`
  MODIFY `id_product` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `typeUser`
--
ALTER TABLE `typeUser`
  MODIFY `id_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `categoryProduct`
--
ALTER TABLE `categoryProduct`
  ADD CONSTRAINT `fk_company_category` FOREIGN KEY (`id_company`) REFERENCES `companyUser` (`id_company`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `companyUser`
--
ALTER TABLE `companyUser`
  ADD CONSTRAINT `fk_idUser_company` FOREIGN KEY (`fk_id_usuario`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detalleOrder_consumer`
--
ALTER TABLE `detalleOrder_consumer`
  ADD CONSTRAINT `detalleorder_idorder` FOREIGN KEY (`fk_id_order`) REFERENCES `orderUser` (`id_orders`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detalleorder_idproduct` FOREIGN KEY (`fk_id_product`) REFERENCES `Producto` (`id_product`) ON UPDATE CASCADE;

--
-- Constraints for table `orderUser`
--
ALTER TABLE `orderUser`
  ADD CONSTRAINT `order_idcompany` FOREIGN KEY (`fk_id_company`) REFERENCES `companyUser` (`id_company`),
  ADD CONSTRAINT `order_iduser` FOREIGN KEY (`fk_id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE;

--
-- Constraints for table `Producto`
--
ALTER TABLE `Producto`
  ADD CONSTRAINT `fk_category_product` FOREIGN KEY (`fk_id_category`) REFERENCES `categoryProduct` (`id_category`) ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `typeuser_idtype` FOREIGN KEY (`type_user`) REFERENCES `typeUser` (`id_type`) ON UPDATE CASCADE;
COMMIT;

