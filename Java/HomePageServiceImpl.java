package com.xianbei.cloud.travelcenter.web.impl;

import com.xianbei.cloud.travelcenter.common.entity.dto.HomePageDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.ThisMonthDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.MyPerformanceDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.DepartmentPerformanceDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.CustomerTravelDto;
import com.xianbei.cloud.travelcenter.common.entity.dto.CustomerBirthdayDto;
import com.xianbei.cloud.travelcenter.common.service.HomePageService;
import com.xianbei.cloud.travelcenter.web.dao.HomePageDao;
import com.xianbei.cloud.usercenter.common.entity.Role;
import com.xianbei.cloud.usercenter.common.entity.User;
import com.xianbei.cloud.usercenter.common.service.IUserRoleService;
import com.xianbei.cloud.usercenter.common.service.IUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.Optional;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 首页数据服务接口实现
 */
@RefreshScope
@Service
public class HomePageServiceImpl implements HomePageService {

    /**
     * 首页数据访问接口
     */
    private final HomePageDao homePageDao;
    /**
     * 用户角色关系服务
     */
    @DubboReference
    private IUserRoleService iUserRoleService;
    /**
     * 销售角色
     */
    @Value("${salesStaffRole.roleId}")
    private Long salesStaffRole;

    /**
     * 用户服务
     */
    @DubboReference
    private IUserService iUserService;

    /**
     * 构造函数
     *
     * @param homePageDao 首页数据访问接口
     */
    public HomePageServiceImpl(HomePageDao homePageDao) {
        this.homePageDao = homePageDao;
    }

    @Override
    public HomePageDto getDasboardData(Long userId) {
        //根据用户id获取角色
        List<Role> roleList = iUserRoleService.findRole(userId);
        //判断是否是销售角色
        boolean hasSalesStaffRole = roleList != null && roleList.stream().anyMatch(
                item -> Objects.equals(item.getId(), salesStaffRole));
        HomePageDto homePageDto = new HomePageDto();
        if (hasSalesStaffRole) {
            //我的跟单
            //跟进中
            Map<String, Object> followingUpMap = homePageDao.getDisposePlanCount(userId);
            Long followingUp = (Long) followingUpMap.get("record_count");
            homePageDto.setFollowingUp(followingUp);
            Object planIdsObjOne = followingUpMap.get("plan_ids");
            String planIdsOne = (planIdsObjOne != null) ? planIdsObjOne.toString() : "";
            homePageDto.setFollowingUpIds(planIdsOne);
            //已制作报价
            Map<String, Object> quotationPreparedMap = homePageDao
                    .getQuotationPreparedCount(userId);
            Long quotationPrepared = (Long) quotationPreparedMap.get("record_count");
            homePageDto.setQuotationPrepared(quotationPrepared);
            Object planIdsObjTwo = quotationPreparedMap.get("plan_ids");
            String planIdsTwo = (planIdsObjTwo != null) ? planIdsObjTwo.toString() : "";
            homePageDto.setQuotationPreparedIds(planIdsTwo);
            //已定制路书
            Map<String, Object> customizedRoadBookMap = homePageDao
                    .getCustomizedRoadBookCount(userId);
            Long customizedRoadBook = (Long) customizedRoadBookMap.get("record_count");
            homePageDto.setCustomizedRoadBook(customizedRoadBook);
            Object planIdsObjThree = customizedRoadBookMap.get("plan_ids");
            String planIdsThree = (planIdsObjThree != null) ? planIdsObjThree.toString() : "";
            homePageDto.setCustomizedRoadBookIds(planIdsThree);
            //前期沟通
            Long sum = homePageDao.getEarlyCommunicationCount(userId);
            Long earlyCommunication = followingUp - customizedRoadBook - quotationPrepared + sum;
            Set<String> followingUpIdsSet = new HashSet<>(Arrays
                    .asList(planIdsOne.split(",")));
            Set<String> quotationPreparedIdsSet = new HashSet<>(Arrays
                    .asList(planIdsTwo.split(",")));
            Set<String> customizedRoadBookIdsSet = new HashSet<>(Arrays
                    .asList(planIdsThree.split(",")));
            followingUpIdsSet.removeAll(quotationPreparedIdsSet);
            followingUpIdsSet.removeAll(customizedRoadBookIdsSet);
            homePageDto.setEarlyCommunicationIds(String.join(",", followingUpIdsSet));
            homePageDto.setEarlyCommunication(earlyCommunication);
            //暂未采购订单
            Map<String, Object> pendingPurchaseOrderMap = homePageDao
                    .getPendingPurchaseOrderCount(userId);
            Long pendingPurchaseOrder = (Long) pendingPurchaseOrderMap.get("record_count");
            homePageDto.setPendingPurchaseOrder(pendingPurchaseOrder);
            Object planIdsObjFour = pendingPurchaseOrderMap.get("plan_ids");
            String planIdsFour = (planIdsObjFour != null) ? planIdsObjFour.toString() : "";
            homePageDto.setPendingPurchaseOrderIds(planIdsFour);
            //暂未采购产品
            Map<String, Object> pendingPurchaseProductMap = homePageDao
                    .getPendingPurchaseProductCount(userId);
            Long pendingPurchaseProduct = (Long) pendingPurchaseProductMap.get("record_count");
            homePageDto.setPendingPurchaseProduct(pendingPurchaseProduct);
            Object planIdsObjFive = pendingPurchaseProductMap.get("plan_ids");
            String planIdsFive = (planIdsObjFive != null) ? planIdsObjFive.toString() : "";
            homePageDto.setPendingPurchaseProductIds(planIdsFive);
            //已成单未完成-待出行
            Map<String, Object> toBeTraveledMap = homePageDao.getToBeTraveledCount(userId);
            Long toBeTraveled = (Long) toBeTraveledMap.get("record_count");
            homePageDto.setToBeTraveled(toBeTraveled);
            Object planIdsObjSix = toBeTraveledMap.get("plan_ids");
            String planIdsSix = (planIdsObjSix != null) ? planIdsObjSix.toString() : "";
            homePageDto.setToBeTraveledIds(planIdsSix);
            //已成单未完成-已出行
            Map<String, Object> traveledMap = homePageDao.getTraveledCount(userId);
            Long traveled = (Long) traveledMap.get("record_count");
            homePageDto.setTraveled(traveled);
            Object planIdsObjSeven = traveledMap.get("plan_ids");
            String planIdsSeven = (planIdsObjSeven != null) ? planIdsObjSeven.toString() : "";
            homePageDto.setTraveledIds(planIdsSeven);
        } else {
            resetHomePageDtoForNonSalesRole(homePageDto);
        }
        return homePageDto;
    }

    /**
     * 获取本月数据
     *
     * @param userId      用户id
     * @param flag  查询范围
     */
    @Override
    public ThisMonthDto getThisMonthData(Long userId, Long flag) {
        //根据用户id获取角色
        List<Role> roleList = iUserRoleService.findRole(userId);
        //判断是否是销售角色
        boolean hasSalesStaffRole = roleList != null && roleList.stream().anyMatch(
                item -> Objects.equals(item.getId(), salesStaffRole));
        ThisMonthDto thisMonthDto = new ThisMonthDto();
        if (hasSalesStaffRole) {
            Long correspondingDemand = homePageDao.getCorrespondingDemandCount(userId);
            thisMonthDto.setCorrespondingDemand(correspondingDemand);
            Long completedOrder = homePageDao.getCompletedOrderCount(userId);
            thisMonthDto.setCompletedOrder(completedOrder);
            Map<String, BigDecimal> flowAndNetGrossProfit = homePageDao
                    .getFlowAndNetGrossProfit(userId, flag);
            thisMonthDto.setFlow(flowAndNetGrossProfit.get("turnover"));
            thisMonthDto.setGrossProfit(flowAndNetGrossProfit.get("grossProfit"));
            thisMonthDto.setNetGrossProfitRate(flowAndNetGrossProfit.get("netGrossProfitRate"));
            Long ranking = calculateRankingByNetGrossProfit(userId, flag);
            thisMonthDto.setRanking(ranking);
        } else {
            thisMonthDto.setCorrespondingDemand(0L);
            thisMonthDto.setCompletedOrder(0L);
            thisMonthDto.setFlow(BigDecimal.ZERO);
            thisMonthDto.setGrossProfit(BigDecimal.ZERO);
            thisMonthDto.setNetGrossProfitRate(BigDecimal.ZERO);
            thisMonthDto.setRanking(0L);
        }
        return thisMonthDto;
    }

    /**
     * 获取我的业绩数据
     *
     * @param flag 查询范围
     * @param userId      用户id
     */
    @Override
    public List<MyPerformanceDto> getMyPerformanceData(Long userId, Long flag) {
        //根据用户id获取角色
        List<Role> roleList = iUserRoleService.findRole(userId);
        //判断是否是销售角色
        boolean hasSalesStaffRole = roleList != null && roleList.stream().anyMatch(
                item -> Objects.equals(item.getId(), salesStaffRole));
        List<MyPerformanceDto> myPerformanceDtoList = new ArrayList<>();
        if (hasSalesStaffRole) {
            Map<String, String[]> ranges = getDateRanges();
            for (Map.Entry<String, String[]> entry : ranges.entrySet()) {
                String[] range = entry.getValue();
                MyPerformanceDto data = Optional.ofNullable(homePageDao.getMyPerformanceData(userId,
                                range[0], range[1], flag))
                        .orElseGet(this::createDefaultMyPerformanceDto);
                data.setDateRange(entry.getKey());
                // rank 由 Java 逻辑计算
                data.setRank(calculateRankForUser(userId, range[0], range[1], flag));
                myPerformanceDtoList.add(data);
            }
        }
        return myPerformanceDtoList;
    }

    /**
     * 获取部门业绩数据
     *
     * @param flag 查询范围
     * @param userId      用户id
     */
    @Override
    public List<DepartmentPerformanceDto> getDepartmentPerformanceData(Long userId, Long flag) {
        //根据用户id获取角色
        List<Role> roleList = iUserRoleService.findRole(userId);
        //判断是否是销售角色
        boolean hasSalesStaffRole = roleList != null && roleList.stream().anyMatch(
                item -> Objects.equals(item.getId(), salesStaffRole));
        List<DepartmentPerformanceDto> departmentList = new ArrayList<>();
        if (hasSalesStaffRole) {
            List<User> departmentUsers = iUserService.findByRoleId(salesStaffRole);
            List<Long> userIds = departmentUsers.stream().map(User::getId)
                    .collect(Collectors.toList());

            for (Map.Entry<String, String[]> entry : getDepartmentDateRanges().entrySet()) {
                String[] range = entry.getValue();
                List<DepartmentPerformanceDto> departmentDtos = homePageDao
                        .getDepartmentUsersPerformanceDataByTimeRange(userIds,
                                range[0], range[1], flag);
                departmentDtos.get(0).setDateRange(entry.getKey());
                departmentList.addAll(departmentDtos);
            }

        }
        return departmentList;
    }

    private static Map<String, String[]> getDateRanges() {
        Map<String, String[]> ranges = new LinkedHashMap<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 今天
        String todayStart = "'" + now.format(formatter) + "'";
        String todayEnd = "'" + now.format(formatter) + "'";

        // 最近7天
        String lastSevenDaysStart = "'" + now.minusDays(6).format(formatter) + "'";

        // 最近30天
        String lastThirtyDaysStart = "'" + now.minusDays(29).format(formatter) + "'";

        // 最近90天
        String lastNintyDaysStart = "'" + now.minusDays(89).format(formatter) + "'";

        ranges.put("today", new String[]{todayStart, todayEnd});
        ranges.put("last7Days", new String[]{lastSevenDaysStart, todayEnd});
        ranges.put("last30Days", new String[]{lastThirtyDaysStart, todayEnd});
        ranges.put("last90Days", new String[]{lastNintyDaysStart, todayEnd});
        return ranges;
    }

    private static Map<String, String[]> getDepartmentDateRanges() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 今日
        String todayStart = "'" + now.format(formatter) + "'";
        String todayEnd = "'" + now.format(formatter) + "'";

        // 昨日
        String yesterdayStart = "'" + now.minusDays(1).format(formatter) + "'";
        String yesterdayEnd = "'" + now.minusDays(1).format(formatter) + "'";

        // 最近7天
        String lastSevenDaysStart = "'" + now.minusDays(6).format(formatter) + "'";

        // 本月开始日期
        LocalDate thisMonthStart = now.withDayOfMonth(1);

        // 本月结束日期
        LocalDate thisMonthEnd = now.with(TemporalAdjusters.lastDayOfMonth());


        Map<String, String[]> ranges = new LinkedHashMap<>();
        ranges.put("today", new String[]{todayStart, todayEnd});
        ranges.put("yesterday", new String[]{yesterdayStart, yesterdayEnd});
        ranges.put("last7Days", new String[]{lastSevenDaysStart, todayEnd});
        ranges.put("thisMonth", new String[]{"'" + thisMonthStart.format(formatter) + "'",
                "'" + thisMonthEnd.format(formatter) + "'"});
        return ranges;
    }


    private MyPerformanceDto createDefaultMyPerformanceDto() {
        MyPerformanceDto data = new MyPerformanceDto();
        data.setDemandQuntity(0L);
        data.setCompletedOrder(0L);
        data.setCompletedOrdeRate(BigDecimal.ZERO);
        data.setTurnover(BigDecimal.ZERO);
        data.setNetGrossProfit(BigDecimal.ZERO);
        data.setNetGrossProfitRate(BigDecimal.ZERO);
        return data;
    }

    /**
     * 计算指定用户在给定时间范围内的净毛利排名（基于部门内所有用户）
     *
     * @param userId    用户id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param flag   查询范围
     * @return  净毛利排名
     */
    private Integer calculateRankForUser(Long userId, String startDate, String endDate, Long flag) {
        // 获取部门下所有销售用户
        List<User> users = iUserService.findByRoleId(salesStaffRole);
        if (users == null || users.isEmpty()) {
            return 1; // 无用户时默认排第1
        }

        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        // 查询所有用户的业绩数据
        List<MyPerformanceDto> allData = homePageDao
                .getDepartmentUsersPerformanceData(userIds, startDate, endDate, flag);
        if (allData == null || allData.isEmpty()) {
            return 1; // 无数据时默认排第1
        }

        // 按照 netGrossProfit 降序排序，并处理 null 值
        allData.sort(Comparator.comparing(
                MyPerformanceDto::getNetGrossProfit,
                Comparator.nullsLast(Comparator.naturalOrder())
        ).reversed());

        // 计算密集排名（如 1, 2, 2, 3 模式）
        BigDecimal prevProfit = null;
        int currentRank = 1; // 当前排名

        for (MyPerformanceDto data : allData) {
            BigDecimal profit = data.getNetGrossProfit();
            // 使用 compareTo 比较 BigDecimal 值，处理 null 和精度问题
            if (prevProfit != null && profit != null && profit.compareTo(prevProfit) < 0) {
                currentRank++; // 只有当当前值小于前一个值时，排名增加
            }
            prevProfit = profit;

            // 找到当前用户，返回排名
            if (data.getUserId().equals(userId)) {
                return currentRank;
            }
        }

        return 1; // 未找到用户时默认排第1
    }



    /**
     * 非销售角色时，重置首页数据
     *
     * @param dto 首页数据
     */
    private void resetHomePageDtoForNonSalesRole(HomePageDto dto) {
        dto.setFollowingUp(0L);
        dto.setEarlyCommunication(0L);
        dto.setCustomizedRoadBook(0L);
        dto.setQuotationPrepared(0L);
        dto.setPendingPurchaseOrder(0L);
        dto.setPendingPurchaseProduct(0L);
    }


    /**
     * 计算当前用户所属角色下所有用户的净毛利排名
     *
     * @param currentUserId 当前用户ID
     * @param flag 查询范围
     * @return  净毛利排名
     */
    private Long calculateRankingByNetGrossProfit(Long currentUserId, Long flag) {
        // 1. 获取该角色下的所有用户
        List<User> users = iUserService.findByRoleId(salesStaffRole);
        if (users.isEmpty()) {
            return null; // 无用户时直接返回
        }

        // 2. 存储用户ID与净毛利的映射关系（确保null转换为ZERO）
        Map<Long, BigDecimal> userNetGrossProfitMap = new HashMap<>();
        for (User user : users) {
            Map<String, BigDecimal> result = homePageDao
                    .getFlowAndNetGrossProfit(user.getId(), flag);
            // 关键修改：将null强制转换为BigDecimal.ZERO，避免后续null问题
            userNetGrossProfitMap.put(user.getId(),
                    result.getOrDefault("grossProfit", BigDecimal.ZERO));
        }

        // 3. 按净毛利降序排序，净毛利相同时按用户ID升序
        List<Map.Entry<Long, BigDecimal>> sortedUsers =
                new ArrayList<>(userNetGrossProfitMap.entrySet());
        sortedUsers.sort(Map.Entry.<Long, BigDecimal>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry::getKey));

        // 4. 计算密集排名（如 1, 2, 2, 3 模式）
        Map<Long, Long> userIdToRankMap = new HashMap<>();
        if (sortedUsers.isEmpty()) {
            return null;
        }

        long currentRank = 1; // 当前排名
        BigDecimal previousValue = sortedUsers.get(0).getValue(); // 前一个值

        // 处理第一个元素
        userIdToRankMap.put(sortedUsers.get(0).getKey(), currentRank);

        // 从第二个元素开始处理
        for (int i = 1; i < sortedUsers.size(); i++) {
            Map.Entry<Long, BigDecimal> entry = sortedUsers.get(i);
            BigDecimal currentValue = entry.getValue();

            if (currentValue.compareTo(previousValue) < 0) {
                // 当前值小于前一个值，排名增加1
                currentRank++;
                // 记录新的前一个值
                previousValue = currentValue;
            }
            // 无论值是否相同，都赋予当前排名
            userIdToRankMap.put(entry.getKey(), currentRank);
        }

        // 5. 返回当前用户的排名
        return userIdToRankMap.getOrDefault(currentUserId, null);
    }


    /**
     * 获取客户出行相关时间范围
     *
     * @return 获取客户出行相关时间范围
     */
    private static Map<String, String[]> getDateRangesForTravel() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 明日出发
        String tomorrowStart = "'" + today.plusDays(1).format(formatter) + "'";
        String tomorrowEnd = tomorrowStart;

        // 3日内出发
        String threeDaysStart = "'" + today.format(formatter) + "'";
        String threeDaysEnd = "'" + today.plusDays(2).format(formatter) + "'";

        // 7日内出发
        String sevenDaysStart = "'" + today.format(formatter) + "'";
        String sevenDaysEnd = "'" + today.plusDays(6).format(formatter) + "'";

        // 14日内出发
        String fourteenDaysStart = "'" + today.format(formatter) + "'";
        String fourteenDaysEnd = "'" + today.plusDays(13).format(formatter) + "'";

        Map<String, String[]> ranges = new LinkedHashMap<>();
        ranges.put("tomorrow", new String[]{tomorrowStart, tomorrowEnd});        // 明日出发
        ranges.put("within3Days", new String[]{threeDaysStart, threeDaysEnd});   // 3日内出发
        ranges.put("within7Days", new String[]{sevenDaysStart, sevenDaysEnd});   // 7日内出发
        ranges.put("within14Days", new String[]{fourteenDaysStart, fourteenDaysEnd}); // 14日内出发

        return ranges;
    }

    private static Map<String, String[]> getDateRangesInTransit() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 今日出行
        String todayStart = "'" + today.format(formatter) + "'";
        String todayEnd = todayStart;

        // 行程中（start_date <= today AND end_date >= today）
        String inTransitDate = "'" + today.format(formatter) + "'";

        // 今日回程
        String todayReturnDate = todayStart;

        // 回程7日内
        String returnSevenDaysStart = todayStart;
        String returnSevenDaysEnd = "'" + today.plusDays(6).format(formatter) + "'";

        Map<String, String[]> ranges = new LinkedHashMap<>();
        ranges.put("todayTravel",
                new String[]{todayStart, todayEnd, "todayTravel"});           // 今日出行
        ranges.put("inTransit",
                new String[]{inTransitDate, inTransitDate, "inTransit"});                    // 行程中
        ranges.put("todayReturn", new String[]{todayReturnDate,
                todayReturnDate, "todayReturn"});                // 今日回程
        ranges.put("returnWithin7Days", new String[]{returnSevenDaysStart,
                returnSevenDaysEnd, "returnWithin7Days"}); // 回程7日内

        return ranges;
    }

    /**
     * 获取客户出行数据
     *
     * @param userId      用户id
     * @param travelScope 查询范围
     */
    @Override
    public List<CustomerTravelDto> getCustomerTravelData(Long userId, Long travelScope) {
        //根据用户id获取角色
        List<Role> roleList = iUserRoleService.findRole(userId);
        //判断是否是销售角色
        boolean hasSalesStaffRole = roleList != null && roleList.stream()
                .anyMatch(item -> Objects.equals(item.getId(), salesStaffRole));
        List<CustomerTravelDto> customerTravelDtoList = new ArrayList<>();
        List<User> users = iUserService.findByRoleId(salesStaffRole);
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        if (hasSalesStaffRole) {
            List<Map.Entry<String, String[]>> rangeEntries = new ArrayList<>(
                    getDateRangesForTravel().entrySet());
            List<Map.Entry<String, String[]>> rangeTwoEntries = new ArrayList<>(
                    getDateRangesInTransit().entrySet());

            int size = Math.min(rangeEntries.size(), rangeTwoEntries.size());

            if (travelScope == 1L) {
                for (int i = 0; i < size; i++) {
                    Map.Entry<String, String[]> entry = rangeEntries.get(i);
                    Map.Entry<String, String[]> entryTwo = rangeTwoEntries.get(i);

                    CustomerTravelDto dto = new CustomerTravelDto();

                    dto.setDateRange(entry.getKey());
                    dto.setTravelOrderCount(homePageDao.getMyCustomerTravelCount(userId,
                            entry.getValue()[0], entry.getValue()[1]));

                    dto.setTravelDateRange(entryTwo.getKey());
                    dto.setInTransitOrderCount(homePageDao.getMyCustomerInTransitCount(userId,
                            entryTwo.getValue()[0],
                            entryTwo.getValue()[1],
                            entryTwo.getValue()[2]));

                    customerTravelDtoList.add(dto);
                }
            } else if (travelScope == 0L) {
                for (int i = 0; i < size; i++) {
                    Map.Entry<String, String[]> entry = rangeEntries.get(i);
                    Map.Entry<String, String[]> entryTwo = rangeTwoEntries.get(i);

                    CustomerTravelDto dto = new CustomerTravelDto();

                    dto.setDateRange(entry.getKey());
                    dto.setTravelOrderCount(homePageDao.getDepartmentCustomerTravelCount(userIds,
                            entry.getValue()[0], entry.getValue()[1]));

                    dto.setTravelDateRange(entryTwo.getKey());
                    dto.setInTransitOrderCount(homePageDao
                            .getDepartmentCustomerInTransitCount(userIds,
                                    entryTwo.getValue()[0],
                                    entryTwo.getValue()[1],
                                    entryTwo.getValue()[2]));

                    customerTravelDtoList.add(dto);
                }
            }
        }

        return customerTravelDtoList;
    }

    private static Map<String, String[]> getBirthdayDateRanges() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        // 今日
        String todayStart = "'" + today.format(formatter) + "'";
        String todayEnd = "'" + today.format(formatter) + "'";

        // 3日内：今天 ~ 今天 + 3天
        String threeDaysEnd = "'" + today.plusDays(2).format(formatter) + "'";

        // 7日内：今天 ~ 今天 + 7天
        String sevenDaysEnd = "'" + today.plusDays(6).format(formatter) + "'";

        Map<String, String[]> ranges = new LinkedHashMap<>();
        ranges.put("today", new String[]{todayStart, todayEnd});           // 今天
        ranges.put("last3Days", new String[]{todayStart, threeDaysEnd});   // 今天 ~ +3天
        ranges.put("last7Days", new String[]{todayStart, sevenDaysEnd});   // 今天 ~ +7天

        return ranges;
    }


    /**
     * 获取客户生日数据
     *
     * @param userId        用户id
     * @param birthdayScope 查询范围
     */
    @Override
    public List<CustomerBirthdayDto> getCustomerBirthdayData(Long userId, Long birthdayScope) {
        List<CustomerBirthdayDto> customerBirthdayDtoList = new ArrayList<>();
        List<User> users = iUserService.findByRoleId(salesStaffRole);
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        //根据用户id获取角色
        List<Role> roleList = iUserRoleService.findRole(userId);
        //判断是否是销售角色
        boolean hasSalesStaffRole = roleList != null && roleList.stream()
                .anyMatch(item -> Objects.equals(item.getId(), salesStaffRole));
        if (hasSalesStaffRole) {
            Map<String, String[]> ranges = getBirthdayDateRanges();
            for (Map.Entry<String, String[]> entry : ranges.entrySet()) {
                CustomerBirthdayDto customerBirthdayDto = new CustomerBirthdayDto();
                String key = entry.getKey();
                String[] value = entry.getValue();
                customerBirthdayDto.setDateRange(key);
                if (birthdayScope == 1L) {
                    Map<String, Object> myMap = homePageDao
                            .getMyCustomerBirthdayCount(userId, value[0], value[1]);
                    Long birthdayCount = Long.valueOf(myMap.get("record_count").toString());
                    customerBirthdayDto.setBirthdayCount(birthdayCount);
                    Object myPlanIdsObj = myMap.get("plan_ids");
                    String myPlanIds = (myPlanIdsObj != null) ? myPlanIdsObj.toString() : "";
                    customerBirthdayDto.setIds(myPlanIds);

                } else if (birthdayScope == 0L) {
                    Map<String, Object> departMap = homePageDao
                            .getDepartmentCustomerBirthdayCount(userIds, value[0], value[1]);
                    Long birthdayCount = (Long) departMap.get("record_count");
                    customerBirthdayDto.setBirthdayCount(birthdayCount);
                    Object departPlanIdsObj = departMap.get("plan_ids");
                    String departPlanIds = (departPlanIdsObj != null) ? departPlanIdsObj
                            .toString() : "";
                    customerBirthdayDto.setIds(departPlanIds);
                }
                customerBirthdayDtoList.add(customerBirthdayDto);
            }
        }
        return customerBirthdayDtoList;
    }

    /**
     * 订单是否存在
     *
     * @param planId 订单号
     * @return 是或否
     */
    @Override
    public boolean isPlanExist(String planId) {
        int count = homePageDao.isPlanExist(Long.valueOf(planId));
        return count > 0;
    }
}
