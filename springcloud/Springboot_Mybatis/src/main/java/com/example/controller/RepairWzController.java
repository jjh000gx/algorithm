package com.example.controller;

import com.example.utils.ExcelParseWriteUtils;
import com.example.utils.SqlScriptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.util.*;

@RestController
@RequestMapping("/RepairWz")
@Slf4j
public class RepairWzController {
    private static String PAYER_SQL_FORAT = "UPDATE `core_newpolicy_%s`.`policy_payer_%s` SET `user_id`=%s,`user_type`=2,`credit_code`='%s',`name`='%s',`certi_no`=null,`certi_type`=null,`gender`=null,`birthday`=null,`phone_no`=null,`gmt_modified`=now(),`modifier`='jiangjianhe' WHERE `policy_id`=%s;\n";

    private static String BILL_SQL_FORAT = "UPDATE `newbee_%s`.`bill_bill_%s` SET `cus_user_id`=%s, `modifier`='jiangjianhe', gmt_modified=NOW() where `related_policy_id`=%s;\n";


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
            FileWriter dest = new FileWriter(filePath +"\\订正订货贷付款人账单数据.sql");
            List<Map<String, Object>> dataList = ExcelParseWriteUtils.parse(file.getInputStream(),fileName);
            for(Map<String,Object> item : dataList) {
                String policyId        = (String) item.get("policy_id");
                String db = SqlScriptUtil.getDbCount2(policyId, true, 1024L, 8L);
                String tb = SqlScriptUtil.getTableCount2(policyId, true, 1024L);
                String user_id        = (String) item.get("insurant_user_id");
                String certi_no = (String) item.get("insurant_certi_no");
                String user_name = (String) item.get("insurant_user_name");
                String wline = String.format(PAYER_SQL_FORAT,db,tb, user_id,certi_no,user_name,policyId);
                dest.write(wline);
            }
            dest.close();
        } catch (Exception e) {
            System.out.println("生产订正sql失败：" + e.getMessage());
            log.error(null,e);
        }


        return "生产订正sql成功111";
    }

    @PostMapping("/bill")
    @ResponseBody
    public String bill(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//user.dir指定了当前的路径 　
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            FileWriter dest = new FileWriter(filePath +"\\prd_dml_newbee.sql");
            List<Map<String, Object>> dataList = ExcelParseWriteUtils.parse(file.getInputStream(),fileName);
            for(Map<String,Object> item : dataList) {
                String policyId        = (String) item.get("policy_id");
                String user_id        = (String) item.get("insurant_user_id");
                String db = SqlScriptUtil.getDbCount2(policyId, false, 256L, 8L);
                String tb = SqlScriptUtil.getTableCount2(policyId, false, 256L);
                String wline = String.format(BILL_SQL_FORAT,db,tb, user_id,policyId);
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
