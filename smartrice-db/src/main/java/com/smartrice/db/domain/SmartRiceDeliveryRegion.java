package com.smartrice.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SmartRiceDeliveryRegion {
    private Integer id;
    private String name;
    private BigDecimal centerLat;
    private BigDecimal centerLng;
    private BigDecimal radiusKm;
    private Integer priority;
    private Integer minBatchThreshold;
    private Integer driverId;
    private Integer servicePointId;
    private Boolean enabled;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private Boolean deleted;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getCenterLat() { return centerLat; }
    public void setCenterLat(BigDecimal centerLat) { this.centerLat = centerLat; }
    public BigDecimal getCenterLng() { return centerLng; }
    public void setCenterLng(BigDecimal centerLng) { this.centerLng = centerLng; }
    public BigDecimal getRadiusKm() { return radiusKm; }
    public void setRadiusKm(BigDecimal radiusKm) { this.radiusKm = radiusKm; }
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public Integer getMinBatchThreshold() { return minBatchThreshold; }
    public void setMinBatchThreshold(Integer minBatchThreshold) { this.minBatchThreshold = minBatchThreshold; }
    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public Integer getServicePointId() { return servicePointId; }
    public void setServicePointId(Integer servicePointId) { this.servicePointId = servicePointId; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public LocalDateTime getAddTime() { return addTime; }
    public void setAddTime(LocalDateTime addTime) { this.addTime = addTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
}
