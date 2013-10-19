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
package org.smartparam.demo.context;

import org.smartparam.engine.config.ParamEngineConfig;
import org.smartparam.engine.config.pico.ParamEngineConfigBuilder;
import org.smartparam.engine.config.pico.PicoParamEngineFactory;
import org.smartparam.engine.core.context.DefaultContext;
import org.smartparam.engine.core.context.ParamContext;
import org.smartparam.engine.core.engine.ParamEngine;
import org.smartparam.engine.test.assertions.Assertions;
import org.smartparam.repository.fs.ClasspathParamRepository;
import org.testng.annotations.Test;

/**
 *
 * @author Adam Dubiel
 */
public class ParameterContextDemo {

    @Test
    public void demonstrateUsingContextToGetParameter() {
        // given
        ClasspathParamRepository classpathRepository = new ClasspathParamRepository("/param/", ".*csv$");
        ParamEngineConfig engineConfig = ParamEngineConfigBuilder.paramEngineConfig()
                .withAnnotationScanEnabled("org.smartparam.demo.context")
                .withParameterRepositories(classpathRepository).build();
        ParamEngine engine = PicoParamEngineFactory.paramEngine(engineConfig);

        // when
        ParamContext context = new DefaultContext().with("code", "HELLO_WORLD").with("amount", 1);
        String value = engine.get("contextParameter", context).get("value").getString();

        // then
        Assertions.assertThat(value).isEqualTo("Hello World");
    }
}
