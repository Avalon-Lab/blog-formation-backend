package fr.avalonlab.blog.workshop.blogpost

import io.jooby.annotations.*
import javax.inject.Inject
import org.apache.logging.log4j.kotlin.logger
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Path("/api/blogpost")
class BlogPostController @Inject constructor(private val blogPostRepository: BlogPostRepository) {

    @GET("/")
    fun getAllBlogPosts() : List<BlogPost> {
        logger().info { "Get all BlogPost" }
        return blogPostRepository.findAll()
    }

    @POST("/")
    fun addBlogPost(blogPost: BlogPost) {
        logger().info { "Add a new BlogPost" }
        blogPost.creationDate = OffsetDateTime.now(ZoneOffset.UTC).toString()
        blogPostRepository.create(blogPost)
    }

    @PUT("/")
    fun updateBlogPost(blogPost: BlogPost) {
        logger().info { "Update a BlogPost" }
        blogPost.updateDate = OffsetDateTime.now(ZoneOffset.UTC).toString()
        blogPostRepository.update(blogPost)
    }

    @DELETE("/")
    fun deleteAllBlogPosts() {
        logger().info { "Delete all BlogPosts" }
        blogPostRepository.removeAll()
    }

    @GET("/{id}")
    fun getBlogPost(@PathParam id: String) : BlogPost {
        logger().info { "Retrieve BlogPost $id" }
        return blogPostRepository.findById(id)!!
    }

    @DELETE("/{id}")
    fun deleteBlogPost(@PathParam id: String) {
        logger().info { "Delete BlogPost $id" }
        blogPostRepository.removeById(id)
    }

    @GET("/author/{name}")
    fun getBlogPostFromAuthor(@PathParam name: String): List<BlogPost> {
        logger().info { "Retrieve $name's (author) BlogPosts" }
        return blogPostRepository.find("author",name)
    }
}