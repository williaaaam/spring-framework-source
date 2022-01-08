package com.xjz.springframework.service;

import com.xjz.springframework.dao.entity.ProductionPO;
import com.xjz.springframework.dao.mapper.ProductionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Williami
 * @description
 * @date 2022/1/8
 */
@Service
public class ProductionService {

	/**
	 * jdk proxy代理类
	 */
	@Resource
	ProductionMapper productionMapper;

	public List<ProductionPO> list() {
		// class com.sun.proxy.$Proxy33
		System.out.println(productionMapper.getClass());
		return productionMapper.listProduction();
	}

}
