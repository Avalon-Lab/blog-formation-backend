package fr.avalonlab.blog.workshop.comment

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mongodb.MongoClient
import io.vavr.collection.List
import org.bson.types.ObjectId
import org.jongo.Jongo
import org.jongo.MongoCollection
import org.jongo.marshall.jackson.JacksonMapper
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor (@Named("dbName") dbName: String, mongoClient: MongoClient){

    // private val log = LoggerFactory.getLogger(javaClass)

    private var jongo: Jongo

    private val COMMENTS = "comments"

    init {
        val mongoDb = mongoClient.getDB(dbName)
        jongo = Jongo(mongoDb,JacksonMapper.Builder()
                                .registerModule(Jdk8Module())
                                .registerModule(JavaTimeModule())
                                .build())
    }

    private fun getCollection(): MongoCollection {
        return jongo.getCollection(COMMENTS)
    }

    fun findAll(blogId: String): List<Comment> {
        val result = getCollection().find("{blogId: #}", blogId).`as`(Comment::class.java)
        return List.ofAll(Iterable { result.iterator() })
    }

    fun findById(id: String): Comment {
        val query = ObjectId(id)
        return getCollection().findOne(query).`as`(Comment::class.java)
    }

    fun create(entity: Comment): Comment {
        getCollection().save(entity)
        return entity
    }

    fun removeById(id: String) {
        val query = ObjectId(id)
        getCollection().remove(query)
    }
}