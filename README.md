# SmartRice Platform

基于 SpringBoot 和 Vue 的大米农产品电商平台。

## 技术栈

- **后端**: Java 17 / SpringBoot 3.2 / MyBatis / MySQL 8.0
- **管理端前端**: Vue 3 / Vite / Element Plus
- **客户端**: 微信小程序 (计划中)
- **司机端**: 微信小程序 (计划中)

## 项目结构

```
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

**V0.1.0** — 项目骨架 + 数据库基线 + 后台登录 + 数据基线概览（当前唯一开发版本）

- 首页展示当前数据库中的基线数据概览，用于核对 `smartrice_seed.sql` 是否已正确导入。
- 当前版本不包含分类、商品、服务点、司机、片区的完整 CRUD，只提供只读核对视图。

详见 [Spec/roadmap.md](Spec/roadmap.md)
