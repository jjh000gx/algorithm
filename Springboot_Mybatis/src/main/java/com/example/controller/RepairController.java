package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.AESSUtils;
import com.example.utils.ExcelParseWriteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/Repair")
@Slf4j
public class RepairController {
    private static String SQL_FORAT = "UPDATE `open_gw_00`.`lotus_order` SET response='%s' WHERE id=%s;";

    private static String REPLACE_STR = "insurantList\\\\\":[{\\\\\"certificateNo\\\\\":\\\\\"%s\\\\\",\\\\\"certificateType\\\\\":\\\\\"%s\\\\\",\\\\\"isDeleted\\\\\":\\\\\"Normal\\\\\",\\\\\"phoneNo\\\\\":\\\\\"%s\\\\\",\\\\\"policyCustomerType\\\\\":\\\\\"%s\\\\\",\\\\\"policyUserType\\\\\":\\\\\"INSURANT\\\\\",\\\\\"userName\\\\\":\\\\\"%s\\\\\"}]";

    @PostMapping("/lotusOrder")
    @ResponseBody
    public String lotusOrder(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        if (file1.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        String fileName2 = file2.getOriginalFilename();
        System.out.println(fileName1);
        try {
            FileWriter dest = new FileWriter(filePath +"\\prd_dml_open_gw.sql" );
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Map<String,Map<String,Object>>  sanzheMap = new HashMap<String, Map<String, Object>>() {};
            for(Map<String,Object> item : sourceDataList) {
                sanzheMap.put((String) item.get("交易订单号"),item);
            }
            System.out.println(sourceDataList);
            String pattern   = "(914\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*5Q)";
            String pattern1  = "(insurantList\\\\\"\\:\\[\\{)[\\s\\S]+?\\}\\]";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            Pattern r1 = Pattern.compile(pattern1);
            List<Map<String, Object>> sourceDataList2 = ExcelParseWriteUtils.parse(file2.getInputStream(),fileName2);
            for(Map<String,Object> item : sourceDataList2) {
                String response = (String) item.get("response");

                Matcher m = r1.matcher(response);
                if(m.find()) {
                    System.out.println(m.group(0));
                } else {
                   throw new Exception("没有配置");
                }
                Map<String,Object> tmap = sanzheMap.get((String)item.get("channel_order_no"));
                if(null == tmap) {
                    System.out.println((String)item.get("channel_order_no"));
                    throw new Exception((String)item.get("channel_order_no"));
                }
                //System.out.println(tmap);
                String certificateNo = (String)tmap.get("被保人证件号");
                String certificateType = (String)tmap.get("被保人证件类型");
                certificateType  = certificateType.equals("身份证") ? "I" : "TY";
                String phoneNo = (String)tmap.get("被保人电话");
                String policyCustomerType = certificateType.equalsIgnoreCase("I") ? "Person":"Company";
                String userName = (String)tmap.get("被保人姓名");
                String replace = String.format(REPLACE_STR,certificateNo,certificateType,phoneNo,policyCustomerType,userName);
                response = response.replaceAll(pattern,"91440300326594785Q");
                response = response.replaceAll(pattern1,replace);
                response = escapeSql(response);
                //System.out.println(response);
                String wline = String.format(SQL_FORAT , response,(String) item.get("id")) + "\n";
                dest.write(wline);
                //response.replaceFirst("")
            }
            dest.close();

        } catch (Exception e) {
            System.out.println("生产sql失败：" + e.getMessage());
            log.error(null,e);
        }


        return "生产sql结束";
    }

    public String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char src = str.charAt(i);
            switch (src) {
                case '\'':
                    sb.append("''");// hibernate转义多个单引号必须用两个单引号
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                default:
                    sb.append(src);
                    break;
            }
        }
        return sb.toString();
    }

    @PostMapping("/test")
    public String edit(@RequestBody String request) throws Exception {
        log.info("request={}", request);
        String ttt = "fp7Amr+5yr9Dfo04h0aNtCphxFkhJXbeQap8olncvXM=";
       // String response = AESSUtils.decrypt(ttt,se);
        String se = "b430fe4fafab77a5ee8da2e3b526b4d4";
        JSONObject data = new JSONObject();
        data.put("success",true);
        String  uu =  AESSUtils.encrypt(JSONObject.toJSONString(data),se);
        JSONObject r = new JSONObject();

        r.put("serviceName","zhongan.superman.surrender.notify");
        r.put("success",true);
        r.put("errorCode","");
        r.put("errorMsg","");
        r.put("data",uu);
        return JSONObject.toJSONString(data);
    }

}
