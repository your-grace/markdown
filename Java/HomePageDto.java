package com.xianbei.cloud.travelcenter.common.entity.dto;

/**
 * 我的跟单、本月、我的业绩和部门业绩
 */
public class HomePageDto {
    /**
     * 跟进中
     */
    private Long followingUp;
    /**
     * 跟进中订单id
     */
    private String followingUpIds;
    /**
     * 前期沟通
     */
    private Long earlyCommunication;
    /**
     * 前期沟通订单id
     */
    private String earlyCommunicationIds;
    /**
     * 已定制路书
     */
    private Long customizedRoadBook;
    /**
     * 已定制路书订单id
     */
    private String customizedRoadBookIds;
    /**
     * 已制作报价
     */
    private Long quotationPrepared;
    /**
     * 已制作报价订单id
     */
    private String quotationPreparedIds;
    /**
     * 暂未采购订单
     */
    private Long pendingPurchaseOrder;
    /**
     * 暂未采购订单id
     */
    private String pendingPurchaseOrderIds;
    /**
     * 暂未采购产品
     */
    private Long pendingPurchaseProduct;
    /**
     * 暂未采购产品id
     */
    private String pendingPurchaseProductIds;

    /**
     * 待出行订单数量
     */
    private Long toBeTraveled;

    /**
     * 待出行订单id
     */
    private String toBeTraveledIds;

    /**
     * 已出行订单数量
     */
    private Long traveled;

    /**
      * 待出行订单id
      */
    private String traveledIds;


    public Long getFollowingUp() {
        return followingUp;
    }

    public void setFollowingUp(Long followingUp) {
        this.followingUp = followingUp;
    }

    public Long getEarlyCommunication() {
        return earlyCommunication;
    }

    public void setEarlyCommunication(Long earlyCommunication) {
        this.earlyCommunication = earlyCommunication;
    }

    public Long getCustomizedRoadBook() {
        return customizedRoadBook;
    }

    public void setCustomizedRoadBook(Long customizedRoadBook) {
        this.customizedRoadBook = customizedRoadBook;
    }

    public Long getQuotationPrepared() {
        return quotationPrepared;
    }

    public void setQuotationPrepared(Long quotationPrepared) {
        this.quotationPrepared = quotationPrepared;
    }

    public Long getPendingPurchaseOrder() {
        return pendingPurchaseOrder;
    }

    public void setPendingPurchaseOrder(Long pendingPurchaseOrder) {
        this.pendingPurchaseOrder = pendingPurchaseOrder;
    }

    public Long getPendingPurchaseProduct() {
        return pendingPurchaseProduct;
    }

    public void setPendingPurchaseProduct(Long pendingPurchaseProduct) {
        this.pendingPurchaseProduct = pendingPurchaseProduct;
    }

    public String getFollowingUpIds() {
        return followingUpIds;
    }

    public void setFollowingUpIds(String followingUpIds) {
        this.followingUpIds = followingUpIds;
    }

    public String getEarlyCommunicationIds() {
        return earlyCommunicationIds;
    }

    public void setEarlyCommunicationIds(String earlyCommunicationIds) {
        this.earlyCommunicationIds = earlyCommunicationIds;
    }

    public String getCustomizedRoadBookIds() {
        return customizedRoadBookIds;
    }

    public void setCustomizedRoadBookIds(String customizedRoadBookIds) {
        this.customizedRoadBookIds = customizedRoadBookIds;
    }

    public String getQuotationPreparedIds() {
        return quotationPreparedIds;
    }

    public void setQuotationPreparedIds(String quotationPreparedIds) {
        this.quotationPreparedIds = quotationPreparedIds;
    }

    public String getPendingPurchaseOrderIds() {
        return pendingPurchaseOrderIds;
    }

    public void setPendingPurchaseOrderIds(String pendingPurchaseOrderIds) {
        this.pendingPurchaseOrderIds = pendingPurchaseOrderIds;
    }

    public String getPendingPurchaseProductIds() {
        return pendingPurchaseProductIds;
    }

    public void setPendingPurchaseProductIds(String pendingPurchaseProductIds) {
        this.pendingPurchaseProductIds = pendingPurchaseProductIds;
    }

    public Long getToBeTraveled() {
        return toBeTraveled;
    }

    public void setToBeTraveled(Long toBeTraveled) {
        this.toBeTraveled = toBeTraveled;
    }

    public String getToBeTraveledIds() {
        return toBeTraveledIds;
    }

    public void setToBeTraveledIds(String toBeTraveledIds) {
        this.toBeTraveledIds = toBeTraveledIds;
    }

    public Long getTraveled() {
        return traveled;
    }

    public void setTraveled(Long traveled) {
        this.traveled = traveled;
    }

    public String getTraveledIds() {
        return traveledIds;
    }

    public void setTraveledIds(String traveledIds) {
        this.traveledIds = traveledIds;
    }
}
