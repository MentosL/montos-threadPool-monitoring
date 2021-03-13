package xyz.lushun.threadPool.adaptor;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import xyz.lushun.threadPool.util.MBeanServerUtils;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Montos
 * @create 2021/3/13 5:55 下午
 */
public class HtmlMBeanAdaptor {

    private static final String PORT = "8082";
    private static HtmlAdaptorServer adapter = new HtmlAdaptorServer();

    public static void start() {
        try {
            MBeanServerUtils.registerMBean(adapter, new ObjectName(String.format("HtmlAgent:name=html-adapter, port=%s", PORT)));
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        adapter.start();
    }
}
