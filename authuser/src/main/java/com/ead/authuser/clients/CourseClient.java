package com.ead.authuser.clients;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.ead.authuser.dtos.CourseRecordDto;
import com.ead.authuser.dtos.ResponsePageDto;

@Component
public class CourseClient {

    Logger logger = LogManager.getLogger(CourseClient.class);

    @Value("${ead.api.url.course}")
    String baseUrlCourse;

    @Autowired
    private RestClient.Builder restClient;

    private final String COURSE_URL = "/courses?";
    private final String FILTER_USER_ID = "userId=";
    private final String FILTER_PAGE = "&page=";
    private final String FILTER_SIZE = "&size=";
    private final String FILTER_SORT = "&sort=";

    public Page<CourseRecordDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        String url = baseUrlCourse + COURSE_URL + FILTER_USER_ID + userId + FILTER_PAGE + pageable.getPageNumber()
                + FILTER_SIZE + pageable.getPageSize() + FILTER_SORT
                + pageable.getSort().toString().replaceAll(": ", ",");

        try {
            return restClient.build()
                .get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ResponsePageDto<CourseRecordDto>>() {});
        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause {}", e.getMessage());
            throw new RuntimeException("Error Resquest RestClient", e);
        }
    }
}
