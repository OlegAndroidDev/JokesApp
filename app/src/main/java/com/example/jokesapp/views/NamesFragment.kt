package com.example.jokesapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jokesapp.R
import com.example.jokesapp.databinding.FragmentNamesBinding
import com.example.jokesapp.model.RandomJoke
import com.example.jokesapp.viewmodel.ResultState
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NamesFragment : BaseFragment() {

    private val binding by lazy(){
        FragmentNamesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("****** name frag before", "model")

        jokesViewModel.jokeLiveData.observe(viewLifecycleOwner,::handleState)

        binding.btnGetJoke.setOnClickListener{
            val firstName = binding.txtFirstName.toString().trim()
            val lastName = binding.txtLastName.toString().trim()
            //jokesViewModel.getJokeWithName("John", "Doe")
        }
        return binding.root
    }

    private fun handleState(resultState: ResultState?) {
        Log.d("****** handle state", "model")
        when(resultState){
            is ResultState.LOADING ->{
                Toast.makeText(requireContext(), "Loading ....", Toast.LENGTH_LONG).show()
            }
            is ResultState.SUCCESS<*> -> {
                val jokes = resultState.jokes as RandomJoke
                val joke = jokes.joke
                AlertDialog.Builder(requireContext())
                    .setMessage(joke.joke)
                    .setPositiveButton("Dismiss") { dialog, i ->
                        dialog.dismiss()
                        dialog.cancel()
                    }
                    .show()
            }
            is ResultState.ERROR -> { Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG
            ).show()}
        }
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            NamesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}