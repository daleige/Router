package com.chenyangqi.router.processor.bean;

public class ServiceLoaderBean {
    private final String interfaceName;
    private final String realPath;
    private final String key;
    private final boolean singleton;

    public ServiceLoaderBean(String interfaceName, String realPath, String key, boolean singleton) {
        this.interfaceName = interfaceName;
        this.realPath = realPath;
        this.key = key;
        this.singleton = singleton;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getRealPath() {
        return realPath;
    }

    public String getKey() {
        return key;
    }

    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public String toString() {
        return "ServiceLoaderBean{" +
                "interfaceName='" + interfaceName + '\'' +
                ", realPath='" + realPath + '\'' +
                ", key='" + key + '\'' +
                ", singleton=" + singleton +
                '}';
    }
}
