-- SmartRice Platform Database Schema
-- Aligned with thesis tables 4.1 ~ 4.11 and 4.18
-- Execute smartrice_bootstrap.sql first when initializing a brand new environment.

USE smartrice;

DROP TABLE IF EXISTS smartrice_service_point_goods;
DROP TABLE IF EXISTS smartrice_coupon_user;
DROP TABLE IF EXISTS smartrice_coupon;
DROP TABLE IF EXISTS smartrice_goods_attribute;
DROP TABLE IF EXISTS smartrice_goods_specification;
DROP TABLE IF EXISTS smartrice_brand;
DROP TABLE IF EXISTS smartrice_category;
DROP TABLE IF EXISTS smartrice_permission;
DROP TABLE IF EXISTS smartrice_role;
DROP TABLE IF EXISTS smartrice_admin;
DROP TABLE IF EXISTS smartrice_delivery_batch;
DROP TABLE IF EXISTS smartrice_driver;
DROP TABLE IF EXISTS smartrice_delivery_region;
DROP TABLE IF EXISTS smartrice_service_point;
DROP TABLE IF EXISTS smartrice_address;
DROP TABLE IF EXISTS smartrice_cart;
DROP TABLE IF EXISTS smartrice_order_goods;
DROP TABLE IF EXISTS smartrice_order;
DROP TABLE IF EXISTS smartrice_goods_product;
DROP TABLE IF EXISTS smartrice_goods;
DROP TABLE IF EXISTS smartrice_user;

-- ============================================================
-- Table 4.1: User (litemall_user equivalent)
-- ============================================================
CREATE TABLE smartrice_user (
    id            INT(11)       NOT NULL AUTO_INCREMENT,
    username      VARCHAR(63)   NOT NULL DEFAULT '',
    password      VARCHAR(63)   NOT NULL DEFAULT '',
    gender        TINYINT(3)    NOT NULL DEFAULT 0 COMMENT '0:unknown 1:male 2:female',
    birthday      DATE          DEFAULT NULL,
    last_login_time DATETIME    DEFAULT NULL,
    last_login_ip VARCHAR(63)   DEFAULT NULL,
    user_level    TINYINT(3)    NOT NULL DEFAULT 0 COMMENT '0:normal 1:VIP 2:senior VIP',
    nickname      VARCHAR(63)   NOT NULL DEFAULT '',
    mobile        VARCHAR(20)   DEFAULT NULL,
    avatar        VARCHAR(255)  DEFAULT NULL,
    weixin_openid VARCHAR(63)   DEFAULT NULL,
    session_key   VARCHAR(100)  DEFAULT NULL,
    status        TINYINT(3)    NOT NULL DEFAULT 0 COMMENT '0:active 1:disabled 2:cancelled',
    add_time      DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.2: Goods
-- ============================================================
CREATE TABLE smartrice_goods (
    id            INT(11)       NOT NULL AUTO_INCREMENT,
    goods_sn      VARCHAR(63)   NOT NULL DEFAULT '',
    name          VARCHAR(127)  NOT NULL DEFAULT '',
    category_id   INT(11)       DEFAULT 0,
    brand_id      INT(11)       DEFAULT 0,
    gallery       VARCHAR(1023) DEFAULT NULL COMMENT 'JSON array of image URLs',
    keywords      VARCHAR(255)  DEFAULT NULL,
    brief         VARCHAR(255)  DEFAULT NULL,
    is_on_sale    TINYINT(1)    NOT NULL DEFAULT 1,
    sort_order    SMALLINT(4)   DEFAULT 100,
    pic_url       VARCHAR(255)  DEFAULT NULL,
    share_url     VARCHAR(255)  DEFAULT NULL,
    is_new        TINYINT(1)    NOT NULL DEFAULT 0,
    is_hot        TINYINT(1)    NOT NULL DEFAULT 0,
    unit          VARCHAR(31)   DEFAULT NULL,
    counter_price DECIMAL(10,2) DEFAULT 0.00,
    retail_price  DECIMAL(10,2) DEFAULT 0.00,
    detail        TEXT          DEFAULT NULL COMMENT 'Rich text product detail',
    add_time      DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_category (category_id),
    KEY idx_brand (brand_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.3: Goods Product (SKU)
-- ============================================================
CREATE TABLE smartrice_goods_product (
    id             INT(11)       NOT NULL AUTO_INCREMENT,
    goods_id       INT(11)       NOT NULL DEFAULT 0,
    specifications VARCHAR(1023) DEFAULT NULL COMMENT 'JSON array of spec values',
    price          DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    number         INT(11)       NOT NULL DEFAULT 0 COMMENT 'Stock quantity',
    url            VARCHAR(255)  DEFAULT NULL,
    add_time       DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_goods (goods_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.4: Order
-- ============================================================
CREATE TABLE smartrice_order (
    id                INT(11)       NOT NULL AUTO_INCREMENT,
    user_id           INT(11)       NOT NULL,
    order_sn          VARCHAR(63)   NOT NULL,
    order_status      SMALLINT(6)   NOT NULL DEFAULT 101,
    aftersale_status  SMALLINT(6)   DEFAULT 0 COMMENT '0:can_apply 1:applied 2:approved 3:refunded 4:rejected 5:cancelled',
    consignee         VARCHAR(63)   DEFAULT NULL,
    mobile            VARCHAR(63)   DEFAULT NULL,
    address           VARCHAR(127)  DEFAULT NULL,
    message           VARCHAR(512)  DEFAULT NULL,
    goods_price       DECIMAL(10,2) DEFAULT 0.00,
    freight_price     DECIMAL(10,2) DEFAULT 0.00,
    coupon_price      DECIMAL(10,2) DEFAULT 0.00,
    integral_price    DECIMAL(10,2) DEFAULT 0.00,
    groupon_price     DECIMAL(10,2) DEFAULT 0.00,
    order_price       DECIMAL(10,2) DEFAULT 0.00,
    actual_price      DECIMAL(10,2) DEFAULT 0.00,
    pay_id            VARCHAR(63)   DEFAULT NULL,
    pay_time          DATETIME      DEFAULT NULL,
    ship_sn           VARCHAR(63)   DEFAULT NULL,
    ship_channel      VARCHAR(63)   DEFAULT NULL,
    ship_time         DATETIME      DEFAULT NULL,
    confirm_time      DATETIME      DEFAULT NULL,
    end_time          DATETIME      DEFAULT NULL,
    service_point_id  INT(11)       DEFAULT NULL,
    service_method    VARCHAR(31)   DEFAULT NULL COMMENT 'DELIVERY or PICKUP',
    delivery_status   VARCHAR(31)   DEFAULT NULL,
    batch_id          INT(11)       DEFAULT NULL,
    latitude          DECIMAL(10,6) DEFAULT NULL,
    longitude         DECIMAL(10,6) DEFAULT NULL,
    delivery_region_id INT(11)      DEFAULT NULL,
    add_time          DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted           TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_sn (order_sn),
    KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.5: Order Goods
-- ============================================================
CREATE TABLE smartrice_order_goods (
    id             INT(11)       NOT NULL AUTO_INCREMENT,
    order_id       INT(11)       NOT NULL,
    goods_id       INT(11)       NOT NULL,
    goods_name     VARCHAR(127)  DEFAULT NULL,
    goods_sn       VARCHAR(63)   DEFAULT NULL,
    product_id     INT(11)       DEFAULT NULL,
    number         SMALLINT(5)   NOT NULL DEFAULT 1,
    price          DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    specifications VARCHAR(1023) DEFAULT NULL,
    pic_url        VARCHAR(255)  DEFAULT NULL,
    comment        INT(11)       DEFAULT 0 COMMENT '-1:expired 0:can_comment other:comment_id',
    add_time       DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_order (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.6: Cart
-- ============================================================
CREATE TABLE smartrice_cart (
    id             INT(11)       NOT NULL AUTO_INCREMENT,
    user_id        INT(11)       NOT NULL,
    goods_id       INT(11)       NOT NULL,
    goods_sn       VARCHAR(63)   DEFAULT NULL,
    goods_name     VARCHAR(127)  DEFAULT NULL,
    product_id     INT(11)       DEFAULT NULL,
    price          DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    number         SMALLINT(5)   NOT NULL DEFAULT 1,
    specifications VARCHAR(1023) DEFAULT NULL COMMENT 'JSON array',
    checked        TINYINT(1)    NOT NULL DEFAULT 1,
    pic_url        VARCHAR(255)  DEFAULT NULL,
    add_time       DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.7: Address
-- ============================================================
CREATE TABLE smartrice_address (
    id             INT(11)       NOT NULL AUTO_INCREMENT,
    name           VARCHAR(63)   NOT NULL DEFAULT '',
    user_id        INT(11)       NOT NULL,
    province       VARCHAR(31)   DEFAULT NULL,
    city           VARCHAR(31)   DEFAULT NULL,
    county         VARCHAR(31)   DEFAULT NULL,
    address_detail VARCHAR(127)  DEFAULT NULL,
    area_code      CHAR(6)       DEFAULT NULL,
    postal_code    CHAR(6)       DEFAULT NULL,
    tel            VARCHAR(20)   DEFAULT NULL,
    is_default     TINYINT(1)    NOT NULL DEFAULT 0,
    add_time       DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.8: Service Point
-- ============================================================
CREATE TABLE smartrice_service_point (
    id             INT(11)       NOT NULL AUTO_INCREMENT,
    name           VARCHAR(63)   NOT NULL DEFAULT '',
    type           VARCHAR(31)   DEFAULT 'STORE' COMMENT 'STORE/CENTER/STATION/DEALER',
    city           VARCHAR(63)   DEFAULT NULL,
    address        VARCHAR(255)  DEFAULT NULL,
    latitude       DECIMAL(10,6) DEFAULT NULL COMMENT 'GCJ-02',
    longitude      DECIMAL(10,6) DEFAULT NULL COMMENT 'GCJ-02',
    phone          VARCHAR(63)   DEFAULT NULL,
    distance       VARCHAR(31)   DEFAULT NULL,
    image_url      VARCHAR(255)  DEFAULT NULL,
    business_hours VARCHAR(127)  DEFAULT NULL,
    description    VARCHAR(511)  DEFAULT NULL,
    facilities     VARCHAR(255)  DEFAULT NULL COMMENT 'JSON array',
    enabled        TINYINT(1)    NOT NULL DEFAULT 1,
    add_time       DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.9: Delivery Region
-- ============================================================
CREATE TABLE smartrice_delivery_region (
    id                  INT(11)       NOT NULL AUTO_INCREMENT,
    name                VARCHAR(63)   NOT NULL DEFAULT '',
    center_lat          DECIMAL(10,6) NOT NULL,
    center_lng          DECIMAL(10,6) NOT NULL,
    radius_km           DECIMAL(5,2)  NOT NULL DEFAULT 3.00,
    priority            INT(11)       NOT NULL DEFAULT 1,
    min_batch_threshold INT(11)       NOT NULL DEFAULT 3,
    driver_id           INT(11)       DEFAULT NULL,
    service_point_id    INT(11)       DEFAULT NULL,
    enabled             TINYINT(1)    NOT NULL DEFAULT 1,
    add_time            DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted             TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.10: Driver
-- ============================================================
CREATE TABLE smartrice_driver (
    id                 INT(11)     NOT NULL AUTO_INCREMENT,
    username           VARCHAR(63) NOT NULL DEFAULT '',
    password           VARCHAR(63) NOT NULL DEFAULT '',
    name               VARCHAR(63) DEFAULT NULL,
    mobile             VARCHAR(63) DEFAULT NULL,
    status             TINYINT(3)  NOT NULL DEFAULT 0 COMMENT '0:active 1:disabled',
    delivery_region_id INT(11)     DEFAULT NULL,
    area               VARCHAR(63) DEFAULT NULL,
    add_time           DATETIME    DEFAULT CURRENT_TIMESTAMP,
    update_time        DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted            TINYINT(1)  NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.11: Delivery Batch
-- ============================================================
CREATE TABLE smartrice_delivery_batch (
    id          INT(11)     NOT NULL AUTO_INCREMENT,
    batch_sn    VARCHAR(63) NOT NULL DEFAULT '',
    driver_id   INT(11)     DEFAULT NULL,
    status      VARCHAR(31) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING/DELIVERING/COMPLETED/LOCKED',
    date        DATE        DEFAULT NULL,
    add_time    DATETIME    DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)  NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_batch_sn (batch_sn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Table 4.18: Admin + Role + Permission
-- ============================================================
CREATE TABLE smartrice_admin (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    username    VARCHAR(63)  NOT NULL DEFAULT '',
    password    VARCHAR(255) NOT NULL DEFAULT '',
    last_login_ip   VARCHAR(63)  DEFAULT NULL,
    last_login_time DATETIME     DEFAULT NULL,
    avatar      VARCHAR(255) DEFAULT NULL,
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    role_ids    VARCHAR(127) DEFAULT '[]' COMMENT 'JSON array of role IDs',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE smartrice_role (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(63)  NOT NULL DEFAULT '',
    `desc`      VARCHAR(1023) DEFAULT NULL,
    enabled     TINYINT(1)   NOT NULL DEFAULT 1,
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE smartrice_permission (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    role_id     INT(11)      NOT NULL,
    permission  VARCHAR(63)  NOT NULL DEFAULT '',
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Supplementary: Category, Brand, Goods Spec/Attr
-- ============================================================
CREATE TABLE smartrice_category (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(63)  NOT NULL DEFAULT '',
    keywords    VARCHAR(1023) DEFAULT NULL,
    `desc`      VARCHAR(255) DEFAULT NULL,
    pid         INT(11)      NOT NULL DEFAULT 0,
    icon_url    VARCHAR(255) DEFAULT NULL,
    pic_url     VARCHAR(255) DEFAULT NULL,
    `level`     VARCHAR(255) DEFAULT 'L1',
    sort_order  TINYINT(3)   DEFAULT 50,
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE smartrice_brand (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL DEFAULT '',
    `desc`      VARCHAR(255) DEFAULT NULL,
    pic_url     VARCHAR(255) DEFAULT NULL,
    sort_order  TINYINT(3)   DEFAULT 50,
    floor_price DECIMAL(10,2) DEFAULT 0.00,
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE smartrice_goods_specification (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    goods_id    INT(11)      NOT NULL DEFAULT 0,
    specification VARCHAR(255) NOT NULL DEFAULT '',
    value       VARCHAR(255) NOT NULL DEFAULT '',
    pic_url     VARCHAR(255) DEFAULT NULL,
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_goods (goods_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE smartrice_goods_attribute (
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    goods_id    INT(11)      NOT NULL DEFAULT 0,
    attribute   VARCHAR(255) NOT NULL DEFAULT '',
    value       VARCHAR(255) NOT NULL DEFAULT '',
    add_time    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_goods (goods_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Deferred: Coupon (DDL placeholder, thesis mentions coupons)
-- ============================================================
CREATE TABLE smartrice_coupon (
    id          INT(11)       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(63)   NOT NULL DEFAULT '',
    `desc`      VARCHAR(127)  DEFAULT NULL,
    tag         VARCHAR(63)   DEFAULT NULL,
    total       INT(11)       NOT NULL DEFAULT 0,
    discount    DECIMAL(10,2) DEFAULT 0.00,
    min         DECIMAL(10,2) DEFAULT 0.00,
    `limit`     SMALLINT(5)   DEFAULT 1,
    type        SMALLINT(5)   DEFAULT 0,
    status      SMALLINT(5)   DEFAULT 0,
    goods_type  SMALLINT(5)   DEFAULT 0,
    goods_value VARCHAR(1023) DEFAULT '[]',
    start_time  DATETIME      DEFAULT NULL,
    end_time    DATETIME      DEFAULT NULL,
    add_time    DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE smartrice_coupon_user (
    id          INT(11)    NOT NULL AUTO_INCREMENT,
    user_id     INT(11)    NOT NULL,
    coupon_id   INT(11)    NOT NULL,
    status      SMALLINT(5) DEFAULT 0,
    used_time   DATETIME   DEFAULT NULL,
    start_time  DATETIME   DEFAULT NULL,
    end_time    DATETIME   DEFAULT NULL,
    order_id    INT(11)    DEFAULT NULL,
    add_time    DATETIME   DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Service Point Goods (many-to-many)
-- ============================================================
CREATE TABLE smartrice_service_point_goods (
    id               INT(11)       NOT NULL AUTO_INCREMENT,
    service_point_id INT(11)       NOT NULL,
    goods_id         INT(11)       NOT NULL,
    price            DECIMAL(10,2) DEFAULT NULL COMMENT 'Override price, NULL=use goods retail_price',
    enabled          TINYINT(1)    NOT NULL DEFAULT 1,
    add_time         DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted          TINYINT(1)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_sp (service_point_id),
    KEY idx_goods (goods_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
