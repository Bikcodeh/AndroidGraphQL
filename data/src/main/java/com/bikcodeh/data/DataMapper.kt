package com.bikcodeh.data

import com.bikcodeh.UsersQuery
import com.bikcodeh.common.NullableInputListMapper
import com.bikcodeh.domain.model.Post
import javax.inject.Inject

class DataMapper @Inject constructor(): NullableInputListMapper<UsersQuery.Post?, Post> {
    override fun map(input: List<UsersQuery.Post?>?): List<Post> {
        return input?.map { post -> Post(post?.id ?: "" ,post?.comment ?: "") }.orEmpty()
    }
}
