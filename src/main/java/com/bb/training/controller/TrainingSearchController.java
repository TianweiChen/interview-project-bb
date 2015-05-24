package com.bb.training.controller;

import com.bb.training.domain.Node;
import com.bb.training.search.SearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TrainingSearchController
{
  @Autowired
  private SearchService searchService;

  @Autowired
  ObjectMapper objectMapper;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<String> search(@PathVariable String id) throws Throwable
  {
    Node searchResult = searchService.search(id);

    String searchResultJson = objectMapper.writeValueAsString(searchResult);

    return new ResponseEntity<String>(searchResultJson, HttpStatus.OK);
  }
}
