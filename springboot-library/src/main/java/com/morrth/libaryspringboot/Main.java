//
//
//package com.morrth.libaryspringboot;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
///**
// * @author morrth
// * @create 2021-03-20-10:59
// */
//
//public class Main {
//    public static void main(String[] args) {
//        //创建generator对象
//        AutoGenerator autoGenerator = new AutoGenerator();
//        //数据源
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//        dataSourceConfig.setDbType(DbType.MYSQL);
//        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
//        dataSourceConfig.setUsername("moon");
//        dataSourceConfig.setPassword("weiwei");
//        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
//        autoGenerator.setDataSource(dataSourceConfig);
//        //全局配置
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
//        globalConfig.setOpen(false); //创建好 不打开文件
//        globalConfig.setAuthor("morrth");
//        globalConfig.setServiceName("%sService");
//        autoGenerator.setGlobalConfig(globalConfig);
//        //包信息
//        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setParent("com.morrth.libaryspringboot");
//        packageConfig.setController("controller");
//        packageConfig.setService("service");
//        packageConfig.setServiceImpl("service.impl");
//        packageConfig.setMapper("mapper");
//        packageConfig.setEntity("entity");
//        autoGenerator.setPackageInfo(packageConfig);
//        //配置策略
//        StrategyConfig strategyConfig = new StrategyConfig();
////        设置生成的表
//        strategyConfig.setEntityLombokModel(true);
//        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        autoGenerator.setStrategy(strategyConfig);
//
//        autoGenerator.execute();
//    }
//}
