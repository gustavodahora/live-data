package dev.gustavodahora.livedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.gustavodahora.livedatademo.databinding.ActivityMainBinding
import dev.gustavodahora.livedatademo.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        viewModel.seconds().observe(this, Observer {
            binding.tvNumber.text = it.toString()
        })
        viewModel.finished.observe(this, Observer {
            if (it) {
                Toast.makeText(this, getString(R.string.finished), Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnStart.setOnClickListener {
            if (binding.inputNumber.text.isEmpty() || binding.inputNumber.text.length < 4) {
                Toast.makeText(this, getString(R.string.invalid_number), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.timerValue.value = binding.inputNumber.text.toString().toLong()
                viewModel.startTimer()
            }
        }

        binding.btnStop.setOnClickListener {
            binding.tvNumber.text = "0"
            viewModel.stopTimer()
        }
    }
}