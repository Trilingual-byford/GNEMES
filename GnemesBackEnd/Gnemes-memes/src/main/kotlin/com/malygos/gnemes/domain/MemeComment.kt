package com.malygos.gnemes.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document(collection = "MemeComment")
data class MemeComment(
        @Id
        var commentId: Long,
        var postId: Long,
        var userId: Long,
        var userName: String? = null,
        var comment: String,
        var postDate: Date
)

