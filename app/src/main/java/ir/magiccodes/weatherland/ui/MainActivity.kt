package ir.magiccodes.weatherland.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.magiccodes.weatherland.R
import ir.magiccodes.weatherland.databinding.ActivityMainBinding
import ir.magiccodes.weatherland.ui.feature.mainScreen.MainFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_main_container, MainFragment())
        transaction.commit()


    }
}