package com.xjz.springframework.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * @author
 */
@Configuration
@ComponentScan(value = {"com.xjz.springframework.service", "com.xjz.springframework.dao"}, excludeFilters = @ComponentScan.Filter(RestController.class))
// 注册Mappers, MapperScan 等同于@Import(MapperScannerRegistrar.class)
// 得到包下的类
@MapperScan("com.xjz.springframework.dao.mapper") // org.mybatis.spring.mapper.MapperFactoryBean
public class RootConfig {

	/**
	 * 等同于<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean"/>
	 *
	 * @param dataSource
	 * @return
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean;
	}

	@Bean
	public DataSource dataSource() {
		// spring-jdbc连接池
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource.setPassword("Monday01");
		driverManagerDataSource.setUrl("jdbc:mysql://101.35.19.88:6389/tmall?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		return driverManagerDataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}


}