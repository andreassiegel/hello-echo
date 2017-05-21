package cc.andreassiegel.helloecho;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static cc.andreassiegel.helloecho.TestUtil.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EchoRestController.class)
public class EchoRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void echoReturns200Ok() throws Exception {
    mvc.perform(post("/")
      .content("{}")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .accept(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().isOk());
  }

  @Test
  public void echoReturnsRequestPath() throws Exception {
    final String expectedPath = "/some/test";

    mvc.perform(post(expectedPath)
      .content("{}")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .accept(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.path").exists())
      .andExpect(jsonPath("$.path").isString())
      .andExpect(jsonPath("$.path", Matchers.is(expectedPath)));
  }

  @Test
  public void echoReturnsBody() throws Exception {
    Map<String, String> expectedBody = new HashMap<>();
    expectedBody.put("message", "Hello World");

    mvc.perform(post("/")
      .content(json(expectedBody))
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .accept(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.body").exists())
      .andExpect(jsonPath("$.body").isMap())
      .andExpect(jsonPath("$.body").isNotEmpty())
      .andExpect(jsonPath("$.body", Matchers.is(expectedBody)));
  }

  @Test
  public void echoReturnsHeaders() throws Exception {
    mvc.perform(post("/")
      .content("{}")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .accept(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.headers").exists())
      .andExpect(jsonPath("$.headers").isMap())
      .andExpect(jsonPath("$.headers").isNotEmpty())
      .andExpect(jsonPath("$.headers.Content-Type").exists())
      .andExpect(jsonPath("$.headers.Content-Type").isString())
      .andExpect(jsonPath("$.headers.Content-Type", Matchers.is(MediaType.APPLICATION_JSON_UTF8_VALUE)));
  }

  @Test
  public void echoReturnsDateTime() throws Exception {
    mvc.perform(post("/")
      .content("{}")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .accept(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.datetime").exists())
      .andExpect(jsonPath("$.datetime").isString())
      .andExpect(jsonPath("$.datetime").isNotEmpty());
  }
}
