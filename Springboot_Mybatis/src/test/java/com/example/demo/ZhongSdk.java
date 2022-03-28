package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import com.zhongan.scorpoin.common.dto.CommonRequest;
import com.zhongan.scorpoin.common.dto.CommonResponse;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ZhongSdk {
    public static void main(String[] argvs) throws  Exception{
        // ZhongAnApiClient client = new ZhongAnApiClient(env, appKey, privateKey, version);
        // env：环境参数，在dev、iTest、uat、prd中取值
        // appKey：开发者的appKey。如何获取appKey,请详见“接入流程说明”
        // privateKey：开发者私钥。如何生成开发者私钥,请详见“接入流程说明”
        // version: 服务的版本号，默认为1.0.0，测试期间版本号请与众安开发人员确认，UAT和生产必须为1.0.0
        //String appKey = "10001";
        //String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO8h8JCJAMb1nd0uBkzZuWyNnL+atBzJKvIG7escD45ODf0AWKr8vSqLZ01HD86+a496CGjsae6GybK8C1MqiMSwaAsIv31nKD6U8xF607MPrD3r2lyjwUnmqBZY++R6yFNYz9ZDXcdiwCudESRsXunPJq7zfnnglCtEH+qqW8/VAgMBAAECgYEAnVc2gtMyKLbZTPuId65WG7+9oDB5S+ttD1xR1P1cmuRuvcYpkS/Eg6a/rJASLZULDpdbyzWqqaAUPD8QMINvAr3ZtkbwH5R0F/4aqOlx/5B0Okjsp3eSK2bQ8J2m/MmFKZxr6Aily7YUDdxcGcjLizsGi1KDkWS22JRufEeUNA0CQQD+g1XJ7ELqmUtrS4m4XnadB25f0g5QC0tMjoa3d9soMzK3q+Drkv8EZVpGSmSHEo1VlE7HUcnKNvK1BO5Nm4iXAkEA8IeZxaWmEcsRqiuFz8xmYGtKcYTmHgheGF4D+fnnFozSNP+3sS1lfgFQrjUkqUyZOoG1hPc6SDhGS4nbXwiscwJASO+gPR58yrgledkK3ZAMk9GWWtVajqu953GMv7UUU//gD+yspzXX6Q2WgkA9cMvrPtQig1I37sAya5e/JvRkfwJARzzCDEmdP9PW7YFqZjrxb0kXiTuFNAviYnEl2FltWb5nW48JBo6dao5VKONQclvfXfagnjriphUUrLatpB3bhQJAKRfJS6jDAIVKt7So5HOdzk4ipxgrMjG/QtZ1grO+VQectk4+tCwdJhOrr5blvdPQvFVqXBQfXuE7cibZrGs4sQ==";
        //ZhongAnApiClient client = new ZhongAnApiClient("iTest", appKey, privatekey, "1.0.0");
        //保游
       // String appKey     = "69f5da376f8aeae2527a9b9eb5b512b7";


        //58同城
       // String appKey = "cf4efba9f7b3e60a5043459fbeffe612";
        //String privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMbLkp+bVhI7L5AAlKqf1AjQ+8VZ6Nzt2QJqhP2WgRoxd6uw+2bFkyr9PU776Z55Gl6U109cvMCYNm5cUR44tHzdbWbr+MPIovltPWU5KZ3iSzd2uMsPzl/eofJqrL1IV8IKPGXylwM1/XsCE2RGw30eVmX5sSPlK2EjQQnmYy9vAgMBAAECgYBMB8XPJ1DW7i31rFMvoqssqAjy5L0r6gfKZcTxW+OKigrT7n9oQf5UPcB5+dzS0ExmihSC9Nv36P9AXCxmG5fjT+emp1cHyBWvsghad1R4LBhZlDADvQLAuBgVSz3kvh6Cnw69XHLcS5cq/aCGSo09R6csOoYqm4Gw1Ipq0b484QJBAP27zlnN7to/PVnXRwRZFEHSvhDr52vDrcwmQf+fSIPa7csO1/9tBszd+uKbV5oPLsU83lbPYVQScGlXh6Qthn8CQQDIkiSgxpiEe0oD0Av35F3yyR92eQw9LVpCMTLR83vGGUgCqNttWF4rS9yblnIQDifLG5gaei8jmJWj7ib6vD8RAkEA2Br+84nr50J5dG1gdRmRoUZmz2682hH9kqT7DUaSsorigVIRMeGgKxjdN+fqwrIvuZnQHMvgL4TM7Uu5oWGM7QJAQJS98n6/sTi5dkzQ7bq8fWZBGTaUuEYYH+QThwKtQfX199VDAgxIFgzRNc/VXlIQgheafDwHXaDN3gDa0DU44QJBANontmdHgZxu8vaaCp/npx0J1MmdocDxKvmQI4Ewh+13KW09d74yPN02Uni878qhvsi/976Uo9PGv0WTUe0qKls=";

        //由于开发环境众安网关地址不定，可在ZhongAnApiClient构造方法中传入url。注意，该方法传入的url只在开发环境有效，其他环境中ZhongAnApiClient只会取默认的url。
         String url ="http://opengw.daily.zhongan.com/Gateway.do";
         //ZhongAnApiClient client = new ZhongAnApiClient("uat",url, appKey, privatekey, "1.0.0");

        //保游预发环境
       // String appKey     = "69f5da376f8aeae2527a9b9eb5b512b7";
        //String privatekey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKs5mbPviZYIBTPjBTzojyBpvvcfMd0kMq4Vmi2ELTixUoGF10MnWW/upE9UlLMJZpS9fy3Mc/sLOwNOMfBHA/L2eytJGYFNePzXIQGw2ywI5EqJKuEgs1zk2fessOxHpg+XQHwPF1uWynPrWHITfBTJURpLsMoS2Ljt2L4N9tdHAgMBAAECgYEAlFqY6QVfcWXLlDRb4nUhJZVZx2X430EwxBKlflBqmhdRhDXB2EZswIXFSZLgL+uyPKefXMArsVT+sZN7w1LUgLBUaTLe50fjkYLRtqO6pYpIbmMumSrf8z7OHKn9+TkYhznBBWIe+YX+jndpH0wZhHZK0LCuBBJhqJCgwFdqXwECQQDdzbuU144c3UlDAnv8izBrXkLS8CXZ2/I8mgPpwHt9pyBX3WKRtddMBe1mmCR6q03tMXt1kziYxsB5LOrNHtjHAkEAxZ+aKtrbG2NMuO8Jr33eObZgBiG3qKskyGN2ch1KAe69LRdIBS5bbZqJZ3Go+NYKQbSRm0X27pBRhbGGIcqNgQJBANmqHd1RM9tLpaum8nnBNkEHfdh3Zw3G9I9YLWLlS8rhibHwRdzscF3gqzq1L4daONz+ZEPbt/zlftjrpXiqPt0CQCOHK+okalukIHjm0HDySe9KMD0p9qCmE/jKmNKvHUz3pJ7knpf/24P60lT8hAooQLPLSJoHm/sLivxkC98rUQECQQDZP+WWORR9xRaK+x/bx6BRbxw0TBcUlQb2+rWd49z0n9xvmDqeCtMc0XsUiap9oHTMh8m9LntI4FNWsNefgFBI";


        //合翔生产环境
        String appKey     = "ad48ff9d2049f9d1e94fcccb4f2eea2d";
        String privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL3jrQ8L6HYJ0XryCjjLl3dQF+5+43dpLFvabTI9IspZYzoOaDBbqsagyKcwcGpIt1lIjHD8fRcavb915F7Y0U2/W3baX7bMhtL5C1P0ogNLT5nGoLIfS3cAs496clrj5a9GeexXP4qrO7Ly+ukmk1c8RBUw1qk2B1cLH/EpFgvBAgMBAAECgYB5DhBhq0HDHndBXdROaNxi39ih3pq8oXv6AsHiaT8oqNutQ0oAqi+w+QdyLls8Jqdcb/e4f/5vIEkfKdUw8iBbyiyoLi0HSNkYcPNH0LSky953ABIgJ+obHOxyl8fnHSVJSOgKIkjtXqQwUSwmOx/iTwvB+IY644FKLH5To2ZXwQJBAO+ycSbpCSTUgDCz9OPKrnoSfXJlHi354mhxhWtEhF6F77vNWwJ/qABGGU1VPCDWoE0SX/n/IjzUjxULH/EQyj0CQQDKzf4Pr096jE0ukkl/ZmIS4RXE/kdWpqJAnYLXA0vqDlA09PMb6wxo6/Mr24CElaNPFic0lffm3UH7eGXbfFPVAkBsvEQ8ZIazYHrunRRAqddQpUanOFvie3NL8gOWT7TEtHm2dkgM8CAkT6h2vm3Sb8q0a9uCK006zypBX5ST5Qv5AkEArgNsULcDAkrqWuCIkfkeg8aAcGQigBZAuCKuxqD8fVtEtPvMsZLQNiLmpyYTsy/WTaDbiQJ5EK2e1RAsjdSy9QJBAIw/0moHAJb3DMA/M4CqCdJaNIBDpel8LbDVK0Uc1jLzYAxnGWuJ++dHAu1Z6lAuoL8AyUF4ZsjOMG/xOrW8vHU=";

        ZhongAnApiClient client = new ZhongAnApiClient("prd", appKey, privatekey, "1.0.0");
        //接口名称
        String serviceName = "zhongan.supermandoc.open.v2.invoice";
        CommonRequest request = new CommonRequest(serviceName);
        String insureParams = "{\"invoiceTitle\":\"崔金秋\",\"taxpayerNum\":\"\",\"applyCertNo\":\"#t4ZQHAVyROXY/fFSQMArbSgk\",\"applyCertType\":\"I\",\"policyList\":[{\"amount\":\"1.40\",\"policyNo\":\"PI157CV220100257994818\"},{\"amount\":\"1.40\",\"policyNo\":\"PI157CV220100257994917\"},{\"amount\":\"1.40\",\"policyNo\":\"PI157CV220100257994999\"}]}";
        JSONObject insureParamJson = JSONObject.parseObject(insureParams);
        request.setParams(insureParamJson);
        //发起请求
        CommonResponse response = (CommonResponse) client.call(request);
        System.out.println(response.getBizContent());
    }
}
