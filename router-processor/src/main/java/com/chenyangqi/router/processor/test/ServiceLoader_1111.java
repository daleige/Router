package com.chenyangqi.router.processor.test;

import com.chenyangqi.router.processor.bean.ServiceLoaderBean;

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

    public static Map<String, ServiceLoaderBean> get() {
        Map<String, ServiceLoaderBean> mapping = new HashMap<>();
        ServiceLoaderBean bean0 = new ServiceLoaderBean("11", "11", "11", true);
        mapping.put(bean0.getInterfaceName(), bean0);
        ServiceLoaderBean bean1 = new ServiceLoaderBean("11", "11", "11", true);
        mapping.put(bean1.getInterfaceName(), bean1);
        ServiceLoaderBean bean2 = new ServiceLoaderBean("11", "11", "11", true);
        mapping.put(bean2.getInterfaceName(), bean2);
        ServiceLoaderBean bean3 = new ServiceLoaderBean("11", "11", "11", true);
        mapping.put(bean3.getInterfaceName(), bean3);
        return mapping;
    }

}
