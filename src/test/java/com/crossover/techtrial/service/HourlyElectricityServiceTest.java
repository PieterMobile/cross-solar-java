package com.crossover.techtrial.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.controller.PanelController;
import com.crossover.techtrial.model.HourlyElectricity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HourlyElectricityServiceTest {

	  
    private static final long TEST_PANEL_ID = 1;
  
    @Autowired
    HourlyElectricityService hourlyElectricityService;

    @Test
	public void testGetAllHourlyElectricityByPanelIdShouldWork() {
		Page<HourlyElectricity> page = hourlyElectricityService.getAllHourlyElectricityByPanelId(TEST_PANEL_ID,  PageRequest.of(0, 5));
		List<HourlyElectricity> lsH;
		if(page.hasContent()) {
			  lsH = page.getContent();
			  HourlyElectricity hourlyElectricity  = lsH.get(0);
			  hourlyElectricity.toString().equals("test hour");
			  hourlyElectricity.equals(new HourlyElectricity());
		}
		Assert.assertEquals(true,page.hasContent());
	}

	@Test
	public void testGetAllHourlyElectricityByPanelIdShouldFail() {
		Page<HourlyElectricity> page = hourlyElectricityService.getAllHourlyElectricityByPanelId(new Long(-1),  PageRequest.of(0, 5));
		Assert.assertEquals(false,page.hasContent());
	}
	
	@Test
	public void testHourlyElectricity() {
		
	}
}
