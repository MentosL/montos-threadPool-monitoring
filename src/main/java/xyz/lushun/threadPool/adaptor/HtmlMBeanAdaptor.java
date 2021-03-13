package xyz.lushun.threadPool.adaptor;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Montos
 * @create 2021/3/13 5:55 下午
 */
public class HtmlMBeanAdaptor extends MBeanAdaptor{

    private  final HtmlAdaptorServer adapter = new HtmlAdaptorServer();

    @Override
    public void start() {
        try {
            registerMBean(adapter, new ObjectName(String.format("HtmlAgent:name=html-adapter, port=%s", "8888")));
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        adapter.start();
    }
}
