package com.example.myapplication.utils

import com.example.myapplication.utils.ApplicationPreferences.getLog
import kotlin.Throws
import com.example.myapplication.view.MyApplication
import com.example.myapplication.R
import android.os.Build.VERSION
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.*
import android.widget.Toast
import android.net.ConnectivityManager
import android.content.Intent
import android.view.MotionEvent
import android.widget.EditText
import android.view.ViewGroup
import android.graphics.drawable.Drawable.ConstantState
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.multidex.BuildConfig
import android.content.Context
import androidx.core.app.NotificationCompat
import android.media.ExifInterface
import org.json.JSONObject
import org.json.JSONException
import org.json.JSONArray
import android.view.animation.Animation
import android.view.animation.AlphaAnimation
import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import android.os.*
import android.text.TextUtils
import android.text.style.TypefaceSpan
import android.text.TextPaint
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

@SuppressLint("StaticFieldLeak")
object Utilities {
    var logFileName = "logs"
    //var logFilePath = "data/data/.CriticalRiver/.Logs"
    var logFilePath = "data/data/myapplication/.Logs"
    var exception_email = true
    private const val PASSWORD_PATTERN =
        "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_?-]).{6,32})"

    //    public static boolean dummy_data = false;
    var copy_api_response_to_file = false
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"
                + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )
    var thisActivity: Activity? = null
    var res: Resources? = null
    var mypath: File? = null
    var path = ""
    const val PERMISSION_READ_WRITE_REQUEST_CODE = 9999
    @Throws(IOException::class)
    fun readBytesFromFile(file: File): ByteArray {
        val `is`: InputStream = FileInputStream(file)

        // Get the size of the file
        val length = file.length()

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Int.MAX_VALUE) {
            throw IOException(
                "Could not completely read file " + file.name + " as it is too long (" + length + " bytes, max supported "
                        + Int.MAX_VALUE + ")"
            )
        }

        // Create the byte array to hold the data
        val bytes = ByteArray(length.toInt())

        // Read in the bytes
        var offset = 0
        var numRead = 0
        while (offset < bytes.size && `is`.read(bytes, offset, bytes.size - offset)
                .also { numRead = it } >= 0
        ) {
            offset += numRead
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.size) {
            throw IOException("Could not completely read file " + file.name)
        }

        // Close the input stream and return bytes
        `is`.close()
        return bytes
    }

    fun print(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.i(res!!.getString(R.string.app_name), contentStr)
            } catch (e: Exception) {
                Log.i("CriticalRiver", contentStr)
            }
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
            } else {
                try {
                    if (ContextCompat.checkSelfPermission(
                            MyApplication.getInstance(),
                            permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                        } else {
                            generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                        }
                    } else {
                        generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun logI(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.i(res!!.getString(R.string.app_name), contentStr)
            } catch (e: Exception) {
                Log.i("CriticalRiver", contentStr)
            }
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
            } else {
                try {
                    if (ContextCompat.checkSelfPermission(
                            MyApplication.getInstance(),
                            permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                        } else {
                            generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                        }
                    } else {
                        generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun checkPermission(): Boolean {
        return if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val readCheck = ContextCompat.checkSelfPermission(
                MyApplication.getInstance(),
                permission.READ_EXTERNAL_STORAGE
            )
            val writeCheck = ContextCompat.checkSelfPermission(
                MyApplication.getInstance(),
                permission.WRITE_EXTERNAL_STORAGE
            )
            readCheck == PackageManager.PERMISSION_GRANTED && writeCheck == PackageManager.PERMISSION_GRANTED
        }
    }

    fun logErrors(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.i(res!!.getString(R.string.app_name), "Error in $contentStr")
            } catch (e: Exception) {
                Log.i("CriticalRiver", "Error in $contentStr")
            }
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
            } else {
                try {
                    if (ContextCompat.checkSelfPermission(
                            MyApplication.getInstance(),
                            permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                        } else {
                            generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                        }
                    } else {
                        generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun logV(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.v(res!!.getString(R.string.app_name), contentStr)
            } catch (e: Exception) {
                Log.v("CriticalRiver", contentStr)
            }
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
            } else {
                try {
                    if (ContextCompat.checkSelfPermission(
                            MyApplication.getInstance(),
                            permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                        } else {
                            generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                        }
                    } else {
                        generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun logD(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.d(res!!.getString(R.string.app_name), contentStr)
            } catch (e: Exception) {
                Log.d("CriticalRiver", contentStr)
            }
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
            } else {
                try {
                    if (ContextCompat.checkSelfPermission(
                            MyApplication.getInstance(),
                            permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                        } else {
                            generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                        }
                    } else {
                        generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun logEwithoutLogStore(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.e(res!!.getString(R.string.app_name), contentStr)
            } catch (e: Exception) {
                Log.e("CriticalRiver", contentStr)
            }
        }
    }

    fun logE(contentStr: String?) {
        var contentStr = contentStr
        try {
            res = MyApplication.getInstance().getResources()
        } catch (e: Exception) {
        }
        if (contentStr == null) contentStr = ""
        if (getLog(MyApplication.getInstance())) {
            try {
                Log.e(res!!.getString(R.string.app_name), contentStr)
            } catch (e: Exception) {
                Log.e("CriticalRiver", contentStr)
            }
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
            } else {
                try {
                    if (ContextCompat.checkSelfPermission(
                            MyApplication.getInstance(),
                            permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                        } else {
                            generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                        }
                    } else {
                        generateNotesOnSD(MyApplication.getInstance(), logFileName, contentStr)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    fun showErrorToast(screenObj: Context?, alertMsg: String?) {
        Toast.makeText(screenObj, alertMsg, Toast.LENGTH_SHORT).show()
    }

    fun showAlertWithFinish(thisActivity: Activity, alertMsg: String) {
        try {
            AlertDialog.Builder(thisActivity)
                .setTitle(thisActivity.resources.getString(R.string.alert_header))
                .setPositiveButton("OK") { dialog, whichButton -> thisActivity.finish() }
                .setMessage("" + alertMsg).create().show()
            return
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /* public static void showAlert(Context thisActivity, String alertMsg) {
        try {
            final Dialog dialog = getDialog(thisActivity);

            TextView text = (TextView) dialog.findViewById(R.id.lblHeader);
            text.setText(alertMsg);

            TextView dialogOK = (TextView) dialog.findViewById(R.id.lblOK);
            dialogOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            if(alertMsg.equals(thisActivity.getString(R.string.requestTimedOut))){
                if(haveInternetOnlyCheck(thisActivity) == true){
                    dialog.show();
                }
            }else {
                dialog.show();
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            Utilities.logErrors("Utilities showAlert : "+e.getLocalizedMessage());
        }
    }*/
    fun dateFormater(date: String?): String {
        var sdf: SimpleDateFormat
        sdf =
            SimpleDateFormat("dd MMM yyyy") //format of the date which you send as parameter(if the date is like 08-Aug-2016 then use dd-MMM-yyyy)
        var s = ""
        try {
            val dt = sdf.parse(date)
            sdf = SimpleDateFormat("MM/dd/yyyy")
            s = sdf.format(dt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return s
    }

    /*public static Dialog getDialog(Context thisActivity) {
        final Dialog dialog = new Dialog(thisActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }*/
    /* public static Dialog getInfoActionDialog(Context thisActivity) {

        final Dialog dialog = new Dialog(thisActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cutom_info_action_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }*/
    fun haveInternetOnlyCheck(thisActivity: Context): Boolean {
        var is_Internet = true
        val info =
            (thisActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (info == null || !info.isConnected) {
            return false
        }
        if (info != null) {
            if (info.isRoaming) {
                is_Internet = true
            }
        }
        return is_Internet
    }

    fun showToast(screenObj: Context?, alertMsg: String?) {
        Toast.makeText(screenObj, alertMsg, Toast.LENGTH_SHORT).show()
    }

    fun showActivity(sourceScreen: Activity, cls: Class<*>?) {
        val i = Intent(sourceScreen, cls)
        sourceScreen.startActivity(i)
        sourceScreen.finish()
    }

    fun showActivity(sourceScreen: Context, cls: Class<*>?) {
        val i = Intent(sourceScreen, cls)
        sourceScreen.startActivity(i)
    }

    // ***************** Email Validation ***********************//
    fun ValidEmail(email: String?): Boolean {
        val required = true
        if (email == null) {
            return if (required) {
                false
            } else true
        }
        if (email.length == 0) {
            return if (required) {
                false
            } else true
        }
        if (!NoSpecailChar(email)) { // check to make sure all characters are
            // valid
            return false
        } else if (!allValidCharsNew(email)) { // check to make sure all
            // characters are valid
            return false
        } else if (email.lastIndexOf('.'.code.toChar()) <= email.indexOf("@")) { // last
            // dot
            // must
            // be
            // after
            // the
            // @
            return false
        } else if (email.indexOf("@") == email.length) { // @ must not be the
            // last character
            return false
        } else if (email.indexOf("..") >= 0) { // two periods in a row is not
            // valid
            return false
        } else if (email.lastIndexOf('.') >= email.length - 2) { // . must not
            // be the
            // last
            // character
            return false
        }
        val After_At_Str: String
        var ExtString = ""
        try {
            After_At_Str = email.substring(email.indexOf("@"))
            ExtString = After_At_Str.substring(After_At_Str.indexOf("."))
        } catch (e: Exception) {
            return false
        }
        return if (!ValidEmailExt(ExtString)) { // check to make sure all characters
            // are valid
            false
        } else true
    }

    fun NoSpecailChar(email: String): Boolean {
        var parsed = true
        val validchars = "0123456789abcdefghijklmnopqrstuvwxyz"
        if (email.length < 1) {
            parsed = false
        } else {
            val letter = email[0].lowercaseChar()
            if (validchars.indexOf(letter) == -1) parsed = false
        }
        return parsed
    }

    fun ValidEmailExt(email: String): Boolean {
        var parsed = true
        val validchars = "abcdefghijklmnopqrstuvwxyz.-1234567890"
        if (email.length < 1) {
            parsed = false
        } else for (i in 0 until email.length) {
            val letter = email[i].lowercaseChar()
            if (validchars.indexOf(letter) != -1) continue
            parsed = false
            break
        }
        return parsed
    }

    fun allValidCharsNew(email: String): Boolean {
        var parsed = true
        val validchars = "abcdefghijklmnopqrstuvwxyz0123456789@.-_"
        for (i in 0 until email.length) {
            val letter = email[i].lowercaseChar()
            if (validchars.indexOf(letter) != -1) continue
            parsed = false
            break
        }
        return parsed
    }

    fun rotate(bitmap: Bitmap, degree: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val mtx = Matrix()
        mtx.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true)
    }

    fun WriteJsonResponse(context: Context?, data: String?, DEST_PATH: String, filename: String) {
        var fOut: FileOutputStream? = null
        var osw: OutputStreamWriter? = null
        try {
            if (!File(DEST_PATH).exists()) {
                File(DEST_PATH).mkdirs()
            }
            if (File(DEST_PATH + filename).exists()) {
                File(DEST_PATH + filename).delete()
            }
            fOut = FileOutputStream(DEST_PATH + filename, true)
            osw = OutputStreamWriter(fOut)
            osw.write(data)
            osw.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                osw!!.close()
                fOut!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun gettimezone(activity: Activity?): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        val timezone = TimeZone.getDefault().id
        Log.d("Time zone", "timezone=$timezone")
        return timezone
    }
    /**
     * It will return whether the code is running on emulator or device
     *
     * @param inContext
     * @return
     */
    /*public static boolean isRunningOnEmulator(final Context inContext) {

        final TelephonyManager theTelephonyManager = (TelephonyManager) inContext.getSystemService(Context.TELEPHONY_SERVICE);
        final boolean hasEmulatorImei = theTelephonyManager.getDeviceId().equals("000000000000000");
        final boolean hasEmulatorModelName = Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK");

        return hasEmulatorImei || hasEmulatorModelName;
    }*/
    /**
     * Set click effect on views (buttons) here
     */
    fun clickEffect(v: View, color: Int) {
        v.setOnTouchListener { v, event ->
            res = MyApplication.getInstance().getResources()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
                else -> {}
            }
            false
        }
    }

    fun setupUI(view: View, thisActivity: Activity) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard(thisActivity)
                false
            }
        }
        // If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView, thisActivity)
            }
        }
    }

    fun hideSoftKeyboard(thisActivity: Activity) {
        val inputMethodManager =
            thisActivity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (thisActivity.currentFocus != null) inputMethodManager.hideSoftInputFromWindow(
            thisActivity.currentFocus!!.applicationWindowToken, 0
        )
    }

    fun openSoftKeyboard(thisActivity: Activity) {
        val inputMethodManager =
            thisActivity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (thisActivity.currentFocus != null) inputMethodManager.toggleSoftInputFromWindow(
            thisActivity.currentFocus!!.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0
        )
    }

    fun getCurrentFragment(thisActivity: Activity, tag: String?): Boolean {
        val myFragment = thisActivity.fragmentManager.findFragmentByTag(tag)
        return if (myFragment != null && myFragment.isVisible) {
            true
        } else false
    }

    fun getActiveFragment(thisActivity: Activity): Fragment? {
        if (thisActivity.fragmentManager.backStackEntryCount == 0) {
            return null
        }
        val tag =
            thisActivity.fragmentManager.getBackStackEntryAt(thisActivity.fragmentManager.backStackEntryCount - 1).name
        return thisActivity.fragmentManager.findFragmentByTag(tag) as Fragment
    }

    fun getCurrentFragment(thisActivity: Activity): Fragment? {
        if (thisActivity.fragmentManager.backStackEntryCount == 0) {
            return null
        }
        val tag =
            thisActivity.fragmentManager.getBackStackEntryAt(thisActivity.fragmentManager.backStackEntryCount - 1).name
        return thisActivity.fragmentManager.findFragmentByTag(tag)
    }

    private fun getAllChildren(v: View): ArrayList<View> {
        if (v !is ViewGroup) {
            val viewArrayList = ArrayList<View>()
            viewArrayList.add(v)
            return viewArrayList
        }
        val result = ArrayList<View>()
        val viewGroup = v
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            val viewArrayList = ArrayList<View>()
            viewArrayList.add(v)
            viewArrayList.addAll(getAllChildren(child))
            result.addAll(viewArrayList)
        }
        return result
    }

    fun checkImageResource(ctx: Context?, imageView: ImageView?, imageResource: Int): Boolean {
        var result = false
        if (ctx != null && imageView != null && imageView.drawable != null) {
            val constantState: ConstantState?
            constantState = if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ctx.resources.getDrawable(imageResource, ctx.theme).constantState
            } else {
                ctx.resources.getDrawable(imageResource).constantState
            }
            if (imageView.drawable.constantState === constantState) {
                result = true
            }
        }
        return result
    }

    fun DownloadFromUrl(
        thisActivity: Activity,
        DownloadUrl: String?,
        fileName: String,
        enumer: Int,
        mHandler: Handler
    ): String {
        val exe = Executors.newFixedThreadPool(1)
        exe.submit {
            try {
                var file: File? = null
                val root = File(thisActivity.applicationContext.externalCacheDir, "needsdo")
                val dir = File(root.absolutePath)
                if (dir.exists() == false) {
                    dir.mkdirs()
                }
                val url = URL(DownloadUrl) //you can write here any link
                file = File(dir, fileName)
                val startTime = System.currentTimeMillis()
                Log.d("DownloadManager", "download begining")
                Log.d("DownloadManager", "download url:$url")
                Log.d("DownloadManager", "downloaded file name:$fileName")

                /* Open a connection to that URL. */
                val ucon = url.openConnection()

                /*
                * Define InputStreams to read from the URLConnection.
                */
                val `is` = ucon.getInputStream()
                val bis = BufferedInputStream(`is`)

                /*
                * Read bytes to the Buffer until there is nothing more to read(-1).
                */
                val buffer = ByteArrayOutputStream()
                //We create an array of bytes
                val data = ByteArray(5000)
                var current = 0
                while (bis.read(data, 0, data.size).also { current = it } != -1) {
                    buffer.write(data, 0, current)
                }

                /* Convert the Bytes read to a String. */
                val fos = FileOutputStream(file)
                fos.write(buffer.toByteArray())
                fos.flush()
                fos.close()
                Log.d(
                    "DownloadManager",
                    "download ready in" + (System.currentTimeMillis() - startTime) / 1000 + " sec"
                )
                val b = Bundle()
                b.putString("image_path", file.absolutePath)
                val msg = Message()
                msg.obj = b
                //                    msg.what = EnumHandler.BUZZ_EDIT_IMAGE_DOWNLOAD_COMPLETE;
                msg.what = enumer
                mHandler.sendMessage(msg)
                path = file.absolutePath
            } catch (e: IOException) {
                Log.d("DownloadManager", "Error: $e")
            }
        }
        return path
    }

    fun getAlbumStorageDir(albumName: String?): File {
        // Get the directory for the user's public pictures directory.
        val file = File(Environment.getExternalStorageDirectory(), albumName)
        logI("Directory path: " + file.absolutePath)
        if (!file.mkdirs()) {
            logD("Directory not created")
        }
        return file
    }

    fun getColor(context: Context, id: Int): Int {
        val version = VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun getDrawable(context: Context, id: Int): Drawable? {
        val version = VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getDrawable(context, id)
        } else {
            context.resources.getDrawable(id)
        }
    }

    fun getColorWrapper(context: Context, id: Int): Int {
        return if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun getUsableScreenHeight(thisActivity: Activity, view: View): Int {
        return if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val metrics = DisplayMetrics()
            val windowManager =
                thisActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(metrics)
            metrics.heightPixels
        } else {
            view.rootView.height
        }
    }

    fun getConvertedDate(time: String): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone //get your local time zone.
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        sdf.timeZone = tz //set time zone.
        val localTime = sdf.format(Date(time.toLong() * 1000))
        var date: Date? = Date()
        try {
            date = sdf.parse(localTime) //get local date
            return sdf.format(Date(time.toLong() * 1000))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getConvertedDateForChat(time: String): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone //get your local time zone.
        val sdf = SimpleDateFormat("MMMM dd, yyyy")
        sdf.timeZone = tz //set time zone.
        val localTime = sdf.format(Date(time.toLong() * 1000))
        var date: Date? = Date()
        try {
            date = sdf.parse(localTime) //get local date
            return sdf.format(Date(time.toLong()))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getConvertedDateForChatListing(time: String): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone //get your local time zone.
        val sdf = SimpleDateFormat("MM/dd/yy")
        sdf.timeZone = tz //set time zone.
        val localTime = sdf.format(Date(time.toLong() * 1000))
        var date: Date? = Date()
        try {
            date = sdf.parse(localTime) //get local date
            return sdf.format(Date(time.toLong()))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getConvertedTime(time: String): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone //get your local time zone.
        val sdf = SimpleDateFormat("hh:mm a")
        sdf.timeZone = tz //set time zone.
        val localTime = sdf.format(Date(time.toLong() * 1000))
        var date: Date? = Date()
        try {
            date = sdf.parse(localTime) //get local date
            return sdf.format(Date(time.toLong()))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getConvertedNormalDate(value: String): String {
        val createdDate = Date(value.toLong() * 1000L)
        logV("timezone default value== $value")
        val dateFormatter = SimpleDateFormat("dd MMM yyyy")
        dateFormatter.timeZone = TimeZone.getDefault()
        return dateFormatter.format(createdDate)
    }

    fun showLongToast(screenObj: Context?, alertMsg: String?) {
        Toast.makeText(screenObj, alertMsg, Toast.LENGTH_LONG).show()
    }

    fun isYesterday(date: Long): Boolean {
        val now = Calendar.getInstance()
        val cdate = Calendar.getInstance()
        cdate.timeInMillis = date
        //        Utilities.logV("cdate time in millis"+cdate.getTimeInMillis());
//        Utilities.logV("cdate time date"+cdate.get(Calendar.DATE));
        now.add(Calendar.DATE, -1)
        //        Utilities.logV("cdate time date now"+now.get(Calendar.DATE));
        return now[Calendar.YEAR] == cdate[Calendar.YEAR] && now[Calendar.MONTH] == cdate[Calendar.MONTH] && now[Calendar.DATE] == cdate[Calendar.DATE]
    }

    fun showToastTest(screenObj: Context?, alertMsg: String?) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(screenObj, alertMsg, Toast.LENGTH_SHORT).show()
        }
    }

    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = activityManager.getRunningServices(Int.MAX_VALUE)
        for (runningServiceInfo in services) {
//            Utilities.logV(String.format("Service:%s", runningServiceInfo.service.getClassName()));
            if (runningServiceInfo.service.className == serviceClass.name) {
                return true
            }
        }
        return false
    }

    /**
     * Compares two version strings.
     *
     *
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @param old_ver a string of ordinal numbers separated by decimal points.
     * @param new_ver a string of ordinal numbers separated by decimal points.
     * @return The result is a negative integer if old version is _numerically_ less than new version.
     * The result is a positive integer if old version is _numerically_ greater than new version.
     * The result is zero if the versions are _numerically_ equal.
     * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
     */
    fun versionStringCompare(old_ver: String, new_ver: String): Int {
        val vals1 = old_ver.split("\\.").toTypedArray()
        val vals2 = new_ver.split("\\.").toTypedArray()
        var i = 0
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.size && i < vals2.size && vals1[i] == vals2[i]) {
            i++
        }
        // compare first non-equal ordinal number
        if (i < vals1.size && i < vals2.size) {
            val diff = Integer.valueOf(vals1[i]).compareTo(
                Integer.valueOf(
                    vals2[i]
                )
            )
            return Integer.signum(diff)
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.size - vals2.size)
    }

    fun getCircleBitmap(bitmap: Bitmap?): Bitmap {
        val output = Bitmap.createBitmap(bitmap!!.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = Color.RED
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        bitmap.recycle()
        return output
    }

    fun getFileNames(thisActivity: Activity): ArrayList<String> {
        val list = ArrayList<String>()
        var bitmap = BitmapFactory.decodeResource(thisActivity.resources, R.mipmap.ic_launcher)
        val height = res!!.getDimension(R.dimen._64dp).toInt()
        val width = res!!.getDimension(R.dimen._64dp).toInt()
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        }
        bitmap = getCircleBitmap(bitmap)
        val mNotifyManager =
            thisActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(thisActivity)
        mBuilder.setContentTitle("Loading test")
            .setContentText("Loading")
            .setLargeIcon(bitmap)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
        try {
            val path = Environment.getExternalStorageDirectory().absolutePath
            val folder = File(path)
            val listOfFiles = folder.listFiles()
            for (i in listOfFiles.indices) {
                if (listOfFiles[i].isFile) {
                    val path1 = folder.toString() + "/" + listOfFiles[i].name
                    logE("File $path1")
                    //                    ExifInterface exif = new ExifInterface(folder+"/"+listOfFiles[i].getName());
//                    Utilities.logE(getExif(exif));
                    logE(thisActivity.contentResolver.getType(Uri.fromFile(File(path1))))
                    logE(getConvertedDate(File(path1).lastModified().toString() + ""))
                    logE(File(path1).freeSpace.toString() + "")
                    logE(File(path1).parent + "")
                    logE(File(path1).path + "")
                    logE(File(path1).totalSpace.toString() + "")
                    logE(File(path1).usableSpace.toString() + "")
                    logE(File(path1).isHidden.toString() + "")
                    logE(File(path1).canExecute().toString() + "")
                } else if (listOfFiles[i].isDirectory) {
                    logE("Directory " + listOfFiles[i].name)
                }
                Thread.sleep(250)
                mBuilder.setContentText("Loading " + i + " of " + listOfFiles.size)
                mBuilder.setProgress(100, i, false)
                mNotifyManager.notify(1, mBuilder.build())
            }
            mBuilder.setContentText("Loading complete")
            mBuilder.setProgress(0, 0, false)
            mBuilder.setOngoing(false)
            mNotifyManager.notify(1, mBuilder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun getTagString(tag: String, exif: ExifInterface): String {
        return """$tag : ${exif.getAttribute(tag)}
"""
    }

    fun deleteFile(path: String?): Boolean {
        return try {
            if (File(path).exists()) {
                File(path).delete()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun readableFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / Math.pow(
                1024.0,
                digitGroups.toDouble()
            )
        ) + " " + units[digitGroups]
    }

    fun trimToLimit(name: String, limit: Int): String {
        var name = name
        return if (name.length > limit) {
            name = name.substring(0, limit - 2) + ".."
            name
        } else {
            name
        }
    }

    fun isMyServiceRunning(thisContext: Context, serviceClass: Class<*>): Boolean {
        val manager = thisContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun getDate(value: String): String {
        val createdDate = Date(value.toLong() * 1000L)
        logV("timezone default value== $value")
        val dateFormatter = SimpleDateFormat("dd MMM yyyy")
        dateFormatter.timeZone = TimeZone.getDefault()
        return dateFormatter.format(createdDate)
    }

    fun getAssetFileAsString(thisContext: Context, asset_file_location: String?): String {
        val buf = StringBuilder()
        try {
            val json = thisContext.assets.open(asset_file_location!!)
            val `in` = BufferedReader(InputStreamReader(json, "UTF-8"))
            var str: String?
            while (`in`.readLine().also { str = it } != null) {
                buf.append(str)
            }
            `in`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return buf.toString()
        }
    }

    @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream?): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String? = null
        while (reader.readLine().also { line = it } != null) {
            sb.append(line).append("\n")
        }
        reader.close()
        return sb.toString()
    }

    @Throws(Exception::class)
    fun getStringFromFile(filePath: String?): String {
        val fl = File(filePath)
        val fin = FileInputStream(fl)
        val ret = convertStreamToString(fin)
        //Make sure you close all streams.
        fin.close()
        return ret
    }

    fun throughException() {
        if (BuildConfig.DEBUG) {
            val i = 1 / 0
            logE("" + i)
        }
    }

    fun isJSONValid(test: String?): Boolean {
        try {
            JSONObject(test)
        } catch (ex: JSONException) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                JSONArray(test)
            } catch (ex1: JSONException) {
                return false
            }
        }
        return true
    }

    fun blinkView(v: View) {
        val anim: Animation = AlphaAnimation(0.2f, 1.0f)
        anim.duration = 1000 //You can manage the blinking time with this parameter
        anim.startOffset = 50
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        v.startAnimation(anim)
    }

    fun blinkViewStop(v: View) {
        try {
            v.clearAnimation()
            v.alpha = 1.0f
        } catch (e: Exception) {
        }
    }

    fun checkForTokenInvalidMsg(msg: String): String {
        return try {
            if (msg == "token invalid") {
                MyApplication.getInstance().getResources().getString(R.string.application_error)
            } else if (msg == "request timeout") {
                MyApplication.getInstance().getResources().getString(R.string.requestTimedOut)
            } else {
                msg
            }
        } catch (ex: Exception) {
            msg
        }
    }

    fun getAppVersion(thisActivity: Activity): String {
        var app_version = ""
        try {
            val pInfo = thisActivity.packageManager.getPackageInfo(thisActivity.packageName, 0)
            app_version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return app_version
    }

    fun generateNotesOnSD(context: Context, sFileName: String?, sBody: String) {
        try {
            var root: File? = null
            root = if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(context.filesDir, logFileName)
            } else {
                File(Environment.getExternalStorageDirectory(), logFilePath)
            }
            if (!root.exists()) {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        root.createNewFile()
                        logI("generateNotesOnSD File has been created root.getAbsolutePath() " + root.absolutePath)
                    } catch (e: IOException) {
                        logI("generateNotesOnSD File has been creation failed root.getAbsolutePath() " + root.absolutePath)
                    }
                } else {
                    root.mkdirs()
                }
            }
            var gpxfile: File? = null
            gpxfile = if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(root.toString())
            } else {
                File(root, logFileName)
            }
            try {
                val filesize = FileSize(gpxfile) / 1024
                if (filesize >= 5) {  // Clearing the data int the file after every 5 MB
                    clearLogFileData(context, logFileName)
                } /*else{
                    clearLogfileonDailyBasis(logFileName);
                }*/
            } catch (e: Exception) {
            }
            var dateTime = ""
            try {
                val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                dateTime = sdf.format(Date())
            } catch (e: Exception) {
            }
            try {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    /*FileWriter fileWriter = new FileWriter(gpxfile.getAbsolutePath()+File.separator+""+logFileName, true);
                    BufferedWriter buf = new BufferedWriter(fileWriter);
                    buf.append(dateTime+" : "+sBody);
                    buf.newLine();
                    buf.close();*/
                    val fileWriter = FileWriter(gpxfile, true)
                    val buf = BufferedWriter(fileWriter)
                    buf.append("$dateTime : $sBody")
                    buf.newLine()
                    buf.close()
                } else {
                    val buf = BufferedWriter(FileWriter(gpxfile, true))
                    buf.append("$dateTime : $sBody")
                    buf.newLine()
                    buf.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
        }
    }

    fun FileSize(fileName: File): Long {
        return try {
            val filesize = fileName.length()
            filesize / 1024
        } catch (e: Exception) {
            0
        }
    }

    fun clearLogFileData(context: Context, sFileName: String?) {
        try {
            var root: File? = null
            root = if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(context.filesDir, logFileName)
            } else {
                File(Environment.getExternalStorageDirectory(), logFilePath)
            }
            if (!root.exists()) {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        root.createNewFile()
                        logI("clearLogFileData File has been created root.getAbsolutePath() " + root.absolutePath)
                    } catch (e: IOException) {
                        logI("clearLogFileData File has been creation failed root.getAbsolutePath() " + root.absolutePath)
                    }
                } else {
                    root.mkdirs()
                }
            }
            var gpxfile: File? = null
            gpxfile = if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(root.toString())
            } else {
                File(root, logFileName)
            }
            val writer = PrintWriter(gpxfile)
            writer.print("")
            writer.close()
        } catch (e: Exception) {
        }
    }

    fun clearLogfileonDailyBasis(context: Context, sFileName: String?) {
        var root: File? = null
        root = if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            File(context.filesDir, logFileName)
        } else {
            File(Environment.getExternalStorageDirectory(), logFilePath)
        }
        if (root.exists()) {
            var gpxfile: File? = null
            gpxfile = if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(root.toString())
            } else {
                File(root, logFileName)
            }
            //Read text from file
            val text = StringBuilder()
            try {
                val br = BufferedReader(FileReader(gpxfile))
                val line: String
                if (br != null) {
                    if (br.readLine().toString() == null) return else if (br.readLine()
                            .toString() != null
                    ) {
                        line = br.readLine().substring(
                            0,
                            19
                        ) // Fetching the "dd-MM-yyyy HH:mm:ss" from the first line of log file.
                        try {
                            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                            val dateTime = sdf.format(Date())
                            val firstDate = sdf.parse(line.trim { it <= ' ' })
                            val secondDate = sdf.parse(dateTime)
                            val diffInMillies = Math.abs(secondDate.time - firstDate.time)
                            val diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)
                            logI("old log Duration in Hours with Current Date  : $diff")

//                    if(!dateTime.trim().equals(line.trim())){
                            if (diff > 48) { // Clearing the log when the current and old date is more than two days(48 hours).
                                val writer = PrintWriter(gpxfile)
                                writer.print("")
                                writer.close()
                                logI("old log Duration in Hours with Current Date  : $diff")
                                logI("old log Date : $line  New log date : $dateTime")
                            }
                        } catch (e: Exception) {
                        }
                        br.close()
                    }
                }
            } catch (e: IOException) {
                Log.i("ClearLogfileonDaily : ", "ClearLogfileonDaily : " + e.localizedMessage)
            }
        }
    }

    /*Password pattern Validation*/
    fun isPasswordVaild(password: String): Boolean {
        var retVal = false
        retVal = if (!TextUtils.isEmpty(password) && !password.isEmpty()) {
            val pattern =
                Pattern.compile(PASSWORD_PATTERN)
            val matcher = pattern.matcher(password)
            matcher.matches()
        } else {
            false
        }
        return retVal
    }

    /*Convert from time stamp to long ex : 2017-06-02T09:27:42.271Z to Long */
    fun getTimeInLong(timeStamp: String?): Long {
        var retVal: Long = -1
        var date: Date? = null
        try {
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            date = sourceFormat.parse(timeStamp) // => Date is in UTC now
            if (date != null) retVal = date.time
            val cal = Calendar.getInstance()
            cal.time = date
            retVal = cal.timeInMillis
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return retVal
    }

    /*compare two timestamps like current and another time stamp */
    fun getDiffTime(inTime: Long): String {
        var timeStr = ""
        var currentTime: Long = -1
        val now = Date()
        currentTime = now.time
        val diff = currentTime - inTime
        val diffDays = (diff / (24 * 60 * 60 * 1000)).toInt()
        // System.out.println("difference between days: " + diffDays);
        val diffhours = (diff / (60 * 60 * 1000)).toInt()
        //  System.out.println("difference between hours: " + crunchifyFormatter.format(diffhours));
        val diffmin = (diff / (60 * 1000)).toInt()
        //   System.out.println("difference between minutues: " + crunchifyFormatter.format(diffmin));
        val diffsec = (diff / 1000).toInt()
        //   System.out.println("difference between seconds: " + crunchifyFormatter.format(diffsec));
        if (diffDays > 0) {
            timeStr = if (diffDays == 1) {
                "$diffDays day ago"
            } else {
                "$diffDays days ago"
            }
        } else if (diffDays == 0 && diffhours > 0) {
            timeStr = if (diffhours == 1) {
                "$diffhours hour ago"
            } else {
                "$diffhours hours ago"
            }
        } else if (diffDays == 0 && diffhours == 0 && diffmin > 0) {
            timeStr = if (diffmin == 1) {
                "$diffmin min ago"
            } else {
                "$diffmin mins ago"
            }
        } else if (diffDays == 0 && diffhours == 0 && diffmin == 0 && diffsec > 0) {
            timeStr = "$diffsec sec ago"
        } else if (diffDays == 0 && diffhours == 0 && diffmin == 0 && diffsec == 0) {
            timeStr = "Updated Now"
        }
        return timeStr
    }

    fun getTimeInHoursMinutes(duration: Int): String {
        return if (duration == 0) {
            "0m"
        } else try {
            val seconds = duration % 60 // seconds
            var hours = duration / 60
            val minutes = hours % 60 // minutes
            hours = hours / 60 // seconds
            if (hours > 0 && minutes > 0 && seconds > 0) {
                hours.toString() + "h " + minutes + "m " + seconds + "s"
            } else if (hours > 0 && minutes > 0) {
                hours.toString() + "h " + minutes + "m"
            } else if (minutes > 0 && seconds > 0) {
                minutes.toString() + "m " + seconds + "s"
            } else if (seconds > 0) {
                minutes.toString() + "m " + seconds + "s"
            } else {
                minutes.toString() + "m"
            }
        } catch (e: Exception) {
            ""
        }
    }

    fun CapitalizeFirstChar(message: String): String {
        var message = message
        message = message.lowercase(Locale.getDefault())
        message = message.replace("_", " ")
        try {
            // stores each characters to a char array
            val charArray = message.toCharArray()
            var foundSpace = true
            for (i in charArray.indices) {

                // if the array element is a letter
                if (Character.isLetter(charArray[i])) {

                    // check space is present before the letter
                    if (foundSpace) {

                        // change the letter into uppercase
                        charArray[i] = charArray[i].uppercaseChar()
                        foundSpace = false
                    }
                } else {
                    // if the new character is not character
                    foundSpace = true
                }
            }

            // convert the char array to the string
            message = String(charArray)
        } catch (e: Exception) {
        }
        return message
    }

    fun dataConverstion(data: String?): String {
        var data = data
        if (data!!.isEmpty() || data == null) {
            data = "0"
        }
        val size_bytes = data.toDouble()
        val size_kb = size_bytes / 1024
        val size_mb = size_kb / 1024
        val size_gb = size_mb / 1024
        return String.format("%.2f", size_gb)
    }

    class CustomTypefaceSpan(family: String?, private val newType: Typeface) :
        TypefaceSpan(family) {
        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeFace(ds, newType)
        }

        override fun updateMeasureState(paint: TextPaint) {
            applyCustomTypeFace(paint, newType)
        }

        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old = paint.typeface
            oldStyle = old?.style ?: 0
            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }
            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }
            paint.typeface = tf
        }
    }
}