package com.percyvega.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.percyvega.Application;
import com.percyvega.model.Customer;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DatabaseSetup(value = "classpath:sampleData.xml", type = DatabaseOperation.REFRESH)
@TestExecutionListeners(
        mergeMode = MERGE_WITH_DEFAULTS,
        listeners = {
                DbUnitTestExecutionListener.class
        })
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findById_found() {
        Optional<Customer> customerOptional = customerService.findById(100L);
        assertThat(customerOptional.isPresent(), is(true));
        Customer customer = customerOptional.get();
        assertThat(customer.getFirstName(), is("Phillip"));
    }

    @Test
    public void findById_not_found() {
        Optional<Customer> customerOptional = customerService.findById(1000L);
        assertThat(customerOptional.isPresent(), is(false));
    }
}
