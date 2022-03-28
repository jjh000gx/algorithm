package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.DateUtils;
import com.example.utils.ExcelParseWriteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/Djnewrate")
@Slf4j
public class DjNewRateController {
    private static String NEWRATE_FORAT = "%s,%s,%s\n";

    @PostMapping("/newRate")
    @ResponseBody
    public String newRate(@RequestParam("file1") MultipartFile file1,  @RequestParam("packageId") String packageId) {
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        System.out.println(fileName1);
        try {
            FileWriter dest = new FileWriter(filePath +"\\newrate_"+ packageId + ".txt");
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Set<Map<String,String>> newfactorMap     = new HashSet<Map<String,String>>();
            for(Map<String,Object> item : sourceDataList) {
                String airplaneType        = (String) item.get("airplaneType");
                Map<String,String> titem   = new HashMap<>();
                String amount = String.valueOf(item.get("premium"));
                if(amount.lastIndexOf(".0") > 0) {
                    amount = amount.substring(0, amount.lastIndexOf(".0"));
                }
                String sumInsured = String.valueOf(item.get("sumInsured"));
                if(sumInsured.lastIndexOf(".0") > 0) {
                    sumInsured = sumInsured.substring(0, sumInsured.lastIndexOf(".0"));
                }
                titem.put("premium",amount);
                titem.put("sumInsured",sumInsured);
                titem.put("airplaneType", airplaneType);
                newfactorMap.add(titem);
            }
            for(Map<String,String> item : newfactorMap) {
                String wline = String.format(NEWRATE_FORAT, item.get("airplaneType"),item.get("premium"),item.get("sumInsured"));
                dest.write(wline);
            }
            dest.close();
        } catch (Exception e) {
            System.out.println("生产新费率失败：" + e.getMessage());
            log.error(null,e);
        }

        return "生产新费率结束";
    }


    @PostMapping("/sanzhe")
    @ResponseBody
    public String sanzhe(@RequestParam("file1") MultipartFile file1,@RequestParam("packageId") String packageId) {
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        System.out.println(fileName1);
        try {
            FileWriter dest = new FileWriter(filePath +"\\newrate_"+ packageId + ".txt");
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Map<String, Map<String,String>>  factorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList) {
                String cal_type = String.valueOf(item.get("cal_type"));
                cal_type = cal_type.substring(0, cal_type.lastIndexOf(".0"));
                if("0".equalsIgnoreCase(cal_type)) {
                    String factor         = (String) item.get("factor");
                    JSONObject factorJson = JSONObject.parseObject(factor);
                    String productModel   = factorJson.getString("productModel");
                    System.out.println(factor);//user.dir指定了当前的路径 　
                    String[] productModelArr = productModel.split(";");
                    for(String model : productModelArr) {
                        String liablity = String.valueOf(item.get("liablity"));
                        String mkey              = model + "_"+liablity;
                        Map<String,String> titem = new HashMap<>();
                        titem.put("airplaneType",  model);
                        titem.put("cal_type",  cal_type);
                        titem.put("liablity",  liablity);
                        String amount = String.valueOf(item.get("amount"));
                        if(amount.lastIndexOf(".0") > 0) {
                            amount = amount.substring(0, amount.lastIndexOf(".0"));
                        }
                        titem.put("amount",  amount);
                        factorMap.put(mkey,titem);
                    }
                }
            }

            for(String tkey :factorMap.keySet()) {
                Map<String, String> titem = factorMap.get(tkey);
                String liablity = titem.get("liablity");
                if("ZXD007".equalsIgnoreCase(liablity)) {
                    String airplaneType = titem.get("airplaneType");
                    String ttkey = airplaneType + "_ZXD008";
                    Map<String, String> ttitem = factorMap.get(ttkey);
                    if(null == ttitem) {
                        System.out.println(airplaneType+ ":错误");
                    }
                    Integer sumInsured = Integer.valueOf(titem.get("amount")) + Integer.valueOf(ttitem.get("amount"));
                    Integer premium = new BigDecimal(sumInsured).divide(new BigDecimal(5000)).intValue();
                    String wline = String.format(NEWRATE_FORAT, airplaneType,premium,sumInsured);
                    dest.write(wline);
                }
            }

            dest.close();
        } catch (Exception e) {
            System.out.println("生产sql失败：" + e.getMessage());
            log.error(null,e);
        }


        return "";
    }
}
