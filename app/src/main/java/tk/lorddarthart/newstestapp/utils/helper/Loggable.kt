package tk.lorddarthart.newstestapp.utils.helper

import android.util.Log
import tk.lorddarthart.newstestapp.BuildConfig
import tk.lorddarthart.newstestapp.utils.static_collections.Strings

interface Loggable

/** Lazy wrapper over [Log.v] */
inline fun <reified T: Loggable> T.logVerbose(
    lazyMessage: () -> String
) {
   Log.v(
       getClassSimpleName(),
       lazyMessage.invoke()
   )
}

/** Lazy wrapper over [Log.d] */
inline fun <reified T: Loggable> T.logDebug(
    onlyInDebugMode: Boolean = true,
    lazyMessage: () -> String
) {
    log(onlyInDebugMode) {
        Log.d(
            getClassSimpleName(),
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.i] */
inline fun <reified T : Loggable> T.logInfo(
    crossinline lazyMessage: () -> String
) {
    Log.i(
        getClassSimpleName(),
        lazyMessage.invoke()
    )
}

/** Lazy wrapper over [Log.w] */
inline fun <reified T: Loggable> T.logWarn(
    lazyMessage: () -> String
) {
    Log.w(
        getClassSimpleName(),
        lazyMessage.invoke()
    )
}

/** Lazy wrapper over [Log.e] */
inline fun <reified T: Loggable> T.logError(
    exception: Exception? = null,
    lazyMessage: () -> String
) {
    Log.e(
        getClassSimpleName(),
        lazyMessage.invoke(),
        exception
    )
}

/** Lazy wrapper over [Log.e] */
inline fun <reified T: Loggable> T.logError(
    lazyMessage: () -> String
) {
    Log.e(
        getClassSimpleName(),
        lazyMessage.invoke()
    )
}

inline fun log(onlyInDebugMode: Boolean, logger: () -> Unit) {
    when {
        onlyInDebugMode && BuildConfig.DEBUG -> logger()
        !onlyInDebugMode -> logger()
    }
}

/**
 * Utility that returns the name of the class from within it is invoked.
 */
inline fun <reified T : Loggable> T.getClassSimpleName(): String =
    if (T::class.java.simpleName.isNotBlank()) {
        T::class.java.simpleName
    } else {
        Strings.ANONYMOUS
    }
