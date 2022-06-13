package com.bikcodeh.data

import com.apollographql.apollo3.ApolloClient
import com.bikcodeh.UsersQuery
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.common.makeSafeNetworkRequest
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.Post
import com.bikcodeh.domain.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val dataMapper: DataMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getData(): Flow<Resource<List<Post>>> = flow {
        val dataEmit = makeSafeNetworkRequest(dispatcher) {
            val data = apolloClient.query(UsersQuery()).execute()
            dataMapper.map(data.data?.users?.get(0)?.posts ?: emptyList())
        }
        emit(dataEmit)
    }.flowOn(Dispatchers.IO)
}