package com.example.ufosighting;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.ufosighting.controller.UfoSightingController;
import com.example.ufosighting.service.UfoSightingService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UfoSightingController.class)
public class UfoSightingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UfoSightingService ufoSightingService;

	/**
	 * This is positive test for getAllSightings() method.
	 * This test case tests UfoSightingService.getAllSightings()
	 * And expected behavior is it should invoke UfoSightingService.getAllSightings();
	 * and return status code OK (200) with list of all ufo sightings.
	 * @throws Exception
	 */
	
	@Test
	public void shouldReturn200WhenSendingRequestToGetAllUfoSightings() throws Exception {
		final String testData = "19950911	19950911	 Las Vegas, NV			Man repts. bright, multi-colored obj. in NW night sky. Disappeared while he was in house.";
		
		final List<String> list = of(testData).collect(toList());

		when(ufoSightingService.getAllSightings()).thenReturn(list);
		
		final MvcResult result =  mockMvc.perform(get("/sightings"))
									     .andExpect(status().isOk())
									     .andDo(print())
									     .andReturn();
		
		final String resultString = result.getResponse().getContentAsString();
		System.out.println(resultString);
		
		assertThat(resultString.contains("19950911"), equalTo(true));
		assertThat(resultString.contains("Las Vegas, NV"), equalTo(true));
		assertThat(resultString.contains("Disappeared while he was in house."), equalTo(true));
		
		verify(ufoSightingService, times(1)).getAllSightings();
        verifyNoMoreInteractions(ufoSightingService);
	}
	
	/**
	 * This is positive test for search(year,month) method.
	 * This test case tests UfoSightingService.search(yearSeen, monthSeen)
	 * with valid request parameters year and month values. And expected behavior is
	 * it should invoke UfoSightingService.search(year,month)
	 * and return status code OK (200) with list of ufo sightings for given year and month.
	 * @throws Exception
	 */
	
	@Test
	public void shouldReturn200WhenSendingRequestToGetUfoSightingsByYearAndMonth() throws Exception {
		final String testData = "19951022	19951024	 Bessemer, AL		15 min.	Man, wife, 2 children,  driving down long hill, see bizarre cluster of lights, &quot;like Christmas tree in the sky.&quot;  Multiple witnesses.";
		
		final List<String> list = of(testData).collect(toList());

		when(ufoSightingService.search(anyInt(),anyInt())).thenReturn(list);
		
		final MvcResult result =  mockMvc.perform(get("/sightings?year=1995&month=10"))
									     .andExpect(status().isOk())
									     .andDo(print())
									     .andReturn();
		
		final String resultString = result.getResponse().getContentAsString();
		
		assertThat(resultString.contains("19951022"), equalTo(true));
		assertThat(resultString.contains("Bessemer, AL"), equalTo(true));
		
		verify(ufoSightingService, times(1)).search(anyInt(),anyInt());
        verifyNoMoreInteractions(ufoSightingService);
	}
	
	/**
	 * This is negative test for search(year,month) method.
	 * This test case tests UfoSightingService.search(yearSeen, monthSeen)
	 * without passing year and month values. And expected behavior it should not invoke UfoSightingService.search(year,month);
	 * @throws Exception
	 */
	
	@Test
	public void shouldReturn400WhenSendingEmptyYearAndMonthToSerach() throws Exception {
		final String testData = "19951022	19951024	 Bessemer, AL		15 min.	Man, wife, 2 children,  driving down long hill, see bizarre cluster of lights, &quot;like Christmas tree in the sky.&quot;  Multiple witnesses.";
		
		final List<String> list = of(testData).collect(toList());

		when(ufoSightingService.search(anyInt(),anyInt())).thenReturn(list);
		
		final MvcResult result =  mockMvc.perform(get("/sightings?year=&month="))
									     .andExpect(status().isBadRequest())
									     .andDo(print())
									     .andReturn();
		
		final String resultString = result.getResponse().getContentAsString();
		
		assertThat(resultString.contains("19951022"), equalTo(false));
		assertThat(resultString.contains("Bessemer, AL"), equalTo(false));
		
		verify(ufoSightingService, times(0)).search(anyInt(),anyInt());
        verifyNoMoreInteractions(ufoSightingService);        
	}
	
	@Test
	public void shouldReturn400WhenSendingNullYearAndMonthToSerach() throws Exception {
		final String testData = "19951022	19951024	 Bessemer, AL		15 min.	Man, wife, 2 children,  driving down long hill, see bizarre cluster of lights, &quot;like Christmas tree in the sky.&quot;  Multiple witnesses.";
		
		final List<String> list = of(testData).collect(toList());

		when(ufoSightingService.search(anyInt(),anyInt())).thenReturn(list);
		
		final MvcResult result =  mockMvc.perform(get("/sightings?year=null&month=null"))
									     .andExpect(status().isBadRequest())
									     .andDo(print())
									     .andReturn();
		
		final String resultString = result.getResponse().getContentAsString();
		
		assertThat(resultString.contains("19951022"), equalTo(false));
		assertThat(resultString.contains("Bessemer, AL"), equalTo(false));
		
		verify(ufoSightingService, times(0)).search(anyInt(),anyInt());
        verifyNoMoreInteractions(ufoSightingService);        
	}

}
