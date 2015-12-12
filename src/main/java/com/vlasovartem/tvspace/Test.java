package com.vlasovartem.tvspace;

import com.vlasovartem.tvspace.config.AppConfig;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by artemvlasov on 09/12/15.
 */
public class Test {
    public static void main(String[] args) throws UnknownHostException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SeriesRepository seriesRepository = (SeriesRepository) context.getBean("seriesRepository");
    }
}
