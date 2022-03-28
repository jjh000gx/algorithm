package com.example.jmx;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 *
 * 可以通过 Jconsole 工具实现监控调用
 * @link https://www.cnblogs.com/dongguacai/p/5900507.html
 */
public class HelloAgent {
        public static void main(String[] args) throws JMException, Exception {
              MBeanServer server = ManagementFactory.getPlatformMBeanServer();
              ObjectName helloName = new ObjectName("jmxBean:name=hello");
              //create mbean and register mbean
              server.registerMBean(new Hello(), helloName);
              //Thread.sleep(60*60*1000);
             try
             {
                //这个步骤很重要，注册一个端口，绑定url后用于客户端通过rmi方式连接JMXConnectorServer
                LocateRegistry.createRegistry(9999);
                //URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
                JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
                JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
                System.out.println("begin rmi start");
                jcs.start();
                System.out.println("rmi start");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
