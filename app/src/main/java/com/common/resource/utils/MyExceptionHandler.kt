package com.common.resource.utils

import android.content.Context
import android.content.Intent
import android.os.Process
import java.io.PrintWriter
import java.io.StringWriter

class MyExceptionHandler(private val myContext: Context, private val myActivityClass: Class<*>) :
    Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, e: Throwable) {
        val stackTrace = StringWriter()
        e.printStackTrace(PrintWriter(stackTrace))
        System.err.println(stackTrace)
        // You can use LogCat too
        val message = stackTrace.toString()
        Utilities.logV(
            """
    needs.do uncaught exception: LAST_URL: $message
    
    $LAST_URL
    """.trimIndent()
        )
        // You can use LogCat too
        val intent = Intent(myContext, myActivityClass)
        val s = stackTrace.toString()
        // you can use this String to know what caused the exception and in
        // which Activity
        intent.putExtra("uncaughtException", "Exception is: $stackTrace")
        intent.putExtra("stacktrace", s)
        myContext.startActivity(intent)


        // for restarting the Activity
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    companion object {
        var LAST_URL = ""
    }
}