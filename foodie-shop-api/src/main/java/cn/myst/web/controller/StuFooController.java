package cn.myst.web.controller;

import cn.myst.web.pojo.Stu;
import cn.myst.web.service.StuService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@ApiIgnore
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StuFooController {
    private final StuService stuService;

    @ApiOperation(value = "获取学生信息")
    @GetMapping("/getStu")
    public Stu getStuInfo(@RequestParam int id) {
        return stuService.getStuInfo(id);
    }

    @ApiOperation("保存学生")
    @PostMapping("/saveStu")
    public Object saveStu() {
        stuService.saveStu();
        return "OK";
    }

    @ApiOperation("更新学生")
    @PutMapping("/updateStu")
    public Object updateStu(@RequestParam int id) {
        stuService.updateStu(id);
        return "OK";
    }

    @ApiOperation("删除学生")
    @PostMapping("/deleteStu")
    public Object deleteStu(@RequestParam int id) {
        stuService.deleteStu(id);
        return "OK";
    }
}
