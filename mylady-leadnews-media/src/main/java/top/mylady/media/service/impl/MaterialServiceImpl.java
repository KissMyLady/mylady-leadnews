package top.mylady.media.service.impl;

import org.apache.log4j.Logger;
import top.mylady.common.fastdfs.FastDfsClient;
import top.mylady.media.service.MaterialService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.wemedia.WmMaterialMapper;
import top.mylady.model.mappers.wemedia.WmNewsMaterialMapper;
import top.mylady.model.media.dtos.WmMaterialDto;
import top.mylady.model.media.dtos.WmMaterialListDto;
import top.mylady.model.media.pojos.WmMaterial;
import top.mylady.model.media.pojos.WmUser;
import top.mylady.utils.threadlocal.WmThreadLocalUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//@SuppressWarnings("all")
@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private static final Logger logger = Logger.getLogger(MaterialServiceImpl.class);

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    //@Autowired
    //private FastDfsClient fastDfsClient;

    @Value("${FILE_SERVER_URL}")
    private String fileServerUrl;

    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        //获取当前登录用户
        WmUser wmUser = WmThreadLocalUtils.getUser();
        if(wmUser==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        //验证参数
        if(multipartFile==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //上传图片到fastdfs
        String originalFilename = multipartFile.getOriginalFilename();//aa.jpg
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        if(!extName.matches("(gif|png|jpg|jpeg)")){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_IMAGE_FORMAT_ERROR);
        }
        String fileId = null;
        try {
            logger.debug("fastDfsClient上传, 这里隐藏");
            //fileId = fastDfsClient.uploadFile(multipartFile.getBytes(), extName);
            fileId = "adsadasdsaas";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("user {} upload file {} to fastDfs error,error info:n", wmUser.getId(),originalFilename,e.getMessage());
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }
        //上传成功后，保存一条数据wm_material
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setCreatedTime(new Date());
        wmMaterial.setType((short)0);
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setUserId(wmUser.getId());
        wmMaterialMapper.insertMaterial(wmMaterial);
        wmMaterial.setUrl(fileServerUrl+fileId);
        return ResponseResult.okResult(wmMaterial);
    }

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Override
    public ResponseResult delPicture(WmMaterialDto dto) {
        WmUser user = WmThreadLocalUtils.getUser();
        if(dto==null || dto.getId() ==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //删除fastdfs中的图片 先去判断当前图片有没有关联
        WmMaterial wmMaterial = wmMaterialMapper.selectByMaterialId(dto.getId());
        if(wmMaterial==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        int count = wmNewsMaterialMapper.countByMid(wmMaterial.getId());
        if(count > 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"当前图片被引用");
        }
        String fileId = wmMaterial.getUrl().replace(fileServerUrl,"");
        try {
            //fastDfsClient.delFile(fileId);
            logger.warn("fastDfsClient删除文件, 隐藏不写, SDK明显的报错");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("user {} delete file {} from fastDfs error,error info :n",user.getId(),fileId,e.getMessage());
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }
        //删除 wm_material表中的数据
        wmMaterialMapper.deleteByMaterialId(dto.getId());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult findList(WmMaterialListDto dto) {
        //验证参数
        dto.checkParam();
        //获取用户
        Long uid = WmThreadLocalUtils.getUser().getId();
        //查询
        List<WmMaterial> datas = wmMaterialMapper.findListByUidAndStatus(dto, uid);
        datas = datas.stream().map((item)->{
            item.setUrl(fileServerUrl+item.getUrl());
            return item;
        }).collect(Collectors.toList());
        int total = wmMaterialMapper.countListByUidAndStatus(dto, uid);
        //返回
        Map<String,Object> resDatas = new HashMap<>();
        resDatas.put("curPage",dto.getPage());
        resDatas.put("size",dto.getSize());
        resDatas.put("list",datas);
        resDatas.put("total",total);
        return ResponseResult.okResult(resDatas);
    }

    @Override
    public ResponseResult changeUserMaterialStatus(WmMaterialDto dto, Short type) {
        if(dto ==null || dto.getId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取用户信息
        WmUser user = WmThreadLocalUtils.getUser();
        wmMaterialMapper.updateStatusByUidAndId(dto.getId(),user.getId(),type);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
