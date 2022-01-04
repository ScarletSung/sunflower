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

package com.google.samples.apps.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.PagingSampleData

class PagingSampleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<PagingSampleData> = ArrayList<PagingSampleData>()

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sampleIv: ImageView = itemView.findViewById(R.id.iv_profile)
        private val sampleTv: TextView = itemView.findViewById(R.id.tv_profile)

        fun bind(data: PagingSampleData) {
//            sampleIv.setImageResource(data.sampleImg)
            sampleTv.text = data.sampleTitle
        }
    }

//    fun setData(data: ArrayList<PagingSampleData>) {
//        this.data = data
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_paging_sample, parent, false)

        return VHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.let {
            (holder as VHolder).bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

}