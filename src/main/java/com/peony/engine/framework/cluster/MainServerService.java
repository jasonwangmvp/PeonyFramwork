package com.peony.engine.framework.cluster;

import com.peony.engine.framework.control.annotation.Service;
import com.peony.engine.framework.control.netEvent.NetEventService;
import com.peony.engine.framework.data.DataService;
import com.peony.engine.framework.tool.helper.ConfigHelper;

import java.util.List;
import java.util.Map;

/**
 * 主服务器服务：serverlist服务器服务
 *
 * 获取服务器列表，并缓存
 * 向NetEvent注册所有的服务器
 *
 */
@Service(init="init")
public class MainServerService {

    private DataService dataService;
    private NetEventService netEventService;

    private List<ServerInfo> serverInfoList;

    public void init(){
        Map<String, String> mainServerMap = ConfigHelper.getMap("mainServer");
        if(!mainServerMap.get("mainServer.use").trim().equals("true")){
            return;
        }
        List<ServerInfo> serverInfoList = dataService.selectList(ServerInfo.class,"");
        this.serverInfoList = serverInfoList;
        for(ServerInfo serverInfo : serverInfoList){
            netEventService.registerServerAsync(serverInfo.getId(),serverInfo.getHost(),serverInfo.getNetEventPort());
        }
    }

    /**
     * 向主服务器发送事件
     * 异步
     */
//    public void fireMainServerNetEvent(NetEventData netEvent) {
//        if (ServerType.isMainServer()) {
//            handleNetEventData(netEvent);
//            return;
//        }
//        if (mainServerClient != null) {
//            mainServerClient.push(netEvent);
//            return;
//        }
//        throw new MMException("mainServerClient is null");
//    }
}
