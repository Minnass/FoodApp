-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 13, 2023 at 04:00 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foodapp`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `dateIncrement` ()   BEGIN
	SET @i=30;
    WHILE @i>=1 DO
	SET @x=CONCAT("2022-12-",@i);
  	 SET @i=@i-1;
     SELECT @i;
    END WHILE;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `deliveryform`
--

CREATE TABLE `deliveryform` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `icon` text NOT NULL,
  `fee` int(11) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `deliveryform`
--

INSERT INTO `deliveryform` (`id`, `name`, `icon`, `fee`, `description`) VALUES
(1, 'Bình thường', 'https://cdn-icons-png.flaticon.com/512/8870/8870735.png', 10000, 'Thức ăn được giao tùy giao tùy thuộc vào thời gian vận chuyển. Có thể đến muộn hơn dự kiến từ 10-20 phút.'),
(2, 'Hỏa tốc', 'https://cdn-icons-png.flaticon.com/512/7114/7114627.png', 50000, 'Ngay khi có đơn đặt chúng tôi sẽ giao hàng ngay lập tức. Đơn hàng sẽ được vận chuyển đến bạn trong vòng (5-10 phút).');

-- --------------------------------------------------------

--
-- Table structure for table `monan`
--

CREATE TABLE `monan` (
  `id` int(11) NOT NULL,
  `foodname` varchar(100) NOT NULL,
  `image` text NOT NULL,
  `price` float NOT NULL,
  `discription` text NOT NULL,
  `category` text NOT NULL,
  `quantitysold` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  `eaterNumber` int(11) NOT NULL,
  `expiration` text NOT NULL,
  `preparationTime` text NOT NULL,
  `preservationGuide` text NOT NULL,
  `publishedDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `monan`
--

INSERT INTO `monan` (`id`, `foodname`, `image`, `price`, `discription`, `category`, `quantitysold`, `discount`, `eaterNumber`, `expiration`, `preparationTime`, `preservationGuide`, `publishedDate`) VALUES
(1, 'Crispy Chicken Sandwich', 'https://www.timeoutdoha.com/public/images/2019/09/04/Burger.jpg', 130000, 'McDonald\'s Crispy Chicken Sandwich là một loại bánh mì gà rán kiểu miền Nam giòn, ngon ngọt và mềm hoàn hảo. Nó đứng đầu với dưa chua cắt khoanh và phục vụ trên một cuộn khoai tây nướng bơ. Bánh mì gà giòn có 470 calo.', 'Tráng miệng', 1, 10, 1, '1 ngày', '10 phút', '- Bảo quản nơi khô ráo.\r\n- Tránh tiếp xúc ở nhiệt độ cao.', '2022-12-01'),
(5, 'Hủ tiếu Nam Vang', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045028-5_600x258.jpg', 50000, 'Có nguồn gốc từ “người hàng xóm” Campuchia, hủ tiếu Nam Vang cũng là một món ăn được lòng người Nam Bộ nhờ vị thanh của nước dùng, nguyên liệu hấp dẫn: tôm, gan heo, thịt heo xắt lát, tóp mỡ…', 'Phở bún cháo', 1, 10, 1, '5 giờ', '20 phút', '- Bảo quản nơi ở tủ mát.\r\n- Tránh tiếp xúc ở nhiệt độ cao.', '2022-12-06'),
(6, 'Bánh canh cua', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045016-4_600x292.jpg', 50000, 'Không ai có thể cưỡng lại được một tô bánh canh cua với những sợi bánh canh bột lọc dai dai, nước dùng “điểm xuyết” gạch cua đỏ tươi ngon mắt, một chút hành lá và ngò xanh, thêm một ít tiêu và vài lát ớt cho một chiều mưa bất chợt cả.', 'Phở bún cháo', 2, 0, 1, '5 giờ', '30 phút', '- Bảo quản nơi ở tủ mát.\r\n- Tránh tiếp xúc ở nhiệt độ cao.', '2022-12-07'),
(7, 'Mì hoành thánh	', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-044957-3_600x279.jpg', 50000, 'Mì hoành thánh xá xíu là một món ăn của người Hoa mà bí quyết của món này luôn nằm ở công thức nấu nước dùng. Những lát thịt xá xíu đỏ ngon mắt được xếp xen kẽ với mì và hoành thánh vàng ươm, hành hẹ xanh tươi mát, càng trở nên hấp dẫn hơn với vài viên tóp mỡ được chan lên trên.', 'Phở bún cháo', 0, 0, 1, '5 giờ', '30 phút', '- Bảo quản nơi ở tủ mát.\r\n- Tránh tiếp xúc ở nhiệt độ cao.', '2022-12-08'),
(8, 'Bún mắm miền Tây', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-044947-2_600x262.jpg', 50000, 'Với nhiều người, bún mắm là một món khó ăn vì mùi mắm quá nồng. Tuy nhiên, một khi đã thưởng thức thì thật khó mà “kiêng” món ăn dân dã này. Là sản phẩm của sự giao thoa văn hoá ẩm thực giữa người Khmer bản địa, người miền Trung và miền Nam khẩn hoang, bún mắm miền Tây có nguyên liệu là đặc sản của 3 vùng đất này: cá, tôm, mực, cua của biển miền Trung, thịt bò, heo và các loài rau đa dạng từ miền Tây và Campuchia. Nước dùng là nước lèo mắm chưng chính là công thức “thần kì” tạo nên sức hấp dẫn khó chối từ của bún mắm miền Tây.', 'Phở bún cháo', 1, 10, 0, '5 tiếng', '30 phút', '- Bảo quản nơi ở tủ mát.\r\n- Tránh tiếp xúc ở nhiệt độ cao.', '2022-12-09'),
(11, ' Bún riêu cua', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045057-7_600x273.jpg', 50000, 'Bún riêu là một món ăn có vị chua thanh, ăn mùa hè rất mát nên được người Việt Nam rất ưa thích. Có nhiều hàng quán bán bún riêu trên các đường phố, nên đây cũng là một trong những “ông hoàng đường phố” của Việt Nam.', 'Phở bún cháo', 0, 0, 0, '5 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-12'),
(12, 'Miến gà', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045121-9_600x283.jpg', 50000, 'So với nhiều món nước khác thì miến gà khá dễ làm và ngon. Nước dùng có vị ngọt thanh từ thịt và xương gà, mùi thơm thoang thoảng của hành phi, khi dùng chung với những sợi miến dai và trong suốt cộng thêm thịt gà có độ dai vừa phải sẽ mang đến cho bạn cảm giác ngất ngây khó tả, nhất là trong một buổi sáng trời mưa lành lạnh.', 'Phở bún cháo', 2, 0, 1, '5 tiếng.', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-13'),
(13, 'Bún sứa', 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045134-10_600x260.jpg', 50000, 'Bún sứa là món ăn trứ danh của đất Nha Trang du nhập vào Sài Gòn cách đây khoảng từ 3 – 4 năm và nhanh chóng chiếm được cảm tình của người dân ở đây. Cũng như các món ăn nước khác, bún sứa phải ăn nóng mới ngon. Thịt sứa sau khi được chế biến kĩ càng thì được xếp lên trên mặt bún rồi chan nước dùng lên. Đừng quên thêm một tí ớt dầu cho thật cay, cắn thêm một trái ớt hiểm thì càng ngon.', 'Phở bún cháo', 0, 0, 1, '5 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-14'),
(17, 'Nộm khô bò', 'https://cdn.tgdd.vn/Files/2021/08/24/1377372/20-mon-an-vat-hot-nhat-hien-nay-ma-ban-khong-nen-bo-qua-du-chi-1-mon-202108240918237786.jpg', 25000, 'Với vị ngọt nhẹ và giòn giòn của đu đủ kết hợp cùng vị dai dai của khô bò, thơm nồng mùi ớt cực hấp dẫn. Món nộm khô bò thường được các bạn trẻ thưởng thức vào những buổi chiều mát ở những quán lề đường, vừa ăn vừa trò chuyện với bạn bè quả thật là thú vị và tuyệt vời.', 'FastFood', 2, 0, 3, '1 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-18'),
(18, 'Cơm trộn Hàn Quốc', 'https://static1.bestie.vn/Mlog/ImageContent/201910/trua-nay-an-gi-da-co-danh-sach-15-mon-ngon-phu-hop-cho-ngay-ban-ron-c1fce0.jpg', 100000, 'Cơm trộn là món ăn truyền thống và cực kỳ nổi tiếng của Hàn Quốc. Món ăn hấp dẫn thực khách không chỉ nhờ vào cách trình bày đặc sắc, đẹp mắt mà còn giàu chất dinh dưỡng và hương vị đặc biệt.', 'Cơm', 1, 0, 0, '', '', '', '2022-12-20'),
(19, 'Bò nhúng mẻ', 'https://nhahangdalat.info/wp-content/uploads/2021/04/Cach-lam-lau-bap-bo-chanh-Leo-ngon-xuat-sac-11-mocviquan.vn_-600x399.jpg', 200000, 'Bò nhúng chanh dây Đà Lạt là món ăn được kết hợp hài hòa giữa vị béo ngọt của thịt bò và vị chua thanh đạm của chanh dây. Đảm bảo khi ăn bạn sẽ cảm nhận được sự khác lạ của bò hòa quyện với nước sốt chanh dây, nó chua chua lại ngọt ngọt công thêm beo béo của bò. Đây chắc hẳn sẽ là một món ăn vừa lạ miệng lại vừa đẹp mắt, khiến bạn khi ghé nhà hàng sẽ nhớ mãi không thể nào quên. ', 'Thịt, Cá', 1, 10, 0, '', '', '', '2022-12-21'),
(20, 'Lẩu gà nấu nấm Đà Lạt', 'https://nhahangdalat.info/wp-content/uploads/2021/04/yh-600x397.png', 260000, 'Lẩu gà nấm là món ăn được rất nhiều người yêu thích lựa chọn bởi hương vị thơm ngon hấp dẫn. Không chỉ vậy, món lẩu này còn chiếm được cảm tình của người ăn bởi giá trị dinh dưỡng cao rất tốt cho sức khỏe và rất phổ biến trong bữa cơm gia đình. ', 'Phở bún cháo', 1, 0, 2, '1 ngày', '45 phút', '- Bảo quản ngăn mát\r\n- Tránh tiếp xúc nhiệt độ cao', '2022-12-22'),
(21, 'Bò nhúng sốt tương Hàn Quốc', 'https://nhahangdalat.info/wp-content/uploads/2021/04/top-5-mon-an-cay-vo-doi-o-hue-du-khoc-van-muon-an-600x390.jpg', 450000, 'Nếu bạn là một phan hâm mộ của các vị tương nổi tiếng ở Hàn Quốc, thì chắc chắn bạn sẽ ưa thích món Bò nhúng sốt tương Hàn Quốc. Với sự hòa quyện của thịt bò ngọt dai dai hòa quyện với hương vị đặc trưng của nước sốt tương Hàn Quốc. ', 'Thịt, Cá', 2, 0, 2, '3 tiếng', '40 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-23'),
(22, 'Cơm thố', 'https://nhahangdalat.info/wp-content/uploads/2021/01/z2316883800839_6f14cfc93e84a0eff479b13c54879102-600x716.jpg', 30000, 'Điểm đặc trưng của món cơm thố là cơm được chiên lên và đảo đều sao cho ngấm gia vị và hơi cháy xém để tạo nên độ giòn cho phần cơm, tiếp đến phần ớt Đà Lạt được xào sơ để làm mất đi mùi hăng của ớt nhưng không làm ớt quá chín dễ mất đi độ giòn cùng với thịt bò hoặc hải sản được tẩm ướp thấm vị.\r\nTại nhà hàng có 2 món cơm thố là cơm thố bò và cơm thố hải sản. Được các đầu bếp chuyên nghiệp làm theo công thức độc đáo chỉ có tại nhà hàng Memory.\r\n', 'Cơm', 1, 0, 2, '2 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-24'),
(23, 'Gà nướng cơm lam Đà Lạt', 'https://nhahangdalat.info/wp-content/uploads/2017/09/ga-nuong-com-lam-dalat-nha-hang-memory-nhahangdalat.info-2.jpg', 400000, 'Gà nướng cơm lam Đà Lạt là món ăn đặc sắc dân dã của những vùng miền. Cũng là món ăn tiêu biểu của vùng núi cao Tây Bắc và Tây Nguyên. Tuy nhiên, ở mỗi vùng miền lại có một hương vị riêng của món gà nướng cơm lam. Nhưng khi bạn đến với thành phố ngàn hoa Đà Lạt thì bạn không thể bỏ lỡ món ngon tại nhà hàng Memory, món ăn khiến bao người mê say và thu hút rất nhiều du khách khi đến đây phải tìm đến để thưởng thức mà không thể bỏ qua.', 'Cơm', 1, 0, 0, '40 phút', '20 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-25'),
(24, 'Đùi cừu đút lò nướng than hồng', 'https://nhahangdalat.info/wp-content/uploads/2020/09/dui-cuu-dut-lo-nuong-than-hong-nha-hang-memory-nhahangdalat.info-06-600x450.jpg', 600000, 'Món đùi cừu đút lò nướng than hồng trứ danh của nhà hàng Memroy Đà Lạt', 'Thịt, Cá', 0, 0, 2, '1 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-26'),
(25, 'Phi lê cá hồi sốt chanh dây', 'https://nhahangdalat.info/wp-content/uploads/2017/09/phi-le-ca-Hoi-sot-chanh-day-nha-hang-Da-Lat-Memory-Mon-ngon-Da-Lat-06-600x450.jpg', 800000, 'Phi lê cá hồi sốt chanh dây là một trong các món ngon Đà Lạt mà bạn không thể bỏ lỡ khi đến với Đà Lạt và nhà hàng Memory\r\nLà một món ăn mang hương vị ẩm thực Châu Âu, Phi lê Cá hồi Nauy sốt chanh dây luôn là món ăn được nhiều thực khách lựa chọn chẳng những vì hương vị thơm ngon mà còn vì món ăn này rất giàu dinh dưỡng, đặc biệt tốt cho sức khoẻ của người già, trẻ em và cực kì, cực kì tốt cho nhan sắc của chị em phụ nữ. \r\n', 'Thịt, Cá', 0, 0, 2, '2 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-27'),
(26, 'Bò nướng nướng xiên rau củ', 'https://nhahangdalat.info/wp-content/uploads/2017/09/bo-nuong-xien-rau-cu-dalat-nhahangdalat.info-03.jpg', 400000, 'Món bò nướng xiên rau củ rất được ưa chuộng đặc biệt là buổi tối se lạnh của phố hoa. Việc quay quần bên bếp lửa, nâng ly rượu vang mà nhấp nháp món bò xiên rau củ nóng hổi thì đời còn gì bằng', 'Thịt, Cá', 0, 5, 2, '3 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-12-28'),
(27, 'Sườn cừu áp chảo sốt xạ hương', 'https://nhahangdalat.info/wp-content/uploads/2017/09/Suon-cuu-ap-chao-sot-xa-huong-Nha-hang-Memory-Da-Lat-Mon-ngon-Da-Lat-nhahangdalat.info-02-Copy-Copy-Copy-600x640.jpg', 500000, 'Sườn cừu áp chảo sốt xạ hương có màu vàng nâu hấp dẫn, phảng phất hương thơm đặc trưng của thịt cừu hòa cùng mùi vị đậm đà của sốt nướng. Đây là món ăn mang đến một hương vị ẩm thực phương Tây mới lạ và vô cùng tinh tế, xứng đáng là mỹ vị trong danh sách món trưa mỗi ngày.', 'Thịt, Cá', 1, 6, 1, '1 tiếng', '20 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-29'),
(28, 'Cà phê Cappuchino', 'https://nhahangdalat.info/wp-content/uploads/2021/02/118973656_1222850524753948_3287187351353121377_o.jpg', 60000, 'Cappuchino 1 loại thức uống cực kỳ phổ biến tại châu âu và đặc biệt là italy, sau này được du nhập vào Châu Á và hiện tại vietnam là 1 trong những nước tiêu thụ café đứng thứ 4 châu á về tổng số lượng.', 'Đồ uống', 0, 4, 2, '3 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-28'),
(29, 'Trà đào chanh sả hạt chia', 'https://www.bartender.edu.vn/wp-content/uploads/2015/09/tra-dao-tuoi.jpg', 40000, 'Đừng bao giờ bỏ lở món trà đà chanh sả hạt chia Memory siệu thơm ngon nhé. Đến Đà Lạt, đến Memory thì bật mí với các bạn đây là món phải thử', 'Đồ uống', 2, 5, 1, '30 phút', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-28'),
(30, 'Mực hấp nước dừa', 'https://nhahangdalat.info/wp-content/uploads/2017/09/sp18.jpg', 200000, 'Các loại gia vị: đường, rượu trắng, tiêu xay, dầu thực vật, bột súp', 'Thịt, Cá', 2, 2, 3, '2 tiếng', '45 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-28'),
(32, 'Bít tết Đà Lạt', 'https://nhahangdalat.info/wp-content/uploads/2017/09/Beefsteak-dalat-sot-tieu-den-nha-hang-memory-nhahangdalat.info-02-600x450.jpg', 200000, 'https://nhahangdalat.info/wp-content/uploads/2017/09/Beefsteak-dalat-sot-tieu-den-nha-hang-memory-nhahangdalat.info-02-600x450.jpg', 'Thịt, Cá', 2, 1, 3, '2 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-27'),
(34, 'Đậu hũ non 3 tầng', 'https://nhahangdalat.info/wp-content/uploads/2017/09/dau-hu-non-3-tang-nha-hang-memory-dalat-nhahangdalat.info-01.jpg', 20000, 'Đậu khuông chiên giòn là món ăn quen thuộc nhà nào cũng làm, ngon bổ lại rẻ. Nhưng bạn đã biết cách làm được món đậu bóng giòn y như ngoài hàng chưa? Thông thường khi nấu cơm hàng ngày, đậu rán rất dễ bị ỉu và kém giòn, trông không đẹp mắt và ăn cũng không “đã” chút nào.\r\nĐến với Memory, bạn sẽ có cơ hội thưởng thức món ăn mới lạ bắt mắt và vô cùng hấp dẫn chính là món đậu hũ non 3 tầng đặc biệt.\r\n', 'Tráng miệng', 0, 0, 1, '3 tiếng', '30 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-27'),
(35, 'Sườn thăn bê Úc', 'https://nhahangdalat.info/wp-content/uploads/2017/09/sp15.jpg', 200000, 'Sườn thăn bê là phần thịt mềm, có nhiều vân mỡ đan xen giúp thớ thịt mịn, rất béo và thơm khi chế biến. Với sườn bê có thể chế biến được nhiều món ăn hấp dẫn khác nhau từ hầm, nướng đến áp chảo…', 'Thịt, Cá', 2, 2, 2, '3 tiếng', '50 phút', '- Tránh ở nhiệt độ cao.\r\n- Dùng ngay sẽ ngon hơn.', '2022-11-07'),
(37, 'Sữa đậu nành', 'https://cdn.dayphache.edu.vn/wp-content/uploads/2019/07/sua-dau-nanh.jpg', 10000, 'Sữa đậu nành từ lâu đã là thức uống bổ dưỡng, vị ngon dễ uống rất được ưa thích. Thường xuyên sử dụng sữa đậu nành sẽ cung cấp cho bạn nhiều dinh dưỡng tốt cho cơ thể.', 'Đồ uống', 2, 0, 1, '1 ngày', '10 phút', '- Uống lạnh sẽ ngon hơn.\r\n- Khui ra dùng liền.', '2022-11-07'),
(38, 'Cá viên chiên giòn', 'https://images.foody.vn/res/g11/108596/prof/s280x175/foody-mobile-m5nl9eo2-jpg-230-635791151540036510.jpg', 30000, 'Là một món ăn không thể thiếu dành cho giới trẻ, Cá viên chiên đã trở thành thương hiệu món ăn nổi tiếng khắp đường phố Việt Nam.', 'FastFood', 2, 2, 3, '1 ngày', '40 phút', '- Tránh ở nhiệt độ cao\r\n- Dùng nóng sẽ ngon hơn', '2022-11-17'),
(39, 'Chả giò xốp tôm cua', 'https://sagrifood.com.vn/wp-content/uploads/2021/08/CHA-GIO-XOP-TOM-CUA.-600x400.jpg', 100000, 'Với sức hấp dẫn của món ăn, món được chế biến với nguyên liệu sẵn có tại cửa hàng, nhằm mang lại trải nghiệm cho người dùng.', 'FastFood', 2, 2, 1, '1 ngày', '30 phút', '- Ăn nóng sẽ ngon hơn.\r\n- Bảo quản ở nhiệt độ thấp.', '2022-11-17'),
(40, 'CAM VÀNG NHẬP KHẨU', 'https://finefoodvn.com/datafiles/31836/upload/files/cam%20vang%201.jpg', 80000, 'Cam Vàng Ai Cập là dòng cam nhập khẩu ngon nhất thế giới hiện nay, có màu sắc vàng tươi, quả to, có mùi thơm đặc trưng, vị ngọt xen chút chua nhẹ. Cam vàng Ai Cập rất giàu vitamin A, B, C Canxi, Magnesium, Sắt, Đồng, Iốt… rất tốt cho cơ thể đặc biệt người già và trẻ nhỏ để tăng sức đề kháng.', 'Tráng miệng', 2, 1, 1, '30 ngày', '5 phút', '- Bảo quản ở ngăn mát', '2022-11-12'),
(41, 'Lẩu Thái', 'https://saigonamthuc.vn/wp-content/uploads/2022/09/mon-thai-ngon-o-sai-gon3.jpg', 200000, 'Lẩu Thái là món ăn Thái Lan được biết đến đông đảo nhất vì du nhập vào Việt Nam khá lâu. Hầu như bất cứ nhà hàng lẩu nào đều có loại lẩu này cho bạn lựa chọn. Tuy nhiên, để có thể có lẩu Thái chuẩn vị ngon nhất thì không phải nhà hàng nào cũng có thể làm được. ', 'Phở bún cháo', 1, 10, 3, '5 giờ', '30 phút', '- Bảo quản ở ngăn mát\r\n', '2022-11-12'),
(42, 'Kem, chè', 'https://saigonamthuc.vn/wp-content/uploads/2021/02/che-hue.jpg', 20000, 'Nói Sài Gòn là thiên đường của kem và vương quốc chè không sai, bởi không nơi đâu, bạn có thể bước ra “thế giới kem” như vùng đất này với hàng loạt cửa hàng, thương hiệu kem đến từ Pháp, Mỹ, Newzeland, Nhật… Mỗi loại kem lại có vị, độ ngọt, mềm khác nhau. Song song thế giới kem, vương quốc chè tại đây cũng đa dạng với chè Mỹ, chè Thái, chè Nhật, chè Campuchia.', 'Tráng miệng', 2, 0, 1, '1 ngày', '30 phút', 'Bao quản ở ngăn mát', '2022-11-12'),
(43, 'Cơm tấm', 'https://saigonamthuc.vn/wp-content/uploads/2021/02/com-tam-1.jpg', 50000, 'Là một trong những món ăn đóng mác “chỉ có ở Sài Gòn”, cơm tấm với những hạt cơm gãy (do được nấu từ tấm), miếng sườn nướng cháy cạnh, chả bì và nước mắm pha lạt luôn có sức hút kỳ lạ với thực khách Sài Gòn mà cả những du khách ghé qua.', 'Cơm', 10, 0, 1, '2 giờ', '30 phút', '', '2022-11-16'),
(44, 'Bột chiên', 'https://saigonamthuc.vn/wp-content/uploads/2021/02/bot-chien.jpg', 20000, 'Bột chiên có thể ăn bất kỳ lúc nào trong ngày nhưng “chén” vào buổi tối là hợp nhất. Một đĩa bột chiên ngon phải hội tụ đủ các điều kiện như miếng bột ngoài giòn tan nhưng bên trong thơm mềm, vị béo của trứng, thanh thanh của đu đủ bào mỏng ngâm dấm, mặn nhẹ của nước tương và gia vị.', 'FastFood', 2, 0, 1, '1 ngày', '30 phút', '- Bảo quản ở ngăn mát', '2022-11-16'),
(45, 'Sủi cảo', 'https://saigonamthuc.vn/wp-content/uploads/2021/02/sui-cao.jpg', 20000, 'Là một món ăn đặc trưng của Hoa, mì há cảo thu hút ẩm khách với miếng sủi cảo vỏ mềm mịn, vị tôm thịt hòa quyện thơm ngon, nước lèo trong veo, ngọt thanh vị xương ống. Bạn có thể lựa chọn một tô sủi cảo thập cẩm với da heo, mực, bong bóng cá hay há cảo để no bụng. Bên cạnh há cảo nước, món há cảo chiên giòn rụm, béo ngậy cũng là một lựa chọn đầy thú vị.', 'FastFood', 2, 0, 1, '1 ngày', '30 phút', '- Bảo quản ở ngăn mát', '2022-11-16'),
(46, 'Xiêng nướng', 'https://saigonamthuc.vn/wp-content/uploads/2021/02/xien-nuong.jpg', 30000, 'Không phải ngồi trong những quán nhậu xô bồ, không bị bức bí trong trong không gian máy lạnh, bạn được lựa chọn bất kỳ chiếc bàn thấp nào trong không gian rộng rãi vỉa hè, vừa nhâm nhi những xiên thịt do mình tự nướng, vừa trò chuyện cùng bạn bè với chi phí cực thấp. Đây là những điểm cộng khiến lẩu nướng – xiên que thật sự chiếm được cảm tình của giới trẻ Sài Gòn.', 'FastFood', 2, 10, 1, '1 ngày', '45 phút', '- Bảo quản ở ngăn mát', '2022-11-20'),
(47, 'Bánh tráng trộn', 'https://saigonamthuc.vn/wp-content/uploads/2021/02/banh-trang-tron.jpg', 20000, 'Một bịch bánh tráng “chuẩn” gồm bánh tráng trộn, dầu sa tế, mỡ hành, hành phi, ruốc, đậu phộng, trứng cút, khô bò, xoài, muối tôm, tấc và rau răm, có giá thành từ 10.000 – 15.000 đồng.\r\n\r\nHiện nay, ngoài bánh tráng trộn đã xuất hiện thêm hai người “họ hàng” cũng hấp dẫn không kém là bánh tráng cuốn và bánh tráng nướng.', 'FastFood', 2, 5, 1, '1 ngày', '20 phút', '- Bảo quản ở ngăn mát.', '2022-11-21'),
(48, 'Toco Toco', 'https://img.jamja.vn/jamja-prod/screen-shot-2020-10-20-at-150605.png?cache=1', 50000, 'Điều đầu tiên làm nên sự yếu mến của các bạn trẻ với Toco có lẽ chính là hương vị. Trà sữa Toco có hương vị rất đặc trưng, với cái ngọt ngào của sữa, cái thơm...', 'Đồ uống', 2, 10, 1, '1 ngày', '30 phút', '- Bảo quản ở ngăn mát', '2022-11-21'),
(51, 'Bánh bao chiên', 'https://images.foody.vn/res/g91/909462/s400x400/30b8cd80-4b59-4618-a9d3-6c2c0ff797e0.jpg', 50000, 'Bánh bao có hương vị hòa quyện cùng với các thành phần dinh dưỡng luôn là sự lựa chọn hàng đầu cho mọi người', 'Tráng miệng', 5, 0, 1, '1 ngày', '20 phút', '- Bảo quản ở ngăn mát', '2022-12-29'),
(53, 'Bún Bò Huế', '52.jpg', 50000, 'Được làm từ nước dùng đâm chất miền Trung, mang lại hương vị ngọt', 'Bún', 0, 0, 1, '3 Giờ', '30phút', 'Tránh tiếp xúc nhiệt độ cao', '2023-01-12');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `orderID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `orderCode` text NOT NULL,
  `totalPrice` float NOT NULL,
  `orderDate` datetime NOT NULL,
  `receivedUser` text NOT NULL,
  `phoneNumber` text NOT NULL,
  `address` text NOT NULL,
  `saleCode` int(11) NOT NULL,
  `deliveryType` text NOT NULL,
  `paymentType` text NOT NULL,
  `deliveryFee` int(11) NOT NULL,
  `checked` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`orderID`, `userID`, `orderCode`, `totalPrice`, `orderDate`, `receivedUser`, `phoneNumber`, `address`, `saleCode`, `deliveryType`, `paymentType`, `deliveryFee`, `checked`) VALUES
(25, 1, 'a6dc30ec-45b7-4fca-9d3b-e75e3540bdee-0', 217000, '2023-09-10 00:00:57', 'phần nhật triều', '9837383737338', 'Phú An', 10000, 'Hỏa tốc', 'Tiền mặt', 50000, 0),
(26, 1, '970968cd-f33c-417d-a836-585e03c4cd94-1', 144000, '2023-01-14 07:12:38', 'phần nhật triều', '9837383737338', 'Phú An', 10000, 'Hỏa tốc', 'Tiền mặt', 50000, 1),
(27, 1, '09fd3f53-1671-4383-8d37-c2d2c7550747-2', 200000, '2023-01-15 00:14:47', 'mình anh', '0987272772', 'quảng bình', 0, 'Bình thường', 'Tiền mặt', 10000, 1),
(28, 1, 'f88b31df-be6d-4a67-aca0-263da8644206-0', 135000, '2023-01-15 00:25:00', 'phan nhật triều', '0488484838', 'phú an', 0, 'Bình thường', 'Tiền mặt', 10000, 1),
(29, 1, '6a44b987-a17a-42b4-8ec9-1c812f92c4da-0', 180000, '2023-01-13 00:45:25', 'mình anh', '087373383', 'quảng bình', 0, 'Bình thường', 'Tiền mặt', 10000, 1),
(30, 1, '6992f459-d71f-49c1-bb33-53efe1ae90de-1', 175000, '2023-01-13 00:45:34', 'mình anh', '087373383', 'quảng bình', 0, 'Bình thường', 'Tiền mặt', 10000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `id` int(11) NOT NULL,
  `orderCode` text NOT NULL,
  `foodName` text NOT NULL,
  `quantity` float NOT NULL,
  `Price` float NOT NULL,
  `discount` int(11) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orderdetail`
--

INSERT INTO `orderdetail` (`id`, `orderCode`, `foodName`, `quantity`, `Price`, `discount`, `image`) VALUES
(34, 'a6dc30ec-45b7-4fca-9d3b-e75e3540bdee-0', ' Bún riêu cua', 2, 50000, 0, 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045057-7_600x273.jpg'),
(35, 'a6dc30ec-45b7-4fca-9d3b-e75e3540bdee-0', 'Crispy Chicken Sandwich', 1, 130000, 10, 'https://www.timeoutdoha.com/public/images/2019/09/04/Burger.jpg'),
(36, '970968cd-f33c-417d-a836-585e03c4cd94-1', 'Xiêng nướng', 2, 30000, 10, 'https://saigonamthuc.vn/wp-content/uploads/2021/02/xien-nuong.jpg'),
(37, '970968cd-f33c-417d-a836-585e03c4cd94-1', 'Hủ tiếu Nam Vang', 2, 50000, 10, 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045028-5_600x258.jpg'),
(38, '09fd3f53-1671-4383-8d37-c2d2c7550747-2', ' Bún riêu cua', 4, 50000, 0, 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045057-7_600x273.jpg'),
(39, 'f88b31df-be6d-4a67-aca0-263da8644206-0', 'Hủ tiếu Nam Vang', 3, 50000, 10, 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045028-5_600x258.jpg'),
(40, '6a44b987-a17a-42b4-8ec9-1c812f92c4da-0', 'Lẩu Thái', 1, 200000, 10, 'https://saigonamthuc.vn/wp-content/uploads/2022/09/mon-thai-ngon-o-sai-gon3.jpg'),
(41, '6992f459-d71f-49c1-bb33-53efe1ae90de-1', 'Miến gà', 2, 50000, 0, 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045121-9_600x283.jpg'),
(42, '6992f459-d71f-49c1-bb33-53efe1ae90de-1', 'Bún sứa', 1, 50000, 0, 'https://s1.img.yan.vn//YanNews/2167221/201507/20150701-045134-10_600x260.jpg'),
(43, '6992f459-d71f-49c1-bb33-53efe1ae90de-1', 'Nộm khô bò', 1, 25000, 0, 'https://cdn.tgdd.vn/Files/2021/08/24/1377372/20-mon-an-vat-hot-nhat-hien-nay-ma-ban-khong-nen-bo-qua-du-chi-1-mon-202108240918237786.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `salecode`
--

CREATE TABLE `salecode` (
  `id` int(11) NOT NULL,
  `code` text NOT NULL,
  `codeName` text NOT NULL,
  `discountValue` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `salecode`
--

INSERT INTO `salecode` (`id`, `code`, `codeName`, `discountValue`) VALUES
(1, 'MKSDAADDFAFFS', 'Mã giảm giá 10.000 đồng', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `userName` text NOT NULL,
  `passWord` text NOT NULL,
  `email` text NOT NULL,
  `name` text NOT NULL,
  `dateofbirth` text NOT NULL,
  `sex` text NOT NULL,
  `address` text NOT NULL,
  `loginType` text NOT NULL,
  `role` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `userName`, `passWord`, `email`, `name`, `dateofbirth`, `sex`, `address`, `loginType`, `role`) VALUES
(1, 'nhattrieu1507', 'nhattrieu', 'phannhattrieu012@gmail.com', 'Phan Nhật Triều', '2002-07-15', 'Nam', 'Phú An, Phú Vang, Thừa Thiên huế', 'normal', 'admin'),
(8, 'nhattrieu123', '63c03b811dc2b', 'phannhattrieu01234@gmail.com', 'Phần NhậT Triều', '12/01/2023', 'Nam', 'Huế', 'normal', 'user'),
(9, '', '', 'phanvantoan3376@gmail.com', 'Phần Văn Toàn', '12/01/2023', 'nam', 'huế', 'google', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `deliveryform`
--
ALTER TABLE `deliveryform`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `monan`
--
ALTER TABLE `monan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`orderID`);

--
-- Indexes for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salecode`
--
ALTER TABLE `salecode`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `deliveryform`
--
ALTER TABLE `deliveryform`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `monan`
--
ALTER TABLE `monan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `orderdetail`
--
ALTER TABLE `orderdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `salecode`
--
ALTER TABLE `salecode`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
