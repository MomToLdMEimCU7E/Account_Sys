/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : account

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 09/04/2023 15:02:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `uid` int(0) NULL DEFAULT NULL,
  `account_type_id` int(0) NULL DEFAULT NULL COMMENT '账户类型id',
  `account_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户名称',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------

-- ----------------------------
-- Table structure for account_details
-- ----------------------------
DROP TABLE IF EXISTS `account_details`;
CREATE TABLE `account_details`  (
  `account_detail_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账户内容id',
  `account_detail_type_id` int(0) NULL DEFAULT NULL COMMENT '账户内容类型id',
  `account_id` int(0) NULL DEFAULT NULL COMMENT '账户id',
  `uid` int(0) NULL DEFAULT NULL,
  `balance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '余额',
  `budget` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '预算',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`account_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_details
-- ----------------------------
INSERT INTO `account_details` VALUES (1, 2, 333, 1, '640', '50', '无');
INSERT INTO `account_details` VALUES (2, 1, 3222, 1, '50', '5', '无');
INSERT INTO `account_details` VALUES (3, 4, 777, 1, '20', '23', '你好');

-- ----------------------------
-- Table structure for account_details_type
-- ----------------------------
DROP TABLE IF EXISTS `account_details_type`;
CREATE TABLE `account_details_type`  (
  `account_detail_type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账户内容类型id',
  `account_detail_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`account_detail_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_details_type
-- ----------------------------
INSERT INTO `account_details_type` VALUES (1, '现金', 'icon-xianjin');
INSERT INTO `account_details_type` VALUES (2, '储蓄卡', 'icon-xinyongka1');
INSERT INTO `account_details_type` VALUES (3, '微信钱包', 'icon-weixin');
INSERT INTO `account_details_type` VALUES (4, '支付宝', 'icon-zhifubao');
INSERT INTO `account_details_type` VALUES (5, '信用卡', 'icon-xinyongka');
INSERT INTO `account_details_type` VALUES (6, '花呗', 'icon-huabei');
INSERT INTO `account_details_type` VALUES (7, '股票', 'icon-gupiao');
INSERT INTO `account_details_type` VALUES (8, '基金', 'icon-gupiao');
INSERT INTO `account_details_type` VALUES (9, '饭卡', 'icon-shouye');
INSERT INTO `account_details_type` VALUES (10, '公交卡', 'icon-gongjiaoka');
INSERT INTO `account_details_type` VALUES (11, '会员卡', 'icon-huiyuanka');
INSERT INTO `account_details_type` VALUES (12, '借入', 'icon-jiekuan');
INSERT INTO `account_details_type` VALUES (13, '借出', 'icon-jiechu');
INSERT INTO `account_details_type` VALUES (14, '其他', 'plus');

-- ----------------------------
-- Table structure for account_month_record
-- ----------------------------
DROP TABLE IF EXISTS `account_month_record`;
CREATE TABLE `account_month_record`  (
  `account_month_id` int(0) NOT NULL COMMENT '账户每月数据记录的id',
  `account_id` int(0) NULL DEFAULT NULL COMMENT '账户id',
  `uid` int(0) NULL DEFAULT NULL,
  `month` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '月份',
  `net_assets` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '净资产',
  `assets` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资产',
  `debt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负债',
  PRIMARY KEY (`account_month_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_month_record
-- ----------------------------

-- ----------------------------
-- Table structure for account_type
-- ----------------------------
DROP TABLE IF EXISTS `account_type`;
CREATE TABLE `account_type`  (
  `account_type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账户类型id',
  `account_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户类型名称',
  `account_details_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拥有的内容类型id 1-2-3',
  PRIMARY KEY (`account_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_type
-- ----------------------------
INSERT INTO `account_type` VALUES (1, '普通账户', '1-2-3-4-14');
INSERT INTO `account_type` VALUES (2, '信用账户', '5-6-14');
INSERT INTO `account_type` VALUES (3, '投资账户', '7-8-14');
INSERT INTO `account_type` VALUES (4, '充值账户', '9-10-11-14');
INSERT INTO `account_type` VALUES (5, '其他账户', '12-13');

-- ----------------------------
-- Table structure for basic_funds
-- ----------------------------
DROP TABLE IF EXISTS `basic_funds`;
CREATE TABLE `basic_funds`  (
  `fund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '款项id BO表示基础支出 BI表示基础收入',
  `fund_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '款项名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`fund_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of basic_funds
-- ----------------------------
INSERT INTO `basic_funds` VALUES ('BI17', '工资', 'icon-gongzi');
INSERT INTO `basic_funds` VALUES ('BI18', '红包', 'icon-hongbao');
INSERT INTO `basic_funds` VALUES ('BI19', '礼金', 'icon-lijin');
INSERT INTO `basic_funds` VALUES ('BI20', '租金', 'icon-fangwuzujin');
INSERT INTO `basic_funds` VALUES ('BI21', '分红', 'icon-bangong');
INSERT INTO `basic_funds` VALUES ('BI22', '理财', 'icon-gupiao');
INSERT INTO `basic_funds` VALUES ('BI23', '年终奖', 'icon-_xianjin');
INSERT INTO `basic_funds` VALUES ('BI24', '借入', 'icon-jiekuan');
INSERT INTO `basic_funds` VALUES ('BI25', '收款', 'icon-qiandai');
INSERT INTO `basic_funds` VALUES ('BI26', '生活费', 'icon-xinyongka1');
INSERT INTO `basic_funds` VALUES ('BI27', '其它', 'icon-shenglvehao');
INSERT INTO `basic_funds` VALUES ('BO1', '餐饮', 'icon-canyin1');
INSERT INTO `basic_funds` VALUES ('BO10', '通讯', 'icon-weibiaoti-');
INSERT INTO `basic_funds` VALUES ('BO11', '服饰', 'icon-clothes');
INSERT INTO `basic_funds` VALUES ('BO12', '快递', 'icon-kuaidi');
INSERT INTO `basic_funds` VALUES ('BO13', '家庭', 'icon-jiatingguanxi');
INSERT INTO `basic_funds` VALUES ('BO14', '社交', 'icon-shejiao');
INSERT INTO `basic_funds` VALUES ('BO15', '旅行', 'icon-lvhang');
INSERT INTO `basic_funds` VALUES ('BO16', '住房', 'icon-zhufang');
INSERT INTO `basic_funds` VALUES ('BO2', '购物', 'icon-gouwu');
INSERT INTO `basic_funds` VALUES ('BO3', '日用', 'icon-daily-necessities');
INSERT INTO `basic_funds` VALUES ('BO4', '交通', 'icon-jiaotong');
INSERT INTO `basic_funds` VALUES ('BO5', '蔬菜', 'icon-shucai');
INSERT INTO `basic_funds` VALUES ('BO6', '水果', 'icon-test');
INSERT INTO `basic_funds` VALUES ('BO7', '零食', 'snacks');
INSERT INTO `basic_funds` VALUES ('BO8', '运动', 'icon-yundong');
INSERT INTO `basic_funds` VALUES ('BO9', '娱乐', 'icon-yule');

-- ----------------------------
-- Table structure for bookkeeping
-- ----------------------------
DROP TABLE IF EXISTS `bookkeeping`;
CREATE TABLE `bookkeeping`  (
  `bookkeeping_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账本id',
  `uid` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `bookkeeping_type_id` int(0) NULL DEFAULT NULL COMMENT '账本类型id',
  `bookkeeping_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账本封面',
  `bookkeeping_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账本名称',
  `customed_funds_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义款项id',
  `extra_member1` int(0) NULL DEFAULT NULL COMMENT '额外用户1，使用uid，用于家庭账本',
  `extra_member2` int(0) NULL DEFAULT NULL COMMENT '额外用户2，使用uid，用于家庭账本',
  PRIMARY KEY (`bookkeeping_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookkeeping
-- ----------------------------
INSERT INTO `bookkeeping` VALUES (1, 1, 1, '红包', '我的账簿1', 'CO1', 2, 3);
INSERT INTO `bookkeeping` VALUES (2, 1, 3, '红色', '我的账簿2', NULL, 2, NULL);
INSERT INTO `bookkeeping` VALUES (3, 1, 2, '红包', '我的账簿3', 'CO1', 2, 3);
INSERT INTO `bookkeeping` VALUES (6, 1, 4, '天空', '我的账簿4', NULL, NULL, 2);
INSERT INTO `bookkeeping` VALUES (7, 1, 5, '大海', '我的账簿5', NULL, 2, 3);
INSERT INTO `bookkeeping` VALUES (8, 2, 6, '大海', '我的账簿1', NULL, 2, 3);
INSERT INTO `bookkeeping` VALUES (9, 2, 7, '大海', '我的账簿2', NULL, NULL, 2);
INSERT INTO `bookkeeping` VALUES (11, 2, 8, '天空', '我的账簿3', NULL, NULL, 2);

-- ----------------------------
-- Table structure for bookkeeping_tpye
-- ----------------------------
DROP TABLE IF EXISTS `bookkeeping_tpye`;
CREATE TABLE `bookkeeping_tpye`  (
  `bookkeeping_type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '账本类型id',
  `bookkeeping_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账本类型名称',
  `bookkeeping_type_funds_id` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拥有的款项类型id，用-间隔，例如1-2-3',
  PRIMARY KEY (`bookkeeping_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookkeeping_tpye
-- ----------------------------
INSERT INTO `bookkeeping_tpye` VALUES (1, '日常开销', 'BO1-BO2-BO3-BO4-BO5-BO6-BO7-BO8-BO9-BO10-BO11-BO12-BO13-BI17-BI18-BI19');
INSERT INTO `bookkeeping_tpye` VALUES (2, '人情往来', 'BO8-BO9-BO10-BO11-BO12-BO13-BO14-BO15-BO16-BI25-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (3, '家庭账本', 'BO3-BO4-BO5-BO6-BO7-BI17-BI23-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (4, '人情往来', 'BO3-BO4-BO5-BO6-BO9-BI17-BI23-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (5, '好好学习', 'BO3-BO4-BO5-BO6-BO8-BI17-BI23-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (6, '发财', 'BO8-BO9-BO10-BO11-BO12-BO13-BO14-BO15-BO16-BI25-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (7, '发财', 'BO3-BO4-BO5-BO6-BO8-BI17-BI23-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (8, '发财', 'BO3-BO4-BO5-BO6-BO8-BI17-BI23-BI26-BI27');

-- ----------------------------
-- Table structure for budget
-- ----------------------------
DROP TABLE IF EXISTS `budget`;
CREATE TABLE `budget`  (
  `budget_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '预算id',
  `bookkeeping_id` int(0) NULL DEFAULT NULL COMMENT '对应账簿id',
  `jan` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '一月预算',
  `feb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '二月预算',
  `mar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '三月预算',
  `apr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '四月预算',
  `may` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '五月预算',
  `jun` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '六月预算',
  `jul` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '七月预算',
  `aug` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '八月预算',
  `sept` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '九月预算',
  `oct` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '十月预算',
  `nov` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '十一月预算',
  `dec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '十二月预算',
  PRIMARY KEY (`budget_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of budget
-- ----------------------------
INSERT INTO `budget` VALUES (1, 1, '3500', '1000', '1000', '2000', '3000', '1500', '6000', '5000', '9000', '7000', '1200', '3100');
INSERT INTO `budget` VALUES (2, 2, '1000', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (3, 3, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (4, 6, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (5, 7, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (6, 8, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (7, 9, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (9, 11, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for customed_funds
-- ----------------------------
DROP TABLE IF EXISTS `customed_funds`;
CREATE TABLE `customed_funds`  (
  `customed_fund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '自定义款项id CO表示自定义支出 CI表示自定义收入',
  `uid` int(0) NOT NULL,
  `customed_fund_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bookkeeping_type_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`customed_fund_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customed_funds
-- ----------------------------
INSERT INTO `customed_funds` VALUES ('CI1', 1, '遗产', 1);
INSERT INTO `customed_funds` VALUES ('CI182fb2efbfc611ed8be0002b67dd5ef6', 1, '遗产', 2);
INSERT INTO `customed_funds` VALUES ('CI20356aedd41f11ed8ca2002b67dd5ef6', 2, '基金', 6);
INSERT INTO `customed_funds` VALUES ('CI2035a928d41f11ed8ca2002b67dd5ef6', 2, '股票', 6);
INSERT INTO `customed_funds` VALUES ('CIc0fba969c0a011ed8be0002b67dd5ef6', 1, '基金', 2);
INSERT INTO `customed_funds` VALUES ('CIc0fbeb74c0a011ed8be0002b67dd5ef6', 1, '股票', 2);
INSERT INTO `customed_funds` VALUES ('CO1', 1, '彩票', 2);
INSERT INTO `customed_funds` VALUES ('CO1a0bebddc08111ed8be0002b67dd5ef6', 1, '你好', 1);
INSERT INTO `customed_funds` VALUES ('CO6b4ac9e0d42511ed8ca2002b67dd5ef6', 2, '虾米', 6);
INSERT INTO `customed_funds` VALUES ('CO6b4c94e3d42511ed8ca2002b67dd5ef6', 2, '鱼', 6);
INSERT INTO `customed_funds` VALUES ('CO803640a6cef411ed8ca2002b67dd5ef6', 1, '旅游', 2);
INSERT INTO `customed_funds` VALUES ('CO80367338cef411ed8ca2002b67dd5ef6', 1, '游戏', 2);
INSERT INTO `customed_funds` VALUES ('CO8711ad93bfc811ed8be0002b67dd5ef6', 1, '测试', 1);

-- ----------------------------
-- Table structure for income
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income`  (
  `income_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `uid` int(0) NULL DEFAULT NULL,
  `bookkeeping_id` int(0) NULL DEFAULT NULL COMMENT '记入账本名称',
  `account_detail_id` int(0) NULL DEFAULT NULL,
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '金额 单位：元',
  `time` timestamp(0) NULL DEFAULT NULL COMMENT '时间',
  `fund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '款项id 若是自定义款项则此项空',
  `customed_fund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义款项类型id  若是系统自带款项则此项空',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `enclosure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件',
  PRIMARY KEY (`income_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of income
-- ----------------------------
INSERT INTO `income` VALUES (1, 1, 1, 1, '100', '2023-04-03 09:37:23', 'BI17', NULL, '无', '无');
INSERT INTO `income` VALUES (2, 1, 3, 1, '400', '2023-01-09 13:25:30', 'BI17', '', '无', '无');
INSERT INTO `income` VALUES (3, 1, 3, 1, '420', '2023-01-09 13:25:30', 'BI17', '', '无', '无');
INSERT INTO `income` VALUES (4, 1, 1, 1, '100', '2023-04-04 09:37:23', 'BI18', NULL, '无', '无');
INSERT INTO `income` VALUES (5, 1, 1, 1, '160', '2023-04-05 10:50:08', 'BI19', NULL, '无', '无');
INSERT INTO `income` VALUES (6, 1, 1, 1, '130', '2023-04-05 12:50:45', 'BI19', NULL, '无', '无');
INSERT INTO `income` VALUES (7, 1, 1, 1, '600', '2023-04-10 14:57:37', 'BI19', NULL, '无', '无');
INSERT INTO `income` VALUES (8, 1, 1, 1, '300', '2023-05-01 15:07:06', 'BI19', NULL, '无', '无');
INSERT INTO `income` VALUES (9, 1, 1, 1, '300', '2022-03-17 15:20:49', 'BI18', NULL, '无', '无');
INSERT INTO `income` VALUES (10, 1, 1, 1, '200', '2023-04-04 19:18:10', NULL, 'CI1', '无', '无');
INSERT INTO `income` VALUES (11, 1, 1, 1, '400', '2023-01-09 13:25:30', 'BI17', '', '无', '无');
INSERT INTO `income` VALUES (12, 1, 1, 1, '200', '2023-01-20 13:25:30', 'BI19', '', '无', '无');

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `payment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `uid` int(0) NULL DEFAULT NULL,
  `bookkeeping_id` int(0) NULL DEFAULT NULL COMMENT '记入账本名称',
  `account_detail_id` int(0) NULL DEFAULT NULL,
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '金额 单位：元',
  `time` timestamp(0) NULL DEFAULT NULL COMMENT '时间',
  `fund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '款项id 若是自定义款项则此项空',
  `customed_fund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义款项类型id  若是系统自带款项则此项空',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `enclosure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件',
  PRIMARY KEY (`payment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payment
-- ----------------------------
INSERT INTO `payment` VALUES (1, 1, 1, 1, '100', '2002-11-14 09:37:23', 'BO1', NULL, '无', '无');
INSERT INTO `payment` VALUES (2, 1, 1, 1, '200', '2023-03-18 12:27:20', 'BO2', '', '无', '无');
INSERT INTO `payment` VALUES (3, 1, 2, 1, '300', '2023-01-01 13:25:30', 'BO3', '', '无', '无');
INSERT INTO `payment` VALUES (4, 1, 3, 1, '400', '2023-01-09 13:25:30', 'BO4', '', '无', '无');
INSERT INTO `payment` VALUES (5, 1, 3, 1, '450', '2023-01-09 13:25:30', 'BO4', '', '无', '无');
INSERT INTO `payment` VALUES (6, 1, 1, 1, '100', '2002-11-14 09:37:23', 'BO5', NULL, '无', '无');
INSERT INTO `payment` VALUES (7, 1, 1, 1, '500', '2023-04-03 20:24:04', 'BO5', NULL, '无', '无');
INSERT INTO `payment` VALUES (8, 1, 1, 1, '300', '2023-04-06 20:24:51', 'BO3', NULL, '无', '无');
INSERT INTO `payment` VALUES (9, 1, 1, 1, '100', '2023-04-08 20:28:05', 'BO1', NULL, '无', '无');
INSERT INTO `payment` VALUES (10, 1, 1, 1, '300', '2023-05-10 20:28:24', '', 'CO1a0bebddc08111ed8be0002b67dd5ef6', '无', '无');
INSERT INTO `payment` VALUES (11, 1, 1, 1, '100', '2023-04-03 20:33:39', 'BO5', NULL, '无', '无');
INSERT INTO `payment` VALUES (12, 1, 1, 1, '400', '2023-01-09 13:25:30', 'BO4', '', '无', '无');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `schedule_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '日程id',
  `date` datetime(0) NULL DEFAULT NULL COMMENT '日程时间',
  `uid` int(0) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地点',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`schedule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (1, '2023-03-20 00:00:00', 1, '刚刚', '杭州', 'test');
INSERT INTO `schedule` VALUES (2, '2023-04-02 00:00:00', 1, '231', '南京', NULL);
INSERT INTO `schedule` VALUES (3, '2023-04-02 00:00:00', 1, 'aqours', '浦之星', NULL);
INSERT INTO `schedule` VALUES (4, '2023-04-02 19:22:49', 1, 'μ\'s', '音乃木板', NULL);

-- ----------------------------
-- Table structure for surplus
-- ----------------------------
DROP TABLE IF EXISTS `surplus`;
CREATE TABLE `surplus`  (
  `surplus_id` int(0) NOT NULL COMMENT '结余id',
  `uid` int(0) NULL DEFAULT NULL,
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '结余金额',
  `timestamp` timestamp(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`surplus_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of surplus
-- ----------------------------

-- ----------------------------
-- Table structure for transfer
-- ----------------------------
DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer`  (
  `transfer_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '转账记录id',
  `uid` int(0) NULL DEFAULT NULL,
  `source_account_detail_id` int(0) NULL DEFAULT NULL COMMENT '原账户id',
  `destination_account_detail_id` int(0) NULL DEFAULT NULL COMMENT '目标账户id',
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '金额',
  `time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`transfer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transfer
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '默认为微信名',
  `wxid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信号',
  `register_date` timestamp(0) NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '小宏', '3321', '2002-11-14 09:37:23');
INSERT INTO `user` VALUES (2, '小李', '221', '2023-03-08 14:34:15');
INSERT INTO `user` VALUES (3, '小王', '3322', '2023-03-08 15:19:04');
INSERT INTO `user` VALUES (4, '小白', '2212', '2023-03-11 10:40:49');

-- ----------------------------
-- Procedure structure for find_bookkeeping_type_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `find_bookkeeping_type_id`;
delimiter ;;
CREATE PROCEDURE `find_bookkeeping_type_id`(IN uid INT,IN bookkeeping_name VARCHAR(255),IN bookkeeping_type_name VARCHAR(255))
BEGIN
	SELECT bookkeeping_tpye.bookkeeping_type_id
	#INTO bookkeeping_tpye_id
	FROM bookkeeping_tpye WHERE bookkeeping_tpye.bookkeeping_type_name=bookkeeping_type_name AND bookkeeping_tpye.bookkeeping_type_id IN
	(SELECT bookkeeping.bookkeeping_type_id
	FROM bookkeeping WHERE bookkeeping.uid=uid AND bookkeeping.bookkeeping_name=bookkeeping_name);
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table bookkeeping
-- ----------------------------
DROP TRIGGER IF EXISTS `deleteBookkeeping`;
delimiter ;;
CREATE TRIGGER `deleteBookkeeping` AFTER DELETE ON `bookkeeping` FOR EACH ROW BEGIN			
			DELETE FROM bookkeeping_tpye WHERE bookkeeping_type_id=old.bookkeeping_type_id;
			DELETE FROM budget WHERE bookkeeping_id=old.bookkeeping_id;
			DELETE FROM customed_funds WHERE uid=old.uid AND bookkeeping_type_id=old.bookkeeping_type_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table bookkeeping
-- ----------------------------
DROP TRIGGER IF EXISTS `budgetAdd`;
delimiter ;;
CREATE TRIGGER `budgetAdd` AFTER INSERT ON `bookkeeping` FOR EACH ROW BEGIN			
			INSERT INTO budget(bookkeeping_id) VALUES(NEW.bookkeeping_id);
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table income
-- ----------------------------
DROP TRIGGER IF EXISTS `accountBalanceAdd`;
delimiter ;;
CREATE TRIGGER `accountBalanceAdd` AFTER INSERT ON `income` FOR EACH ROW BEGIN	
			
			UPDATE account_details
			SET balance=balance+0+NEW.amount
			WHERE account_detail_id=NEW.account_detail_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table payment
-- ----------------------------
DROP TRIGGER IF EXISTS `accountBalanceDecrease`;
delimiter ;;
CREATE TRIGGER `accountBalanceDecrease` AFTER INSERT ON `payment` FOR EACH ROW BEGIN	
			
			UPDATE account_details
			SET balance=balance+0-NEW.amount
			WHERE account_detail_id=NEW.account_detail_id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
