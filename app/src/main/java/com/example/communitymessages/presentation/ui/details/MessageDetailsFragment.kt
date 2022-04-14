package com.example.communitymessages.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.communitymessages.MyApplication
import com.example.communitymessages.R
import com.example.communitymessages.databinding.FragmentMessageDetailsBinding
import com.example.communitymessages.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class MessageDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMessageDetailsBinding
    private val args: MessageDetailsFragmentArgs by navArgs()

    private val viewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageDetailsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
//        (requireContext().applicationContext as MyApplication).appComponent.injectMessage(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.item = args.message
        binding.actionSelectorPreference.setOnClickListener {
            viewModel.addToInterest(args.message.id)
        }
    }

    private fun setObservers() {
        viewModel.preference.observe(viewLifecycleOwner) { preference ->
            if (preference == true) {
                binding.actionSelectorPreference.setImageResource(R.drawable.ic_heart)
            } else {
                binding.actionSelectorPreference.setImageResource(R.drawable.ic_heart_outlined)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.layoutMessageDetails.showSnackBar(R.string.error_message)
        }
    }
}