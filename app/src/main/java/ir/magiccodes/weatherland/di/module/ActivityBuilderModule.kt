package ir.magiccodes.weatherland.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.magiccodes.weatherland.ui.MainActivity
import ir.magiccodes.weatherland.ui.feature.mainScreen.MainFragment

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector
    abstract fun contributeMainScreen(): MainFragment?
}