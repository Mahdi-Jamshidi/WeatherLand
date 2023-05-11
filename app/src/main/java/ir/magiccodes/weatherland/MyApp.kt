package ir.magiccodes.weatherland

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ir.magiccodes.weatherland.di.component.DaggerMainComponent

class MyApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerMainComponent
            .builder()
            .application(this)
            .build()
    }
}