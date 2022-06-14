package com.bikcodeh.data

import com.apollographql.apollo3.ApolloClient
import com.bikcodeh.UsersQuery
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.common.makeSafeNetworkRequest
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.Post
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.repository.MainRepository
import com.bikcodeh.mappers.UsersDataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val usersDataMapper: UsersDataMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getUsers(): Flow<Resource<List<User>>> = flow {
        val dataEmit = makeSafeNetworkRequest(dispatcher) {
            val response = apolloClient.query(UsersQuery()).execute()
            usersDataMapper.map(response.data?.users)
        }
        emit(dataEmit)
    }.flowOn(dispatcher)

    override suspend fun getUserDetail(userId: String): Flow<Resource<User>> {
        TODO("Not yet implemented")
    }
}