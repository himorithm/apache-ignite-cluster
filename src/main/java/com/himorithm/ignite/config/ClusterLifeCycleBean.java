package com.himorithm.ignite.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;

@Slf4j
public class ClusterLifeCycleBean implements LifecycleBean {

    private final String clusterName;

    public ClusterLifeCycleBean(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {

        switch (evt) {
            case AFTER_NODE_START:
                log.info("**************** Ignite {} Started ****************", clusterName);
                break;
            case AFTER_NODE_STOP:
                log.info("S**************** topping Ignite {} ****************", clusterName);
                break;
            default:
        }
    }
}
