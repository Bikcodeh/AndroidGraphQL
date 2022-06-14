package com.bikcodeh.mappers

import com.bikcodeh.UsersQuery
import com.bikcodeh.common.NullableInputListMapper
import com.bikcodeh.domain.model.User
import javax.inject.Inject

class UsersDataMapper @Inject constructor() : NullableInputListMapper<UsersQuery.User?, User> {
    override fun map(input: List<UsersQuery.User?>?): List<User> {
        return input?.map { user ->
            User(
                id = user?.id ?: String(),
                name = user?.name ?: String(),
                age = user?.age ?: 0,
                profession = user?.profession ?: String()
            )
        }.orEmpty()
    }
}
