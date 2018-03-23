package com.github.kahlkn.yui.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisTest {
    private Jedis jedis;

    @Before
    public void init() {
        jedis = Jedis.on("share0.oaw.me")
                .setPassword("+5WZSDV3CZU2PPW+").init();
    }

    @Test
    public void test1() throws IOException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("aa", "11");
        data.put("bb", "22");
        data.put("cc", "33");
        data.put("dd", "44");
        String key = "test_data";
        List<Response<Long>> result = new ArrayList<Response<Long>>();

        redis.clients.jedis.Jedis jedis = this.jedis.getJedis();
        Pipeline pipelined = jedis.pipelined();
        try {

            for (Map.Entry<String, String> entry : data.entrySet()) {
                Response<Long> response = pipelined.hset(key, entry.getKey(), entry.getValue());
                result.add(response);
            }

            pipelined.sync();
        }
        finally {
            pipelined.close();
            jedis.close();
        }

        for (Response<Long> response : result) {
            System.out.print(response.get() + " ");
        }
    }

    @Test
    public void test2() throws IOException {
        String key = "test_data";
        Response<Map<String, String>> response;

        redis.clients.jedis.Jedis jedis = this.jedis.getJedis();
        Pipeline pipelined = jedis.pipelined();
        try {
            response = pipelined.hgetAll(key);
            System.out.println(response.get());
            pipelined.sync();
        }
        finally {
            pipelined.close();
            jedis.close();
        }

        System.out.println(response.get());
    }

}
