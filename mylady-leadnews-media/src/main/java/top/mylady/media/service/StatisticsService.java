package top.mylady.media.service;

import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.dtos.StatisticDto;

public interface StatisticsService {
	/**  
	* 查找图文统计数据  
	* @param dto  
	* @return  
	*/  
	ResponseResult findWmNewsStatistics(StatisticDto dto);
}