#### 关联递归查询

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.iocoder.gyimom.module.process.dal.mysql.process.ProcessMapper">
    <select id="selectPageNew"
            resultType="cn.iocoder.gyimom.module.process.controller.admin.process.vo.ProcessPageItemRespVo"
            parameterType="java.util.Map">
        SELECT p.*,dept.name as departName
        FROM gm_process p
        left join system_dept dept on dept.id = p.depart_id
        where p.deleted = 0
        <if test="pageReqVO.processNumber != null and pageReqVO.processNumber != ''">
            AND p.process_number LIKE CONCAT('%',#{pageReqVO.processNumber},'%')
        </if>
        <if test="pageReqVO.processName != null and pageReqVO.processName != ''">
            AND p.process_name LIKE CONCAT('%',#{pageReqVO.processName},'%')
        </if>
        <if test="pageReqVO.processDesc != null and pageReqVO.processDesc != ''">
            AND p.process_desc LIKE CONCAT('%',#{pageReqVO.processDesc},'%')
        </if>
        <if test="pageReqVO.departId != null">
            AND p.depart_id IN (
            <include refid="getAllDepart"/>
            )
        </if>
        <if test="pageReqVO.processFocus != null">
            AND p.process_focus = #{pageReqVO.processFocus}
        </if>
        <if test="pageReqVO.processCheck != null">
            AND p.process_check = #{pageReqVO.processCheck}
        </if>
        <if test="pageReqVO.updateStatus != null">
            AND p.update_status = #{pageReqVO.updateStatus}
        </if>
        <if test="pageReqVO.createTime != null and pageReqVO.createTime.length > 0">
            AND p.create_time <![CDATA[ >= ]]> #{pageReqVO.createTime[0]}
            AND p.create_time <![CDATA[ <= ]]> #{pageReqVO.createTime[1]}
        </if>
        ORDER BY p.id DESC
    </select>
    <sql id="getAllDepart">
        WITH RECURSIVE cte AS (
            SELECT * FROM system_dept WHERE id = #{pageReqVO.departId}
            UNION ALL
            SELECT d.* FROM system_dept d JOIN cte ON d.parent_id = cte.id
        )
        SELECT id FROM cte
    </sql>
</mapper>
```

#### java

```java
//controller
@GetMapping("/page")
@Operation(summary = "获得工序信息分页")
@PreAuthorize("@ss.hasPermission('process:process:query')")
public CommonResult<PageResult<ProcessPageItemRespVo>> getProcessPage(@Valid ProcessPageReqVO pageVO) {
    PageResult<ProcessPageItemRespVo> pageResult = processService.getProcessNewPage(pageVO);
    if (CollUtil.isEmpty(pageResult.getList())) {
        return success(new PageResult<>(pageResult.getTotal())); // 返回空
    }
    return success(pageResult);
}
//Service
PageResult<ProcessPageItemRespVo> getProcessNewPage(ProcessPageReqVO pageReqVO);
//ServiceImpl
@Override
public PageResult<ProcessPageItemRespVo> getProcessNewPage(ProcessPageReqVO pageReqVO) {
    Page page = new Page(pageReqVO.getPageNo(), pageReqVO.getPageSize());
    List<ProcessPageItemRespVo> list = processMapper.selectPageNew(page, pageReqVO);
    PageResult<ProcessPageItemRespVo> pageResult = new PageResult<ProcessPageItemRespVo>();
    pageResult.setList(list);
    pageResult.setTotal(page.getTotal());
    return pageResult;
}
//MapperUtil
List<ProcessPageItemRespVo> selectPageNew(@Param("page") Page page,@Param("pageReqVO") ProcessPageReqVO pageReqVO);
//mybatis-plus拦截器根据mapper
public static <T> Page<T> buildPage(PageParam pageParam) {
    return buildPage(pageParam, null);
}

public static <T> Page<T> buildPage(PageParam pageParam, Collection<SortingField> sortingFields) {
    // 页码 + 数量
    Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
    // 排序字段
    if (!CollectionUtil.isEmpty(sortingFields)) {
        page.addOrder(sortingFields.stream().map(sortingField -> SortingField.ORDER_ASC.equals(sortingField.getOrder()) ?
                    OrderItem.asc(sortingField.getField()) : OrderItem.desc(sortingField.getField()))
                    .collect(Collectors.toList()));
    }
    return page;
}
//BaseMapperX
default PageResult<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
    // MyBatis Plus 查询
    IPage<T> mpPage = MyBatisUtils.buildPage(pageParam);
    selectPage(mpPage, queryWrapper);
    // 转换返回
    return new PageResult<>(mpPage.getRecords(), mpPage.getTotal());
}
```

#### 单表list

```java
default List<RouteDO> selectList(RouteExportReqVO reqVO) {
    return selectList(new LambdaQueryWrapperX<RouteDO>()
                      .likeIfPresent(RouteDO::getRouteNumber, reqVO.getRouteNumber())
                      .likeIfPresent(RouteDO::getRouteName, reqVO.getRouteName())
                      .eqIfPresent(RouteDO::getUseState, reqVO.getUseState())
                      .eqIfPresent(RouteDO::getUpdateStatus, reqVO.getUpdateStatus())
                      .eqIfPresent(RouteDO::getFinterid, reqVO.getFinterid())
                      .betweenIfPresent(RouteDO::getCreateTime, reqVO.getCreateTime())
                      .orderByDesc(RouteDO::getId));
}
```

#### 单表分页

```java
default PageResult<RouteDO> selectPage2(RoutePageReqVO reqVO) {
    return selectPage(reqVO, new LambdaQueryWrapperX<RouteDO>()
                      .likeIfPresent(RouteDO::getRouteNumber, reqVO.getRouteNumber())
                      .likeIfPresent(RouteDO::getRouteName, reqVO.getRouteName())
                      .eqIfPresent(RouteDO::getUseState, reqVO.getUseState())
                      .eqIfPresent(RouteDO::getUpdateStatus, reqVO.getUpdateStatus())
                      .eqIfPresent(RouteDO::getFinterid, reqVO.getFinterid())
                      .betweenIfPresent(RouteDO::getCreateTime, reqVO.getCreateTime())
                      .orderByDesc(RouteDO::getId));
}
```

#### 关联分页查询

```java
default PageResult<RouteRespVO> selectPage3(RoutePageReqVO reqVO) {
    MPJLambdaWrapper<RouteDO> query = new MPJLambdaWrapper<RouteDO>();
    query.selectAll(RouteDO.class);
    query.eq(RouteDO::getUseState, 1);
    query.selectAs(MaterialsDO::getMaterialNumber, RouteRespVO::getMaterialNumber);
    query.selectAs(MaterialsDO::getMaterialName, RouteRespVO::getMaterialName);
    query.leftJoin(RouteMaterialDO.class, RouteMaterialDO::getProcessrouteId, RouteDO::getId);
    query.leftJoin(MaterialsDO.class, MaterialsDO::getId, RouteMaterialDO::getMaterialId);
    if (StrUtil.isNotBlank(reqVO.getRouteNumber())) {
        query.like(RouteDO::getRouteNumber, reqVO.getRouteNumber());
    }
    if (StrUtil.isNotBlank(reqVO.getRouteName())) {
        query.like(RouteDO::getRouteName, reqVO.getRouteName());
    }
    if (reqVO.getUseState() != null) {
        query.eq(RouteDO::getUseState, reqVO.getUseState());
    }
    if (reqVO.getUpdateStatus() != null) {
        query.eq(RouteDO::getUpdateStatus, reqVO.getUpdateStatus());
    }
    if (StrUtil.isNotBlank(reqVO.getMaterialNumber())) {
        query.like(MaterialsDO::getMaterialNumber, reqVO.getMaterialNumber());
    }
    if (StrUtil.isNotBlank(reqVO.getMaterialName())) {
        query.like(MaterialsDO::getMaterialName, reqVO.getMaterialName());
    }
    query.orderByDesc(RouteDO::getId);
    Page<RouteRespVO> listPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize())
                                                , RouteRespVO.class, query);
    PageResult<RouteRespVO> pageResult = new PageResult<RouteRespVO>();
    pageResult.setTotal(listPage.getTotal());
    pageResult.setList(listPage.getRecords());
    return pageResult;
}
```

