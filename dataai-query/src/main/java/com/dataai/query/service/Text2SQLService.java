package com.dataai.query.service;

import com.dataai.common.config.QwenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Text2SQLService {

    @Autowired
    private QwenService qwenService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> query(String question) {
        String sql = generateSQL(question);
        System.out.println("生成的 SQL: " + sql);
        return jdbcTemplate.queryForList(sql);
    }

    private String generateSQL(String question) {
        String prompt = "已知数据库表 sales，字段：id, product_name, category, amount, quantity, region, sale_date\n"
                + "请生成 MySQL 查询语句，只返回 SQL，不要解释，不要 markdown 格式。\n"
                + "用户问题：" + question;

        try {
            String sql = qwenService.chat(prompt);
            return sql.replaceAll("(?i)```sql", "")
                    .replaceAll("(?i)```", "")
                    .trim();
        } catch (Exception e) {
            throw new RuntimeException("生成 SQL 失败: " + e.getMessage(), e);
        }
    }
}