package com.xjz.springframework.dao.mapper;

import com.xjz.springframework.dao.entity.ProductionPO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Williami
 * @description
 * @date 2022/1/8
 */
public interface ProductionMapper {

	/**
	 * 如何实例化？ JDKProxy，如何将实例化后的对象交给Spring来管理呢？
	 * 比如
	 * @Bean
	 * public ProductionMapper mapper(){
			SqlSession.getMapper(ProductionMapper.class);
	 *     return xxx;
	 * }
	 *
	 * @return
	 */
	@Select("select * from tb_production")
	List<ProductionPO> listProduction();

}
