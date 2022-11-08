package com.example.pinterestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.comix.overwatch.HiveProgressView
import com.example.pinterestapp.R
import com.example.pinterestapp.adapter.RetrofitGetAdapter2
import com.example.pinterestapp.modelSearch.Result
import com.example.pinterestapp.modelSearch.Welcome
import com.example.pinterestapp.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment4 : Fragment() {

    var count = 1
    var photos = ArrayList<Result>()
    lateinit var recyclerView4: RecyclerView
    lateinit var swipeRefreshLayout4: SwipeRefreshLayout
    private lateinit var adapter: RetrofitGetAdapter2
    lateinit var progressBar4: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_4, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        recyclerView4 = view.findViewById(R.id.recyclerView4)
        swipeRefreshLayout4 = view.findViewById(R.id.swipe_refresh4)
        progressBar4 = view.findViewById(R.id.progress_bar4)

        apiPosterListRetrofitFragment4()
        refreshAdapter(photos)

        recyclerView4.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    count++
                    apiPosterListRetrofitFragment4()
                }
            }
        })

        recyclerView4.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView4.layoutManager = layoutManager

        swipeRefreshLayout4.setOnRefreshListener {
            count++
            photos.clear()
            swipeRefreshLayout4.isRefreshing = false
            apiPosterListRetrofitFragment4()
            adapter.notifyDataSetChanged()
        }
    }

    private fun apiPosterListRetrofitFragment4() {
        progressBar4.isVisible = true

//        RetrofitHttp.posterService.searchPhotos("cars").enqueue(object : Callback<ArrayList<ResponseItem>> {
        RetrofitHttp.posterService.searchPhotos(count,"space").enqueue(object : Callback<Welcome> {
            override fun onResponse(
                call: Call<Welcome>,
                response: Response<Welcome>
            ) {
                if (response.body() != null) {
                    photos.addAll(response.body()!!.results!!)
                    progressBar4.isVisible = false
                }
                else
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Welcome>, t: Throwable) {
                Toast.makeText(requireContext(), "Something error!", Toast.LENGTH_SHORT).show()
                progressBar4.isVisible = false
                t.printStackTrace()
            }
        })
    }


    fun refreshAdapter(photos: ArrayList<Result>) {
        adapter = RetrofitGetAdapter2(requireContext(), photos)
        recyclerView4.adapter = adapter
        adapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }
}