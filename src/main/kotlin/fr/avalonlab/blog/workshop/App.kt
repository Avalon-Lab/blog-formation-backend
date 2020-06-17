package fr.avalonlab.blog.workshop

import co.elastic.apm.attach.ElasticApmAttacher
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import fr.avalonlab.blog.workshop.blogpost.BlogPost
import fr.avalonlab.blog.workshop.blogpost.BlogPostController
import fr.avalonlab.blog.workshop.blogpost.BlogPostRepository
import fr.avalonlab.blog.workshop.comment.Comment
import fr.avalonlab.blog.workshop.comment.CommentRepository
import io.jooby.*
import io.jooby.di.GuiceModule
import io.jooby.json.JacksonModule
import org.apache.logging.log4j.kotlin.logger
import java.time.OffsetDateTime
import java.time.ZoneOffset


private const val PATH_BLOGPOST = "/api/blogpost"

class App: Kooby({
  install(JacksonModule())
  require(ObjectMapper::class.java).registerModule(ParameterNamesModule())
  require(ObjectMapper::class.java).registerModule(Jdk8Module())
  require(ObjectMapper::class.java).registerModule(JavaTimeModule())
  install(OpenAPIModule())
  install(GuiceModule())

  val cors = cors {
    methods = listOf("GET", "POST", "PUT", "DELETE")
  }
  decorator(CorsHandler(cors))


  get("/") { "I'm alive !!" }

  mvc(BlogPostController::class)

  /* path(PATH_BLOGPOST) {

   get("/{blogId}/comment") {
      logger().info { "Retrieve comments of BlogPost ${ctx.path("blogId")}" }
      val result: List<Comment> = require(CommentRepository::class).findAll(ctx.path("blogId").value())
      result
    }

    post("/{blogId}/comment") {
      logger().info { "Add comment to BlogPost ${ctx.path("blogId")}" }
      val comment = ctx.body<Comment>()
      comment.blogId = ctx.path("blogId").value()
      comment.creationDate = OffsetDateTime.now(ZoneOffset.UTC).toString()
      require(CommentRepository::class).create(comment)
    }

    get("/{blogId}/comment/{id}") {
      logger().info { "Retrieve comment ${ctx.path("id")}" }
      require(CommentRepository::class).findById(ctx.path("id").value())!!
    }

    delete("/{blogId}/comment/{id}") {
      logger().info { "Delete Comment ${ctx.path("id")}" }
      require(CommentRepository::class).removeById(ctx.path("id").value())
      StatusCode.NO_CONTENT
    }
  }*/

})



fun main(args: Array<String>) {
  ElasticApmAttacher.attach()
  runApp(args, App::class)
}
