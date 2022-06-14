package com.bikcodeh.domain.usecase

import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.repository.MainRepository
import javax.inject.Inject


class GetUserDetail @Inject constructor(private val mainRepository: MainRepository) {

    suspend operator fun invoke(userId: String): Resource<User?> {
        return mainRepository.getUserDetail(userId)
    }
}