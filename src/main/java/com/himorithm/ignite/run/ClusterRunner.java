package com.himorithm.ignite.run;

import com.himorithm.ignite.config.ClusterConfigurations;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClusterRunner implements ApplicationRunner {

    private final ClusterConfigurations clusterConfigurations;

    @Autowired
    public ClusterRunner(ClusterConfigurations clusterConfigurations) {
        this.clusterConfigurations = clusterConfigurations;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Ignite ignite = Ignition.start(clusterConfigurations.getConfiguration());
        ignite.cluster().active(true); //activate cluster
    }
}
