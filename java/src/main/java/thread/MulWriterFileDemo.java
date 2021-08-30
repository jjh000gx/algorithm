package thread;

import java.io.FileWriter;
import java.io.IOException;

/**
 * http://www.blogjava.net/watchzerg/archive/2012/09/02/386777.html
 *
 */
public class MulWriterFileDemo extends Thread {
    public MulWriterFileDemo(String name) throws IOException {
        //writer=new FileWriter(file,true);
        this.setName(name);
    }
    @Override
    public void run() {
        String path = "D:\\web\\test1.txt";
        try {
            FileWriter dest = new FileWriter(path,true);
            for(int i= 0 ;i<100; i++) {
                dest.append("线程"+Thread.currentThread().getName() + "_"+i +"正在写入文件，" +
                                                    "妈妈说名字要很长才能够测试出这几个线程有没有冲突啊，" +
                                                     "不过还是没有论坛里帖子的名字长，怎么办呢？" +
                                                     "哎呀，后面是换行符了\n");
                //dest.append(Thread.currentThread().getName() + "_" +i+"\n");
            }
            dest.close();
        } catch (Exception e) {

        }
    }
    public static void main(String[] argvs)  throws IOException{
        String path = "D:\\web\\test.txt";
        //File file=new File(path);
        //file.createNewFile();
        MulWriterFileDemo t1 = new MulWriterFileDemo("t1");
        MulWriterFileDemo t2 = new MulWriterFileDemo("t2");
        MulWriterFileDemo t3 = new MulWriterFileDemo("t3");
        t1.start();
        t2.start();
        t3.start();

        //主线程休眠100毫秒
         try {
             Thread.sleep(100);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    }

}
