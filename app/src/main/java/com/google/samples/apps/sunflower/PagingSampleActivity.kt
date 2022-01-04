/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.adapters.PagingSampleAdapter
import com.google.samples.apps.sunflower.data.PagingSampleData
import com.google.samples.apps.sunflower.data.UnsplashRepository
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding
import com.google.samples.apps.sunflower.databinding.ActivityPagingSampleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.zip.Inflater
import javax.inject.Inject
import kotlin.math.floor

@AndroidEntryPoint
class PagingSampleActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: UnsplashRepository

    private lateinit var binding: ActivityPagingSampleBinding

    private var recyclerView: RecyclerView? = null
    private var manager: LinearLayoutManager? = null
    private var adapter: PagingSampleAdapter? = null
    private var isScrolling: Boolean = false
    private var currentItems: Int = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0
    private var itemList: ArrayList<PagingSampleData> = ArrayList<PagingSampleData>()
    private var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_paging_sample)

        binding = DataBindingUtil.setContentView<ActivityPagingSampleBinding>(this, R.layout.activity_paging_sample)

        recyclerView = binding.pagingSampleList
        manager = LinearLayoutManager(this)
        adapter = PagingSampleAdapter()

        with(recyclerView) {
            this?.layoutManager = manager
            this?.adapter = adapter
        }

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = manager?.childCount ?: 0
//                totalItems = manager?.itemCount ?: Int.MAX_VALUE
                totalItems = manager?.itemCount ?: 0
                scrollOutItems = manager?.findFirstVisibleItemPosition() ?: 0

                if (isScrolling && (totalItems == currentItems + scrollOutItems)) {
                    isScrolling = false
                    fetchData()
                }
            }
        })

        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            delay(2000)
//            itemList = repository.getSearchResult("carrot", currentPage++, 10).map {
//                it.id
//            }
            for (i: Int in 1..10) {
                adapter?.data?.add(PagingSampleData(0, "$i($totalItems):: ${floor(Math.random()*100)}"))
                adapter?.notifyDataSetChanged()
                recyclerView?.smoothScrollToPosition(0)
            }
        }
    }
}