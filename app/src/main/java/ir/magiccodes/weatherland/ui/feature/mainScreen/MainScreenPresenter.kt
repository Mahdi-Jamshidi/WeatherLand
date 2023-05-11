package ir.magiccodes.weatherland.ui.feature.mainScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import ir.magiccodes.weatherland.model.ApiService
import ir.magiccodes.weatherland.model.data.ForecastAdapterData
import ir.magiccodes.weatherland.model.data.WeatherData
import ir.magiccodes.weatherland.util.convertDateToDayOfWeek
import ir.magiccodes.weatherland.util.convertDateToHour
import ir.magiccodes.weatherland.util.getCurrentHour

class MainScreenPresenter(
    private val apiService: ApiService
): MainScreenContract.Presenter  {
    var mainView: MainScreenContract.View? = null
    lateinit var disposable: Disposable
    lateinit var data: WeatherData
    private var itemPosition: Int = 0

    override fun onAttach(view: MainScreenContract.View) {
        mainView = view

        apiService.getWeather()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherData>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSuccess(t: WeatherData) {
                    // set data to current weather
                    data = t
                    mainView!!.setCurrentWeather(data,itemPosition)

                    setHourlyForecastData(data)

                    // set detail data
                    mainView!!.showHourlyDetail(data,itemPosition)
                }

                override fun onError(e: Throwable) {
                    Log.v("RxjavaError", e.message!!)
                }
            })



    }

    override fun onDetach() {
        mainView = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHourlyForecastClicked() {
        setHourlyForecastData(data)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onWeeklyForecastClicked() {
        val dataWeeklyForecast = arrayListOf<ForecastAdapterData>()
        data.forecast.forecastday.forEach {
            dataWeeklyForecast.apply {
                add(ForecastAdapterData(convertDateToDayOfWeek(it.date), it.day.condition.code, it.day.avgtempC.toInt().toString()))
            }
        }

        mainView!!.showWeeklyForecast(dataWeeklyForecast)
    }

    override fun onWeatherItemClicked() {
        TODO("Not yet implemented")
    }


    override fun onMapClicked() {
        TODO("Not yet implemented")
    }

    override fun onAddWeatherClicked() {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setHourlyForecastData(data: WeatherData){

        // set data to recycler hourly forecast
        val forecastData = arrayListOf<ForecastAdapterData>()
        data.forecast.forecastday[0].hour.forEach {
            forecastData.apply {
                add(ForecastAdapterData(convertDateToHour(it.time), it.condition.code, it.tempC.toInt().toString()))
            }
        }

        // find current time item(weather)
        forecastData.forEachIndexed { index, it ->
            if (it.hourOrDate == getCurrentHour()){
                it.hourOrDate = "Now"
                //this is for find now item
                itemPosition = index
            }
        }

        mainView!!.showHourlyForecast(forecastData, itemPosition)
    }

}