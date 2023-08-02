package com.veripark.interview

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veripark.interview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Sample slab configuration


    private lateinit var activityMainBinding: ActivityMainBinding;


    val mainActivityViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.activityVM = mainActivityViewModel

        val historyAdapter: HistoryAdapter = HistoryAdapter(ArrayList<Pair<Int, Double>>())
        mainActivityViewModel.historyAdapter = historyAdapter
        activityMainBinding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding.historyRecyclerView.adapter = historyAdapter

    }

    override fun onStart() {
        super.onStart()
        changeStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent))
    }

    /**
     * Method use to change the Status bor color of the application
     * @param activity Activity context
     * @param color status bar color
     */
    fun changeStatusBarColor(activity: Activity?, color: Int) {
        activity?.let { activeIt ->
            activeIt.window?.let { window ->
                window.statusBarColor = color
                window.navigationBarColor = color
            }
        }
    }
}
