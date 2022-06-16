package com.bikcodeh.domain.usecase

import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.repository.MainRepository
import javax.inject.Inject

class AddUserUC @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        name: String,
        age: Int,
        profession: String
    ): Resource<Boolean> = mainRepository.addUser(name, age, profession)
}