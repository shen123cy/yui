package com.github.kahlkn.yui.core.serialize;

import com.github.kahlkn.artoria.codec.Hex;
import com.github.kahlkn.artoria.serialize.SerializeUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

public class FstTest implements Serializable {

    @Before
    public void init() {
        SerializeUtils.setSerializer(new FstSerializer());
        SerializeUtils.setDeserializer(new FstDeserializer());
    }

    @Test
    public void test1() {
        FstTest fstTest = new FstTest();
        System.out.println(fstTest);
        byte[] bytes = SerializeUtils.serialize(fstTest);
        System.out.println(Hex.ME.encode(bytes));
        Object obj = SerializeUtils.deserialize(bytes);
        System.out.println(obj);
    }

}
