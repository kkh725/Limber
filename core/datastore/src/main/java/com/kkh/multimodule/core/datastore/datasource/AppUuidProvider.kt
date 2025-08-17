package com.kkh.multimodule.core.datastore.datasource

import android.content.Context
import androidx.core.content.edit
import java.util.UUID

object AppUuidProvider {
    private var cachedUuid: String? = null

    fun getUuid(context: Context): String {
        if (cachedUuid == null) {
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            cachedUuid = prefs.getString("app_uuid", null)
            if (cachedUuid == null) {
                cachedUuid = UUID.randomUUID().toString()
                prefs.edit { putString("app_uuid", cachedUuid) }
            }
        }
        return cachedUuid!!
    }
}