package com.bb.training.context

import com.google.gson.Gson
import spock.lang.Specification

/**
 * Test loading the navigation data
 */
class ContextSpec extends Specification
{
  def "load data test"() {
    given:
    Context context = new Context()
    Gson gson = new Gson()
    context.gson = gson

    when:
    context.init()

    then:
    context.getData().getChildren().size() == 6
  }

}
