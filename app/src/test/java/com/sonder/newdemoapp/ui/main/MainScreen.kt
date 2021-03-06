package com.sonder.newdemoapp.ui.main

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KSnackbar
import com.agoda.kakao.text.KTextView
import com.sonder.newdemoapp.R
import org.hamcrest.Matcher

class MainScreen : Screen<MainScreen>() {
    val recycler = KRecyclerView(builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = {
            itemType(::Item)
        })

    val toast =  KSnackbar()
}

class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
    val title: KTextView = KTextView(parent) { withId(R.id.nameTextView) }
    val image: KImageView = KImageView(parent) { withId(R.id.itemImageView) }
}