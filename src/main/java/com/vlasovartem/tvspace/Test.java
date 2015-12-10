package com.vlasovartem.tvspace;

import com.vlasovartem.tvspace.config.AppConfig;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.entity.UserSeries;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import com.vlasovartem.tvspace.persistence.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by artemvlasov on 09/12/15.
 */
public class Test {
    public static void main(String[] args) throws UnknownHostException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserRepository repository = (UserRepository) context.getBean("userRepository");
        SeriesRepository seriesRepository = (SeriesRepository) context.getBean("seriesRepository");
        User user = repository.findOne("566725ab0364e7312e89b6b3");
        Series series = seriesRepository.findByTitleIgnoreCase("Arrow");
        System.out.println(series);
    }
}
