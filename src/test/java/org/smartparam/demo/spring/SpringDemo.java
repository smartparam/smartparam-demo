/*
 * Copyright 2013 Adam Dubiel, Przemek Hertel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartparam.demo.spring;

import org.smartparam.engine.core.ParamEngine;
import org.smartparam.engine.core.context.DefaultContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 *
 * @author Adam Dubiel
 */
@ContextConfiguration(locations = "classpath:/spring/demo-context.xml")
public class SpringDemo extends AbstractTestNGSpringContextTests {

    @Autowired
    private ParamEngine paramEngine;

    @Test
    public void demonstrateUsingParamEngineAsSpringBean() {
        // given

        // when
        String value = paramEngine.get("simpleParameter", "HELLO_WORLD", 1).get();

        // then
        assertThat(value).isEqualTo("Hello World");
    }

    @Test
    public void demonstrateSpringPluginsInDynamicContext() {
        // given

        // when
        DefaultContext context = new DefaultContext();
        /*
         * Passing context declares usage of dynamic level creators. Only
         * input level in dayToMonthMapping parameter is "date" level with
         * "date" level creator defined in SpringLevelCreators. It returns
         * date from DateProvider, which is always 2013-04-15.
         */
        String value = paramEngine.get("dayToMonthMapping", context).get();

        // then
        assertThat(value).isEqualTo("April 2013");

    }
}
