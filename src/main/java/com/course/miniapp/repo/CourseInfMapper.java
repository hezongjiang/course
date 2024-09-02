package com.course.miniapp.repo;

import com.course.miniapp.repo.model.CourseInf;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseInfMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseInf record);

    int insertSelective(CourseInf record);

    CourseInf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseInf record);

    int updateByPrimaryKeyWithBLOBs(CourseInf record);

    int updateByPrimaryKey(CourseInf record);

    List<CourseInf> selectByOpenId(String openid);
}
