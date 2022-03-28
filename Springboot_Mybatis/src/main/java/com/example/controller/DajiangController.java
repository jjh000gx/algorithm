package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.ExcelParseWriteUtils;
import lombok.extern.slf4j.Slf4j;
import com.example.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Dajiang")
@Slf4j
public class DajiangController {
    private static String SQL_XF_FORAT = "(%s, '51414901_2020new', '1510026201', 'ZXD001', '{\"productModel\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";
    private static String SQL_HY_FORAT = "(%s, '51414824_2020new', '1510026124', 'ZXD001', '{\"productModel\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";
    private static String SQL_YB_FORAT = "(%s, '51414825_2020new', '1510026125', 'ZXD018', '{\"productModel\":\"%s\",\"batchNo\":\"xubao_yb\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";
    private static String SQL_YB1_FORAT = "(%s, '51414825_2020new', '1510026125', 'ZXD018', '{\"productModel\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";

    private static String SQL_XFSZ1_FORAT = "(%s, '51414902_2020new', '1510026202', 'ZXD007', '{\"productModel\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";
    private static String SQL_XFSZ2_FORAT = "(%s, '51414902_2020new', '1510026202', 'ZXD008', '{\"productModel\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";

    private static String SQL_HYSZ1_FORAT = "(%s, '51419205_2020new', '1510030303', 'ZXD007', '{\"sumInsured\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";
    private static String SQL_HYSZ2_FORAT = "(%s, '51419205_2020new', '1510030303', 'ZXD008', '{\"sumInsured\":\"%s\",\"prePeriodOfInsuranceMonth\":\"12\"}', NULL, NULL, 'N', 'jiangjianhe', '%s', 'jiangjianhe', '%s', '%s', 0, NULL),";


    @PostMapping("/xiaofeiji")
    @ResponseBody
    public String Xiaofeiji(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        if (file1.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        String fileName2 = file2.getOriginalFilename();
        System.out.println(fileName1);

        try {
            FileWriter dest = new FileWriter(filePath +"\\xiaofeiji_pre_dml_prism.sql" );
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Map<String, Map<String,String>>  factorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList) {
                String factor         = (String) item.get("factor");
                JSONObject factorJson = JSONObject.parseObject(factor);
                String productModel   = factorJson.getString("productModel");
                String[] productModelArr = productModel.split(";");
                for(String model : productModelArr) {
                    String cal_type = String.valueOf(item.get("cal_type"));
                    cal_type = cal_type.substring(0, cal_type.lastIndexOf(".0"));
                    String mkey              = model + "_" + cal_type;
                    Map<String,String> titem = new HashMap<>();
                    titem.put("airplaneType",  model);
                    titem.put("cal_type",  cal_type);
                    String amount = String.valueOf(item.get("amount"));
                    if(amount.lastIndexOf(".0") > 0) {
                        amount = amount.substring(0, amount.lastIndexOf(".0"));
                    }
                    titem.put("amount",  amount);
                    factorMap.put(mkey,titem);
                }
            }
            Map<String,String> test = factorMap.get("djiPhantom34K_2");
            List<Map<String, Object>> sourceDataList2 = ExcelParseWriteUtils.parse(file2.getInputStream(),fileName2);
            Map<String, Map<String,String>>  newfactorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList2) {
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
                newfactorMap.put(airplaneType, titem);
            }
            //Map<String,Boolean> hflag = new HashMap<>();
            String gmtDate = DateUtils.format(new Date(), DateUtils.DatePatternEnum.DEFAULT_PATTERN);
            for(String tkey :factorMap.keySet()) {
                Map<String,String> titem   = factorMap.get(tkey);
                if("0".equalsIgnoreCase(titem.get("cal_type"))) {
                    String  airplaneType      = titem.get("airplaneType");
                    String premium            = factorMap.get(airplaneType+ "_1").get("amount");
                    String sumInsured         = titem.get("amount");
                    Map<String,String> ntitem = newfactorMap.get(airplaneType);
                    String singleSumInsured   = "";

                    if(null != factorMap.get(airplaneType+ "_2")) {
                        singleSumInsured = factorMap.get(airplaneType+ "_2").get("amount");
                    }
                    if(null != ntitem) {
                        premium         = ntitem.get("premium");
                        sumInsured = ntitem.get("sumInsured");
                        if(StringUtils.isNotBlank(singleSumInsured)) {
                            singleSumInsured = String.valueOf(new BigDecimal(sumInsured).divide(new BigDecimal(2),1));
                            if(singleSumInsured.lastIndexOf(".0") > 0) {
                                singleSumInsured = singleSumInsured.substring(0, singleSumInsured.lastIndexOf(".0"));
                            }
                        }
                    }
                    String wline = String.format(SQL_XF_FORAT, "0", airplaneType,gmtDate,gmtDate,sumInsured) + "\n";
                    dest.write(wline);
                    String wline1 = String.format(SQL_XF_FORAT, "1", airplaneType,gmtDate,gmtDate,premium) + "\n";
                    dest.write(wline1);
                    if(StringUtils.isNotBlank(singleSumInsured)) {
                        String wline2 = String.format(SQL_XF_FORAT, "2", airplaneType,gmtDate,gmtDate, singleSumInsured) + "\n";
                        dest.write(wline2);
                    }
                }
            }

            dest.close();

        } catch (Exception e) {
            System.out.println("生产sql失败：" + e.getMessage());
            log.error(null,e);
        }

        return "生产sql结束";
    }



    @PostMapping("/Hangyeji")
    @ResponseBody
    public String Hangyeji(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        if (file1.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        String fileName2 = file2.getOriginalFilename();
        System.out.println(fileName1);

        try {
            FileWriter dest = new FileWriter(filePath +"\\hangyeji_pre_dml_prism.sql" );
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Map<String, Map<String,String>>  factorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList) {
                String factor         = (String) item.get("factor");
                JSONObject factorJson = JSONObject.parseObject(factor);
                String productModel   = factorJson.getString("productModel");
                String[] productModelArr = productModel.split(";");
                for(String model : productModelArr) {
                    String cal_type = String.valueOf(item.get("cal_type"));
                    cal_type = cal_type.substring(0, cal_type.lastIndexOf(".0"));
                    String mkey              = model + "_" + cal_type;
                    Map<String,String> titem = new HashMap<>();
                    titem.put("airplaneType",  model);
                    titem.put("cal_type",  cal_type);
                    String amount = String.valueOf(item.get("amount"));
                    if(amount.lastIndexOf(".0") > 0) {
                        amount = amount.substring(0, amount.lastIndexOf(".0"));
                    }
                    titem.put("amount",  amount);
                    factorMap.put(mkey,titem);
                }
            }
            List<Map<String, Object>> sourceDataList2 = ExcelParseWriteUtils.parse(file2.getInputStream(),fileName2);
            Map<String, Map<String,String>>  newfactorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList2) {
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
                newfactorMap.put(airplaneType, titem);
            }
            String gmtDate = DateUtils.format(new Date(), DateUtils.DatePatternEnum.DEFAULT_PATTERN);
            for(String tkey :factorMap.keySet()) {
                Map<String,String> titem   = factorMap.get(tkey);
                if("0".equalsIgnoreCase(titem.get("cal_type"))) {
                    String  airplaneType      = titem.get("airplaneType");
                    String premium            = factorMap.get(airplaneType+ "_1").get("amount");
                    String sumInsured         = titem.get("amount");
                    Map<String,String> ntitem = newfactorMap.get(airplaneType);
                    String singleSumInsured   = "";

                    if(null != factorMap.get(airplaneType+ "_2")) {
                        singleSumInsured = factorMap.get(airplaneType+ "_2").get("amount");
                    }
                    if(null != ntitem) {
                        premium         = ntitem.get("premium");
                        sumInsured = ntitem.get("sumInsured");
                        if(StringUtils.isNotBlank(singleSumInsured)) {
                            singleSumInsured = String.valueOf(new BigDecimal(sumInsured).divide(new BigDecimal(2),1));
                            if(singleSumInsured.lastIndexOf(".0") > 0) {
                                singleSumInsured = singleSumInsured.substring(0, singleSumInsured.lastIndexOf(".0"));
                            }
                        }
                    }
                    String wline = String.format(SQL_HY_FORAT, "0", airplaneType,gmtDate,gmtDate,sumInsured) + "\n";
                    dest.write(wline);
                    String wline1 = String.format(SQL_HY_FORAT, "1", airplaneType,gmtDate,gmtDate,premium) + "\n";
                    dest.write(wline1);
                    if(StringUtils.isNotBlank(singleSumInsured)) {
                        String wline2 = String.format(SQL_HY_FORAT, "2", airplaneType,gmtDate,gmtDate, singleSumInsured) + "\n";
                        dest.write(wline2);
                    }
                }
            }

            dest.close();

        } catch (Exception e) {
            System.out.println("生产sql失败：" + e.getMessage());
            log.error(null,e);
        }

        return "生产sql结束";
    }

    @PostMapping("/yanbao")
    @ResponseBody
    public String yanbao(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        if (file1.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        String fileName2 = file2.getOriginalFilename();
        System.out.println(fileName1);

        try {
            FileWriter dest = new FileWriter(filePath +"\\yanbao_pre_dml_prism.sql" );
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Map<String, Map<String,String>>  factorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList) {
                String factor         = (String) item.get("factor");
                JSONObject factorJson = JSONObject.parseObject(factor);
                String productModel   = factorJson.getString("productModel");
                System.out.println(factor);//user.dir指定了当前的路径 　
                String[] productModelArr = productModel.split(";");
                for(String model : productModelArr) {
                    String cal_type = String.valueOf(item.get("cal_type"));
                    cal_type = cal_type.substring(0, cal_type.lastIndexOf(".0"));
                    String mkey              = model + "_" + cal_type;
                    Map<String,String> titem = new HashMap<>();
                    titem.put("airplaneType",  model);
                    titem.put("cal_type",  cal_type);
                    String amount = String.valueOf(item.get("amount"));
                    if(amount.lastIndexOf(".0") > 0) {
                        amount = amount.substring(0, amount.lastIndexOf(".0"));
                    }
                    titem.put("amount",  amount);
                    factorMap.put(mkey,titem);
                }
            }
            List<Map<String, Object>> sourceDataList2 = ExcelParseWriteUtils.parse(file2.getInputStream(),fileName2);
            Map<String, Map<String,String>>  newfactorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList2) {
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
                newfactorMap.put(airplaneType, titem);
            }
            String gmtDate = DateUtils.format(new Date(), DateUtils.DatePatternEnum.DEFAULT_PATTERN);
            for(String tkey :factorMap.keySet()) {
                Map<String,String> titem   = factorMap.get(tkey);
                if("0".equalsIgnoreCase(titem.get("cal_type"))) {
                    String  airplaneType      = titem.get("airplaneType");
                    String premium            = factorMap.get(airplaneType+ "_1").get("amount");
                    String sumInsured         = titem.get("amount");
                    Map<String,String> ntitem = newfactorMap.get(airplaneType);
                    if(null != ntitem) {
                        premium     = ntitem.get("premium");
                        sumInsured  = ntitem.get("sumInsured");
                    }
                    String wline = String.format(SQL_YB_FORAT, "0", airplaneType,gmtDate,gmtDate,sumInsured) + "\n";
                    dest.write(wline);
                    String wline1 = String.format(SQL_YB1_FORAT, "1", airplaneType,gmtDate,gmtDate,premium) + "\n";
                    dest.write(wline1);
                }
            }

            dest.close();

        } catch (Exception e) {
            System.out.println("生产sql失败：" + e.getMessage());
            log.error(null,e);
        }

        return "生产sql结束";
    }


    @PostMapping("/xfsanzhe")
    @ResponseBody
    public String Xfsanzhe(@RequestParam("file1") MultipartFile file1) {

        if (file1.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName1 = file1.getOriginalFilename();
        System.out.println(fileName1);

        try {
            FileWriter dest = new FileWriter(filePath +"\\xfsanzhe_pre_dml_prism.sql" );
            List<Map<String, Object>> sourceDataList = ExcelParseWriteUtils.parse(file1.getInputStream(),fileName1);
            Map<String, Map<String,String>>  factorMap     = new HashMap<String, Map<String, String>>();
            for(Map<String,Object> item : sourceDataList) {
                String factor         = (String) item.get("factor");
                JSONObject factorJson = JSONObject.parseObject(factor);
                String productModel   = factorJson.getString("productModel");
                System.out.println(factor);//user.dir指定了当前的路径 　
                String[] productModelArr = productModel.split(";");
                for(String model : productModelArr) {
                    String cal_type = String.valueOf(item.get("cal_type"));
                    cal_type = cal_type.substring(0, cal_type.lastIndexOf(".0"));
                    String liablity = String.valueOf(item.get("liablity"));
                    String mkey              = model + "_" + cal_type+ "_"+liablity;
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

            String gmtDate = DateUtils.format(new Date(), DateUtils.DatePatternEnum.DEFAULT_PATTERN);
            for(String tkey :factorMap.keySet()) {
                Map<String,String> titem   = factorMap.get(tkey);
                if("0".equalsIgnoreCase(titem.get("cal_type"))) {
                    String  airplaneType      = titem.get("airplaneType");
                    String sumInsured         = titem.get("amount");
                    String premium            = String.valueOf(new BigDecimal(sumInsured).multiply(new BigDecimal("0.0002")).intValue());
                    String liablity  = titem.get("liablity");
                    if("ZXD007".equalsIgnoreCase(liablity)) {
                        String wline = String.format(SQL_XFSZ1_FORAT, "0", airplaneType,gmtDate,gmtDate,sumInsured) + "\n";
                        dest.write(wline);
                        String wline1 = String.format(SQL_XFSZ1_FORAT, "1", airplaneType,gmtDate,gmtDate,premium) + "\n";
                        dest.write(wline1);
                    } else {
                        String wline = String.format(SQL_XFSZ2_FORAT, "0", airplaneType,gmtDate,gmtDate,sumInsured) + "\n";
                        dest.write(wline);
                        String wline1 = String.format(SQL_XFSZ2_FORAT, "1", airplaneType,gmtDate,gmtDate,premium) + "\n";
                        dest.write(wline1);
                    }
                }
            }

            dest.close();
        } catch (Exception e) {
            System.out.println("生产sql失败：" + e.getMessage());
            log.error(null,e);
        }

        return "生产sql结束";
    }

}
