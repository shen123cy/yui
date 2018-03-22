package com.github.kahlkn.yui.core.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.github.kahlkn.artoria.codec.Hex;
import com.github.kahlkn.artoria.serialize.SerializeUtils;
import org.junit.Before;
import org.junit.Test;

public class KryoTest {

    @Before
    public void init() {
        KryoFactory factory = new KryoFactory() {
            @Override
            public Kryo create() {
                return new Kryo();
            }
        };
        KryoPool pool = new KryoPool.Builder(factory).softReferences().build();
        SerializeUtils.setSerializer(new KryoSerializer(pool));
        SerializeUtils.setDeserializer(new KryoDeserializer(pool));
    }

    @Test
    public void test1() {
        KryoTest kryoTest = new KryoTest();
        System.out.println(kryoTest);
        byte[] bytes = SerializeUtils.serialize(kryoTest);
        System.out.println(Hex.ME.encode(bytes));
        Object obj = SerializeUtils.deserialize(bytes);
        System.out.println(obj);
    }

}
