package cn.dao.tds;




import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface TdsHomeMapper {
	
	
	
	/**
	 * 统计客户数量，累计充值金额，剩余佣金
	 * @param userId
	 * @return
	 */
	 Map<String,Object> countByUserId(@Param("userId") Integer userId);
	
}
