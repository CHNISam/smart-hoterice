package com.smartrice.admin.service;

import com.smartrice.db.mapper.DashboardOverviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardService {

    @Autowired
    private DashboardOverviewMapper dashboardOverviewMapper;

    public Map<String, Object> buildOverview() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("version", "V0.1.0");
        data.put("summary", buildSummary());
        data.put("sections", List.of(
                buildSection(
                        "admins",
                        "管理员账号",
                        dashboardOverviewMapper.countAdmins(),
                        List.of(column("username", "用户名"), column("roleIds", "角色 ID")),
                        dashboardOverviewMapper.findAdminPreview()
                ),
                buildSection(
                        "categories",
                        "商品分类",
                        dashboardOverviewMapper.countCategories(),
                        List.of(column("name", "分类名称"), column("level", "层级"), column("keywords", "关键词")),
                        dashboardOverviewMapper.findCategoryPreview()
                ),
                buildSection(
                        "goods",
                        "测试商品",
                        dashboardOverviewMapper.countGoods(),
                        List.of(column("name", "商品名称"), column("retailPrice", "零售价"), column("unit", "单位")),
                        dashboardOverviewMapper.findGoodsPreview()
                ),
                buildSection(
                        "servicePoints",
                        "服务点",
                        dashboardOverviewMapper.countServicePoints(),
                        List.of(column("name", "服务点"), column("city", "城市"), column("type", "类型")),
                        dashboardOverviewMapper.findServicePointPreview()
                ),
                buildSection(
                        "deliveryRegions",
                        "配送片区",
                        dashboardOverviewMapper.countDeliveryRegions(),
                        List.of(column("name", "片区"), column("radiusKm", "半径(km)"), column("priority", "优先级")),
                        dashboardOverviewMapper.findDeliveryRegionPreview()
                ),
                buildSection(
                        "drivers",
                        "司机",
                        dashboardOverviewMapper.countDrivers(),
                        List.of(column("username", "账号"), column("name", "姓名"), column("area", "负责片区")),
                        dashboardOverviewMapper.findDriverPreview()
                )
        ));
        return data;
    }

    private Map<String, Object> buildSummary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("admins", dashboardOverviewMapper.countAdmins());
        summary.put("roles", dashboardOverviewMapper.countRoles());
        summary.put("permissions", dashboardOverviewMapper.countPermissions());
        summary.put("categories", dashboardOverviewMapper.countCategories());
        summary.put("goods", dashboardOverviewMapper.countGoods());
        summary.put("servicePoints", dashboardOverviewMapper.countServicePoints());
        summary.put("deliveryRegions", dashboardOverviewMapper.countDeliveryRegions());
        summary.put("drivers", dashboardOverviewMapper.countDrivers());
        return summary;
    }

    private Map<String, Object> buildSection(String key, String title, int count, List<Map<String, String>> columns, List<Map<String, Object>> rows) {
        Map<String, Object> section = new LinkedHashMap<>();
        section.put("key", key);
        section.put("title", title);
        section.put("count", count);
        section.put("columns", columns);
        section.put("rows", rows);
        return section;
    }

    private Map<String, String> column(String key, String label) {
        Map<String, String> column = new LinkedHashMap<>();
        column.put("key", key);
        column.put("label", label);
        return column;
    }
}
