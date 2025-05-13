package com.ead.course.configs;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.*;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class ResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SpecificationArgumentResolver());

        var pageableResolver = new PageableHandlerMethodArgumentResolver();
        pageableResolver.setFallbackPageable(PageRequest.of(0, 10));
        resolvers.add(pageableResolver);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").maxAge(3600);
    }
}
