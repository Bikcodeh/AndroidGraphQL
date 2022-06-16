package com.bikcodeh.domain.repository

import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.User
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getUsers(): Flow<Resource<List<User>>>
    suspend fun getUserDetail(userId: String): Resource<User?>
    suspend fun addUser(name: String, age: Int, profession: String): Resource<Boolean>
}