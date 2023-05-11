package ir.magiccodes.weatherland.ui.feature.mainScreen

import ir.magiccodes.weatherland.model.data.ForecastAdapterData
import ir.magiccodes.weatherland.model.data.WeatherData

interface MainScreenContract {

    interface Presenter {
        fun onAttach(view: MainScreenContract.View)
        fun onDetach()

        fun onHourlyForecastClicked()
        fun onWeeklyForecastClicked()
        fun onWeatherItemClicked()

        fun onMapClicked()
        fun onAddWeatherClicked()


    }

    interface View{
        fun setCurrentWeather(data: WeatherData, position:Int)
        fun showHourlyForecast(data: List<ForecastAdapterData>, scrollPosition: Int)
        fun showWeeklyForecast(data: List<ForecastAdapterData>)
        fun showHourlyDetail(data: WeatherData, position:Int)

    }
}