package xyz.lushun.threadPool.util;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author Montos
 * @create 2021/3/13 6:24 下午
 *  MBeanServer工具类
 */
public class MBeanServerUtils {

    private static final MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    public static MBeanServer getMBeanServer(){
        return server;
    }

    public static void registerMBean(Object obj, ObjectName objectName) {
        try {
            server.registerMBean(obj, objectName);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }
    }


}
