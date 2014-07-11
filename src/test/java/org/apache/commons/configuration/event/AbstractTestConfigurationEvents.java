/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration.event;


import org.apache.commons.configuration.AbstractConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Base class for testing events generated by configuration classes derived from
 * AbstractConfiguration. This class implements a couple of tests related to
 * event generation. Concrete sub classes only have to implement the
 * {@code createConfiguration()} method for creating an instance of a
 * specific configuration class. Because tests for detail events depend on a
 * concrete implementation an exact sequence of events cannot be checked.
 * Instead the corresponding test methods check whether the enclosing events
 * (not the detail events) are of the expected type.
 *
 * @version $Id$
 */
public abstract class AbstractTestConfigurationEvents
{
    /** Constant for a test property name. */
    static final String TEST_PROPNAME = "event.test";

    /** Constant for a test property value. */
    static final String TEST_PROPVALUE = "a value";

    /** Constant for an existing property. */
    static final String EXIST_PROPERTY = "event.property";

    /** The configuration to be tested. */
    protected AbstractConfiguration config;

    /**
     * A test event listener.
     * @deprecated Use the "modern" listener.
     */
    protected ConfigurationListenerTestImpl l;

    /** A test event listener. */
    protected EventListenerTestImpl listener;

    @Before
    public void setUp() throws Exception
    {
        config = createConfiguration();
        config.addProperty(EXIST_PROPERTY, "existing value");
        l = new ConfigurationListenerTestImpl(config);
        listener = new EventListenerTestImpl(config);
        config.addEventListener(ConfigurationEvent.ANY, listener);
    }

    /**
     * Creates the configuration instance to be tested.
     *
     * @return the configuration instance under test
     */
    protected abstract AbstractConfiguration createConfiguration();

    /**
     * Tests events generated by addProperty().
     */
    @Test
    public void testAddPropertyEvent()
    {
        config.addProperty(TEST_PROPNAME, TEST_PROPVALUE);
        listener.checkEvent(ConfigurationEvent.ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, true);
        listener.checkEvent(ConfigurationEvent.ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, false);
        listener.done();
    }

    /**
     * Tests events generated by addProperty() when detail events are enabled.
     */
    @Test
    public void testAddPropertyEventWithDetails()
    {
        config.setDetailEvents(true);
        config.addProperty(TEST_PROPNAME, TEST_PROPVALUE);
        listener.checkEventCount(2);
        listener.checkEvent(ConfigurationEvent.ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, true);
        listener.skipToLast(ConfigurationEvent.ADD_PROPERTY);
        listener.checkEvent(ConfigurationEvent.ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, false);
        listener.done();
    }

    /**
     * Tests events generated by clearProperty().
     */
    @Test
    public void testClearPropertyEvent()
    {
        config.clearProperty(EXIST_PROPERTY);
        listener.checkEvent(ConfigurationEvent.CLEAR_PROPERTY,
                EXIST_PROPERTY, null, true);
        listener.checkEvent(ConfigurationEvent.CLEAR_PROPERTY,
                EXIST_PROPERTY, null, false);
        listener.done();
    }

    /**
     * Tests events generated by clearProperty() when detail events are enabled.
     */
    @Test
    public void testClearPropertyEventWithDetails()
    {
        config.setDetailEvents(true);
        config.clearProperty(EXIST_PROPERTY);
        listener.checkEventCount(2);
        listener.checkEvent(ConfigurationEvent.CLEAR_PROPERTY,
                EXIST_PROPERTY, null, true);
        listener.skipToLast(ConfigurationEvent.CLEAR_PROPERTY);
        listener.checkEvent(ConfigurationEvent.CLEAR_PROPERTY,
                EXIST_PROPERTY, null, false);
        listener.done();
    }

    /**
     * Tests events generated by setProperty().
     */
    @Test
    public void testSetPropertyEvent()
    {
        config.setProperty(EXIST_PROPERTY, TEST_PROPVALUE);
        listener.checkEvent(ConfigurationEvent.SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, true);
        listener.checkEvent(ConfigurationEvent.SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, false);
        listener.done();
    }

    /**
     * Tests events generated by setProperty() when detail events are enabled.
     */
    @Test
    public void testSetPropertyEventWithDetails()
    {
        config.setDetailEvents(true);
        config.setProperty(EXIST_PROPERTY, TEST_PROPVALUE);
        listener.checkEventCount(2);
        listener.checkEvent(ConfigurationEvent.SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, true);
        listener.skipToLast(ConfigurationEvent.SET_PROPERTY);
        listener.checkEvent(ConfigurationEvent.SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, false);
        listener.done();
    }

    /**
     * Tests the events generated by the clear() method.
     */
    @Test
    public void testClearEvent()
    {
        config.clear();
        listener.checkEvent(ConfigurationEvent.CLEAR, null, null, true);
        listener.checkEvent(ConfigurationEvent.CLEAR, null, null, false);
        listener.done();
    }

    /**
     * Tests the events generated by the clear method when detail events are
     * enabled.
     */
    @Test
    public void testClearEventWithDetails()
    {
        config.setDetailEvents(true);
        config.clear();
        listener.checkEventCount(2);
        listener.checkEvent(ConfigurationEvent.CLEAR, null, null, true);
        listener.skipToLast(ConfigurationEvent.CLEAR);
        listener.checkEvent(ConfigurationEvent.CLEAR, null, null, false);
        listener.done();
    }
}
