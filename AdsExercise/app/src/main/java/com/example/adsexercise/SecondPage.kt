package com.example.adsexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class SecondPage : AppCompatActivity() {

    //For the rewarded ad
    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "MainActivity"

    //For the progressbar
    var points : Int = 0
    var progressBarPoints : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)
    }
        private fun loadRewardedAd() {
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Ad wasn't loaded")
                mRewardedAd = null
                loadRewardedAd()
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd


                mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad was shown.")
                        mRewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        // Called when ad fails to show.
                        Log.d(TAG, "Ad failed to show.")
                        loadRewardedAd()
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad was dismissed.")
                        loadRewardedAd()
                    }
                }

            }
        })
    }

    fun showRewardedAd(view: View){
        val pointTextView = findViewById<TextView>(R.id.points)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        if (mRewardedAd != null) {
            mRewardedAd!!.show(this) { rewardItem ->
                Log.d(TAG, "User earned the reward.")
                Toast.makeText(applicationContext,"120 points earned!", Toast.LENGTH_SHORT).show()
                //Increase the amount of the (progressbar)points
                points = points+120
                progressBarPoints = progressBarPoints + 20
                pointTextView.text = points.toString()
                progressBar.setProgress(progressBarPoints)
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
            loadRewardedAd()
        }

    }

}
