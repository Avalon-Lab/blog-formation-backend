package fr.avalonlab.blog.workshop.blogpost;

import lombok.Data;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.time.LocalDateTime;

class BlogPost {

    @MongoObjectId
    var _id: String? = null

    var title: String? = null
    var article: String? = null
    var creationDate: LocalDateTime? = null
    var updateDate: LocalDateTime? = null
    var author: String? = null
}
