package com.bb.training.search;


import com.bb.training.domain.Node;

public interface SearchService
{
  Node search(String id) throws Throwable;
}
