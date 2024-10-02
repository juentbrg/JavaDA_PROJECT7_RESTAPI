package com.nnk.springboot.utils;

import lombok.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CRUDUtilsTest {

    @Data
    @AllArgsConstructor
    @Generated
    public static class TestObject {
        private String field1;
        private String field2;
        private Integer field3;
    }

    @Test
    public void getNullPropertyNamesWhenFieldsAreNullTest() {
        TestObject testObject = new TestObject(null, "value2", null);

        String[] nullPropertyNames = CRUDUtils.getNullPropertyNames(testObject);

        assertArrayEquals(new String[]{"field1", "field3"}, nullPropertyNames);
    }

    @Test
    public void getNullPropertyNamesWhenNoFieldsAreNullTest() {
        TestObject testObject = new TestObject("value1", "value2", 10);

        String[] nullPropertyNames = CRUDUtils.getNullPropertyNames(testObject);

        assertArrayEquals(new String[]{}, nullPropertyNames);
    }

    @Test
    public void getNullPropertyNamesWhenAllFieldsAreNullTest() {
        TestObject testObject = new TestObject(null, null, null);

        String[] nullPropertyNames = CRUDUtils.getNullPropertyNames(testObject);

        assertArrayEquals(new String[]{"field1", "field2", "field3"}, nullPropertyNames);
    }
}
