package com.redis.service.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redis.dao.StudentMapper;
import com.redis.entity.Student;
import com.redis.redis.RedisUtil;
import com.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: zty
 * @date 2019/9/19 上午10:15
 * @description:
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Student record) {
        String key = "student_list";

        int insert = studentMapper.insert(record);
        if(insert > 0){
            redisUtil.del(key);
        }
        return insert;
    }

    @Override
    public Student selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Student> selectAll() {
        String key = "student_list";
        Boolean hasKey = redisUtil.hasKey(key);

        ValueOperations operations = redisTemplate.opsForValue();
        if(hasKey){
            String redisList = (String) redisUtil.get(key);

            Type type = new TypeToken<List<Student>>() {}.getType();
            List<Student> list =  new Gson().fromJson(redisList,type);


            log.info("StudentServiceImpl.selectAll() : 从缓存取得数据，条数：" + list.size());
            return list;
        }
        List<Student> list = studentMapper.selectAll();
        String toJson = new Gson().toJson(list);
        // 存在到缓存中
        redisUtil.set(key,toJson,600);
        return list;
    }

    /**
     *更新学生信息逻辑
     * 如果缓存存在 删除
     * 如果缓存不存在 不操作
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(Student record) {
        String key = "student_" + record.getNumber();
        Boolean hasKey = redisUtil.hasKey(key);

        int update = studentMapper.updateByPrimaryKey(record);

        if(update>0){
            //缓存存在 删除
            if(hasKey){
                redisUtil.del(key);
                log.info("StudentServiceImpl.update() : 从缓存中删除学生 >> " + record.toString());
            }
        }
        return update;
    }

    /**
     * 获取一位学生的逻辑
     * 如果缓存存在 从缓存中获取学生信息
     * 如果缓存不存在 从DB中获取学生信息 然后插入缓存
     * @param num
     * @return
     */
    @Override
    public Student getStudent(String num) {
        //从缓存中取出学生信息
        String key = "student_" + num;
        Boolean hasKey = redisUtil.hasKey(key);
        //缓存存在
        if (hasKey){
            String str = (String) redisUtil.get(key);
            Student student = new Gson().fromJson(str,Student.class);
            log.info("StudentServiceImpl.getStudent() : 从缓存取得数据 >> " + student.toString());
            return student;
        }
        Student student = studentMapper.getStudent(num);
        String str = new Gson().toJson(student);
        //插入缓存
        redisUtil.set(key,str,600);
        log.info("StudentServiceImpl.getStudent() : 学生信息插入缓存 >> " + student.toString());
        return student;
    }

    @Override
    public int delete(String num) {
        String key = "student_" + num;
        Boolean hasKey = redisUtil.hasKey(key);

        int delete = studentMapper.delete(num);
        if( delete > 0 ){
            //判断缓存是否存在 并删除
            if(hasKey){
                redisUtil.del(key);
                log.info("StudentServiceImpl.update() : 从缓存中删除编号学生 >> " + num);
            }
        }
        return delete;
    }
}
