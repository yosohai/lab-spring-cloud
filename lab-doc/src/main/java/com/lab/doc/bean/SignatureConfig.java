package com.lab.doc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@PropertySource(value = "classpath:signature.yml", encoding = "UTF-8", factory = YamlPropertySourceFactory.class)
//@ConfigurationProperties(prefix = "word1")
public class SignatureConfig {
    @Value("${word1.sign1}")
    private String sign1;
    @Value("${word1.sign2}")
    private String sign2;
    @Value("${word1.sign3}")
    private String sign3;
    @Value("${word1.sign4}")
    private String sign4;
    @Value("${word1.sign5}")
    private String sign5;
}
