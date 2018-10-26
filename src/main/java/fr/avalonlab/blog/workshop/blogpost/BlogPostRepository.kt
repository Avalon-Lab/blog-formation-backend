package fr.avalonlab.blog.workshop.blogpost

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mongodb.MongoClient
import io.vavr.collection.List
import org.bson.types.ObjectId
import org.jongo.Jongo
import org.jongo.MongoCollection
import org.jongo.marshall.jackson.JacksonMapper
import org.jongo.Oid.withOid
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Named

class BlogPostRepository @Inject constructor (@Named("dbName") dbName: String, mongoClient: MongoClient){

    // private val log = LoggerFactory.getLogger(javaClass)

    private val jongo: Jongo

    private val BLOGPOSTS = "blogposts"

    init {
        val mongoDb = mongoClient.getDB(dbName)
        jongo = Jongo(mongoDb,JacksonMapper.Builder()
                                .registerModule(Jdk8Module())
                                .registerModule(JavaTimeModule())
                                .build())
    }

    private fun getCollection(): MongoCollection {
        return jongo.getCollection(BLOGPOSTS)
    }

    fun findAll(): List<BlogPost> {
        val result = getCollection().find().`as`(BlogPost::class.java)
        return List.ofAll(result)
    }

    fun findById(id: String): BlogPost {
        val query = ObjectId(id)
        return getCollection().findOne(query).`as`(BlogPost::class.java)
    }

    fun create(entity: BlogPost): BlogPost {
        getCollection().save(entity)
        return entity
    }

    fun update(entity: BlogPost): BlogPost {
        getCollection().update(withOid(entity._id)).with(entity)
        return entity
    }

    fun removeById(id: String) {
        val query = ObjectId(id)
        getCollection().remove(query)
    }

    fun removeAll() {
        getCollection().drop()
    }

    fun find(field: String, param: String): List<BlogPost> {
        val result = getCollection().find("{$field: #}", param).`as`(BlogPost::class.java)
        return List.ofAll(result)
    }
}