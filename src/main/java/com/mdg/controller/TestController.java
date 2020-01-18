package com.mdg.controller;

import com.mdg.entity.TablesEntity;
import com.mdg.service.TablesInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用于测试的Controller
 */
@RestController
@RequestMapping("/api/v1.0/tables")
public class TestController {

    @Resource
    private TablesInfoService tablesInfoService;

    @GetMapping(value = "/listTables")
    @ResponseBody
    public List<TablesEntity> listTables(@RequestParam("databaseName") String databaseName) {
        List<TablesEntity> tablesEntityList = tablesInfoService.listTables(databaseName);
        return tablesEntityList;
    }

    @GetMapping(value = "/listField")
    @ResponseBody
    public List<Map<String,String>> listField(@RequestParam("databaseName") String databaseName,
                                              @RequestParam("tableName") String tableName) {
        List<Map<String,String>> map = tablesInfoService.listTableField(databaseName, tableName);
        return map;
    }

    @GetMapping(value = "/genDocument")
    @ResponseBody
    public String genDocument(@RequestParam("databaseName") String databaseName) throws IOException {
        return tablesInfoService.generateDocument(databaseName);
    }
}
