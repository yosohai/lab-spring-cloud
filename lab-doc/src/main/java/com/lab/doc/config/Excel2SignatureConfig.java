package com.lab.doc.config;

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
public class Excel2SignatureConfig {
    @Value("${excel2.sign1}")
    private String sign1;
    @Value("${excel2.sign2}")
    private String sign2;
    @Value("${excel2.sign3}")
    private String sign3;
    @Value("${excel2.sign4}")
    private String sign4;
    @Value("${excel2.sign5}")
    private String sign5;
    @Value("${excel2.sign6}")
    private String sign6;
    @Value("${excel2.sign7}")
    private String sign7;
    @Value("${excel2.sign8}")
    private String sign8;
    @Value("${excel2.sign9}")
    private String sign9;
    @Value("${excel2.sign10}")
    private String sign10;
    @Value("${excel2.sign11}")
    private String sign11;
    @Value("${excel2.sign12}")
    private String sign12;
    @Value("${excel2.sign13}")
    private String sign13;
    @Value("${excel2.sign14}")
    private String sign14;
    @Value("${excel2.sign15}")
    private String sign15;
    @Value("${excel2.sign16}")
    private String sign16;
    @Value("${excel2.sign17}")
    private String sign17;
    @Value("${excel2.sign18}")
    private String sign18;
    @Value("${excel2.sign19}")
    private String sign19;
    @Value("${excel2.sign20}")
    private String sign20;
}
