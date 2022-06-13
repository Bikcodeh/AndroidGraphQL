package com.bikcodeh.domain.repository

import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getData(): Flow<Resource<List<Post>>>
}