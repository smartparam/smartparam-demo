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

import org.smartparam.engine.core.ParamEngine;
import org.smartparam.engine.core.context.DefaultContext;
import org.smartparam.engine.core.context.ParamContext;
import org.smartparam.engine.core.output.MultiValue;
import org.smartparam.engine.core.output.ParamValue;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.smartparam.test.ParamValueBuilder.paramValue;

/**
 *
 * @author Adam Dubiel
 */
public class UnitTestingDemo {

    @Test
    public void demonstrateUsingParamValueBuilderToMockParamEngineCallsForSimpleValues() {
        // given
        ParamEngine engine = mock(ParamEngine.class);
        when(engine.get(eq("test"), any(ParamContext.class))).thenReturn(paramValue(5));

        // when
        int value = engine.get("test", new DefaultContext()).getInteger();

        // then
        assertThat(value).isEqualTo(5);
    }

    @Test
    public void demonstrateUsingParamValueBuilderToMockMultipleNamedLevelsInResult() {
        // given
        ParamEngine engine = mock(ParamEngine.class);
        ParamValue paramValue = paramValue().withNamedLevels("min", "max", "name").withRow(0, 10, "constraint").build();
        when(engine.get(eq("test"), any(ParamContext.class))).thenReturn(paramValue);

        // when
        MultiValue row = engine.get("test", new DefaultContext()).row();

        // then
        assertThat(row.get("name")).isEqualTo("constraint");
        assertThat(row.getInteger("min")).isEqualTo(0);
        assertThat(row.getInteger("max")).isEqualTo(10);
    }

    @Test
    public void demonstrateUsingParamValueBuilderToMockMultipleRowsInResult() {
        // given
        ParamEngine engine = mock(ParamEngine.class);
        ParamValue paramValue = paramValue().withNamedLevels("min", "max", "name")
                .withRow(0, 10, "constraint")
                .withRow(5, 20, "constraint2").build();
        when(engine.get(eq("test"), any(ParamContext.class))).thenReturn(paramValue);

        // when
        ParamValue matrix = engine.get("test", new DefaultContext());

        // then
        assertThat(matrix.get(0, "name", String.class)).isEqualTo("constraint");
        assertThat(matrix.get(1, "name", String.class)).isEqualTo("constraint2");
    }
}
