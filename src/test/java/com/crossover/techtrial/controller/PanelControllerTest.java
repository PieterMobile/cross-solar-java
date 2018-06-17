package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PanelController panelController;
  
  @Autowired
  private TestRestTemplate template;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
  }

  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
        "{\"serial\": \"1234567890123457\", \"longitude\": \"54.123232\","
            + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
        "/api/register", panel, Panel.class);
    Assert.assertEquals(202,response.getStatusCode().value());
  }

  @Test
  public void testPanelShouldBeFail() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
            "{\"serial\": \"232323232323232312\", \"longitude\": \"54.123232\","
                    + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
            "/api/register", panel, Panel.class);
    Assert.assertEquals(412,response.getStatusCode().value());
  }

  @Test
  public void testHourlyElectricityShouldBeSaved() throws Exception {
    HttpEntity<Object> hourly = getHttpEntity(
            "{\"id\": 2, \"generatedElectricity\": 200,"
                    + " \"readingAt\": [2018,6,15,10,10,10,100000000]}");
    ResponseEntity<String> response = template.postForEntity(
            "/api/panels/{panel-serial}/hourly", hourly, String.class, "1234567890123457");
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testHourlyElectricityCollect() throws Exception {
    ResponseEntity<HourlyElectricity> response = template.getForEntity("/api/panels/{panel-serial}/hourly", HourlyElectricity.class, "1234567890123457");
    Assert.assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void testDailyElectricityFromYesterday() throws Exception {
    ResponseEntity<DailyElectricity[]> response = template.getForEntity(
            "/api/panels/1234567890123456/daily",  DailyElectricity[].class);
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testDailyElectricityFromYesterdayFail() throws Exception {
    ResponseEntity<List<DailyElectricity>> response = panelController.allDailyElectricityFromYesterday("99999999999");
    Assert.assertEquals(null,response);
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
