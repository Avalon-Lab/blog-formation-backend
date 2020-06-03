package fr.avalonlab.blog.workshop.comment

import com.mongodb.client.MongoDatabase
//import org.bson.types.ObjectId
import org.litote.kmongo.*
import javax.inject.Inject
import javax.inject.Named

class CommentRepository @Inject constructor (@Named("db") db: String, @Named("dbName") dbName: String) {

    private val database: MongoDatabase
    private val COMMENTS = "comments"

    init {
        val client = KMongo.createClient(db)
        database = client.getDatabase(dbName)
    }

    private fun getCollection() = database.getCollection<Comment>(COMMENTS)

    fun findAll(blogId: String): List<Comment> {
        val result =  getCollection().find("{ blogId: '$blogId' }")
        return result.toList()
    }

    fun findById(id: String): Comment? {
        //val query = ObjectId(id)
        return getCollection().findOneById(id)
    }

    fun create(entity: Comment): Comment {
        getCollection().save(entity)
        return entity
    }

    fun removeById(id: String) {
        //val query = ObjectId(id)
        getCollection().deleteOneById(id)
    }
}