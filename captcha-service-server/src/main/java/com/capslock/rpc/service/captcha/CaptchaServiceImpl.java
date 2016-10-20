package com.capslock.rpc.service.captcha;

import com.capslock.rpc.api.captcha.CaptchaService;
import com.google.common.base.Strings;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Random;

/**
 * Created by alvin.
 */
@MotanService
public class CaptchaServiceImpl implements CaptchaService {
    private final Random random = new Random(System.currentTimeMillis());

    @Autowired
    private CaptchaCache captchaCache;

    @Override
    public void sendCaptcha(final int countryCode, final long phoneNumber) {
        final int captcha = 10000 + random.nextInt(90000);
        captchaCache.addCaptchaWithExpiredTime(countryCode, phoneNumber, Integer.toString(captcha));
    }

    @Override
    public int verifyCaptcha(final int countryCode, final long phoneNumber, final String captcha) {
        final String captchaInCache = captchaCache.getCaptcha(countryCode, phoneNumber);
        if (Strings.isNullOrEmpty(captchaInCache)) {
            return VerifyResultCode.EXPIRED;
        } else if (!Objects.equals(captcha, captchaInCache)) {
            return VerifyResultCode.ILLEGAL;
        }
        return VerifyResultCode.SUCCEED;
    }
}
