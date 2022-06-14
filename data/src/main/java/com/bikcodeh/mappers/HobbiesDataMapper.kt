package com.bikcodeh.mappers

import com.bikcodeh.UserDetailQuery
import com.bikcodeh.common.NullableInputListMapper
import com.bikcodeh.domain.model.Hobby
import com.bikcodeh.domain.model.Post
import javax.inject.Inject

class HobbiesDataMapper @Inject constructor(): NullableInputListMapper<UserDetailQuery.Hooby?, Hobby> {
    override fun map(input: List<UserDetailQuery.Hooby?>?): List<Hobby> {
        return input?.map { hobby -> Hobby(
            id = hobby?.id ?: String(),
            title = hobby?.title ?: String(),
            description = hobby?.description ?: String()
        ) }.orEmpty()
    }
}