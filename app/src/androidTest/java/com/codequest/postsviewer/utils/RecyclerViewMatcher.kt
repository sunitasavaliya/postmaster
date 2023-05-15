package com.codequest.postsviewer.utils

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

private class RecyclerViewMatcher(
    private val recyclerViewId: Int,
    private val position: Int,
    private val targetViewId: Int
) :
    TypeSafeMatcher<View>() {
    private var resources: Resources? = null
    private var childView: View? = null

    override fun describeTo(description: Description) {
        var idDescription = Integer.toString(recyclerViewId)
        this.resources?.let {
            idDescription = try {
                it.getResourceName(recyclerViewId)
            } catch (var4: Resources.NotFoundException) {
                String.format(
                    "%s (resource name not found)",
                    recyclerViewId
                )
            }
        }

        description.appendText("with id: $idDescription")
    }

    public override fun matchesSafely(view: View): Boolean {
        this.resources = view.resources

        if (childView == null) {
            val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
            if (recyclerView != null && recyclerView.id == recyclerViewId) {
                childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
            } else {
                return false
            }
        }

        return when (targetViewId) {
            -1 -> view === childView
            else -> view === childView?.findViewById<View>(targetViewId)
        }
    }
}

fun atPositionOnView(recyclerViewId: Int, position: Int, targetViewId: Int = -1): Matcher<View> =
    RecyclerViewMatcher(recyclerViewId, position, targetViewId)
