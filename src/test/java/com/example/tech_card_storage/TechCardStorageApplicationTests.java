package com.example.tech_card_storage;

import com.example.tech_card_storage.repository.TechnologyCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class TechCardStorageApplicationTests {

	@MockBean
	private TechnologyCardRepository technologyCardRepository;

	@Test
	void contextLoads() {
	}
}
