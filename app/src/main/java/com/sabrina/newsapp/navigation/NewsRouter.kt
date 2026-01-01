package com.sabrina.newsapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.toColorInt

object NewsRouter {
    fun openArticle(context: Context,url: String){
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor("#000000".toColorInt())
            .build()

        try {
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }catch (e: Exception){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }
}