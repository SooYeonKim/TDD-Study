package com.example.test.service;

import com.example.test.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisServiceTest extends IntegrationTest {
  @Autowired private RedisService redisService;

  @Test
  @DisplayName("Redis Get / Set 테스트")
  public void redisGetSetTest() {
    // given
    String expectValue = "hello";
    String key = "hi";

    // when
    redisService.set(key, expectValue);

    // then
    String actualValue = redisService.get(key);

    Assertions.assertEquals(expectValue, actualValue);
  }
}
