package xyz.lushun.threadPool.adaptor;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author Montos
 * @create 2021/3/13 5:55 下午
 */
public abstract class MBeanAdaptor {


    private static final MBeanServer server = ManagementFactory.getPlatformMBeanServer();

   public  abstract  void  start();

    public  void registerMBean(Object obj, ObjectName objectName) {
        try {
            server.registerMBean(obj, objectName);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }
    }

}
