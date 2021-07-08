package top.mylady.media.ctrl.v1;
import top.mylady.media.service.StatisticsService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.dtos.StatisticDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @PostMapping("/news")
    public ResponseResult newsData(@RequestBody StatisticDto dto){
        return statisticsService.findWmNewsStatistics(dto);
    }
}
