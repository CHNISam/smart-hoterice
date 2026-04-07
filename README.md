# SmartRice Platform

基于 SpringBoot 和 Vue 的大米农产品电商平台。

## 技术栈

- **后端**: Java 17 / SpringBoot 3.2 / MyBatis / MySQL 8.0
- **管理端前端**: Vue 3 / Vite / Element Plus
- **客户端**: 微信小程序（计划中）
- **司机端**: 微信小程序（计划中）

## 项目结构

```text
smartrice-db/          数据库层（实体、Mapper、Service）
smartrice-core/        通用核心层（JWT、响应工具、跨域配置）
smartrice-admin-api/   管理端后端 API
smartrice-wx-api/      小程序端后端 API
smartrice-all/         统一启动入口
admin-web/             管理端前端（Vue 3）
Spec/                  产品规范（PRD、roadmap、测试用例）
```

## 环境要求

- JDK 17+
- Maven 3.9+
- MySQL 8.0+
- Node.js 18+

## 快速启动

### 1. 初始化数据库

```bash
mysql -u root < smartrice-db/sql/smartrice_bootstrap.sql
mysql -u smartrice -psmartrice123456 smartrice < smartrice-db/sql/smartrice_schema.sql
mysql -u smartrice -psmartrice123456 smartrice < smartrice-db/sql/smartrice_seed.sql
```

### 2. 启动后端

```bash
cd smartrice-all
mvn spring-boot:run
# 后端将在 http://localhost:8080 启动
```

### 3. 启动管理端前端

```bash
cd admin-web
npm install
npm run dev
# 前端将在 http://localhost:9528 启动
```

## 测试账号

> 测试账号仅记录在 README，不得在任何 UI 中展示。

| 版本 | 角色 | 用户名 | 密码 |
|------|------|--------|------|
| V0.1.0 | 超级管理员 | admin123 | admin123 |

## 当前版本

**V0.2.0** - 商品管理 + 服务点基础 + 客户端商品浏览接口（当前开发版本）

- 管理端已包含商品、服务点、配送片区、服务点商品绑定的最小 CRUD 能力。
- 客户端已提供分类列表、商品列表/详情、服务点列表等浏览接口，可按服务点筛选商品。
- `smartrice_seed.sql` 已包含 `service_point_goods` 基线记录，初始化数据库后可直接验证 `V0.2.0` 管理页和客户端浏览接口。

详见 [Spec/roadmap.md](Spec/roadmap.md)
