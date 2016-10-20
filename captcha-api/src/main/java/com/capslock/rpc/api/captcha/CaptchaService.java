package com.capslock.rpc.api.captcha;

/**
 * Created by alvin.
 */
public interface CaptchaService {
    void sendCaptcha(final int countryCode, final long phoneNumber);

    int verifyCaptcha(final int countryCode, final long phoneNumber, final String captcha);

    class VerifyResultCode {
        /**
         * Don't let anyone instantiate this class.
         */
        private VerifyResultCode() {
            // This constructor is intentionally empty.
        }
        public static final int SUCCEED = 0;
        public static final int EXPIRED = 1;
        public static final int ILLEGAL = 2;
    }
}
