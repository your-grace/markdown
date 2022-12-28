package com.wise.gemmes.stockDispatch.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.HttpUtils;
/**
 * 通用flos工具类
 */
public class FlosUtils {
    /**
     * flos地址前缀
     */
    private static final String prefixFlosURL = "http://10.2.176.32:8397";
    /**
     * 创建任务
     */
    public static String creatFlosTask(String param){
        JSONObject result = JSONObject.fromObject(HttpUtils.sendPost(prefixFlosURL+"/v1/order", param));
        return  result.toString();
    }
    /**
     * 追加步骤
     */
    public static String appendFlosSteps(String param){
        JSONObject result = JSONObject.fromObject(HttpUtils.sendPost(prefixFlosURL+"/v1/steps", param));
        return  result.toString();
    }
    /**
     * 改变封口
     */
    public static String changeFlosComplete(String param){
        JSONObject result = JSONObject.fromObject(HttpUtils.sendPut(prefixFlosURL+"/v1/order", param));
        return  result.toString();
    }
    /**
     * 查询任务
     */
    public static String queryFlosTask(String param){
        JSONObject result = JSONObject.fromObject(HttpUtils.sendGet(prefixFlosURL+"/v1/order", param));
        return  result.toString();
    }
    /**
     * 确认步骤
     */
    public static String confirmFlosSteps(String param){
        JSONObject result = JSONObject.fromObject(HttpUtils.sendPost(prefixFlosURL+"/v1/confirm", param));
        return  result.toString();
    }
}
