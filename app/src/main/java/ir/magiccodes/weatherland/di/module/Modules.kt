package ir.magiccodes.weatherland.di.module

import dagger.Module
import dagger.Provides
import ir.magiccodes.weatherland.model.ApiService
import ir.magiccodes.weatherland.ui.feature.mainScreen.MainScreenContract
import ir.magiccodes.weatherland.ui.feature.mainScreen.MainScreenPresenter
import ir.magiccodes.weatherland.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class Modules {

    @Provides
    @Singleton
    fun provideRetrofit() :Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) :ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMainScreenPresenter(apiService: ApiService):MainScreenContract.Presenter{
        return MainScreenPresenter(apiService)
    }
}