package com.jmu.bibasedmanage.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动任务
 * Created by ljc on 2018/1/12.
 */
public class provide {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:applicationContext-quartz.xml" });
    }
}
