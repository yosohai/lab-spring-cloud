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
}
