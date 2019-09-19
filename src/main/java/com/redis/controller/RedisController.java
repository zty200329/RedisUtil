package com.redis.controller;

import com.google.gson.Gson;
import com.redis.VO.ResultVO;
import com.redis.entity.Student;
import com.redis.enums.ResultEnum;
import com.redis.redis.RedisUtil;
import com.redis.service.RedisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zty
 * @date 2019/9/18 下午8:59
 * @description:
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisService redisService;
    @GetMapping( path = "/getList")
    public List<Student> getList(){
        List<Student> students = redisService.selectAll();
        return students;
    }

    /**
     * 根据学号查找学生
     * @param numberCode
     * @return
     */
    @GetMapping("/getStudent")
    public String getStu(@RequestParam("number") String numberCode){
        Student students = redisService.getStudent(numberCode);
        String str = new Gson().toJson(students);
        return str;
    }

    @PostMapping(path = "/insert")
    public String insert(@RequestParam Student student){
        int insert = redisService.insert(student);
        String msg = "";
        if( insert > 0 ){
            msg = "{\"msg\":\"新增成功\",\"flag\":true}";
        }else {
            msg = "{\"msg\":\"新增失败\",\"flag\":false}";
        }
        return msg;
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam("numberCode") String numberCode){
        int delete = redisService.delete(numberCode);
        String msg = "";
        if( delete > 0 ){
            msg = "{\"msg\":\"删除成功！！\",\"flag\":true}";
        }else {
            msg = "{\"msg\":\"删除失败！！\",\"flag\":false}";
        }
        return msg;
    }
    @PostMapping(path = "/update")
    public String update(@RequestParam Student student){
        int update = redisService.updateByPrimaryKey(student);
        String msg = "";
        if( update > 0 ){
            msg = "{\"msg\":\"更新成功！！！\",\"flag\":true}";
        }else {
            msg = "{\"msg\":\"更新失败！！！\",\"flag\":false}";
        }
        return msg;
    }
}
