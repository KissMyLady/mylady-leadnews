package top.mylady.media.service.impl;

import org.apache.log4j.Logger;
import top.mylady.common.media.constans.WmMediaConstans;
import top.mylady.media.service.StatisticsService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
//import top.mylady.model.mappers.wemedia.WmNewsStatisticsMapper;
import top.mylady.model.mappers.wemedia.WmUserMapper;
import top.mylady.model.media.dtos.StatisticDto;
import top.mylady.model.media.pojos.WmNewsStatistics;
import top.mylady.model.media.pojos.WmUser;
import top.mylady.utils.common.BurstUtils;
import top.mylady.utils.threadlocal.WmThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class);

    @Autowired
    WmUserMapper wmUserMapper;

    //@Autowired
    //WmNewsStatisticsMapper wmNewsStatisticsMapper;

    @Override
    public ResponseResult findWmNewsStatistics(StatisticDto dto) {
        if(dto==null && dto.getType()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(WmMediaConstans.WM_NEWS_STATISTIC_CUR!=dto.getType() && (dto.getStime()==null || dto.getEtime()==null)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询用户
        WmUser user = WmThreadLocalUtils.getUser();
        WmUser wmUser = wmUserMapper.selectById(user.getId());
        String burst = BurstUtils.groudOne(wmUser.getApUserId());
        //List<WmNewsStatistics> list = wmNewsStatisticsMapper.findByTimeAndUserId(burst, wmUser.getApUserId(), dto);
        List<WmNewsStatistics> list = null;
        logger.warn("media-StatisticsServiceImpl -> WmNewsStatisticsMapper 映射mapper未设置, 这里仅仅返回了一个空, 注意记得修改");
        return ResponseResult.okResult(list);
    }
}
