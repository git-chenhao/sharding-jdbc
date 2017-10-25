/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package io.shardingjdbc.orchestration.api;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;
import io.shardingjdbc.orchestration.api.config.OrchestrationShardingConfiguration;
import io.shardingjdbc.orchestration.internal.config.ConfigurationService;
import io.shardingjdbc.orchestration.internal.state.InstanceStateService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Orchestration sharding data source factory.
 * 
 * @author zhangliang 
 * @author caohao 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrchestrationShardingDataSourceFactory {
    
    /**
     * Create sharding data source.
     *
     * @param config orchestration sharding configuration
     * @return sharding data source
     * @throws SQLException SQL exception
     */
    public static DataSource createDataSource(final OrchestrationShardingConfiguration config) throws SQLException {
        config.getRegistryCenter().init();
        ShardingDataSource result = (ShardingDataSource) ShardingDataSourceFactory.createDataSource(config.getDataSourceMap(), config.getShardingRuleConfig());
        new ConfigurationService(config.getRegistryCenter(), config.getName()).addShardingConfiguration(config, result);
        new InstanceStateService(config.getRegistryCenter(), config.getName()).addShardingState(result);
        return result;
    }
    
    /**
     * Create sharding data source.
     *
     * @param config orchestration sharding configuration
     * @param props properties for data source
     * @return sharding data source
     * @throws SQLException SQL exception
     */
    public static DataSource createDataSource(final OrchestrationShardingConfiguration config, final Properties props) throws SQLException {
        config.getRegistryCenter().init();
        // TODO props
        ShardingDataSource result = (ShardingDataSource) ShardingDataSourceFactory.createDataSource(config.getDataSourceMap(), config.getShardingRuleConfig(), props);
        new ConfigurationService(config.getRegistryCenter(), config.getName()).addShardingConfiguration(config, result);
        new InstanceStateService(config.getRegistryCenter(), config.getName()).addShardingState(result);
        return result;
    }
    
}