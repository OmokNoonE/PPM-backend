package org.omoknoone.ppm.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

	@Value("${jasypt.encryptor.password}")      // jasypt password를 주입
	String PASSWORD_KEY;

	@Bean("jasyptStringEncryptor")              // application.yml과 이름을 같게 해야한다
	public StringEncryptor stringEncryptor() {

		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(PASSWORD_KEY);
		config.setPoolSize("1");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setStringOutputType("base64");
		config.setKeyObtentionIterations("1000");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		encryptor.setConfig(config);

		return encryptor;
	}
}
