package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;


import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.implementation.LogRepositoryImplementation;
import com.example.demo.model.LogLine;
import com.example.demo.model.Query;
import com.example.demo.model.Target;

import com.example.demo.service.QueryService;
import com.example.demo.service.TargetService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {

	static Logger LOG = LogManager.getLogger(MainController.class);

	private final ElasticsearchOperations elasticsearchOperations;
	
	@Autowired
	private RestHighLevelClient client ;
	
	@Autowired
	private LogRepositoryImplementation lri;
	
	@Autowired
	private QueryService queryService;
	
	@Autowired
	private TargetService targetService;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public MainController(ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}
	
	@GetMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
		LOG.info("returning user");
        return user;
    }

	
	@GetMapping("/getAllIndices")
	public List<String> getAllIndices() throws IOException{
		RestClient lowLevelClient = client.getLowLevelClient();
	    List<String> indexList = new ArrayList<>();

	    Response response = lowLevelClient.performRequest(new Request("GET", "/_cat/indices?h=index&format=json"));

	    // parse the JSON response
	    List<HashMap<String, String>> listOfIndicesFromEs = null;
	    if (response != null) {
	        String rawBody = EntityUtils.toString(response.getEntity());
	        TypeReference<List<HashMap<String, String>>> typeRef = 
	        		new TypeReference<List<HashMap<String, String>>>() {};
	        listOfIndicesFromEs = mapper.readValue(rawBody, typeRef);
	    }

	    // get the index names
	    if (listOfIndicesFromEs != null) {
	        indexList = listOfIndicesFromEs.stream()
	                .map(index -> index.get("index"))
	                .collect(Collectors.toList());
	    }
	    return indexList;
	}
	
	

	@RequestMapping("/alllogs")
	public ModelAndView getAllLogline() {
		 
		Page<LogLine> l = lri.findAll();
		l.forEach(x-> System.out.println(x.toString()));
		ModelAndView mv = new ModelAndView();
		mv.addObject("logs",l);
		System.out.println(l.toString());
		mv.setViewName("alllogs");
		return mv;
		
	}
		
	
	@GetMapping("/logs")
	public ModelAndView getLogById(@RequestParam String id) {


		LogLine logline = elasticsearchOperations.get(id,LogLine.class); 
		LOG.info(logline.toString());
		ModelAndView mv = new ModelAndView();
		mv.addObject("logline",logline);
		mv.setViewName("logline");
		return mv;
	}
	
		
	@GetMapping("/search")
	public List<LogLine> searchByQuery(@RequestParam String queryName,
			@RequestParam(defaultValue ="logs-applicationframework") String index,
			@RequestParam(defaultValue = "temp.txt") String sourcefilename,
			@RequestParam(defaultValue = "false") String shouldSaveToFile) {
		List<LogLine> logline  = null;
		System.out.println(queryName);
		Query q = queryService.getByQueryName(queryName);
		try {
			if(q.getElasticSearchIndex() != null)
				index = q.getElasticSearchIndex();
			logline = lri.search(q.getQueryText(),index,q.getMaskList());
		} catch (IOException e1) {
			LOG.error("IOException Caught while searching:"+e1.getMessage());
		}
		Boolean b = Boolean.valueOf(shouldSaveToFile);
		if(b) {
			try {
				
				Target t = targetService.getByTargetName(q.getTargetName());
				String file = t.getLocation();
				lri.saveToFile(file,logline);
			} catch (IOException io) {
				LOG.error("IOException Caught while saving to file:"+io.getMessage());
			}
		}
		return logline;
	}	
	
}
