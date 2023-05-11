package ir.magiccodes.weatherland.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.magiccodes.weatherland.databinding.ItemForecastBinding
import ir.magiccodes.weatherland.model.data.ForecastAdapterData
import ir.magiccodes.weatherland.util.conditionImage

class ForecastAdapter(
    private val data: List<ForecastAdapterData>,
    private var checkPosition: Int,
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(position: Int){

            binding.tvHourOrDate.text = data[position].hourOrDate
            binding.imgConditionImage.setImageResource(conditionImage(data[position].conditionImage,true))
            binding.tvTemperature.text = data[position].temperature

            // handle radioButton check or uncheck
            binding.radioButton.isChecked = position == checkPosition
            binding.radioButton.setOnClickListener {
                checkPosition = position
                notifyDataSetChanged()

                itemClickListener.onItemClicked(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ForecastViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = data.size

}

interface ItemClickListener {
    fun onItemClicked(position: Int)
}