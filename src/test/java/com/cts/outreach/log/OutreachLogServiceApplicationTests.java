package com.cts.outreach.log;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.outreach.log.controller.LogController;
import com.cts.outreach.log.entity.LogEntity;
import com.cts.outreach.log.repo.LogRepo;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.cts.outreach.event"})
@AutoConfigureMockMvc
@SpringBootTest
class OutreachLogServiceApplicationTests {

	@Autowired
	MockMvc mockMvc;
	MvcResult mvcResult;
	
	@Autowired
	LogController logController;
	
	@MockBean
	LogRepo logRepo;
	
	@Test
	public void getAllLogsTest() throws Exception {
		List<LogEntity> allLogs = new ArrayList<LogEntity>();
		allLogs.add(new LogEntity("1", "Event1", "user1", "user registered", ""));
		allLogs.add(new LogEntity("2", "Event1", "user2", "user registered", ""));
		allLogs.add(new LogEntity("3", "Event1", "user3", "user registered", ""));
		
		Mockito.when(logRepo.getAllLogs()).thenReturn(allLogs);
		this.mockMvc.perform(get("/v1/getAllLogs"))
		.andExpect(status().isOk());
	}

}
