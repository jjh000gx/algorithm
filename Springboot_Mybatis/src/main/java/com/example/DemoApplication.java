package com.example;

import com.example.jmx.Hello;
import com.example.netty.codec.MyServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

//@MapperScan("com.example.mapper") //扫描的mapper
@EnableSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication implements CommandLineRunner {
	public static void main(String[] args) throws JMException, Exception{
		/*MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName helloName = new ObjectName("jmxBean:name=hello");
		server.registerMBean(new Hello(), helloName);
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
		}*/
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MyServer myServer = new MyServer(8082);
		System.out.println("run .... . ... 127.0.0.1:8082");
		myServer.run();
	}
}

