package com.course.miniapp.repo;

import com.course.miniapp.repo.model.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseInfoMapper {
    int deleteByPrimaryKey(Long courseId);

    int deleteByUserId(String userId);

    int deleteByUserIdAndCourseId(String userId, Long courseId);

    int insert(CourseInfo record);

    int batchInsert(@Param("list") List<CourseInfo> list);

    int insertSelective(CourseInfo record);

    CourseInfo selectByPrimaryKey(Long courseId);

    int updateByPrimaryKeySelective(CourseInfo record);

    int updateByCourseIdAndUserId(@Param("list") List<CourseInfo> list);

    int updateByPrimaryKey(CourseInfo record);

    List<CourseInfo> selectByUserId(String userId);

    List<CourseInfo> batchSelectByCourseId(long courseId);
}
