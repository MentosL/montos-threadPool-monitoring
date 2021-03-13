//package xyz.lushun.threadPool.adaptor;
//
//import xyz.lushun.threadPool.util.MBeanServerUtils;
//
//import javax.management.*;
//
///**
// * @author Montos
// * @create 2021/3/13 5:55 下午
// */
//public abstract class MBeanAdaptor {
//
//
//    private static MBeanServer server = null;
//
//    static {
//        server = MBeanServerUtils.getMBeanServer();
//    }
//
//    public void registerMBean(Object obj, ObjectName objectName) {
//        try {
//            server.registerMBean(obj, objectName);
//        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public abstract void start();
//
//}
