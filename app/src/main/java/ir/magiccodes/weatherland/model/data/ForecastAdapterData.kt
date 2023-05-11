package ir.magiccodes.weatherland.model.data

data class ForecastAdapterData(
    var hourOrDate: String,
    val conditionImage: Int,
    val temperature:String
)