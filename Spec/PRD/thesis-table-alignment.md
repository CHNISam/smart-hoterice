# Thesis Table Alignment

## Scope

- Source thesis: [Thesis.docx](D:/HW/smart-hoterice/.claude/worktrees/ecstatic-herschel/Spec/PRD/Thesis.docx)
- Source schema: [smartrice_schema.sql](D:/HW/smart-hoterice/.claude/worktrees/ecstatic-herschel/smartrice-db/sql/smartrice_schema.sql)
- Reference legacy schema: `D:/HW/litemall/litemall-db/sql/litemall_table.sql`

## Conclusion

- Thesis tables `4.1` to `4.11` are now aligned with `smartrice_schema.sql`.
- Field names and data types follow the thesis table definitions.
- Non-thesis drift removed in `V0.1.0`:
  - `smartrice_order.payment_method`
  - `smartrice_address.latitude`
  - `smartrice_address.longitude`
- `smartrice_schema.sql` now contains only business DDL.
- Privileged bootstrap statements were moved to [smartrice_bootstrap.sql](D:/HW/smart-hoterice/.claude/worktrees/ecstatic-herschel/smartrice-db/sql/smartrice_bootstrap.sql).

## Mapping

| Thesis | Table | Result |
| --- | --- | --- |
| 表4.1 用户信息表 | `smartrice_user` | aligned |
| 表4.2 商品基本信息表 | `smartrice_goods` | aligned |
| 表4.3 商品货品表 | `smartrice_goods_product` | aligned |
| 表4.4 订单表 | `smartrice_order` | aligned |
| 表4.5 订单商品表 | `smartrice_order_goods` | aligned |
| 表4.6 购物车商品表 | `smartrice_cart` | aligned |
| 表4.7 收货地址表 | `smartrice_address` | aligned |
| 表4.8 服务点信息表 | `smartrice_service_point` | aligned |
| 表4.9 配送片区定义表 | `smartrice_delivery_region` | aligned |
| 表4.10 司机表 | `smartrice_driver` | aligned |
| 表4.11 司机履约批次表 | `smartrice_delivery_batch` | aligned |

## Notes

- Thesis captions and field rows were extracted directly from `Thesis.docx` section `4.3.2 数据库表设计`.
- `litemall` was used only as a structural reference for legacy table lineage and naming continuity.
- `V0.1.0` keeps supplementary tables such as category, brand, goods specification, goods attribute, coupon, and service point goods outside the thesis core set.
