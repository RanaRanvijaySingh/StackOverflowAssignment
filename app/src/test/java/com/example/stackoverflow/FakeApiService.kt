package com.example.stackoverflow

import com.example.stackoverflow.data.remote.apis.StackOverflowApiService
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto
import retrofit2.Response

class FakeApiService(
    private val response: Response<UsersResponseDto>
) : StackOverflowApiService {
    override suspend fun getUsers(
        page: Int,
        pageSize: Int,
        order: String,
        sort: String,
        site: String
    ): Response<UsersResponseDto> = response
}