package com.xianbei.cloud.travelcenter.common.entity.dto;

/**
 * 客户出行
 */
public class CustomerTravelDto {
    /**
     * 出行日期
     */
    private String dateRange;
    /**
     * 出行数量
     */
    private Long travelOrderCount;

    /**
     * 行程中日期
     */
    private String travelDateRange;
    /**
     * 行程中数量
     */
    private Long inTransitOrderCount;

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public Long getTravelOrderCount() {
        return travelOrderCount;
    }

    public void setTravelOrderCount(Long travelOrderCount) {
        this.travelOrderCount = travelOrderCount;
    }

    public String getTravelDateRange() {
        return travelDateRange;
    }

    public void setTravelDateRange(String travelDateRange) {
        this.travelDateRange = travelDateRange;
    }

    public Long getInTransitOrderCount() {
        return inTransitOrderCount;
    }

    public void setInTransitOrderCount(Long inTransitOrderCount) {
        this.inTransitOrderCount = inTransitOrderCount;
    }
}
