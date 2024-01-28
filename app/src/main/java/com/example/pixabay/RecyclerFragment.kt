package com.example.pixabay

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabay.databinding.FragmentRecyclerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerFragment : Fragment() {
    private var page = 1
    var adapter = PixaAdapter(arrayListOf())
    private var oldWord = ""
    private var newWord = ""
    private lateinit var binding: FragmentRecyclerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        with(binding) {
            rvPixabay.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && dy > 0) {
                        getImages()
                    }
                }
            })
            btnNext.setOnClickListener {
                newWord = etSearch.text.toString()
                if (newWord != oldWord) {
                    Toast.makeText(activity, "Нажмите Search чтоб найти другое", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    page++
                    getImages()
                }
            }
            btnSearch.setOnClickListener {
                oldWord = etSearch.text.toString()
                if (oldWord == newWord) {
                    Toast.makeText(
                        activity,
                        "Введите новый запрос чтобы найти другое",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    adapter.list.clear()
                    getImages()
                }
            }
        }
    }

    private fun FragmentRecyclerBinding.getImages() {
        RetrofitService().api.getImages(
            keyWordForSearch = etSearch.text.toString(), page = page
        ).enqueue(object : Callback<PixabayModel> {
            override fun onResponse(
                call: Call<PixabayModel>, response: Response<PixabayModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.list.addAll(it.hits)
                        rvPixabay.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}