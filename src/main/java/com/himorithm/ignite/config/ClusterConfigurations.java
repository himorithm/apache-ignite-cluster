package com.himorithm.ignite.config;

import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class ClusterConfigurations {
    private final String clusterName;
    private final String clusterIp;
    private final String storagePath;

    public ClusterConfigurations(
            @Value("${local.cluster.name}") String clusterName,
            @Value("${local.cluster.ip}") String clusterIp,
            @Value("${local.cluster.path}") String storagePath) {
        this.clusterName = clusterName;
        this.clusterIp = clusterIp;
        this.storagePath = storagePath;
    }

    public String getClusterName() {
        return clusterName;
    }

    public IgniteConfiguration getConfiguration() {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList(clusterIp));
        spi.setIpFinder(ipFinder);
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(false);
        cfg.setIgniteInstanceName(clusterName);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setActiveOnStart(true);
        cfg.setLifecycleBeans(new ClusterLifeCycleBean(clusterName));
        DataStorageConfiguration defaultDataStorage = new DataStorageConfiguration();
        defaultDataStorage.setStoragePath(storagePath);
        DataRegionConfiguration defaultDataRegionConfig = new DataRegionConfiguration();
        defaultDataRegionConfig.setPersistenceEnabled(true); // Enable Local Persistence
        defaultDataStorage.setDefaultDataRegionConfiguration(defaultDataRegionConfig);
        cfg.setDataStorageConfiguration(defaultDataStorage);
        return cfg;
    }
}
