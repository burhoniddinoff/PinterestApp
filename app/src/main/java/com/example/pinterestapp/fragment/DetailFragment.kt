package com.example.pinterestapp.fragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pinterestapp.R
import com.example.pinterestapp.adapter.RetrofitGitAdapter
import com.example.pinterestapp.database.MyDatabase
import com.example.pinterestapp.database.SaveImage
import com.example.pinterestapp.databinding.Fragment1Binding
import com.example.pinterestapp.databinding.FragmentDetailBinding
import com.example.pinterestapp.model.ResponseItem
import com.example.pinterestapp.networking.RetrofitHttp
import com.example.pinterestapp.util.GetDetailsInfo
import com.example.pinterestapp.util.RandomColor
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import com.google.android.material.imageview.ShapeableImageView
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    var count = 1
    lateinit var nestedScrollView: NestedScrollView
    lateinit var detailsRecyclerView: RecyclerView
    lateinit var retrofitGetAdapter: RetrofitGitAdapter
    private lateinit var myDatabase: MyDatabase
    private val photos = ArrayList<ResponseItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
        initViews()
    }

    private fun initViews() {

        val view: View

        myDatabase = MyDatabase.getInstance(requireContext())
        val imageView: ImageView = binding.imageView

        detailsRecyclerView.setHasFixedSize(true)
        detailsRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        detailsRecyclerView.isNestedScrollingEnabled = false

        if (GetDetailsInfo.title.isNullOrEmpty()) {
            binding.textView.text = GetDetailsInfo.title
        } else {
            binding.textView.text = getString(R.string.picture)
        }

        Glide.with(imageView) // error
            .load(GetDetailsInfo.links)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(RandomColor.randomColor())
            .into(imageView)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.viewCard.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_fullScreenFragment)
        }

        binding.shareImage.setOnClickListener {
            shareImageView()
        }

        apiPosterListRetrofitFragment()
        refreshAdapter(photos)

        binding.saveCard.setOnClickListener {
            showBottomSheet()
        }

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= (nestedScrollView.height + scrollY)) {
                count++
                apiPosterListRetrofitFragment()
            }
        })

    }

    private fun apiPosterListRetrofitFragment() {
        RetrofitHttp.posterService.listPhotos()
            .enqueue(object : Callback<ArrayList<ResponseItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<ResponseItem>>,
                    response: Response<ArrayList<ResponseItem>>
                ) {
                    photos.addAll(response.body()!!)
                    retrofitGetAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun refreshAdapter(photos: ArrayList<ResponseItem>) {
        retrofitGetAdapter = RetrofitGitAdapter(requireContext(), photos)
        detailsRecyclerView.adapter = retrofitGetAdapter
        retrofitGetAdapter.onItemClick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }

    private fun showBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val textGallery: TextView = view.findViewById(R.id.textGallery)
        val textProfile: TextView = view.findViewById(R.id.textProfile)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        textGallery.setOnClickListener {
            saveToGallery(binding.imageView)
            dialog.dismiss()
        }
        textProfile.setOnClickListener {
            saveToDatabase()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun saveToDatabase() {
        if (GetDetailsInfo.title.isNullOrEmpty()) {
            myDatabase.dao()
                .saveImage(
                    SaveImage(
                        GetDetailsInfo.id!!,
                        GetDetailsInfo.links!!,
                        GetDetailsInfo.title!!
                    )
                )
        } else {
            myDatabase.dao()
                .saveImage(
                    SaveImage(
                        GetDetailsInfo.id!!,
                        GetDetailsInfo.links!!,
                        ""
                    )
                )
        }
        toasty("Saved!")
    }

    private fun shareImageView() {
        val bitmapDrawable = binding.imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "title",
            null
        )
        val uri = Uri.parse(bitmapPath)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/jpg"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, GetDetailsInfo.links)
        }
        startActivity(Intent.createChooser(intent, "Share"))
    }

    private fun saveToGallery(iv_image: ImageView) {
        val bitmap = getScreenShotFromView(iv_image)

        if (bitmap != null) {
            saveMediaToStorage(bitmap)
        }
    }

    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null

        try {
            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {

                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            toasty("Saved!")
        }
    }

    private fun toasty(msg: String) {
        Toasty.success(requireContext(), msg, Toasty.LENGTH_LONG, true).show()
    }

}