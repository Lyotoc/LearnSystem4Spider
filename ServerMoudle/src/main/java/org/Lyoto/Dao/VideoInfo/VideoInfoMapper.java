package org.Lyoto.Dao.VideoInfo;

import org.Lyoto.Pojo.CourseInfo;
import org.Lyoto.Pojo.courseVideoInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lyoto
 * @Date 2021-05-07 22:00
 **/

public interface VideoInfoMapper {
    //增加
    int addVideoInfo(courseVideoInfo courseVideoInfo);
}
