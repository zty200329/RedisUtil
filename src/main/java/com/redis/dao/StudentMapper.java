package com.redis.dao;

import com.redis.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    Student selectByPrimaryKey(Integer id);

    List<Student> selectAll();

    int updateByPrimaryKey(Student record);

    Student getStudent(String num);

    int delete(@Param("number") String num);
}