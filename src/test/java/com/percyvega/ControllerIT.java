package com.percyvega;

import com.percyvega.model.Customer;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void ping() {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Ping!"));
    }

    @Test
    public void findByLastName_httpStatus() {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/customers/lastName/Bauer", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        log.info(response.toString());
    }

    @Test
    public void findByLastName_headForHeaders() {
        HttpHeaders httpHeaders = template.headForHeaders(base.toString() + "/customers/lastName/Bauer");
        assertThat(httpHeaders.getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
        log.info(httpHeaders);
    }

    @Test
    public void findByLastName_getForObject() {
        Customer customer = template.getForObject(base.toString() + "/customers/id/1", Customer.class);
        assertThat(customer.getId(), equalTo(1L));
        assertThat(customer.getFirstName(), equalTo("Jack"));
        assertThat(customer.getLastName(), equalTo("Bauer"));
        log.info(customer);
    }

}
