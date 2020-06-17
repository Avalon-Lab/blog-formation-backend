package fr.avalonlab.blog.workshop.blogpost

import co.elastic.apm.api.CaptureSpan
import com.mongodb.ConnectionString
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*
import javax.inject.Inject
import javax.inject.Named

class BlogPostRepository @Inject constructor (@Named("db") db: String, @Named("dbName") dbName: String) {

    private val database: MongoDatabase
    private val BLOGPOSTS = "blogposts"

    init {
        val client = KMongo.createClient(ConnectionString(db))
        database = client.getDatabase(dbName)
    }

    private fun getCollection() = database.getCollection<BlogPost>(BLOGPOSTS)

    @CaptureSpan
    fun findAll(): List<BlogPost> {
        val result = getCollection().find()
        return result.toList()
    }

    @CaptureSpan
    fun findById(id: String): BlogPost? {
        return getCollection().findOneById(id)
    }

    @CaptureSpan
    fun create(entity: BlogPost): BlogPost {
        getCollection().insertOne(entity)
        return entity
    }

    fun update(entity: BlogPost): BlogPost {
        getCollection().updateOne(entity)
        return entity
    }

    fun removeById(id: String) {
        //val query = ObjectId(id)
        getCollection().deleteOneById(id)
    }

    fun removeAll() {
        getCollection().drop()
    }

    fun find(field: String, param: String): List<BlogPost> {
        val result = getCollection().find("{ $field: '$param' }")
        return result.toList()
    }
}