package top.mylady.media.service.impl;

import top.mylady.media.service.AdChannelService;
import top.mylady.model.admin.pojos.AdChannel;
import top.mylady.model.mappers.admin.AdChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdChannelServiceImpl implements AdChannelService {

    @Autowired
    private AdChannelMapper adChannelMapper;

    @Override
    public List<AdChannel> selectAll() {
        return adChannelMapper.selectAll();
    }
}
