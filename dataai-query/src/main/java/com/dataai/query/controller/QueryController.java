package com.dataai.query.controller;

import com.dataai.common.pojo.Result;
import com.dataai.query.service.Text2SQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private Text2SQLService text2SQLService;

    @GetMapping("/query")
    public Result<List<Map<String, Object>>> query(@RequestParam String q) {
        try {
            List<Map<String, Object>> data = text2SQLService.query(q);
            return Result.success("查询成功", data);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
}