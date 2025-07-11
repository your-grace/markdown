package com.xianbei.cloud.travelcenter.common.entity.dto;

/**
 * 客户生日
 */
public class CustomerBirthdayDto {
    /**
     * 日期范围
     */
    private String dateRange;
    /**
     * 生日人数
     */
    private Long birthdayCount;

    /**
     * 订单ids
     */
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public Long getBirthdayCount() {
        return birthdayCount;
    }

    public void setBirthdayCount(Long birthdayCount) {
        this.birthdayCount = birthdayCount;
    }
}
