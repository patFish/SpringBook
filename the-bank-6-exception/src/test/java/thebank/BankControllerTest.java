package thebank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BankController.class)
// @SpringBootTest
public class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BankController bankController;

	@MockBean
	BankService bankService;

	private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

	private SavingAccount standardSavingAccountForTest;
	// @Autowired
	// TestRestTemplate testRestTemplate;

	@Before
	public void setupStandardAccount() {

	}

	@Test
	public void testIndex() {
		String body = this.bankController.index();
		assertThat(body).isEqualTo("<b>Wohoooo, Spring Boot !!!</b>");
	}

	@Test
	public void testIndexViaRest() throws Exception {
		this.mockMvc.perform(get("/bank/test")).andExpect(content().string("<b>Wohoooo, Spring Boot !!!</b>"));

		// assertThat(body).isEqualTo("<b>Wohoooo, Spring Boot !!!</b>");
	}

	@Test
	public void testCreateAccountController() throws AccountCreationException {
		standardSavingAccountForTest = new SavingAccount();
		Account testReturnValue = this.bankController.createAccountController(standardSavingAccountForTest);
		assertEquals(standardSavingAccountForTest, testReturnValue);
	}

	// @Ignore
	@Test
	public void testCreateAccountControllerRest() throws Exception {
		standardSavingAccountForTest = new SavingAccount(1);
		standardSavingAccountForTest.setId(1);
		standardSavingAccountForTest.setBalance(1000);
		JSONObject jsonc = new JSONObject(standardSavingAccountForTest);
		this.mockMvc
				.perform(post("/bank/saveAccount").contentType(MediaType.APPLICATION_JSON).content(jsonc.toString()))
				.andExpect(content().string(jsonc.toString()));

		// Account entity = (Account)
		// this.testRestTemplate.getForEntity("http://localhost:8080/saveAccount",
		// Account.class);

		// this.mockMvc.perform(get("/saveAccount").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
		// .andExpect(content().string("Honda Civic"));
		// assertEquals(temp, testReturnValue);
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
