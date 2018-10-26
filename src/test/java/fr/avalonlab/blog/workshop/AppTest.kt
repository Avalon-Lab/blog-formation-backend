package fr.avalonlab.blog.workshop

import io.restassured.RestAssured.get
import org.hamcrest.Matchers.equalTo
import org.jooby.test.JoobyRule
import org.junit.ClassRule
import org.junit.Test

class AppTest {

  companion object {
    @ClassRule
    @JvmField
    val app: JoobyRule = JoobyRule(App())
  }

  /**
   * Simply test if the Api start correctly
   */
  @Test
  fun integrationTest(){
    get("/")
        .then()
        .assertThat()
        .body(equalTo("\"I'm alive !!\""))
        .statusCode(200)
        .contentType("application/json;charset=UTF-8")
  }
}
