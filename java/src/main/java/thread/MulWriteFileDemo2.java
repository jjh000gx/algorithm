package thread;

import java.io.FileWriter;
import java.io.IOException;

public class MulWriteFileDemo2 extends Thread {
    private FileWriter writer;

    public MulWriteFileDemo2(FileWriter writer, String name)  {
        this.writer = writer;
        this.setName(name);
    }
    @Override
    public void run() {
        try {
            for(int i= 0 ;i<300; i++) {
                writer.append("线程"+Thread.currentThread().getName()+"_"+i+"_正在写入文件，" +
                        "妈妈说名字要很长才能够测试出这几个线程有没有冲突啊，" +
                        "不过还是没有论坛里帖子的名字长，怎么办呢？" +
                        "哎呀，后面是换行符了\n");
                //dest.append(Thread.currentThread().getName() + "_" +i+"\n");
            }
           // dest.close();
        } catch (Exception e) {

        }
    }
    public static void main(String[] argvs)  throws IOException{
        String path = "D:\\web\\test.txt";
        FileWriter fw = new FileWriter(path, true);
        MulWriteFileDemo2 t1 = new MulWriteFileDemo2(fw,"t1");
        MulWriteFileDemo2 t2 = new MulWriteFileDemo2(fw,"t2");
        MulWriteFileDemo2 t3 = new MulWriteFileDemo2(fw,"t3");
        t1.start();
        t2.start();
        t3.start();
        //主线程休眠100毫秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fw.close();
    }
}
