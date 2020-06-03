package fr.avalonlab.blog.workshop

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator
import fr.avalonlab.blog.workshop.blogpost.BlogPost
import fr.avalonlab.blog.workshop.blogpost.BlogPostRepository
import fr.avalonlab.blog.workshop.comment.Comment
import fr.avalonlab.blog.workshop.comment.CommentRepository
import io.jooby.*
import io.jooby.di.GuiceModule
import io.jooby.json.JacksonModule
import org.apache.logging.log4j.ThreadContext
import org.apache.logging.log4j.kotlin.logger
import java.time.LocalDateTime


private const val PATH_BLOGPOST = "/api/blogpost"

class App: Kooby({
  install(JacksonModule())
  require(ObjectMapper::class.java).registerModule(ParameterNamesModule())
  require(ObjectMapper::class.java).registerModule(Jdk8Module())
  require(ObjectMapper::class.java).registerModule(JavaTimeModule())
  install(OpenAPIModule())
  install(GuiceModule())

  before {
    val fishTagGenerator = PermutationBasedHumanReadableIdGenerator()
    ThreadContext.put("fishTag", fishTagGenerator.generate())
  }

  get("/") { "I'm alive !!" }

  path(PATH_BLOGPOST) {

    get("/") {
      logger().info { "Get all BlogPosts" }
      val result: List<BlogPost> = require(BlogPostRepository::class).findAll()
      result
    }

    post("/") {
      logger().info { "Add a new BlogPost" }
      val blogPost = ctx.body<BlogPost>()
      blogPost.creationDate = LocalDateTime.now()
      require(BlogPostRepository::class).create(blogPost)
    }

    put("/") {
      logger().info { "Update a BlogPost" }
      val blogPost = ctx.body<BlogPost>()
      blogPost.updateDate = LocalDateTime.now()
      require(BlogPostRepository::class).update(blogPost)
    }

    get("/:id") {
      logger().info { "Retrieve BlogPost ${ctx.path("id")}" }
      require(BlogPostRepository::class).findById(ctx.path("id").value())!!
    }

    delete("/:id") {
      logger().info { "Delete BlogPost ${ctx.path("id")}" }
      require(BlogPostRepository::class).removeById(ctx.path("id").value())
      StatusCode.NO_CONTENT
    }

    delete(PATH_BLOGPOST) {
      logger().info { "Delete all BlogPosts" }
      require(BlogPostRepository::class).removeAll()
      StatusCode.NO_CONTENT
    }

    get("/author/:name") {
      logger().info { "Retrieve author's ${ctx.path("name")} BlogPosts" }
      val result: List<BlogPost> = require(BlogPostRepository::class).find("author", ctx.path("name").value())
      result
    }

    get("/:blogId/comment") {
      logger().info { "Retrieve comments of BlogPost ${ctx.path("blogId")}" }
      val result: List<Comment> = require(CommentRepository::class).findAll(ctx.path("blogId").value())
      result
    }

    post("/:blogId/comment") {
      logger().info { "Add comment to BlogPost ${ctx.path("blogId")}" }
      val comment = ctx.body<Comment>()
      comment.blogId = ctx.path("blogId").value()
      comment.creationDate = LocalDateTime.now()
      require(CommentRepository::class).create(comment)
    }

    get("/:blogId/comment/:id") {
      logger().info { "Retrieve comment ${ctx.path("id")}" }
      require(CommentRepository::class).findById(ctx.path("id").value())!!
    }

    delete("/:blogId/comment/:id") {
      logger().info { "Delete Comment ${ctx.path("id")}" }
      require(CommentRepository::class).removeById(ctx.path("id").value())
      StatusCode.NO_CONTENT
    }
  }

  after { ThreadContext.clearAll() }
})



fun main(args: Array<String>) {
  runApp(args, App::class)
}