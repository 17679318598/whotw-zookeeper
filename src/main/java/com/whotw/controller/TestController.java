package com.whotw.controller;

import com.whotw.incepter.ZkRegister;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author administrator
 * @description TestController
 * @date 2019/9/20 13:40
 */
@RestController
public class TestController {
    @Autowired
    private ZkRegister zkRegister;

    @RequestMapping("/test")
    public String test() throws KeeperException, InterruptedException {
        StringBuffer buffer = new StringBuffer();
        List<String> children = zkRegister.zkClient.getChildren(zkRegister.rootNode, null);
        children.forEach(child -> buffer.append(child + "\n"));
        return buffer.toString();
    }
}
