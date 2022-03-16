/*
 * Copyright 2022 Google LLC
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

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.samples.apps.sunflower.databinding.FragmentEntryBinding
import com.google.samples.apps.sunflower.databinding.FragmentEventHandleBinding
import com.google.samples.apps.sunflower.viewmodels.EntryViewModel
import com.google.samples.apps.sunflower.viewmodels.EventHandleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntryFragment : Fragment() {
    private lateinit var binding: FragmentEntryBinding

    private val viewModel: EntryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                /*viewModel.data.observe(viewLifecycleOwner) {
                    Log.d("EntryFragment", "viewModel - data observe start!!!")
                }*/

                /*viewModel.dataFlow.collect {
                    Log.d("EntryFragment", "viewModel - data collect start!!!")
                }
                */

                viewModel.collect {
                    Log.d("EntryFragment", "viewModel - data collect start!!!:: $it")
                }
            }
        }

        /*lifecycleScope.launch {
            viewModel.data.observe(viewLifecycleOwner) {
                Log.d("EntryFragment", "viewModel - data observe start!!!")
            }

            viewModel.dataFlow.collect {
                Log.d("EntryFragment", "viewModel - data collect start!!!:: $it")
            }
        }*/
        /*viewModel.data.observe(viewLifecycleOwner) {
            Log.d("EntryFragment", "viewModel - data observe start!!!:: $it")
        }*/

        /*lifecycleScope.launchWhenResumed {
            viewModel.dataFlow.collect {
                Log.d("EntryFragment", "viewModel - data collect start!!!")
            }
        }*/

        binding.btnGoEventHandle.setOnClickListener {
            findNavController().navigate(R.id.action_entry_fragment_to_event_handle_fragment)
        }

        binding.btnEmpty.setOnClickListener {
            viewModel.plus()
        }

        return binding.root
    }

}