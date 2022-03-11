package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.example.demo.constant.DruidConfigConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceInit extends AbstractDataSourceProvider {

    @Value("${spring.datasource.dynamic.datasource.datacenter.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.dynamic.datasource.datacenter.url}")
    private String url;

    @Value("${spring.datasource.dynamic.datasource.datacenter.username}")
    private String username;

    @Value("${spring.datasource.dynamic.datasource.datacenter.password}")
    private String password;

    @SneakyThrows
    @Override
    @Bean
    public Map<String, DataSource> loadDataSources() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            Map<String, DataSourceProperty> dataSourcePropertiesMap = executeStmt(statement);
            return createDataSourceMap(dataSourcePropertiesMap);
        } catch (Exception sqlException) {
            log.error("DataSourceInit.loadDataSources -> ", sqlException);
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    protected Map<String, DataSourceProperty> executeStmt(Statement statement) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("select appid, username, password, connectionstring, databasetype from p_s00_app where xver is null");
            HashMap<String, DataSourceProperty> map = new HashMap<>(32);
            while (resultSet.next()) {
                String name = resultSet.getString("appid").toUpperCase();
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String url = resultSet.getString("connectionstring");
                String driver = resultSet.getString("databasetype");
                DataSourceProperty property = new DataSourceProperty();
                property.setUsername(username);
                property.setPassword(password);
                property.setUrl(url);
                property.setDriverClassName(driver);
                property.setPoolName(name);
                property.setType(DruidDataSource.class);
                DruidConfig druidConfig = DruidConfigConstant.getDruidConfig();
                if("oracle.jdbc.driver.OracleDriver".equals(driver)) {
                    druidConfig.setValidationQuery("select 1 from dual");
                }
                property.setDruid(druidConfig);
                map.put(name, property);
            }
            return map;
        } catch (SQLException e) {
            log.error("resultSet error -> :{}", e.toString());
        } finally {
            JdbcUtils.closeResultSet(resultSet);
        }
        return null;
    }

}
