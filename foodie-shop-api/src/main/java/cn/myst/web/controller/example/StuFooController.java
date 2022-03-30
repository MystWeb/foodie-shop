package cn.myst.web.controller.example;

import cn.myst.web.pojo.Stu;
import cn.myst.web.service.StuService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Hidden
@Slf4j
@RestController
@RequestMapping("students")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StuFooController {
    private final StuService stuService;

    @Operation(summary = "获取学生信息")
    @GetMapping("getStu")
    public Stu getStuInfo(@RequestParam int id) {
        return stuService.getStuInfo(id);
    }

    @Operation(summary = "保存学生")
    @PostMapping("saveStu")
    public Object saveStu() {
        stuService.saveStu();
        return "OK";
    }

    @Operation(summary = "更新学生")
    @PutMapping("updateStu")
    public Object updateStu(@RequestParam int id) {
        stuService.updateStu(id);
        return "OK";
    }

    @Operation(summary = "删除学生")
    @DeleteMapping("deleteStu")
    public Object deleteStu(@RequestParam int id) {
        stuService.deleteStu(id);
        return "OK";
    }
}
