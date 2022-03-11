package com.example.demo.constant;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidSlf4jConfig;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidStatConfig;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidWallConfig;

public class DruidConfigConstant {

    public static DruidConfig getDruidConfig() {
        // 一切的起源
        DruidConfig druidConfig = new DruidConfig();
        // 设置过滤器
//        druidConfig.setFilters("stat,wall,log4j2");
        druidConfig.setFilters("stat,log4j2");
        DruidStatConfig statConfig = new DruidStatConfig();
        DruidSlf4jConfig slf4jConfig = new DruidSlf4jConfig();
        DruidWallConfig wallConfig = new DruidWallConfig();
        // 设置是否允许*查询,如果为false则查询时必须加上字段,防止表结构泄露
//        wallConfig.setSelectAllColumnAllow(false);
        // 设置是否允许replace语句
        wallConfig.setReplaceAllow(false);
        // 设置
        wallConfig.setMergeAllow(false);
        // 设置是否允许truncate(极其危险)
        wallConfig.setTruncateAllow(false);
        // 设置是否允许建表
        wallConfig.setCreateTableAllow(false);
        // 设置是否允许更新表结构
        wallConfig.setAlterTableAllow(false);
        // 设置是否允许删除表
        wallConfig.setDropTableAllow(false);
        // 设置SQL语句是否允许存在注释
        wallConfig.setCommentAllow(false);
        // 设置
        wallConfig.setNoneBaseStatementAllow(false);
        // 设置
        wallConfig.setDescribeAllow(false);
        // 设置是否允许show语句
        wallConfig.setShowAllow(false);
        // 设置where是否允许始终为true
        wallConfig.setSelectWhereAlwayTrueCheck(true);
        // 设置Having是否允许始终为true
        wallConfig.setSelectHavingAlwayTrueCheck(true);
        // 设置DeleteWhere是否允许始终为true
        wallConfig.setDeleteWhereAlwayTrueCheck(true);
        // 设置Delete时是否允许没有where条件
        wallConfig.setDeleteWhereNoneCheck(true);
        // 设置Update时是否允许where条件始终为true
        wallConfig.setUpdateWhereAlayTrueCheck(true);
        // 设置Update时是否允许没有where条件
        wallConfig.setUpdateWhereNoneCheck(true);
        // 设置
        wallConfig.setConditionAndAlwayTrueAllow(false);
        // 设置
        wallConfig.setConditionAndAlwayFalseAllow(false);
        // 设置
        wallConfig.setConditionLikeTrueAllow(false);
        // 设置
        wallConfig.setSelectIntoOutfileAllow(false);
        // 设置
        wallConfig.setStrictSyntaxCheck(true);
        // 设置
        wallConfig.setConditionOpXorAllow(false);
        // 设置
        wallConfig.setConditionOpBitwseAllow(false);
        // 设置
        wallConfig.setConditionDoubleConstAllow(false);
        // 设置
        wallConfig.setConstArithmeticAllow(false);
        // 设置limit不能为0,防止泄露表结构
        wallConfig.setLimitZeroAllow(false);
        // 设置查询语句不加limit自动追加limit 10
//        wallConfig.setSelectLimit(10);
        wallConfig.setWrapAllow(false);
        // 设置日志
        slf4jConfig.setStatementExecutableSqlLogEnable(true);
        // 设置日志是否开启
        slf4jConfig.setEnable(true);
        // 设置日志是否记录慢查
        statConfig.setLogSlowSql(true);
        // 设置日志同一SQL合并
        statConfig.setMergeSql(true);
        // 设置SQL执行多少毫秒算慢查
        statConfig.setSlowSqlMillis(Integer.toUnsignedLong(3000));
        // 设置连接验证查询语句
        druidConfig.setValidationQuery("select 1");
        druidConfig.setRemoveAbandoned(true);
        druidConfig.setLogAbandoned(true);

        druidConfig.setTestWhileIdle(true);
        // 设置日志
        druidConfig.setWall(wallConfig);
        // 设置日志
        druidConfig.setSlf4j(slf4jConfig);
        // 设置stat
        druidConfig.setStat(statConfig);
        // 连接失败重试次数，但druid源码应该是写死无限重连，
        druidConfig.setConnectionErrorRetryAttempts(5);
        // 设置失败不重连，可能会出问题
        druidConfig.setBreakAfterAcquireFailure(true);
        return druidConfig;
    }
}
