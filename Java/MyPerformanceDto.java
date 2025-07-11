package com.xianbei.cloud.travelcenter.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 我的业绩
 */
public class MyPerformanceDto {
    /**
     * 日期范围
     */
    private String dateRange;
    /**
     * 需求数
     */
    private Long demandQuntity;
    /**
     * 成单
     */
    private Long completedOrder;
    /**
     * 成单率
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.0")
    private BigDecimal completedOrdeRate;
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
    /**
     * 排名
     */
    private Integer rank;

    /**
     * 用户ID
     */
    private Long userId;

    public Long getDemandQuntity() {
        return demandQuntity;
    }

    public void setDemandQuntity(Long demandQuntity) {
        this.demandQuntity = demandQuntity;
    }

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

    public BigDecimal getNetGrossProfitRate() {
        return netGrossProfitRate;
    }

    public void setNetGrossProfitRate(BigDecimal netGrossProfitRate) {
        this.netGrossProfitRate = netGrossProfitRate != null ?
                netGrossProfitRate.setScale(1, RoundingMode.HALF_UP) :
                null;
    }

    public BigDecimal getCompletedOrdeRate() {
        return completedOrdeRate;
    }

    public void setCompletedOrdeRate(BigDecimal completedOrdeRate) {
        this.completedOrdeRate = completedOrdeRate != null ?
                completedOrdeRate.setScale(1, RoundingMode.HALF_UP) :
                null;
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
