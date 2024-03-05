package cn.easyops.demo.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.easyops.demo.utils.utils;

import org.springframework.kafka.core.KafkaTemplate;

@RestController
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/user/searchByMobile", method = RequestMethod.GET)
    public String searchByMobile(@RequestParam(value = "mobile") String mobile) {
        stringRedisTemplate.opsForHash().put("skywalking.trace.demo", mobile,
                System.currentTimeMillis() + "");

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("skywalking.trace.demo", mobile);
        kafkaTemplate.send(record);

        return mobile;
    }

    @RequestMapping(value = "/user/throwException", method = RequestMethod.GET)
    public void throwException() throws Exception {
        utils.throwException();
    }
}
