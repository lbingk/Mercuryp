package org.mercuryp.transport.metadata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 定义一个单例对象，用于存储每一次请求以及相应
 * @Author luobingkai
 * @Date 2019/7/25 12:56
 * @Version 1.0
 **/
public class RemoteTransporterHolder {

    public Map<String, RemoteTransporter> remoteTransporterMap = new ConcurrentHashMap();

    public RemoteTransporter getRemoteTransporter(String requestMark) {
        return remoteTransporterMap.get(requestMark);
    }

    public RemoteTransporter addRemoteTransporter(String requestMark, RemoteTransporter remoteTransporter) {
        return remoteTransporterMap.put(requestMark, remoteTransporter);
    }

    public RemoteTransporter removeRemoteTransporter(String requestMark) {
        return remoteTransporterMap.remove(requestMark);
    }

    private static RemoteTransporterHolder instance = new RemoteTransporterHolder();

    private RemoteTransporterHolder() {
    }

    public static RemoteTransporterHolder getInstance() {
        return instance;
    }

}
