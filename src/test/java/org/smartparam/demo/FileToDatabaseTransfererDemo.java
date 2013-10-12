/*
 * Copyright 2013 Adam Dubiel.
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

import javax.sql.DataSource;
import org.polyjdbc.core.dialect.DialectRegistry;
import org.polyjdbc.core.integration.DataSourceFactory;
import org.smartparam.repository.fs.ClasspathParamRepository;
import org.smartparam.repository.jdbc.JdbcParamRepository;
import org.smartparam.repository.jdbc.config.Configuration;
import org.smartparam.transferer.StandardTransfererBuilder;
import org.smartparam.transferer.TransferConfig;
import org.smartparam.transferer.Transferer;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.smartparam.repository.jdbc.config.DefaultConfigurationBuilder.defaultConfiguration;
import static org.smartparam.repository.jdbc.config.pico.PicoJdbcParamRepositoryFactory.jdbcRepository;

/**
 *
 * @author Adam Dubiel
 */
public class FileToDatabaseTransfererDemo {

    @Test
    public void transferToDatabase() {
        // given
        DataSource dataSource = DataSourceFactory.create(DialectRegistry.dialect("H2"), "jdbc:h2:mem:test", "smartparam", "smartparam");
        Configuration configuration = defaultConfiguration().withDialect(DialectRegistry.dialect("H2")).build();

        JdbcParamRepository jdbcRepository = jdbcRepository(dataSource, configuration);
        jdbcRepository.initialize();

        ClasspathParamRepository classpathRepository = new ClasspathParamRepository("/param/", ".*\\.csv$");
        classpathRepository.initialize();

        Transferer transferer = StandardTransfererBuilder.standardTransferer().build();

        // when
        transferer.transfer(TransferConfig.allOperations(), classpathRepository, jdbcRepository);

        // then
        assertThat(jdbcRepository.listParameters()).hasSize(1);
    }
}
