
-- ----------------------------
-- 用户相关模块
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` CHAR(64) DEFAULT NULL COMMENT '用户名',
  `password` VARCHAR(64) DEFAULT NULL COMMENT '密码',
  `salt` VARCHAR(20) DEFAULT NULL COMMENT '盐',
  `nickname` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
  `mobile` VARCHAR(20) DEFAULT NULL COMMENT '手机号码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
  `gender` CHAR(4) DEFAULT NULL COMMENT '性别',
  `birth` DATE DEFAULT NULL COMMENT '生日',
  `city` VARCHAR(500) DEFAULT NULL COMMENT '所在城市',
  `job` VARCHAR(255) DEFAULT NULL COMMENT '职业',
  `sign` VARCHAR(255) DEFAULT NULL COMMENT '个性签名',
  `status` TINYINT(4) DEFAULT NULL COMMENT '用户状态：1—正常；0—冻结',
  `create_time` DATETIME DEFAULT NULL COMMENT '注册时间',
  `modified_time` DATETIME DEFAULT NULL COMMENT '更改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='会员';

DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` BIGINT(20) DEFAULT NULL COMMENT '会员id',
  `name` VARCHAR(255) DEFAULT NULL COMMENT '收货人姓名',
  `phone` VARCHAR(64) DEFAULT NULL COMMENT '电话',
  `post_code` VARCHAR(64) DEFAULT NULL COMMENT '邮政编码',
  `province` VARCHAR(100) DEFAULT NULL COMMENT '省份/直辖市',
  `city` VARCHAR(100) DEFAULT NULL COMMENT '城市',
  `region` VARCHAR(100) DEFAULT NULL COMMENT '区',
  `detail_address` VARCHAR(255) DEFAULT NULL COMMENT '详细地址(街道门牌号)',
  `default_status` TINYINT(4) DEFAULT NULL COMMENT '是否默认：1—是；0—否',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='会员收货地址';

DROP TABLE IF EXISTS `t_member_statistics_info`;
CREATE TABLE `t_member_statistics_info` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` BIGINT(20) DEFAULT NULL COMMENT '会员id',
  `consume_amount` DECIMAL(18,4) DEFAULT NULL COMMENT '累计消费金额',
  `coupon_amount` DECIMAL(18,4) DEFAULT NULL COMMENT '累计优惠金额',
  `order_count` INT(11) DEFAULT NULL COMMENT '订单数量',
  `return_order_count` INT(11) DEFAULT NULL COMMENT '退货数量',
  `login_count` INT(11) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='会员统计信息';

-- ----------------------------
-- 商品相关
-- 商品、category(分类：数码、服饰..)、spu(苹果6)、sku(属性：土豪金 128G 苹果6)
-- 商品>category>spu>sku
-- 在这里直接用spu当做商品
-- ----------------------------

DROP TABLE IF EXISTS `t_spu`;
CREATE TABLE `t_spu` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` VARCHAR(1000) DEFAULT NULL COMMENT '商品名称',
  `category_id` BIGINT(20) DEFAULT NULL COMMENT '所属分类id',
  `publish_status` TINYINT(4) DEFAULT NULL COMMENT '上架状态[0-下架，1-上架]',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modified_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='spu信息';

-- 其实还可以加个父子级分类，太麻烦了。。。
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` CHAR(50) DEFAULT NULL COMMENT '分类名称',
  `show_status` TINYINT(4) DEFAULT NULL COMMENT '是否显示[0-不显示;1-显示]',
  `icon` CHAR(255) DEFAULT NULL COMMENT '图标地址',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类';

DROP TABLE IF EXISTS `t_sku`;
CREATE TABLE `t_sku` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'skuId',
  `spu_id` BIGINT(20) DEFAULT NULL COMMENT 'spuId',
  `sku_name` VARCHAR(255) DEFAULT NULL COMMENT 'sku名称（土豪金128G苹果6）',
  `sku_desc` VARCHAR(2000) DEFAULT NULL COMMENT 'sku介绍描述（土豪金128G苹果6有多牛逼巴拉巴拉）',
  `category_id` BIGINT(20) DEFAULT NULL COMMENT '所属分类id',
  `sku_default_img` VARCHAR(255) DEFAULT NULL COMMENT '默认图片（冗余字段）',
  `original_price` DECIMAL(18,2) DEFAULT NULL COMMENT '原价',
  `present_price` DECIMAL(18,2) DEFAULT NULL COMMENT '现价',
  `sale_count` BIGINT(20) DEFAULT NULL COMMENT '销量',
  `publish_status` TINYINT(4) DEFAULT NULL COMMENT '上架状态[0-下架，1-上架]',
  `stock` INT(11) DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='sku信息';

-- 考虑放多张图片的，具体看你们吧。。
DROP TABLE IF EXISTS `t_sku_images`;
CREATE TABLE `t_sku_images` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` BIGINT(20) DEFAULT NULL COMMENT 'sku_id',
  `img_url` VARCHAR(255) DEFAULT NULL COMMENT '图片地址',
  `default_img` TINYINT(4) DEFAULT NULL COMMENT '是否默认图[0-不是,1-是]',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='sku图片';


-- ----------------------------
-- 购物车相关
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` BIGINT(20) DEFAULT NULL COMMENT '会员id',
  `spu_id` BIGINT(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` VARCHAR(255) DEFAULT NULL COMMENT 'spu_name',
  `category_id` BIGINT(20) DEFAULT NULL COMMENT '商品分类id',
  `sku_id` BIGINT(20) DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` VARCHAR(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_img` VARCHAR(255) DEFAULT NULL COMMENT '商品sku图片',
  `sku_original_price` DECIMAL(18,2) DEFAULT NULL COMMENT '原价',
  `sku_present_price` DECIMAL(18,2) DEFAULT NULL COMMENT '现价',
  `sku_count` INT(11) DEFAULT NULL COMMENT '商品购买的数量',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

-- ----------------------------
-- 订单相关
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` BIGINT(20) DEFAULT NULL COMMENT '会员id',
  `order_sn` CHAR(64) DEFAULT NULL COMMENT '订单号(保证唯一性)',
  `member_username` VARCHAR(64) DEFAULT NULL COMMENT '用户名',
  `total_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '订单总额',
  `pay_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '应付总额',
  `freight_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '运费金额',
  `pay_type` TINYINT(4) DEFAULT NULL COMMENT '支付方式【0->支付宝；1->微信；2->银联】',
  `status` TINYINT(4) DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` VARCHAR(64) DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` VARCHAR(64) DEFAULT NULL COMMENT '物流单号(保证唯一性)',
  `auto_confirm_day` INT(11) DEFAULT NULL COMMENT '自动确认收货时间（天）',
  `receiver_name` VARCHAR(100) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` VARCHAR(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` VARCHAR(32) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` VARCHAR(32) DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` VARCHAR(32) DEFAULT NULL COMMENT '城市',
  `receiver_region` VARCHAR(32) DEFAULT NULL COMMENT '区',
  `receiver_detail_address` VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
  `note` VARCHAR(500) DEFAULT NULL COMMENT '订单备注',
  `confirm_status` TINYINT(4) DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` TINYINT(4) DEFAULT NULL COMMENT '删除状态(软删除)【0->未删除；1->已删除】',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `delivery_time` DATETIME DEFAULT NULL COMMENT '发货时间',
  `receive_time` DATETIME DEFAULT NULL COMMENT '确认收货时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '订单创建时间',
  `modified_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` BIGINT(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` CHAR(64) DEFAULT NULL COMMENT 'order_sn',
  `spu_id` BIGINT(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` VARCHAR(255) DEFAULT NULL COMMENT 'spu_name',
  `category_id` BIGINT(20) DEFAULT NULL COMMENT '商品分类id',
  `sku_id` BIGINT(20) DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` VARCHAR(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_img` VARCHAR(255) DEFAULT NULL COMMENT '商品sku图片',
  `sku_original_price` DECIMAL(18,2) DEFAULT NULL COMMENT '原价',
  `sku_present_price` DECIMAL(18,2) DEFAULT NULL COMMENT '现价',
  `sku_count` INT(11) DEFAULT NULL COMMENT '商品购买的数量',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='订单项信息';

DROP TABLE IF EXISTS `t_order_operate_history`;
CREATE TABLE `t_order_operate_history` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` BIGINT(20) DEFAULT NULL COMMENT '订单id',
  `operator` VARCHAR(100) DEFAULT NULL COMMENT '操作人[用户/系统/后台管理员]',
  `operate_time` DATETIME DEFAULT NULL COMMENT '操作时间',
  `order_status` TINYINT(4) DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='订单操作历史记录';

-- 这里的收货人——后台谁操作就是谁（因为实际上并没有人却收货只能模拟）
-- 其实原因那里应该加上图片的，但是觉得好麻烦，而且没有人拍照片
DROP TABLE IF EXISTS `t_order_return_apply`;
CREATE TABLE `t_order_return_apply` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` BIGINT(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` CHAR(64) DEFAULT NULL COMMENT '订单编号',
  `sku_id` BIGINT(20) DEFAULT NULL COMMENT '退货商品id',
  `create_time` DATETIME DEFAULT NULL COMMENT '申请时间',
  `member_username` VARCHAR(64) DEFAULT NULL COMMENT '会员用户名',
  `return_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '退款金额',
  `return_name` VARCHAR(100) DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` VARCHAR(20) DEFAULT NULL COMMENT '退货人电话',
  `status` TINYINT(4) DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` DATETIME DEFAULT NULL COMMENT '处理时间',
  `sku_img` VARCHAR(500) DEFAULT NULL COMMENT '商品图片',
  `sku_name` VARCHAR(200) DEFAULT NULL COMMENT '商品名称',
  `sku_count` INT(11) DEFAULT NULL COMMENT '退货数量',
  `sku_original_price` DECIMAL(18,2) DEFAULT NULL COMMENT '原价',
  `sku_present_price` DECIMAL(18,2) DEFAULT NULL COMMENT '现价',
  `reason` VARCHAR(500) DEFAULT NULL COMMENT '原因',
  `handle_note` VARCHAR(500) DEFAULT NULL COMMENT '处理备注',
  `handle_man` VARCHAR(200) DEFAULT NULL COMMENT '处理人员',
  `receive_man` VARCHAR(100) DEFAULT NULL COMMENT '收货人',
  `receive_time` DATETIME DEFAULT NULL COMMENT '收货时间',
  `receive_note` VARCHAR(500) DEFAULT NULL COMMENT '收货备注',
  `receive_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货人电话',
  `company_address` VARCHAR(500) DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='订单退货申请';


-- ----------------------------
-- 秒杀相关，将秒杀单独抽取出来了
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill`;
CREATE TABLE `t_seckill` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `sku_id` BIGINT(20) DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` VARCHAR(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_img` VARCHAR(255) DEFAULT NULL COMMENT '商品sku图片',
  `sku_original_price` DECIMAL(18,2) DEFAULT NULL COMMENT '原价',
  `seckill_price` DECIMAL(18,2) DEFAULT NULL COMMENT '秒杀价格',
  `stock` INT(11) DEFAULT NULL COMMENT '秒杀库存',
  `start_time` DATETIME DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀的商品';

-- 秒杀一次只能秒杀一件商品，秒杀需要立即付款所以状态没有待付款选项
-- 秒杀不支持退货操作
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT(20) DEFAULT NULL COMMENT '会员id',
  `member_username` VARCHAR(64) DEFAULT NULL COMMENT '用户名',
  `sec_order_sn` CHAR(64) DEFAULT NULL COMMENT '秒杀订单号(保证唯一性)',
  `sku_id` BIGINT(20) DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` VARCHAR(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_img` VARCHAR(255) DEFAULT NULL COMMENT '商品sku图片',
  `sku_original_price` DECIMAL(18,2) DEFAULT NULL COMMENT '原价',
  `pay_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '付款金额',
  `freight_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '运费金额',
  `pay_type` TINYINT(4) DEFAULT NULL COMMENT '支付方式【0->支付宝；1->微信；2->银联】',
  `status` TINYINT(4) DEFAULT NULL COMMENT '订单状态【1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` VARCHAR(64) DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` VARCHAR(64) DEFAULT NULL COMMENT '物流单号(保证唯一性)',
  `auto_confirm_day` INT(11) DEFAULT NULL COMMENT '自动确认收货时间（天）',
  `receiver_name` VARCHAR(100) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` VARCHAR(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` VARCHAR(32) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` VARCHAR(32) DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` VARCHAR(32) DEFAULT NULL COMMENT '城市',
  `receiver_region` VARCHAR(32) DEFAULT NULL COMMENT '区',
  `receiver_detail_address` VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
  `note` VARCHAR(500) DEFAULT NULL COMMENT '订单备注',
  `confirm_status` TINYINT(4) DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` TINYINT(4) DEFAULT NULL COMMENT '删除状态(软删除)【0->未删除；1->已删除】',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `delivery_time` DATETIME DEFAULT NULL COMMENT '发货时间',
  `receive_time` DATETIME DEFAULT NULL COMMENT '确认收货时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '订单创建时间',
  `modified_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单';

-- ----------------------------
-- 后台管理相关,其实还应该有一个role表，想了想算了hhh
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL COMMENT '用户名',
  `password` VARCHAR(64) DEFAULT NULL COMMENT '密码',
  `salt` VARCHAR(20) DEFAULT NULL COMMENT '盐',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `status` TINYINT(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` BIGINT(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modified_time` DATETIME DEFAULT NULL COMMENT '更改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='管理员';