USE smartrice;

-- ============================================================
-- Admin seed (passwords are BCrypt hashed)
-- ============================================================
-- README test account: admin123 / admin123
INSERT INTO smartrice_admin (id, username, password, role_ids) VALUES
(1, 'admin123', '$2a$10$ty3R8Dazeb0tUl8cqAAzHOAucn0VuOblV56ybb6bpOXqs2DUL3O6O', '[1]');

-- ============================================================
-- Roles
-- ============================================================
INSERT INTO smartrice_role (id, name, `desc`, enabled) VALUES
(1, '超级管理员', '拥有系统全部权限', 1),
(2, '商城管理员', '负责商品与订单管理', 1),
(3, '推广管理员', '负责营销推广管理', 1);

-- ============================================================
-- Permissions for super admin
-- ============================================================
INSERT INTO smartrice_permission (role_id, permission) VALUES
(1, '*');

-- ============================================================
-- Categories
-- ============================================================
INSERT INTO smartrice_category (id, name, keywords, `desc`, pid, `level`, sort_order) VALUES
(1, '大米', '大米,米', '主粮大米类目', 0, 'L1', 1),
(2, '丝苗米', '丝苗米,长粒', '细长口感清爽', 1, 'L2', 1),
(3, '油粘米', '油粘米,软糯', '偏软糯香气足', 1, 'L2', 2),
(4, '小农粘米', '小农粘,日常', '日常家用主食', 1, 'L2', 3);

-- ============================================================
-- Brand
-- ============================================================
INSERT INTO smartrice_brand (id, name, `desc`, sort_order) VALUES
(1, '华特米业', '佛山市南海区华特米业有限公司', 1);

-- ============================================================
-- Goods (test products from thesis)
-- ============================================================
INSERT INTO smartrice_goods (id, goods_sn, name, category_id, brand_id, brief, is_on_sale, unit, counter_price, retail_price, is_hot) VALUES
(1, 'SR-001', '华特丝苗米 5kg', 2, 1, '米粒细长，口感清爽，适合煲仔饭', 1, '袋', 49.90, 39.90, 1),
(2, 'SR-002', '华特丝苗米 10kg', 2, 1, '家庭装丝苗米，性价比之选', 1, '袋', 89.90, 75.90, 0),
(3, 'SR-003', '鼠牙油粘米 5kg', 3, 1, '偏软糯，香气足，适合焖饭', 1, '袋', 45.90, 36.90, 1),
(4, 'SR-004', '鼠牙油粘米 10kg', 3, 1, '家庭装油粘米', 1, '袋', 85.90, 69.90, 0),
(5, 'SR-005', '小农粘米 5kg', 4, 1, '日常家用，经济实惠', 1, '袋', 35.90, 28.90, 0),
(6, 'SR-006', '小农粘米 10kg', 4, 1, '家庭装小农粘米', 1, '袋', 65.90, 52.90, 0);

INSERT INTO smartrice_goods_product (goods_id, specifications, price, number) VALUES
(1, '["5kg"]', 39.90, 999),
(2, '["10kg"]', 75.90, 999),
(3, '["5kg"]', 36.90, 999),
(4, '["10kg"]', 69.90, 999),
(5, '["5kg"]', 28.90, 999),
(6, '["10kg"]', 52.90, 999);

-- ============================================================
-- Service Points (from thesis test data)
-- ============================================================
INSERT INTO smartrice_service_point (id, name, type, city, address, latitude, longitude, phone, business_hours, enabled) VALUES
(1, '华特米业总部店', 'STORE', '佛山', '佛山市南海区穗盐路', 23.068800, 113.143500, '0757-12345678', '08:00-18:00', 1),
(2, '华特米业大沥门市', 'STORE', '佛山', '佛山市南海区大沥镇', 23.092500, 113.083600, '0757-23456789', '08:00-18:00', 1),
(3, '华特米业蟠龙门市', 'STORE', '佛山', '佛山市南海区蟠龙路', 23.031200, 113.125600, '0757-34567890', '08:00-18:00', 1);

-- ============================================================
-- Service Point Goods (seed baseline for V0.2.0 management pages)
-- ============================================================
INSERT INTO smartrice_service_point_goods (id, service_point_id, goods_id, price, enabled) VALUES
(1, 1, 1, 79.90, 1),
(2, 2, 3, 69.90, 1),
(3, 3, 5, 52.90, 1);

-- ============================================================
-- Delivery Regions (from thesis test data)
-- ============================================================
INSERT INTO smartrice_delivery_region (id, name, center_lat, center_lng, radius_km, priority, min_batch_threshold, driver_id, service_point_id, enabled) VALUES
(1, '穗盐路片区', 23.068800, 113.143500, 3.00, 1, 3, 1, 1, 1),
(2, '大沥镇片区', 23.092500, 113.083600, 3.00, 2, 3, 2, 2, 1),
(3, '蟠龙路片区', 23.031200, 113.125600, 3.00, 3, 3, 3, 3, 1);

-- ============================================================
-- Drivers (seed baseline for future versions, not exposed in UI)
-- ============================================================
INSERT INTO smartrice_driver (id, username, password, name, mobile, status, delivery_region_id, area) VALUES
(1, 'driver01', '$2a$10$ty3R8Dazeb0tUl8cqAAzHOAucn0VuOblV56ybb6bpOXqs2DUL3O6O', '张师傅', '13800001111', 0, 1, '穗盐路片区'),
(2, 'driver02', '$2a$10$ty3R8Dazeb0tUl8cqAAzHOAucn0VuOblV56ybb6bpOXqs2DUL3O6O', '李师傅', '13800002222', 0, 2, '大沥镇片区'),
(3, 'driver03', '$2a$10$ty3R8Dazeb0tUl8cqAAzHOAucn0VuOblV56ybb6bpOXqs2DUL3O6O', '王师傅', '13800003333', 0, 3, '蟠龙路片区');
