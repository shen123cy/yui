package com.github.kahlkn.yui.redis;

import org.junit.Before;
import org.junit.Test;

public class JediserTest {
    private Jedis jediser;

    @Before
    public void init() {
        jediser = Jedis.on("share0.oaw.me")
                .setPassword("+5WZSDV3CZU2PPW+").init();
    }

    @Test
    public void test1() {
        System.out.println(jediser.ping());
        System.out.println(jediser.set("123", "456"));
        System.out.println(jediser.get("123"));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 300; i++) {
            doSet();
        }
    }

    public void doSet() {
        jediser.flushDB();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
//            System.out.print(redis.set("V" + i, "V" + i) + " ");
            jediser.set("V" + i, "V" + i);
        }
        long end = System.currentTimeMillis();
        long handle1 = end - start;

//        System.out.println();

        start = System.currentTimeMillis();
        redis.clients.jedis.Jedis jedis = jediser.getJedis();
        for (int i = 0; i < 100; i++) {
//            System.out.print(jedis.set("Q" + i, "Q" + i) + " ");
            jedis.set("Q" + i, "Q" + i);
        }
        jedis.close();
        end = System.currentTimeMillis();
        long handle2 = end - start;

//        System.out.println();
//        System.out.println(handle1);
//        System.out.println(handle2);
        System.out.print(handle1 - handle2);
        System.out.print(" ");
    }

}
