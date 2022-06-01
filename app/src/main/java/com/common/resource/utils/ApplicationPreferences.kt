package com.common.resource.utils

import android.content.Context

object ApplicationPreferences {
    private const val KEY = "key"
    private const val LOG = "logger "
    private const val TOKEN = "apitoken"
    private const val MOBILE_DEVICE_TOKEN = "MOBILE_DEVICE_TOKEN"
    private const val PROFILE_PHOTO = "profilephoto"
    private const val ACCOUNT_TYPE = "acc_type"
    private const val USER_ID = "user_id"
    private const val USER_NAME = "last_name"
    private const val CURRENT_VERSION = "CURRENT_VERSION"
    private const val CURRENT_ACTIVITY = "CURRENT_ACTIVITY"
    private const val DEVICE_ID = "deviceid"
    private const val USER_EMAIL = "user_email"
    private const val PASSWORD = "password"
    private const val SOFTWARE_VERSION = "SOFTWARE_VERSION"
    private const val MOBILE_NUMBER = "mobile_number"
    fun setLog(mContext: Context, log: Boolean): Boolean {
        val editor = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
        editor.putBoolean(LOG, log)
        return editor.commit()
    }

    @JvmStatic
    fun getLog(mContext: Context): Boolean {
        return try {
            val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
            user_pref.getBoolean(LOG, false)
        } catch (e: Exception) {
            true
        }
    }

    /**
     * set and get current activity [ Example :- true/false ]
     */
    fun setCurrentActivity(mContext: Context, activity: String?): Boolean {
        val editor = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
        editor.putString(CURRENT_ACTIVITY, activity)
        return editor.commit()
    }

    fun getCurrentActivity(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(CURRENT_ACTIVITY, "")
    }

    /**
     * set and get user id of login user
     */
    fun setUserId(mContext: Context, id: String?): Boolean {
        return if (id != null && id != "" && id != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(USER_ID, id)
            editor.commit()
        } else {
            false
        }
    }

    fun getUserId(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(USER_ID, "")
    }

    /**
     * set and get user name of login user
     */
    fun setUserName(mContext: Context, name: String?): Boolean {
        return if (name != null && name != "" && name != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(USER_NAME, name)
            editor.commit()
        } else {
            false
        }
    }

    fun getUserName(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(USER_NAME, "")
    }

    /**
     * set and get profile photo of login user
     */
    fun setProfilePhoto(mContext: Context, name: String?): Boolean {
        return if (name != null && name != "" && name != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(PROFILE_PHOTO, name)
            editor.commit()
        } else {
            false
        }
    }

    fun getProfilePhoto(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(PROFILE_PHOTO, "")
    }

    /**
     * set and get token of login user
     */
    fun setToken(mContext: Context, name: String?): Boolean {
        return if (name != null && name != "" && name != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(TOKEN, name)
            editor.commit()
        } else {
            false
        }
    }

    fun getToken(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(TOKEN, "")
    }

    /**
     * set and get MOBILE_DEVICE_TOKEN of login user
     */
    fun setMobileDeviceToken(mContext: Context, name: String?): Boolean {
        return if (name != null && name != "" && name != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(MOBILE_DEVICE_TOKEN, name)
            editor.commit()
        } else {
            false
        }
    }

    fun getMobileDeviceToken(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(MOBILE_DEVICE_TOKEN, "")
    }

    /**
     * set and get Account type of login user (individual, business, entity)
     */
    fun setAccountType(mContext: Context, acc_type: String?): Boolean {
        return if (acc_type != null && acc_type != "" && acc_type != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(ACCOUNT_TYPE, acc_type)
            editor.commit()
        } else {
            false
        }
    }

    fun getAccountType(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(ACCOUNT_TYPE, "")
    }

    /**
     * Store device id
     *
     * @return
     */
    fun setDeviceID(mActivity: Context, strUserID: String?): Boolean {
        return if (strUserID != null && strUserID != "" && strUserID != "null") {
            val editor = mActivity.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(DEVICE_ID, strUserID)
            editor.commit()
        } else {
            false
        }
    }

    fun getDeviceID(mActivity: Context): String? {
        val user_pref = mActivity.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(DEVICE_ID, "")
    }

    /**
     * Store software version
     *
     * @return
     */
    fun setSoftwareVersion(mActivity: Context, strUserID: String?): Boolean {
        return if (strUserID != null && strUserID != "" && strUserID != "null") {
            val editor = mActivity.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(SOFTWARE_VERSION, strUserID)
            editor.commit()
        } else {
            false
        }
    }

    fun getSoftwareVersion(mActivity: Context): String? {
        val user_pref = mActivity.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(SOFTWARE_VERSION, "--")
    }

    fun setCurrentVersion(mContext: Context, currentversion: String?): Boolean {
        return if (currentversion != null && currentversion != "" && currentversion != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(CURRENT_VERSION, currentversion)
            editor.commit()
        } else {
            false
        }
    }

    fun getCurrentVersion(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(CURRENT_VERSION, "1.4.1")
    }

    fun setUserEmail(mContext: Context, userEmail: String?): Boolean {
        return if (userEmail != null && userEmail != "" && userEmail != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(USER_EMAIL, userEmail) // value to store
            editor.commit()
        } else {
            false
        }
    }

    fun getUserEmail(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences("key", Context.MODE_PRIVATE)
        return user_pref.getString(USER_EMAIL, "")
    }

    fun setUserPassword(mContext: Context, userPassword: String?): Boolean {
        return if (userPassword != null && userPassword != "" && userPassword != "null") {
            val editor = mContext.getSharedPreferences(
                KEY,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(PASSWORD, userPassword) // value to store
            editor.commit()
        } else {
            false
        }
    }

    fun getUserPassword(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences("key", Context.MODE_PRIVATE)
        return user_pref.getString(PASSWORD, "")
    }

    fun setMobileNumber(mContext: Context, status: String?): Boolean {
        val editor = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
        editor.putString(MOBILE_NUMBER, status)
        return editor.commit()
    }

    fun getMobileNumber(mContext: Context): String? {
        val user_pref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return user_pref.getString(MOBILE_NUMBER, "")
    }
}