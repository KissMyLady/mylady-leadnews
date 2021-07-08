package top.mylady.admin.ctrl.v1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.mylady.admin.service.CommonService;
import top.mylady.model.admin.dtos.CommonDto;
import top.mylady.model.common.dtos.ResponseResult;


@RestController
@RequestMapping("/api/v1/admin/common")
public class CommonCtrl {

    @Autowired
    private CommonService commonService;

    @PostMapping("/list")
    public ResponseResult list(@RequestBody CommonDto dto){
        return commonService.list(dto);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody CommonDto dto){
        return commonService.update(dto);
    }

    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody CommonDto dto){
        return commonService.delete(dto);
    }
}
