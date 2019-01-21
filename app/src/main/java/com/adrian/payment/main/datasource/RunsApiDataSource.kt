package com.adrian.payment.main.datasource

import com.adrian.payment.main.domain.model.GamesResponse
import com.adrian.payment.main.domain.model.RunsResponse
import com.adrian.payment.main.domain.model.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RunsApiDataSource {

    @GET("games")
    fun getGames(@Query("offset") offset: Int)
            : Single<GamesResponse>

    @GET("runs")
    fun getSpeedruns(@Query("game") gameId: String)
            : Single<RunsResponse>

    @GET("users/{idUser}")
    fun getUser(@Path("idUser") userId: String)
            : Single<UserResponse>
}