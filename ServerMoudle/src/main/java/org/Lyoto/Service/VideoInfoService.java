package org.Lyoto.Service;

import org.Lyoto.Dao.VideoInfo.VideoInfoMapper;
import org.Lyoto.Pojo.courseVideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lyoto
 * @Date 2021-05-07 22:57
 **/
@Service
public class VideoInfoService {
    @Autowired
    VideoInfoMapper videoInfoMapper;
    public int addVideoInfo(courseVideoInfo courseVideoInfo){
       return videoInfoMapper.addVideoInfo(courseVideoInfo);
    }

}
