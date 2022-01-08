package com.xjz.springframework.test;

import com.xjz.springframework.config.RootConfig;
import com.xjz.springframework.dao.mapper.ProductionMapper;
import com.xjz.springframework.service.ProductionService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Williami
 * @description
 * @date 2022/1/8
 */
public class MybatisTests {

	public static void main(String[] args) throws Exception {

		System.setProperty("javax.net.debug", "all");

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);

		ProductionService productionService = applicationContext.getBean(ProductionService.class);

		System.out.println(">>>>>>>"+applicationContext.getBean(ProductionMapper.class));

		productionService.list().stream().forEach(System.out::println);

		SqlSessionFactoryBean sqlSessionFactoryBean = applicationContext.getBean(SqlSessionFactoryBean.class);

		/**
		 * 代理对象是由谁产生的？Mybatis
		 */
		SqlSession sqlSession = sqlSessionFactoryBean.getObject().openSession(true);
		// org.apache.ibatis.binding.MapperProxy@66eb985d
		System.out.println(sqlSession.getMapper(ProductionMapper.class));

	}
}
