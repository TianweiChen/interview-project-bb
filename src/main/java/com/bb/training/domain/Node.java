package com.bb.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data model for each node in the tree structure
 */
public class Node implements Serializable
{
  private static final long serialVersionUID = -2678645623936850312L;

  private String id;
  private String name;
  private String url;
  private List<Node> children = new ArrayList();

  @JsonIgnore
  private Node parent;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public List<Node> getChildren()
  {
    return children;
  }

  public void setChildren(List<Node> children)
  {
    this.children = children;
  }

  public void addChild(Node child) {
    children.add(child);
  }

  public Node getParent()
  {
    return parent;
  }

  public void setParent(Node parent)
  {
    this.parent = parent;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Node node = (Node) o;

    if (!id.equals(node.id))
    {
      return false;
    }
    if (!name.equals(node.name))
    {
      return false;
    }
    if (!url.equals(node.url))
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + url.hashCode();
    return result;
  }

  public Node findChild(Node childToSearch)
  {
    for(Node child : children) {
      if(child.equals(childToSearch)) {
        return child;
      }
    }

    return null;
  }
}
