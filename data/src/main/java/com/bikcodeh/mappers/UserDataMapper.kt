package com.bikcodeh.mappers

import com.bikcodeh.UserDetailQuery
import com.bikcodeh.UsersQuery
import com.bikcodeh.common.NullableMapper
import com.bikcodeh.domain.model.User
import javax.inject.Inject

class UserDataMapper @Inject constructor() : NullableMapper<UserDetailQuery.User?, User> {
    override fun map(input: UserDetailQuery.User?): User? {
        return input?.let {
            User(
                id = it.id ?: String(),
                name = it.name ?: String(),
                age = it.age ?: 0,
                profession = it.profession ?: String()
            )
        }
    }
}
