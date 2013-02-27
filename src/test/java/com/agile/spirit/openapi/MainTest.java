package com.agile.spirit.openapi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.JerseyTest;

public class MainTest extends JerseyTest {

    public MainTest() throws Exception {
        super("com.agile.spirit.openapi");
    }

    /**
     * Test if a WADL document is available at the relative path
     * "application.wadl".
     */
    @Test
    public void testApplicationWadl() {
        WebResource webResource = resource();
        String serviceWadl = webResource.path("application.wadl")
                .accept(MediaTypes.WADL).get(String.class);

        assertTrue(serviceWadl.length() > 0);
    }
}
