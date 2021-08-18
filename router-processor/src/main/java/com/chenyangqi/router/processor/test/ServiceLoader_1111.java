package com.chenyangqi.router.processor.test;

import com.chenyangqi.router.processor.ServiceInfo;

import java.util.HashMap;
import java.util.Map;

public class ServiceLoader_1111 {

//    public static Map<String, ServiceLoaderBean> get() {
//        Map<String, ServiceLoaderBean> map = new HashMap<>();
//
//        ServiceLoaderBean bean1 = new ServiceLoaderBean("111", "222", "33", true);
//        map.put(bean1.getInterfaceName(), bean1);
//
//        ServiceLoaderBean bean2 = new ServiceLoaderBean("1112", "2223", "334", true);
//        map.put(bean1.getInterfaceName(), bean2);
//
//        return map;
//    }

    public static Map<String, ServiceInfo> get() {
        Map<String, ServiceInfo> mapping = new HashMap<>();
        ServiceInfo bean0 = new ServiceInfo("11", "11", "11", true);
        mapping.put(bean0.getInterfaceName(), bean0);
        ServiceInfo bean1 = new ServiceInfo("11", "11", "11", true);
        mapping.put(bean1.getInterfaceName(), bean1);
        ServiceInfo bean2 = new ServiceInfo("11", "11", "11", true);
        mapping.put(bean2.getInterfaceName(), bean2);
        ServiceInfo bean3 = new ServiceInfo("11", "11", "11", true);
        mapping.put(bean3.getInterfaceName(), bean3);
        return mapping;
    }

}
