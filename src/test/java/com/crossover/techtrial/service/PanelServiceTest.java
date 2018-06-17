package com.crossover.techtrial.service;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Panel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelServiceTest {
	
	private static final String SERIAL_PANEL_TEST_OK = "1234567890123456";
	private static final String SERIAL_PANEL_TEST_FAIL = "1";
	   
	@Autowired
	PanelService panelService;

	

	@Test
	public void testFindBySerialShouldWork() {
		Panel panel = panelService.findBySerial(SERIAL_PANEL_TEST_OK);
		Panel p2 = panel;
		Assert.assertEquals(true,panel.equals(p2));
	}
	
	@Test
	public void testFindBySerialShouldFail() {
		Panel panel = panelService.findBySerial(SERIAL_PANEL_TEST_FAIL);
		Assert.assertEquals(null,panel);
	}
	
	@Test
	public void testPanelEquals() {
		Panel panel = panelService.findBySerial(SERIAL_PANEL_TEST_OK);
		Panel p2 = new Panel();
		Assert.assertEquals(false,p2.toString().equals(panel.toString()));
		Assert.assertEquals(false,panel.equals(p2));
	}

}
