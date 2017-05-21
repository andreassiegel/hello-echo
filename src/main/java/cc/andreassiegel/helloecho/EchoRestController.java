package cc.andreassiegel.helloecho;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class EchoRestController {

  @PostMapping(path = "/**")
  public ResponseEntity echo(@RequestBody Object requestBody, HttpServletRequest request) {

    log.info("Request:\t{}", request);
    log.info("Request body:\t{}", requestBody);

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("path", request.getRequestURI());
    response.put("headers", headers(request));
    response.put("body", requestBody);
    response.put("datetime", currentDateTime());

    return ResponseEntity.ok(response);
  }

  private Map<String, String> headers(HttpServletRequest request) {
    return Collections.list(request.getHeaderNames())
      .stream()
      .collect(Collectors.toMap(headerName -> headerName, request::getHeader));
  }

  private String currentDateTime() {
    LocalDateTime datetime = LocalDateTime.now();
    return datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
