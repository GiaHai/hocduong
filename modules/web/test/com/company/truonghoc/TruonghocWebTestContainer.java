package com.company.truonghoc;

import com.haulmont.cuba.web.testsupport.TestContainer;

import java.util.Arrays;

public class TruonghocWebTestContainer extends TestContainer {

    public TruonghocWebTestContainer() {
        appComponents = Arrays.asList(
                "com.haulmont.cuba",
                "com.haulmont.addon.helium",
                "com.haulmont.charts",
                "com.haulmont.addon.restapi",
                "com.haulmont.addon.globalevents");
        appPropertiesFiles = Arrays.asList(
                // List the files defined in your web.xml
                // in appPropertiesConfig context parameter of the web module
                "com/company/truonghoc/web-app.properties",
                // Add this file which is located in CUBA and defines some properties
                // specifically for test environment. You can replace it with your own
                // or add another one in the end.
                "com/haulmont/cuba/web/testsupport/test-web-app.properties"
        );
    }

    public static class Common extends TruonghocWebTestContainer {

        // A common singleton instance of the test container which is initialized once for all tests
        public static final TruonghocWebTestContainer.Common INSTANCE = new TruonghocWebTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }

        @Override
        public void before() throws Throwable {
            if (!initialized) {
                super.before();
                initialized = true;
            }
            setupContext();
        }

        @Override
        public void after() {
            cleanupContext();
            // never stops - do not call super
        }
    }
}