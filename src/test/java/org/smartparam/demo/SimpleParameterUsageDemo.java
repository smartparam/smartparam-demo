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
package org.smartparam.demo;

import org.smartparam.engine.config.ParamEngineConfig;
import org.smartparam.engine.config.ParamEngineConfigBuilder;
import org.smartparam.engine.config.ParamEngineFactory;
import org.smartparam.engine.core.engine.ParamEngine;
import org.smartparam.repository.fs.ClasspathParamRepository;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 *
 * @author Adam Dubiel
 */
public class SimpleParameterUsageDemo {

    @Test
    public void demonstratesUsageOfSimpleParameterUsingClasspathRepository() {
        // given
        ClasspathParamRepository classpathRepository = new ClasspathParamRepository("/param/", ".*csv$");
        ParamEngineConfig engineConfig = ParamEngineConfigBuilder.paramEngineConfig().withAnnotationScanEnabled()
                .withParameterRepositories(classpathRepository).build();
        ParamEngine engine = ParamEngineFactory.paramEngine(engineConfig);

        // when
        String value = engine.get("simpleParameter", "HELLO_WORLD", 1).getString();

        // then
        assertThat(value).isEqualTo("Hello World");
    }
}
