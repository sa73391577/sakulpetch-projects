package com.example.prototype;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.prototype.bean.RespBean;
import com.example.prototype.bean.UserBean;
import com.example.prototype.controller.UserController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class UserControllerTest {
	
	private final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWdpc3RlcklkIjoiODQ1Mzg0ZDAtZGZjMy00NjMwLWIwZTItNWE2NWU3N2ExYmZkIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3ODA4NDU3OTAsImV4cCI6MTc4MTY2NDEyMX0.g8lhvfj15ehjNz_Z8hEhrZQpF_hJ_0uUdDqsGYH2Jdc";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private RestTestClient restTestClient;
	
	@BeforeEach
	void printBeforceEach() {
		System.out.println("printBeforceEach working !!!");
	}
	
	@Test
	void testGetUserListService() throws Exception  {
		restTestClient.get()
        .uri("/user/list")
        .header("Authorization", token)
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<RespBean>() {}) // บอกว่าผลลัพธ์เป็น List ของ UserBean
        .consumeWith(result -> {
        	RespBean users = result.getResponseBody();
            assert users != null;
            assertThat(users).isNotNull();
            assertThat(users.getData()).isNotNull();
        });
	}
	
}
