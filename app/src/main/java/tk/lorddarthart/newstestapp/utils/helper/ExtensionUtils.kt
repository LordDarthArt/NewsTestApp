package tk.lorddarthart.newstestapp.utils.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.View.*

/**
 * Function that sets [View]'s visibility according to the boolean parameter value.
 */
fun View.setVisibility(visible: Boolean) {
    if (visible) {
        this.visibility = VISIBLE
    } else {
        this.visibility = GONE
    }
}

/**
 * Function that sets [View]'s visibility to [VISIBLE].
 */
fun View.setVisible() {
    visibility = VISIBLE
}

/**
 * Function that sets [View]'s visibility to [INVISIBLE].
 */
fun View.setInvisible() {
    visibility = INVISIBLE
}

/**
 * Function that sets [View]'s visibility to [GONE].
 */
fun View.setGone() {
    visibility = GONE
}


/**
 * Function that checks if [View]'s visibility is [VISIBLE].
 */
fun View.isVisible(): Boolean {
    return this.visibility == VISIBLE
}


/**
 * Function that checks if [View]'s visibility is [GONE].
 */
fun View.isGone(): Boolean {
    return this.visibility == GONE
}


/**
 * Function that checks if [View]'s visibility is [INVISIBLE].
 */
fun View.isInvisible(): Boolean {
    return this.visibility == INVISIBLE
}

/**
 * Function that makes view appear with fade-in animation.
 */
fun View.fadeShow() {
    val longAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)

    alpha = 0f
    visibility = VISIBLE

    animate()
        .alpha(1f)
        .setDuration(longAnimationDuration.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@fadeShow.visibility = VISIBLE
            }
        })
}

/**
 * Function that makes physical part of view disappear with fade-out animation.
 */
fun View.fadeHide() {
    val longAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)

    alpha = 0f
    visibility = VISIBLE

    animate()
        .alpha(0f)
        .setDuration(longAnimationDuration.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@fadeHide.visibility = GONE
            }
        })
}

