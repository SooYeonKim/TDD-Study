package com.example.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTestDataTest {

    @Test
    public void testDataTest() {
        TestData testData = new TestData();
        testData.setName("test");

        Assertions.assertEquals("test", testData.getName());
    }
}
