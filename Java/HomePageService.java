package com.xianbei.cloud.travelcenter.common.service;

import com.xianbei.cloud.travelcenter.common.entity.dto.HomePageDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.ThisMonthDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.MyPerformanceDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.DepartmentPerformanceDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.CustomerTravelDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.CustomerBirthdayDto;

import java.util.List;

/**
 * 首页数据服务接口
 */
public interface HomePageService {

    /**
     * 获取首页数据
     *
     * @param userId        用户ID
     * @return 我的跟单、本月、我的业绩、部门业绩
     */

    HomePageDto getDasboardData(Long userId);


    /**
     * 获取本月数据
     *
     * @param userId      用户id
     * @param flag        查询范围
     * @return  本月数据
     */
    ThisMonthDto getThisMonthData(Long userId, Long flag);

    /**
     * 获取我的业绩数据
     *
     * @param userId      用户id
     * @param flag        查询范围
     * @return  我的业绩数据
     */
    List<MyPerformanceDto> getMyPerformanceData(Long userId, Long flag);

    /**
     * 获取部门业绩数据
     *
     * @param userId      用户id
     * @param flag        查询范围
     * @return  部门业绩数据
     */
    List<DepartmentPerformanceDto> getDepartmentPerformanceData(Long userId, Long flag);

    /**
     * 获取客户出行和行中数据
     *
     * @param userId      用户id
     * @param travelScope 查询范围
     * @return  客户出行数据
     */
    List<CustomerTravelDto> getCustomerTravelData(Long userId, Long travelScope);

    /**
     * 获取客户生数据
     *
     * @param userId        用户id
     * @param birthdayScope 查询范围
     * @return  客户生日数据
     */
    List<CustomerBirthdayDto> getCustomerBirthdayData(Long userId, Long birthdayScope);


    /**
     * 查询订单号是否存在
     *
     * @param planId 订单号
     * @return  是否
     */
    boolean isPlanExist(String planId);

}
