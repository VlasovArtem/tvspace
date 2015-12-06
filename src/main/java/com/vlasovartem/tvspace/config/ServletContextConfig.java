package com.vlasovartem.tvspace.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlasovartem.tvspace.config.filter.LogFilter;
import com.vlasovartem.tvspace.utils.serializer.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.vlasovartem.tvspace.controller"})
public class ServletContextConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder
                .json()
                .defaultViewInclusion(true)
                .autoDetectFields(true)
                .serializers(new LocalDateSerializer())
                .build();
        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public LogFilter logFilter() {
        return new LogFilter();
    }
}
