package com.redis.service;

import com.redis.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zty
 * @date 2019/9/19 上午10:14
 * @description:
 */
public interface RedisService {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    Student selectByPrimaryKey(Integer id);

    List<Student> selectAll();

    int updateByPrimaryKey(Student record);

    Student getStudent(String num);

    int delete(@Param("number") String num);
}
