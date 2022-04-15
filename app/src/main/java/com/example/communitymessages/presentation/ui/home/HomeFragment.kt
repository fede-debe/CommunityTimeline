package com.example.communitymessages.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.communitymessages.R
import com.example.communitymessages.databinding.FragmentHomeBinding
import com.example.communitymessages.presentation.adapter.CommunityMessageAdapter
import com.example.communitymessages.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy {
        CommunityMessageAdapter(CommunityMessageAdapter.OnClickListener { message ->
            viewModel.openMessageDetails(message)
        })
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        updateTimeline()

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading == true) {
                binding.ivLoadingTimeline.setImageResource(R.drawable.loading_animation)
                binding.ivLoadingTimeline.visibility = View.VISIBLE
            } else {
                binding.ivLoadingTimeline.visibility = View.GONE
            }
        }

        viewModel.networkError.observe(viewLifecycleOwner) { error ->
            if (error == true) {
                binding.ivErrorNetworkTimeline.setImageResource(R.drawable.ic_network_error)
                binding.ivErrorNetworkTimeline.visibility = View.VISIBLE
            } else {
                binding.ivErrorNetworkTimeline.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.layoutHomeFragment.showSnackBar(R.string.error_message)
        }

        viewModel.navigateToMessageDetails.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToMessageDetailsFragment(it))
            }
            viewModel.openMessageDetailsComplete()
        }
    }

    private fun setAdapters() {
        binding.rvListMessages.adapter = adapter
    }

    private fun setListeners() {
        binding.refreshLayout.setOnRefreshListener {
            updateTimeline()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun updateTimeline() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTimeline.collectLatest { timeline ->
                adapter.submitData(timeline)
            }
        }
    }
}
