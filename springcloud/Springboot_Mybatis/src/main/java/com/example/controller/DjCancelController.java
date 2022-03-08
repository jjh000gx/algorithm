package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.ExcelParseWriteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Repair")
@Slf4j
public class DjCancelController {

    private static String ORDER_SQL_FORAT = "UPDATE `front_end_00`.`dji_request_order` SET `rt_status`=2,`asyn_status`=2,`gmt_modified`=now(),`modifier`='jiangjianhe' WHERE `id`=%s;\n";

    @PostMapping("/cancel")
    @ResponseBody
    public String cancel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            FileWriter dest = new FileWriter(filePath +"\\更新大疆请求单.sql");
            FileWriter dest1 = new FileWriter(filePath +"\\更新大疆请求单11.sql");
            List<Map<String, Object>> dataList = ExcelParseWriteUtils.parse(file.getInputStream(),fileName);
            for(Map<String,Object> item : dataList) {
                Double id                = (Double) item.get("id");

                String  response        = (String) item.get("response");
                JSONObject resJson = JSONObject.parseObject(response);
                JSONObject resJson1 =  JSONObject.parseObject(resJson.getString("response"));
                JSONObject  machineryBreakIns = resJson1.getObject("machineryBreakIns", JSONObject.class);
                String wline1 = machineryBreakIns.getString("certNo") + "," +machineryBreakIns.getString("premium")
                        + "," +machineryBreakIns.getString("SN")+"\n";

                String wline = String.format(ORDER_SQL_FORAT,id.intValue());
                dest.write(wline);
                dest1.write(wline1);
            }
            dest.close();
            dest1.close();
        } catch (Exception e) {
            System.out.println("生产订正sql失败：" + e.getMessage());
            log.error(null,e);
        }

        return "生产订正sql成功";
    }
}
