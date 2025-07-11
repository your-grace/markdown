package com.xianbei.cloud.travelcenter.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 本月数据
 */
public class ThisMonthDto {

    /**
     * 对应需求
     */
    private Long correspondingDemand;
    /**
     * 成单
     */
    private Long completedOrder;
    /**
     * 流水
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0")
    private BigDecimal flow;
    /**
     * 净毛利
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0")
    private BigDecimal grossProfit;
    /**
     * 净毛利率
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.0")
    private BigDecimal netGrossProfitRate;
    /**
     * 排名
     */
    private Long ranking;

    public Long getCorrespondingDemand() {
        return correspondingDemand;
    }

    public void setCorrespondingDemand(Long correspondingDemand) {
        this.correspondingDemand = correspondingDemand;
    }

    public Long getCompletedOrder() {
        return completedOrder;
    }

    public void setCompletedOrder(Long completedOrder) {
        this.completedOrder = completedOrder;
    }

    public BigDecimal getFlow() {
        return flow;
    }

    public void setFlow(BigDecimal flow) {
        this.flow = flow != null ?
                flow.setScale(0, RoundingMode.HALF_UP) :
                null;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit != null ?
                grossProfit.setScale(0, RoundingMode.HALF_UP) :
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

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }
}
