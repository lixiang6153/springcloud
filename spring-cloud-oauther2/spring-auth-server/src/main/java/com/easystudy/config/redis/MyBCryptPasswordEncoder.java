package com.easystudy.config.redis;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoder密码验证clientId的时候会报错，因为5.0新特性中需要在密码前方需要加上{Xxx}来判别。
 * 所以需要自定义一个类，重新BCryptPasswordEncoder的match方法
 * MyBCryptPasswordEncoder类是我自定义的一个类，用来重新match方法
 * @author Administrator
 */
public class MyBCryptPasswordEncoder extends BCryptPasswordEncoder {
	@Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String presentedPassword = passwordEncoder.encode(encodedPassword);
        return passwordEncoder.matches(rawPassword, presentedPassword);
    }
}
