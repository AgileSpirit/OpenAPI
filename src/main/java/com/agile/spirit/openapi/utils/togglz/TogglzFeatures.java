package com.agile.spirit.openapi.utils.togglz;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum TogglzFeatures implements Feature {

    /*
     * // Uncomment in order to activate the resource : /notes/all/export
     * 
     * @EnabledByDefault
     */
    @Label("All notes PDF export")
    ALL_NOTES_PDF_EXPORT,

    @EnabledByDefault
    @Label("Second Feature")
    FEATURE_TWO;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}
