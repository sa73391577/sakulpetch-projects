package com.example.prototype;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.prototype.bean.LoginTestRequestBean;
import com.example.prototype.bean.RespBean;
import com.example.prototype.bean.UserBean;
import com.example.prototype.controller.UserController;
import com.example.prototype.exceptions.ErrorMessage;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class AuthenticationControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private RestTestClient restTestClient;
	
	@Test
	public void testLoginTC01() throws Exception  {
		restTestClient.post()
        .uri("/auth/login")
        .body(new LoginTestRequestBean("admin","123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<RespBean>() {})
        .consumeWith(result -> {
        	assert result.getResponseBody() instanceof  RespBean;
        	RespBean res = (RespBean)result.getResponseBody();
            assert res != null;
            assertThat(res.getData()).isNotNull();
        });
	}
	
	
	@Test
	public void testLoginTC02() throws Exception  {
		restTestClient.post()
        .uri("/auth/login")
        .body(new LoginTestRequestBean("xxxx","xxxxx"))
        .exchange()
        .expectStatus().isUnauthorized()
        .expectBody(new ParameterizedTypeReference<ErrorMessage>() {})
        .consumeWith(result -> {
        	assert result.getResponseBody() instanceof  ErrorMessage;
        	ErrorMessage res = (ErrorMessage)result.getResponseBody();
        	assertThat(res).isNotNull();
            assertThat(res.getStatusCode()).isNotNull();
            assertThat(res.getMessage()).isNotNull();
            assertThat(res.getDescription()).isNotNull();
            
            assertThat(res.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(res.getMessage()).isEqualTo("UNAUTHORIZED");
            assertThat(res.getDescription()).isEqualTo("Username or Password are incorrect.");
            
        });
	}

	@Test
	public void testLoginTC03() throws Exception  {
		restTestClient.post()
        .uri("/auth/login")
        .body(new LoginTestRequestBean("admin","xxxxx"))
        .exchange()
        .expectStatus().isUnauthorized()
        .expectBody(new ParameterizedTypeReference<ErrorMessage>() {})
        .consumeWith(result -> {
        	assert result.getResponseBody() instanceof  ErrorMessage;
        	ErrorMessage res = (ErrorMessage)result.getResponseBody();
        	assertThat(res).isNotNull();
            assertThat(res.getStatusCode()).isNotNull();
            assertThat(res.getMessage()).isNotNull();
            assertThat(res.getDescription()).isNotNull();   
            assertThat(res.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(res.getMessage()).isEqualTo("UNAUTHORIZED");
            assertThat(res.getDescription()).isEqualTo("Username or Password are incorrect.");
        });
	}
	
	
	@Test
	public void testLoginTC04() throws Exception  {
		restTestClient.post()
        .uri("/auth/login")
        .body(new LoginTestRequestBean("xxxx","123456"))
        .exchange()
        .expectStatus().isUnauthorized()
        .expectBody(new ParameterizedTypeReference<ErrorMessage>() {})
        .consumeWith(result -> {
        	assert result.getResponseBody() instanceof  ErrorMessage;
        	ErrorMessage res = (ErrorMessage)result.getResponseBody();
        	assertThat(res).isNotNull();
            assertThat(res.getStatusCode()).isNotNull();
            assertThat(res.getMessage()).isNotNull();
            assertThat(res.getDescription()).isNotNull();   
            assertThat(res.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(res.getMessage()).isEqualTo("UNAUTHORIZED");
            assertThat(res.getDescription()).isEqualTo("Username or Password are incorrect.");
        });
	}
	
	@Test
	public void testLoginTC05() throws Exception  {
		restTestClient.post()
        .uri("/auth/login")
        .body(new LoginTestRequestBean(null,"xxxx"))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(new ParameterizedTypeReference<ErrorMessage>() {})
        .consumeWith(result -> {
        	assert result.getResponseBody() instanceof  ErrorMessage;
        	ErrorMessage res = (ErrorMessage)result.getResponseBody();
        	assertThat(res).isNotNull();
            assertThat(res.getStatusCode()).isNotNull();
            assertThat(res.getMessage()).isNotNull();
            assertThat(res.getDescription()).isNotNull();   
            assertThat(res.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST.value());
            assertThat(res.getMessage()).isEqualTo("BAD_REQUEST");
            assertThat(res.getDescription()).isEqualTo("Request Parameter Invalid.");
        });
	}
	
}
