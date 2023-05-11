package ir.magiccodes.weatherland.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.magiccodes.weatherland.MyApp
import ir.magiccodes.weatherland.di.module.ActivityBuilderModule
import ir.magiccodes.weatherland.di.module.Modules
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ActivityBuilderModule::class,
    AndroidSupportInjectionModule::class,
    Modules::class
])
interface MainComponent: AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application):Builder

        fun build():MainComponent

    }

}