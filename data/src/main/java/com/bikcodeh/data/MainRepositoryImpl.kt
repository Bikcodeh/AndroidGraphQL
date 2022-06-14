package com.bikcodeh.data

import com.apollographql.apollo3.ApolloClient
import com.bikcodeh.UserDetailQuery
import com.bikcodeh.UsersQuery
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.common.makeSafeNetworkRequest
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.Post
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.repository.MainRepository
import com.bikcodeh.mappers.HobbiesDataMapper
import com.bikcodeh.mappers.PostsDataMapper
import com.bikcodeh.mappers.UserDataMapper
import com.bikcodeh.mappers.UsersDataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val usersDataMapper: UsersDataMapper,
    private val userDataMapper: UserDataMapper,
    private val postsDataMapper: PostsDataMapper,
    private val hobbiesDataMapper: HobbiesDataMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getUsers(): Flow<Resource<List<User>>> = flow {
        val dataEmit = makeSafeNetworkRequest(dispatcher) {
            val response = apolloClient.query(UsersQuery()).execute()
            usersDataMapper.map(response.data?.users)
        }
        emit(dataEmit)
    }.flowOn(dispatcher)

    override suspend fun getUserDetail(userId: String): Resource<User?> {
        return makeSafeNetworkRequest(dispatcher) {
            val response = apolloClient.query(UserDetailQuery(id = userId)).execute()
            val user = userDataMapper.map(
                response.data?.user
            )
            user?.posts = postsDataMapper.map(response.data?.user?.posts)
            user?.hobbies = hobbiesDataMapper.map(response.data?.user?.hoobies)
            user
        }
    }
}