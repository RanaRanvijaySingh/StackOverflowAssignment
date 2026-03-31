package com.example.stackoverflow.data.remote.apis

import com.example.stackoverflow.constants.API_VERSION
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApiService {

    @GET("$API_VERSION/users")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("pagesize") pageSize: Int = 20,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "reputation",
        @Query("site") site: String = "stackoverflow"
    ): Response<UsersResponseDto>
}
