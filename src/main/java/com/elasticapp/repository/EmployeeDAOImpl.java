package com.elasticapp.repository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.elasticapp.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Repository
public class EmployeeDAOImpl implements EmployeeDAO{
	  private final String INDEX = "employee_data";
	           private final String TYPE = "employees";  
	  
	  @Autowired
	  private RestHighLevelClient restHighLevelClient;
	  @Autowired
	  private ObjectMapper objectMapper;

	  
	  public EmployeeDAOImpl(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
	    this.objectMapper = objectMapper;
	    this.restHighLevelClient = restHighLevelClient;
	  }
	@Override
	public List<Employee> getAllEmployees() {
		SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=null;
try {
         searchResponse =
        		restHighLevelClient.search(searchRequest);
}
catch (java.io.IOException e){
    e.getLocalizedMessage();
  }
        return getSearchResult(searchResponse);
	}

	private List<Employee> getSearchResult(SearchResponse searchResponse) {
		 SearchHit[] searchHit = searchResponse.getHits().getHits();

	        List<Employee> employees = new ArrayList<>();

	        if (searchHit.length > 0) {

	            Arrays.stream(searchHit)
	                    .forEach(hit -> employees
	                            .add(objectMapper
	                                    .convertValue(hit.getSourceAsMap(),
	                                                    Employee.class))
	                    );
	        }

	        return employees;
	}
	@Override
	public Employee saveEmployee(Employee employee) {
	
		Map data=objectMapper.convertValue(employee, Map.class);
		IndexRequest indexrequest=new IndexRequest(INDEX,TYPE,employee.getId()).source(data);
		try {
			IndexResponse indexresponse=restHighLevelClient.index(indexrequest);
		}
		catch (java.io.IOException ex){
		    ex.getLocalizedMessage();
		  }
		return employee;
	}

	@Override
	public Map<String, Object> findById(String id) {
	GetRequest getrequest=new GetRequest(INDEX,TYPE,id);
	GetResponse getresponse=null;
	try {
	    getresponse = restHighLevelClient.get(getrequest);
	  } catch (java.io.IOException e){
	    e.getLocalizedMessage();
	  }
		return getresponse.getSourceAsMap();
	}

	@Override
	public Map<String, Object> updateEmployee(String id, Employee employee) {
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
		          .fetchSource(true);   
		  Map<String, Object> error = new HashMap<>();
		  error.put("Error", "Unable to update employee");
		  try {
		    String bookJson = objectMapper.writeValueAsString(employee);
		    updateRequest.doc(bookJson, XContentType.JSON);
		    UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
		    Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
		    return sourceAsMap;
		  }catch (JsonProcessingException e){
		    e.getMessage();
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
		  return error;
	}

	@Override
	public void deleteById(String id) {
		
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		  try {
		    DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
	}

}
