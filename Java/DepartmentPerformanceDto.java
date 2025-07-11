package com.xianbei.cloud.travelcenter.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 部门业绩
 */
public class DepartmentPerformanceDto {
    /**
     * 日期范围
     */
    private String dateRange;
    /**
     * 成单
     */
    private Long completedOrder;
    /**
     * 流水
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0")
    private BigDecimal turnover;
    /**
     * 净毛利
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0")
    private BigDecimal netGrossProfit;
    /**
     * 净毛利率
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.0")
    private BigDecimal netGrossProfitRate;


    public Long getCompletedOrder() {
        return completedOrder;
    }

    public void setCompletedOrder(Long completedOrder) {
        this.completedOrder = completedOrder;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover != null ?
                turnover.setScale(0, RoundingMode.HALF_UP) :
                null;
    }

    public BigDecimal getNetGrossProfit() {
        return netGrossProfit;
    }

    public void setNetGrossProfit(BigDecimal netGrossProfit) {
        this.netGrossProfit = netGrossProfit != null ?
                netGrossProfit.setScale(0, RoundingMode.HALF_UP) :
                null;
    }

    public BigDecimal getNetGrossProfitRate() {
        return netGrossProfitRate;
    }

    public void setNetGrossProfitRate(BigDecimal netGrossProfitRate) {
        this.netGrossProfitRate = netGrossProfitRate != null ?
                netGrossProfitRate.setScale(1, RoundingMode.HALF_UP) :
                null;
    }
}
