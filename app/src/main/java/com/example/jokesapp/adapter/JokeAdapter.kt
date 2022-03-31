package com.example.jokesapp.adapter

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokesapp.databinding.JokeItemBinding
import com.example.jokesapp.model.Joke
import com.example.jokesapp.model.RandomJoke

class JokeAdapter(
    private val jokeList: MutableList<Joke> = mutableListOf()
) : RecyclerView.Adapter<JokesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val view = JokeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JokesViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(jokeList[position])
    }

    override fun getItemCount(): Int = jokeList.size

    fun updateJokes(nextItems: MutableList<Joke>) {
        jokeList.addAll(nextItems)
        notifyDataSetChanged()
    }
}

class JokesViewHolder(
    private val binding: JokeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.jokeItem.text = joke.joke
        }
    }
