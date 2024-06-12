package com.example.p3practico_dc20026

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewApiPropietario {
    @GET("propietarios/{idPropietario}")
    suspend fun getPropietario(@Path("idPropietario") idPropietario: Int): Response<PropietarioView>
}