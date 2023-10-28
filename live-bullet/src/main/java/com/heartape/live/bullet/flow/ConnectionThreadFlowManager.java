package com.heartape.live.bullet.flow;

import com.heartape.live.bullet.connect.ConnectionManager;

/**
 * 以 {@link  ThreadFlow}作为Flow的实现
 */
public class ConnectionThreadFlowManager extends AbstractFlowManager {

    private final ConnectionManager connectionManager;

    public ConnectionThreadFlowManager(int maxFlowSizePow, int coreFlowSizePow, ConnectionManager connectionManager) {
        super(maxFlowSizePow, coreFlowSizePow);
        this.connectionManager = connectionManager;
        setFlowSize(coreFlowSizePow);
    }

    @Override
    protected Flow create() {
        return new ConnectThreadFlow(connectionManager::push);
    }

}
