package com.agile.spirit.openapi.utils.togglz;

import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.spi.FeatureManagerProvider;
import org.togglz.core.user.NoOpUserProvider;

public class TogglzFeatureManagerProvider implements FeatureManagerProvider {

    private static FeatureManager featureManager;

    @Override
    public int priority() {
        return 30;
    }

    @Override
    public synchronized FeatureManager getFeatureManager() {

        if (featureManager == null) {
            featureManager = new FeatureManagerBuilder() //
                    .featureClass(TogglzFeatures.class) //
                    .stateRepository(new InMemoryStateRepository()) //
                    .userProvider(new NoOpUserProvider()) //
                    .build();
        }

        return featureManager;

    }

}
