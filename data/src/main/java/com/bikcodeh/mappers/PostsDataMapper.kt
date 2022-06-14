package com.bikcodeh.mappers

import com.bikcodeh.UserDetailQuery
import com.bikcodeh.common.NullableInputListMapper
import com.bikcodeh.domain.model.Post
import javax.inject.Inject

class PostsDataMapper @Inject constructor(): NullableInputListMapper<UserDetailQuery.Post?, Post> {
    override fun map(input: List<UserDetailQuery.Post?>?): List<Post> {
        return input?.map { post -> Post(post?.id ?: "" ,post?.comment ?: "") }.orEmpty()
    }
}