package com.code.report.com.controller;

import com.code.report.com.entity.OrderEntity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DemoControlelr {

    @Autowired
    private ResourceLoader resourceLoader;

    @ResponseBody
    @RequestMapping("/pdf")
    public void   report( HttpServletResponse response){
        String path =null;
        try {
             path =resourceLoader.getResource("classpath:templates/test_A4.jrxml").getURI().getPath();
            JasperReport jasperReport = JasperCompileManager.compileReport(path);

            List<OrderEntity> orders = new ArrayList<>();
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            orders.add(new OrderEntity("参数1","参数2","参数3","参数4","参数5"));
            JRDataSource dataSource = new JRBeanCollectionDataSource(orders);
            Map<String,Object> param = new HashMap<>();
            param.put("company_name","东莞市聚特易五金制品有限公司");
            param.put("line","一车间");
            param.put("startTime","2019-04-24");
            param.put("endTime","2019-04-24");
            param.put("orderDataTable",dataSource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport,param,new JREmptyDataSource());
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"users.pdf\""));
            OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(print,out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
