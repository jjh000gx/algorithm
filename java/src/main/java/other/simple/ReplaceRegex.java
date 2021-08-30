package other.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceRegex {
    public static void main(String[] argv) {
        List<String> matchList = new ArrayList<>();
        String template = "Windows95Windows98";
        //编译对象
        String replaceRegex = "Windows(?=95|98|NT|2000)";
        Pattern p = Pattern.compile(replaceRegex);
        //进行匹配
        Matcher m = p.matcher(template);
        while (m.find()) {
            matchList.add(m.group());
        }

        System.out.println(matchList);
    }
}
