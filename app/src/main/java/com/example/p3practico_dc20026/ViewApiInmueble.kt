package com.example.p3practico_dc20026

import retrofit2.http.GET

interface ViewApiInmueble {
    @GET("inmuebles")
    suspend fun getInmueble(): ArrayList<InmuebleInsert>
}