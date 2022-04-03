package com.example.jokesapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokesapp.R
import com.example.jokesapp.adapter.JokeAdapter
import com.example.jokesapp.databinding.FragmentListBinding
import com.example.jokesapp.model.Joke
import com.example.jokesapp.model.RandomJoke
import com.example.jokesapp.viewmodel.ResultState

class ListFragment : BaseFragment() {

    private val binding by lazy{
        FragmentListBinding.inflate(layoutInflater)
    }
    private val jokeAdapter by lazy {
        JokeAdapter()
    }
    private var _listToUpdate = mutableListOf<Joke>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btnBack .setOnClickListener {
            findNavController().navigateUp()
        }

        val valLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)

        binding.jokeRecycler.apply {
            layoutManager = valLayoutManager
            adapter = jokeAdapter
        }

        jokesViewModel.jokeLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ResultState.LOADING -> {

                }

                is ResultState.SUCCESS<*> -> {
                    val jokes = state.jokes as RandomJoke
                    _listToUpdate = jokes.joke as MutableList<Joke>

                    jokeAdapter.updateJokes(_listToUpdate)
                    jokesViewModel.InitJokeMutable()
                }
                is ResultState.ERROR -> {
                    Toast.makeText(
                        requireContext(), state.throwable.localizedMessage, Toast.LENGTH_LONG
                    ).show()
                }
                ResultState.INIT -> {
                    // no-op
                }
            }
        }

        jokesViewModel.getNumberJoke(20)

        binding.jokeRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (valLayoutManager.findLastVisibleItemPosition() == jokeAdapter.itemCount - 1) {
                    jokesViewModel.getNumberJoke(20)
                }
            }
        })
        return binding.root
    }
}