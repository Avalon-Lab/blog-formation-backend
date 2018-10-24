package fr.avalonlab.blog.workshop.comment

import org.jongo.marshall.jackson.oid.MongoObjectId
import java.time.LocalDateTime

class Comment {

    @MongoObjectId
    var _id: String? = null

    var blogId: String? = null
    var message: String? = null
    var creationDate: LocalDateTime? = null
    var author: String? = null
}