package fr.avalonlab.blog.workshop;


import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import fr.avalonlab.blog.workshop.comment.Comment;
import fr.avalonlab.blog.workshop.comment.CommentRepository;
import fr.avalonlab.blog.workshop.blogpost.BlogPost;
import fr.avalonlab.blog.workshop.blogpost.BlogPostRepository;
import io.vavr.collection.List;
import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.handlers.CorsHandler;
import org.jooby.json.Jackson;
import org.jooby.mongodb.Mongodb;
import java.time.LocalDateTime;

public class App extends Jooby {
    
    private final static String PATH_BLOGPOST = "/api/blogpost";

    {

        use(new Jackson().doWith(mapper ->
                mapper.registerModule(new ParameterNamesModule())
                        .registerModule(new Jdk8Module())
                        .registerModule(new JavaTimeModule())
        ));

        use(new Mongodb());

        use("*", new CorsHandler());

        get("/", () -> "I'm alive !!");

        get(PATH_BLOGPOST, req -> {
            List<BlogPost> result = require(BlogPostRepository.class).findAll();

            return result.asJava();
        });

        post(PATH_BLOGPOST, req -> {
            BlogPost blogPost = req.body(BlogPost.class);
            blogPost.setCreationDate(LocalDateTime.now());

            return require(BlogPostRepository.class).create(blogPost);
        });

        put(PATH_BLOGPOST, req -> {
            BlogPost blogPost = req.body(BlogPost.class);
            blogPost.setUpdateDate(LocalDateTime.now());

            return require(BlogPostRepository.class).update(blogPost);
        });

        get(PATH_BLOGPOST + "/:id", req -> require(BlogPostRepository.class).findById(req.param("id").value()));

        delete(PATH_BLOGPOST + "/:id", req -> {
            require(BlogPostRepository.class).removeById(req.param("id").value());

            return Results.ok();
        });

        delete(PATH_BLOGPOST, req -> {
            require(BlogPostRepository.class).removeAll();

            return Results.ok();
        });

        get(PATH_BLOGPOST + "/author/:name", req -> {
            List<BlogPost> result = require(BlogPostRepository.class).find("author", req.param("name").value());

            return result;
        });

        get(PATH_BLOGPOST + "/:blogId/comment", req -> {
            List<Comment> result = require(CommentRepository.class).findAll(req.param("blogId").value());

            return result.asJava();
        });

        post(PATH_BLOGPOST + "/:blogId/comment", req -> {
            Comment comment = req.body(Comment.class);
            comment.setBlogId(req.param("blogId").value());
            comment.setCreationDate(LocalDateTime.now());

            return require(CommentRepository.class).create(comment);
        });

        get(PATH_BLOGPOST + "/:blogId/comment/:id", req -> require(CommentRepository.class).findById(req.param("id").value()));

        delete(PATH_BLOGPOST + "/:blogId/comment", req -> {
            require(CommentRepository.class).removeById(req.param("id").value());

            return Results.ok();
        });
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }
}
