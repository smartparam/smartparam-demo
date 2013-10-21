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

import com.jayway.jsonassert.JsonAssert;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import org.smartparam.engine.model.Parameter;
import org.smartparam.serializer.ParamDeserializer;
import org.smartparam.serializer.ParamSerializer;
import org.smartparam.serializer.config.SerializationConfig;
import org.smartparam.serializer.config.SerializationConfigBuilder;
import org.smartparam.serializer.config.ParamSerializerFactory;
import org.smartparam.serializer.exception.ParamSerializationException;
import org.testng.annotations.Test;
import static org.smartparam.engine.test.assertions.Assertions.assertThat;
import static org.smartparam.engine.test.builder.LevelTestBuilder.level;
import static org.smartparam.engine.test.builder.ParameterTestBuilder.parameter;

/**
 *
 * @author Adam Dubiel
 */
public class SerializationDemo {

    @Test
    public void demonstrateSerializingParameterUsingDefaultConfig() throws ParamSerializationException {
        // given
        SerializationConfig serializationConfig = SerializationConfigBuilder.serializationConfig().build();
        ParamSerializer serializer = ParamSerializerFactory.paramSerializer(serializationConfig);

        Parameter parameter = parameter().withName("parameterName").withLevels(level().build()).build();
        StringWriter stringWriter = new StringWriter();

        // when
        serializer.serialize(parameter, stringWriter);

        // then
        JsonAssert.with(stringWriter.toString()).assertEquals("$.name", "parameterName");
    }

    @Test
    public void demonstrateDeserializingParameterUsingDefaultConfig() throws ParamSerializationException {
        // given
        SerializationConfig serializationConfig = SerializationConfigBuilder.serializationConfig().build();
        ParamDeserializer deserializer = ParamSerializerFactory.paramDeserializer(serializationConfig);

        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/param/simple-parameter.csv")));

        // when
        Parameter parameter = deserializer.deserialize(reader);

        // then
        assertThat(parameter).hasName("simpleParameter");
    }

}
