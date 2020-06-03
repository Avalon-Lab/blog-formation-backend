package fr.avalonlab.blog.workshop.comment

import java.time.LocalDateTime

data class Comment(val _id: String?,
                   var blogId: String?,
                   val message: String,
                   var creationDate: String?,
                   val author: String)