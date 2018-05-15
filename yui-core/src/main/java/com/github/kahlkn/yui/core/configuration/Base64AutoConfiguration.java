package com.github.kahlkn.yui.core.configuration;

import com.github.kahlkn.artoria.codec.Base64;
import com.github.kahlkn.artoria.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Base64 auto configuration.
 * @see org.apache.commons.codec.binary.Base64
 * @author Kahle
 */
@Configuration
public class Base64AutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(Base64AutoConfiguration.class);
    private static final String COMMONS_CODEC_BASE64 = "org.apache.commons.codec.binary.Base64";

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Base64.class.getClassLoader();
        // Apache Commons Codec present on the classpath?
        if (ClassUtils.isPresent(COMMONS_CODEC_BASE64, classLoader)) {
            log.info("Use base64 class: " + COMMONS_CODEC_BASE64);
            CommonsCodecBase64Delegate delegate = new CommonsCodecBase64Delegate();
            Base64.setBase64Delegate(delegate);
        }
    }

    @Override
    public void destroy() throws Exception {
    }

    static class CommonsCodecBase64Delegate implements Base64.Base64Delegate {

        private final org.apache.commons.codec.binary.Base64 base64 =
                new org.apache.commons.codec.binary.Base64();

        private final org.apache.commons.codec.binary.Base64 base64UrlSafe =
                new org.apache.commons.codec.binary.Base64(0, null, true);

        @Override
        public byte[] encode(byte[] source) {
            return this.base64.encode(source);
        }

        @Override
        public byte[] decode(byte[] source) {
            return this.base64.decode(source);
        }

        @Override
        public byte[] encodeUrlSafe(byte[] source) {
            return this.base64UrlSafe.encode(source);
        }

        @Override
        public byte[] decodeUrlSafe(byte[] source) {
            return this.base64UrlSafe.decode(source);
        }

    }

}
