package com.mju.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.mju.management.domain.project.infrastructure.*;
import com.mju.management.global.config.jwtInterceptor.JwtContextHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseApiTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    private static WireMockServer wireMockServer;
    private static final Integer port = 8081;
    protected static final Long leaderId = 1L;
    protected static final Long memberId = 2L;
    protected static final Long outsiderId = 3L;
    protected static final Long nonExistentUserId = 4L;
    protected static final Long serverErrorUserId = 5L;

    @BeforeAll
    public static void startWireMockServer() {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureWireMock();
    }

    @AfterAll
    public static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @AfterEach
    public void deleteAllProject() {
        projectRepository.deleteAll();
    }

    @AfterEach
    public void clearJwtContext() {
        JwtContextHolder.clear();
    }

    private static void configureWireMock() {
        configureWireMockByUserId(leaderId);
        configureWireMockByUserId(memberId);
        configureWireMockByUserId(outsiderId);

        // 유저가 존재하지 않는 경우 응답 정의
        wireMockServer.stubFor(get(urlEqualTo("/user-service/response_userById/" + nonExistentUserId))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": \"User not found\" }")));

        // 서버 장애로 실패하는 경우 응답 정의
        wireMockServer.stubFor(get(urlEqualTo("/user-service/response_userById/" + serverErrorUserId))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": \"Internal Server Error\" }")));
    }

    private static void configureWireMockByUserId(Long userId) {
        // 성공하는 경우 응답 정의
        wireMockServer.stubFor(get(urlEqualTo("/user-service/response_userById/" + userId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ " +
                                "\"id\": " + userId + ", " +
                                "\"email\": " + "\"test" + userId + "\"" + ", " +
                                "\"name\": " + "\"test" + userId + "\"" + ", " +
                                "\"phoneNumber\": " + "\"test" + userId + "\"" + ", " +
                                "\"isApproved\": true " +
                                "}")));
    }

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected ProjectUserRepository projectUserRepository;

    protected final String projectName = "소코아 프로젝트";
    protected final String projectDescription = "소코아 프로젝트입니다.";
    protected final String projectStartDate = "2023-09-01";
    protected final String projectFinishDate = "2023-12-15";

    protected Project createProject(Long leaderId){
        // create Project
        Project project = projectRepository.save(Project
                .builder()
                .name(projectName)
                .description(projectDescription)
                .startDate(LocalDate.parse(projectStartDate))
                .finishDate(LocalDate.parse(projectFinishDate))
                .build()
        );

        // create ProjectUser(leader)
        projectUserRepository.save(ProjectUser
                .builder()
                .userId(leaderId)
                .project(project)
                .role(Role.LEADER)
                .build()
        );
        return project;
    }

    protected ProjectUser createProjectUser(Long memberId, Project project){
        // create ProjectUser(member)
        return projectUserRepository.save(ProjectUser
                .builder()
                .userId(memberId)
                .project(project)
                .role(Role.MEMBER)
                .build()
        );
    }

}
