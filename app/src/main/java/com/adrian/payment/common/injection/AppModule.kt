package com.adrian.payment.common.injection

import com.adrian.payment.common.injection.Url.BASE_URL
import com.adrian.payment.contacts.datasource.ContactsDeviceDataSource
import com.adrian.payment.contacts.datasource.ContactsFakeDataSource
import com.adrian.payment.contacts.datasource.HeroesApiDataSource
import com.adrian.payment.contacts.repository.ContactsRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Url {
    const val BASE_URL = "http://gateway.marvel.com/v1/public/"
}

val appModule = Kodein.Module("App") {
    //bind<OkHttpClient>() with singleton {OkHttpClient()} same as
    bind() from singleton { OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES).build()}
    bind() from singleton {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(instance())
                .build()
    }
    bind() from singleton { Moshi.Builder().build()}

    bind() from singleton { ContactsDeviceDataSource(instance()) }
    bind() from singleton { ContactsFakeDataSource() }
    bind() from singleton { instance<Retrofit>().create(HeroesApiDataSource::class.java) }
    bind() from singleton { ContactsRepository(instance<ContactsDeviceDataSource>(), instance()) }
}