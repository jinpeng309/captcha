package com.capslock.rpc.service.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by alvin.
 */
@Service
public class CaptchaCache {
    private static final String CAPTCHA_KEY_PREFIX = "captcha_";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String createKey(final int countryCode, final long phoneNumber) {
        return CAPTCHA_KEY_PREFIX.concat(Integer.toString(countryCode)).concat(Long.toString(phoneNumber));
    }

    public void addCaptchaWithExpiredTime(final int countryCode, final long phoneNumber, final String captcha) {
        final String key = createKey(countryCode, phoneNumber);
        stringRedisTemplate.boundValueOps(key).set(captcha, 1, TimeUnit.SECONDS);
    }

    public String getCaptcha(final int countryCode, final long phoneNumber) {
        final String key = createKey(countryCode, phoneNumber);
        return stringRedisTemplate.boundValueOps(key).get();
    }
}
