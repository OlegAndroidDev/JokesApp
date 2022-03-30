package com.example.jokesapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jokesapp.R
import com.example.jokesapp.databinding.FragmentRandomBinding
import com.example.jokesapp.model.RandomJoke
import com.example.jokesapp.viewmodel.ResultState
import org.koin.core.component.getScopeId

class RandomFragment : BaseFragment() {
    private val binding by lazy {
        FragmentRandomBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btnRandom .setOnClickListener{
            jokesViewModel.getRandomJoke()
        }

        binding.btnList.setOnClickListener{
            findNavController().navigate(R.id.action_randomFragment_to_listFragment)
        }
        binding.btnName.setOnClickListener {
            findNavController().navigate(R.id.action_randomFragment_to_namesFragment)
        }

        jokesViewModel.jokeLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ResultState.LOADING -> {
//                    Toast.makeText(
//                        requireContext(), "Loading ....", Toast.LENGTH_LONG
//                    ).show()
                }
                is ResultState.SUCCESS<*> -> {
                    val jokes = state.jokes as RandomJoke
                    val joke = jokes.joke
                    AlertDialog.Builder(requireContext())
                        .setMessage(joke.joke)
                        .setPositiveButton("Dismiss") { dialog, i ->
                            dialog.dismiss()
                            dialog.cancel()
                        }
                        .show()
                    jokesViewModel.jokeMutable.postValue(ResultState.INIT)
                    //jokesViewModel.resetState()
                }
                is ResultState.ERROR -> {
                    Toast.makeText(
                        requireContext(), state.throwable.localizedMessage, Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        return binding.root
   }
}