package com.xianbei.cloud.travelcenter.web.dao;

import com.xianbei.cloud.travelcenter.common.entity.dto.DepartmentPerformanceDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.MyPerformanceDto;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 首页数据服务Dao
 */
public interface HomePageDao {
    /**
     * 获取跟单数
     *
     * @param userId 用户id
     * @return 跟单数
     */
    Map<String, Object> getDisposePlanCount(Long userId);

    /**
     * 已定制报价交集
     *
     * @param userId  用户id
     * @return  前期沟通
     */
    Long getEarlyCommunicationCount(Long userId);

    /**
     * 获取已制作报价单
     *
     * @param userId 用户id
     * @return 已制作报价单数量
     */
    Map<String, Object> getQuotationPreparedCount(Long userId);

    /**
     * 获取已定制路书
     *
     * @param userId 用户id
     * @return 已定制路书数量
     */
    Map<String, Object> getCustomizedRoadBookCount(Long userId);


    /**
     * 暂未采购订单
     *
     * @param userId 用户 id
     * @return 暂未采购订单数量
     */
    Map<String, Object> getPendingPurchaseOrderCount(Long userId);

    /**
     * 获取暂未采购产品
     *
     * @param userId 用户id
     * @return 暂未采购产品数量
     */
    Map<String, Object> getPendingPurchaseProductCount(Long userId);

    /**
     * 已成单未完成-待出行
     *
     * @param userId  用户id
     * @return 数据
     */
    Map<String, Object> getToBeTraveledCount(Long userId);

    /**
     * 已成单未完成-已出行
     *
     * @param userId 用户id
     * @return 数据
     */
    Map<String, Object> getTraveledCount(Long userId);

    /**
     * 获取对应需求
     *
     * @param userId 用户id
     * @return 对应需求数量
     */
    Long getCorrespondingDemandCount(Long userId);


    /**
     * 成单
     *
     * @param userId 用户id
     * @return 成单 数量
     */
    Long getCompletedOrderCount(Long userId);


    /**
     * 获取流水、净毛利、净毛利
     *
     * @param userId 用户 id
     * @param flag 查询范围标识
     * @return 流失流、净毛利、净毛利
     */
    Map<String, BigDecimal> getFlowAndNetGrossProfit(
            @Param("userId") Long userId,
            @Param("flag") Long flag);

    /**
     * 获取排名
     *
     * @param userId 用户 id
     * @return 排名
     */
    Long getRanking(Long userId);

    /**
     * 获取用户在指定时间内的业绩数据（我的业绩）
     *
     * @param userId    用户id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param flag   查询范围
     * @return 我的业绩
     */
    MyPerformanceDto getMyPerformanceData(
            @Param("userId") Long userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("flag") Long flag);


    /**
     * 获取部门业绩数据
     *
     * @param userIds   用户 id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param flag   查询范围
     * @return 部门业绩
     */
    List<MyPerformanceDto> getDepartmentUsersPerformanceData(
            @Param("userIds") List<Long> userIds,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("flag") Long flag);


    /**
     * 获取部门在指定时间内的业绩数据（获取部门业绩）
     *
     * @param userIds   用户
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param flag   查询范围
     * @return 部门业绩
     */
    List<DepartmentPerformanceDto> getDepartmentUsersPerformanceDataByTimeRange(
            @Param("userIds") List<Long> userIds,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("flag") Long flag);


    /**
     * 获取我的客户出行数量
     *
     * @param userId    用户id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 出行数量
     */
    Long getMyCustomerTravelCount(
            @Param("userId") Long userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);


    /**
     * 获取部门客户行中数量
     *
     * @param userId    用户id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param condition 行中时间范围标识
     * @return 行中数量
     */
    Long getMyCustomerInTransitCount(
            @Param("userId") Long userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("condition") String condition);


    /**
     * 获取部门客户出行数量
     *
     * @param userIds   用户所属角色下所有用户id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 出行数量
     */
    Long getDepartmentCustomerTravelCount(
            @Param("userIds") List<Long> userIds,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);


    /**
     * 获取部门客户行中数量
     *
     * @param userIds   当前用户所属角色下的所有用户
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param condition 行中时间范围标识
     * @return 行中数量
     */
    Long getDepartmentCustomerInTransitCount(
            @Param("userIds") List<Long> userIds,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("condition") String condition);


    /**
     * 获取我的客户生日数量
     *
     * @param userId    用户id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 客户生日数量
     */
    Map<String, Object> getMyCustomerBirthdayCount(
            @Param("userId") Long userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    /**
     * 获取部门客户生日数量
     *
     * @param userIds   当前用户所属角色下的所有用户
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 客户生日数量
     */
    Map<String, Object> getDepartmentCustomerBirthdayCount(
            @Param("userIds") List<Long> userIds,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);


    /**
     * 查询订单是否存在
     *
     * @param planId 订单id
     * @return  数量
     */
    Integer isPlanExist(Long planId);

}
