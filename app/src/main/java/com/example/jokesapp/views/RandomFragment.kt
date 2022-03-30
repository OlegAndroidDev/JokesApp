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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RandomFragment : BaseFragment() {

    private val binding by lazy {
        FragmentRandomBinding.inflate(layoutInflater)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("main Random fragment", "observe called")
        //jokesViewModel.jokeLiveData.observe(viewLifecycleOwner,::handleState)

        binding.btnRandom .setOnClickListener{
            //jokesViewModel.getJokeWithName("John", "Doe")
            Log.d("******btnRandome", "test")
            jokesViewModel.getRandomJoke()
        }

        binding.btnList.setOnClickListener{
            findNavController().navigate(R.id.action_randomFragment_to_listFragment)
        }
        binding.btnName.setOnClickListener {
            findNavController().navigate(R.id.action_randomFragment_to_namesFragment)
        }

        jokesViewModel.jokeLiveData.observe(viewLifecycleOwner) { state ->
            Log.d("random fragment", "observe")
            Log.d("random fragment", jokesViewModel.jokeLiveData.value.toString())
            when(state) {
                is ResultState.LOADING -> {
                    Toast.makeText(
                        requireContext(), "Loading ....", Toast.LENGTH_LONG
                    ).show()
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
                    jokesViewModel.resetState()
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


//    private fun handleState(resultState: ResultState?) {
//        when(resultState){
//            is ResultState.LOADING ->{
//                Toast.makeText(requireContext(), "Loading ....", Toast.LENGTH_LONG).show()
//            }
//            is ResultState.SUCCESS<*> -> {
//                val jokes = resultState.jokes as RandomJoke
//                val joke = jokes.value
//                AlertDialog.Builder(requireContext())
//                    .setMessage(joke.joke)
//                    .setPositiveButton("Dismiss") { dialog, i ->
//                        dialog.dismiss()
//                        dialog.cancel()
//                    }
//                    .show()
//            }
//            is ResultState.ERROR -> { Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG
//            ).show()}
//        }
//    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            RandomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}