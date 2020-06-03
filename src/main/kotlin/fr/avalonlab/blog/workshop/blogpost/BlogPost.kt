package fr.avalonlab.blog.workshop.blogpost

import java.time.LocalDateTime

data class BlogPost(val _id: String?,
                    val title: String,
                    val article: String,
                    var creationDate: String?,
                    var updateDate: String?,
                    val author: String
)