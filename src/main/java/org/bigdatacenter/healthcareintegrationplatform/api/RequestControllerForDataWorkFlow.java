package org.bigdatacenter.healthcareintegrationplatform.api;

import org.bigdatacenter.healthcareintegrationplatform.domain.workflow.ScenarioTask;
import org.bigdatacenter.healthcareintegrationplatform.exception.RESTException;
import org.bigdatacenter.healthcareintegrationplatform.service.MetaDataDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bigdatacenter.healthcareintegrationplatform.persistence.MetaDataDBMapper.PROCESS_STATE_CODE_REQUEST_ACCEPTED;

@RestController
@RequestMapping("/request/workflow/api")
public class RequestControllerForDataWorkFlow {
    private static final Logger logger = LoggerFactory.getLogger(RequestControllerForDataWorkFlow.class);
    private static final String currentThreadName = Thread.currentThread().getName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${scenario.processor.api.request.workflow}")
    private String scenarioURL;

    private final MetaDataDBService metaDataDBService;

    @Autowired
    public RequestControllerForDataWorkFlow(MetaDataDBService metaDataDBService) {
        this.metaDataDBService = metaDataDBService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "dataWorkFlow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // curl -v -X POST http://was.bigdatacenter.org:20780/request/workflow/api/dataWorkFlow -H "Content-Type:application/json; UTF-8" -d "{\"scenarioQueryList\":[{\"query\":\"SELECT * FROM T1\"},{\"query\":\"SELECT * FROM T2\"}]}"
    public String dataWorkFlow(@RequestBody ScenarioTask scenarioTask, HttpServletResponse httpServletResponse) {
        final String jobAcceptedTime = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info(String.format("%s - ScenarioTask: %s", currentThreadName, scenarioTask));

        try {
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            final String response = restTemplate.postForObject(scenarioURL, scenarioTask, String.class);
            logger.info(String.format("%s - response: %s", currentThreadName, response));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RESTException(e.getMessage(), httpServletResponse);
        }
        return String.format("Accepted Time: %s", jobAcceptedTime);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "dataWorkFlow", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String dataWorkFlow(@RequestParam Integer dataSetUID, HttpServletResponse httpServletResponse) {
        final String jobAcceptedTime = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info(String.format("%s - dataSetUID: %d", currentThreadName, dataSetUID));

        try {
            metaDataDBService.updateProcessState(dataSetUID, PROCESS_STATE_CODE_REQUEST_ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RESTException(e.getMessage(), httpServletResponse);
        }
        return String.format("Accepted Time: %s", jobAcceptedTime);
    }
}