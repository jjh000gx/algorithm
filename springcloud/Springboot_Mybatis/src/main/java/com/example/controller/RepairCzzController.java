package com.example.controller;

import com.example.utils.ExcelParseWriteUtils;
import com.example.utils.SqlScriptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.util.*;

@RestController
@RequestMapping("/RepairCzz")
@Slf4j
public class RepairCzzController {
    private static String SQL_FORAT = "INSERT INTO `policy_%s`.`policy_customer_%s` (`id`, `policy_id`, `user_id`, `user_name`, `user_type`, `policy_user_type`, `phone_no`, `email`, `certificate_type`, `certificate_no`, `address`, `pay_amount`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `extra_info`, `gender`, `province_code`, `city_code`, `country_code`, `zip_code`, `birthday`, `job_code`) VALUES (%s, %s, 383705930, '深圳市大疆百旺科技有限公司', 2, 4, NULL, NULL, 24, '91440300326594785Q', NULL, '60.00', 'jiangjianhe',  now(), 'jiangjianhe', now(), 'N', '{}', NULL, NULL, NULL, NULL, NULL, NULL, NULL);\n";

    private static String BILL_SQL_FORAT = "UPDATE `bill_%s`.`bill_bill_%s` SET `pay_user_id`='383705930',`gmt_modified`=now(),`modifier`='jiangjianhe' WHERE `id`=%s;\n";

    @PostMapping("/payer")
    @ResponseBody
    public String payer(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            FileWriter dest = new FileWriter(filePath +"\\订正操作人意外险付款人账单数据.sql");
            List<Map<String, Object>> dataList = ExcelParseWriteUtils.parse(file.getInputStream(),fileName);
            for(Map<String,Object> item : dataList) {
                String policyId        = (String) item.get("policy_id");
                String db = SqlScriptUtil.getDbCount2(policyId, true, 1024L, 32L);
                String tb = SqlScriptUtil.getTableCount2(policyId, true, 1024L);
                String pay_user_id = (String) item.get("pay_user_id");
                if(pay_user_id.equals("383705930")) {
                    continue;
                }
                String bill_id = (String) item.get("bill_id");
                String wline = String.format(BILL_SQL_FORAT,db,tb, bill_id);
                dest.write(wline);
            }
            dest.close();
        } catch (Exception e) {
            System.out.println("生产订正sql失败：" + e.getMessage());
            log.error(null,e);
        }


        return "生产订正sql成功";
    }

    @PostMapping("/payerBill")
    @ResponseBody
    public String payerBill(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            int pcid = 213974382;
            FileWriter dest = new FileWriter(filePath +"\\订正操作人意外险付款人账单数据.sql");
            List<Map<String, Object>> dataList = ExcelParseWriteUtils.parse(file.getInputStream(),fileName);
            for(Map<String,Object> item : dataList) {
                String policyId        = (String) item.get("policy_id");
                String db = SqlScriptUtil.getDbCount2(policyId, true, 1024L, 32L);
                String tb = SqlScriptUtil.getTableCount2(policyId, true, 1024L);
                String wline = String.format(SQL_FORAT,db,tb, pcid++,policyId);
                dest.write(wline);
            }
            dest.close();
        } catch (Exception e) {
            System.out.println("生产订正sql失败：" + e.getMessage());
            log.error(null,e);
        }


        return "生产订正sql成功";
    }
}
