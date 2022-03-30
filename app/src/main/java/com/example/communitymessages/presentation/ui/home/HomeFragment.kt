package com.example.communitymessages.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.communitymessages.MyApplication
import com.example.communitymessages.R
import com.example.communitymessages.databinding.FragmentHomeBinding
import com.example.communitymessages.presentation.adapter.CommunityMessageAdapter
import com.example.communitymessages.utils.COMMUNITY_ID
import com.example.communitymessages.utils.showSnackBar
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter =
        CommunityMessageAdapter(CommunityMessageAdapter.OnClickListener { message ->
            viewModel.openMessageDetails(message)
        })

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        (requireContext().applicationContext as MyApplication).appComponent.injectTimeline(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setObservers()
        viewModel.getId(COMMUNITY_ID)
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.timeline.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

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
}
