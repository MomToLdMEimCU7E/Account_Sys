#当我每次push后会把几张表的变动更新在这里，必须要运行
ALTER TABLE bookkeeping DROP bookkeeping_period;
ALTER TABLE bookkeeping DROP bookkeeping_create_date;
ALTER TABLE bookkeeping DROP bookkeeping_end_date;

CREATE TRIGGER budgetAdd
AFTER INSERT ON bookkeeping
FOR EACH ROW
BEGIN			
			INSERT INTO budget(bookkeeping_id) VALUES(NEW.bookkeeping_id);
END;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of budget
-- ----------------------------
INSERT INTO `budget` VALUES (1, 1, '3500', '1000', '1000', '2000', '3000', '1500', '6000', '5000', '9000', '7000', '1200', '3100');
INSERT INTO `budget` VALUES (2, 2, '1000', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (3, 3, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (4, 6, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `budget` VALUES (5, 10, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `bookkeeping` VALUES (7, 1, 5, '大海', '我的账簿5', NULL, 2, 3);
INSERT INTO `bookkeeping` VALUES (8, 2, 6, '大海', '我的账簿1', NULL, 2, 3);

INSERT INTO `bookkeeping_tpye` VALUES (5, '好好学习', 'BO3-BO4-BO5-BO6-BO8-BI17-BI23-BI26-BI27');
INSERT INTO `bookkeeping_tpye` VALUES (6, '发财', 'BO3-BO4-BO5-BO6-BO8-BI17-BI23-BI26-BI27');

UPDATE bookkeeping_tpye SET bookkeeping_type_funds_id='BO3-BO4-BO5-BO6-BO7-BI17-BI23-BI26-BI27' WHERE bookkeeping_type_id=3;
UPDATE bookkeeping_tpye SET bookkeeping_type_funds_id='BO3-BO4-BO5-BO6-BO7-BI17-BI23-BI26-BI27' WHERE bookkeeping_type_id=4;
-- 以上为v1.7 预算功能实现 ----------------------------

-- 以下为v1.8 删除功能和模糊查询 -----------------
CREATE TRIGGER deleteBookkeeping
AFTER DELETE ON bookkeeping
FOR EACH ROW
BEGIN			
			DELETE FROM bookkeeping_tpye WHERE bookkeeping_type_id=old.bookkeeping_type_id;
			DELETE FROM budget WHERE bookkeeping_id=old.bookkeeping_id;
			DELETE FROM customed_funds WHERE uid=old.uid AND bookkeeping_type_id=old.bookkeeping_type_id;
END;

UPDATE budget SET bookkeeping_id=7 WHERE budget_id=5;
INSERT INTO `budget` VALUES (6, 8, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- 以下为v1.9 删除自定义款项和接口优化 -------
UPDATE bookkeeping_tpye SET bookkeeping_type_funds_id='BO3-BO4-BO5-BO6-BO8-BI17-BI23-BI26-BI27' WHERE bookkeeping_type_id=5 AND bookkeeping_type_name='好好学习';







