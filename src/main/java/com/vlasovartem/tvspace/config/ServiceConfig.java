package com.vlasovartem.tvspace.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by artemvlasov on 13/12/15.
 */
@Configuration
@ComponentScan({"com.vlasovartem.tvspace.service", "com.vlasovartem.tvspace.utils.exception.handler", "com.vlasovartem.tvspace.utils.validation"})
public class ServiceConfig {
}
