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
import com.example.jokesapp.databinding.FragmentNamesBinding
import com.example.jokesapp.model.RandomJoke
import com.example.jokesapp.viewmodel.ResultState
import com.google.android.material.snackbar.Snackbar

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

        binding.btnBack.setOnClickListener(){
            findNavController().navigate(R.id.action_namesFragment_to_randomFragment)
        }

        jokesViewModel.jokeLiveData.observe(viewLifecycleOwner,::handleState)

        binding.btnGetJoke.setOnClickListener{
            val fullName = binding.editName.getText().trim() //binding.editName.toString().trim()

            if(fullName.isNotEmpty() && binding.editName.getText().length > 2 && (fullName.contains(" "))){
                val firstName = (fullName.trim().split(" ")).first().replaceFirstChar { it.uppercase() }
                val lastName = (fullName.trim().split(" "))[1].replaceFirstChar { it.uppercase() }
                jokesViewModel.getJokeWithName(firstName, lastName)
            }else
            {
                val snack = Snackbar.make(it,"Please enter a valid name",Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
        return binding.root
    }

    private fun handleState(state: ResultState?) {
        when(state) {
            is ResultState.LOADING -> {
//                    Toast.makeText(
//                        requireContext(), "Loading ....", Toast.LENGTH_LONG
//                    ).show()
            }
            is ResultState.SUCCESS<*> -> {
                val jokes = state.jokes as RandomJoke
                val joke = jokes.joke.first()
                AlertDialog.Builder(requireContext())
                    .setMessage(joke.joke)
                    .setPositiveButton("Dismiss") { dialog, i ->
                        dialog.dismiss()
                        dialog.cancel()
                    }
                    .show()
                //jokesViewModel.jokeMutable.postValue(ResultState.INIT)
                jokesViewModel.InitJokeMutable()
            }
            is ResultState.ERROR -> {
                Toast.makeText(
                    requireContext(), state.throwable.localizedMessage, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}