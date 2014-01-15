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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartparam.engine.config.ParamEngineConfig;
import org.smartparam.engine.config.ParamEngineConfigBuilder;
import org.smartparam.engine.config.ParamEngineFactory;
import org.smartparam.engine.core.ParamEngine;
import org.smartparam.repository.fs.ClasspathParamRepository;
import org.testng.annotations.Test;

/**
 *
 * @author Adam Dubiel
 */
public class ComplexParameterUsageDemo {

    private static final Logger logger = LoggerFactory.getLogger(ComplexParameterUsageDemo.class);

    @Test
    public void demonstratesUsageOfSimpleParameterUsingClasspathRepository() {
        // given
        ClasspathParamRepository classpathRepository = new ClasspathParamRepository("/param/", ".*csv$");
        ParamEngineConfig engineConfig = ParamEngineConfigBuilder.paramEngineConfig()
                .withParameterRepositories(classpathRepository).build();
        ParamEngine engine = ParamEngineFactory.paramEngine(engineConfig);

        // when
        engine.get("complexParameter", "A", "");
        engine.get("complexParameter", "A", "P");
    }
}
