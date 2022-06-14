package com.bikcodeh.domain.usecase

import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUC @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<User>>> = mainRepository.getUsers()
}