package ir.magiccodes.weatherland.ui.feature.mainScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.DaggerFragment
import ir.magiccodes.weatherland.adapter.ForecastAdapter
import ir.magiccodes.weatherland.adapter.ItemClickListener
import ir.magiccodes.weatherland.databinding.FragmentMainBinding
import ir.magiccodes.weatherland.model.data.ForecastAdapterData
import ir.magiccodes.weatherland.model.data.WeatherData
import ir.magiccodes.weatherland.util.descriptionOfAirQuality
import ir.magiccodes.weatherland.util.descriptionOfUvIndex
import javax.inject.Inject

class MainFragment : DaggerFragment(), MainScreenContract.View {
    lateinit var binding: FragmentMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var adapter: ForecastAdapter
    lateinit var weatherData: WeatherData

    @Inject lateinit var mainPresenter: MainScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If bottomSheet was wide, it should collapse instead of exiting the program
        activity?.onBackPressedDispatcher?.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        binding.layoutBottomSheet.NestedScrollView.scrollTo(0, 0)
                        val anim = AlphaAnimation(0f, 1f)
                        anim.duration = 200
                        anim.fillAfter = true
                        binding.bottomTabBar.startAnimation(anim)
                    }
                }
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleBottomSheetDetail()

        binding.layoutBottomSheet.btnWeeklyForecast.setOnClickListener {
            mainPresenter.onWeeklyForecastClicked()
        }

        binding.layoutBottomSheet.btnHourlyForecast.setOnClickListener {
            mainPresenter.onHourlyForecastClicked()
        }

    }

    override fun onStart() {
        super.onStart()
        mainPresenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.onDetach()
    }
    private fun handleBottomSheetDetail() {
        val bottomSheet = binding.layoutBottomSheet.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        binding.btnOpenDetail.setOnClickListener {
            binding.layoutBottomSheet.airQualityCardView.visibility = View.VISIBLE
            binding.bottomTabBar.visibility = View.INVISIBLE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // when bottomSheet in low height
                        binding.layoutBottomSheet.NestedScrollView.scrollTo(0, 0)

                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.layoutBottomSheet.airQualityCardView.visibility = View.VISIBLE
                        binding.layoutBottomSheet.topBar.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // when bottomSheet is fully open
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
                binding.layoutBottomSheet.topBar.alpha = slideOffset

                binding.layoutBottomSheet.airQualityCardView.alpha = slideOffset

                binding.bottomTabBar.alpha = (1f - slideOffset)
            }
        })
    }

    override fun setCurrentWeather(data: WeatherData, position:Int) {
        // We value this weatherData here to use this data in other functions when needed
        weatherData = data

        binding.tvCityName.text = data.location.name
        binding.tvTemperatureCurrent.text = data.forecast.forecastday[0].hour[position].tempC.toString()
        binding.tvConditionName.text = data.current.condition.text
        binding.tvMaxTemperature.text = data.forecast.forecastday[0].day.maxtempC.toString()
        binding.tvMinTemperature.text = data.forecast.forecastday[0].day.mintempC.toString()

        // bottom sheet header
        binding.layoutBottomSheet.tvHeaderCityName.text = data.location.name
        binding.layoutBottomSheet.tvHeaderTemperature.text = data.current.tempC.toInt().toString()
        binding.layoutBottomSheet.tvHeaderConditionName.text = data.current.condition.text
    }

    override fun showHourlyForecast(data: List<ForecastAdapterData>, scrollPosition: Int) {
        binding.layoutBottomSheet.underlineHourlyForecast.visibility = View.VISIBLE
        binding.layoutBottomSheet.underlineWeeklyForecast.visibility = View.INVISIBLE

        val myAdapter = ForecastAdapter(data, scrollPosition,object: ItemClickListener{
            override fun onItemClicked(position: Int) {
                // Changing the current detail data to the item data that was clicked
                updateHourlyForecastDetail(weatherData, position)
            }
        })
        binding.layoutBottomSheet.recyclerForecast.adapter = myAdapter
        binding.layoutBottomSheet.recyclerForecast.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.layoutBottomSheet.recyclerForecast.scrollToPosition(scrollPosition)
    }

    override fun showWeeklyForecast(data: List<ForecastAdapterData>) {
        binding.layoutBottomSheet.underlineHourlyForecast.visibility = View.INVISIBLE
        binding.layoutBottomSheet.underlineWeeklyForecast.visibility = View.VISIBLE

        // checkPosition is 0 because today's item is the first item
        val myAdapter = ForecastAdapter(data,0, object: ItemClickListener{
            override fun onItemClicked(position: Int) {
                Toast.makeText(requireContext(), "weekssssssss", Toast.LENGTH_SHORT).show()
            }
        })
        binding.layoutBottomSheet.recyclerForecast.adapter = myAdapter
        binding.layoutBottomSheet.recyclerForecast.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }

    override fun showHourlyDetail(data: WeatherData, position:Int) {
        updateHourlyForecastDetail(data, position)
    }

    @SuppressLint("SetTextI18n")
    private fun updateHourlyForecastDetail(data: WeatherData, position: Int){
        val hourData = data.forecast.forecastday[0].hour[position]

        // air quality cardView
        binding.layoutBottomSheet.tvAirQuality.text = descriptionOfAirQuality(data.current.airQuality.co.toInt())
        binding.layoutBottomSheet.ariQualitySeekbar.progress = data.current.airQuality.co.toInt()
        binding.layoutBottomSheet.btnSeeMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://aqicn.org/map/"))
            startActivity(intent)
        }

        // uv index cardView
        binding.layoutBottomSheet.tvUvIndex.text = hourData.uv.toInt().toString()
        binding.layoutBottomSheet.tvUvIndexText.text = descriptionOfUvIndex(hourData.uv.toInt())
        binding.layoutBottomSheet.uvIndexSeekbar.progress = hourData.uv.toInt() - 1

        //humidity
        binding.layoutBottomSheet.tvHumidity.text = hourData.humidity.toString() + "%"
        binding.layoutBottomSheet.tvDewPoint.text = "the dew point is ${hourData.dewpointC} right now"

        // wind
        binding.layoutBottomSheet.tvWindSpeed.text = hourData.windKph.toString()
        binding.layoutBottomSheet.imgArrowWindDegree.rotation = hourData.windDegree.toFloat()

        //rainFall
        binding.layoutBottomSheet.tvRainFall.text = hourData.precipMm.toString()
        binding.layoutBottomSheet.tvCloudCover.text = "The cloud cover is currently ${hourData.cloud}%."

        // fells like
        binding.layoutBottomSheet.tvFellsLike.text = hourData.feelslikeC.toString()

        // visibility
        binding.layoutBottomSheet.tvVisibility.text = hourData.visKm.toInt().toString()
    }

}