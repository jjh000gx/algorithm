package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BRRead {

    public static void main(String[] argvs)  throws IOException {
        char c;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输出字符，按'q'退出");
        do {
             c = (char) br.read();
             System.out.println(c);
        } while(c != 'q');
    }
}
