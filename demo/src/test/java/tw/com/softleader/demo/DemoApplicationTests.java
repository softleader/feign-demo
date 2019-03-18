package tw.com.softleader.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DemoApplicationTests {

  @Autowired private MockMvc mockMvc;

  @Value("${spring.application.name}")
  private String testApp;

  @Test
  public void responseByEachApp() throws Exception {
    String[] apps = {testApp, "app1", "app2", "app3"};
    for (String app : apps) {
      this.mockMvc
          .perform(MockMvcRequestBuilders.get(String.format("/%s", app)))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.app").value(app));
    }
  }

  @Test
  public void responseRootCauseByEachApp() throws Exception {
    String[] apps = {testApp, "app1", "app2", "app3"};
    int[] status = new Random().ints(apps.length, 300, 599).toArray();
    for (int i = 0; i < apps.length; i++) {
      responseRootCauseByEachApp(apps[i], status[i]);
    }
  }

  private void responseRootCauseByEachApp(String app, int status) throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(String.format("/%s?ex=%d", app, status)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().is(status))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message")
                .value(
                    String.format("throw by %s, response status code should be %d", app, status)));
  }
}
