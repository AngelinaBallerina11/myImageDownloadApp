package com.angelinaandronova.myimagedownloadapplication

import android.support.design.widget.TextInputEditText
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import android.provider.MediaStore.Images.Media.getBitmap
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth




class EspressoTestMatchers {

    companion object {
        fun hasTextInputEditTextError(expectedErrorText: String): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {}

                override fun matchesSafely(item: View?): Boolean {
                    if (item !is TextInputEditText) {
                        return false;
                    }
                    val error = item.error ?: return false
                    return expectedErrorText == error.toString()
                }
            }
        }

        fun hasNoTextInputEditTextError(): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {}

                override fun matchesSafely(item: View?): Boolean {
                    if (item !is TextInputEditText) {
                        return false;
                    }
                    return item.error == null
                }
            }
        }

        fun withDrawable(expectedId: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {}

                override fun matchesSafely(item: View?): Boolean {
                    if (item !is ImageView) {
                        return false
                    }
                    val imageView = item as ImageView
                    if (expectedId < 0) {
                        return imageView.drawable == null
                    }
                    val resources = item.getContext().resources
                    val expectedDrawable = resources.getDrawable(expectedId, null) ?: return false
                    val bitmap = getBitmap(item.drawable)
                    val otherBitmap = getBitmap(expectedDrawable)
                    return bitmap.sameAs(otherBitmap)
                }

            }
        }

        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}