package com.wise.gemmes.flosotask.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.wise.gemmes.stockDispatch.service.StockDispatchServiceI;
import com.wise.gemmes.util.baseservlet.BaseServlet;

import net.sf.json.JSONObject;

@WebServlet("/taskCallBackServlet")
public class TaskCallBackServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private StockDispatchServiceI stockDispatchService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // 使用字符流读取客户端发过来的数据
        BufferedReader tReqDataBuf = null;
        // 参数示例
        String code = "";
        try {
            // 使用字符流读取客户端发过来的数据
            tReqDataBuf = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = "";
            StringBuffer tInfoStrBuf = new StringBuffer();
            while ((line = tReqDataBuf.readLine()) != null) {
                tInfoStrBuf.append(line);
            }
            JSONObject json = JSONObject.fromObject(tInfoStrBuf.toString());
            String action = stockDispatchService.agvNextWork(json);
            // 回写信息
            JSONObject rjson = new JSONObject();
            rjson.put("code", code);
            rjson.put("receive", 0);
            rjson.put("action", action);
            this.writeRJison(response, rjson);
        } catch (IOException e) {
            e.printStackTrace();
            // 返回json串
            JSONObject rjson = new JSONObject();
            rjson.put("message", "出错");
            rjson.put("receive", -1);
            this.writeRJison(response, rjson);
        } finally {
            if (tReqDataBuf != null)
                tReqDataBuf.close();
        }
    }
}