package com.example.imagesearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

private var mPage = 1
private var mKeyword = ""
var imageViewModel: ImageViewModel = ImageViewModel()
lateinit var listAdapter: ListAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addObserveData()

        textWatch()

        scrollWatch()

    }

    private fun addObserveData() {
        imageViewModel.isSuccessNetwork.observe(this, Observer {
            if (it) {
                if (mPage > 1) {
                    Log.e("jhjh", "추가사진 부를때만 타야지?")
                    listAdapter.notifyItemRangeChanged(listAdapter.items.size - 1, imageViewModel.imageRepository.docuSize)
                    Toast.makeText(baseContext, getString(R.string.more_image), Toast.LENGTH_LONG).show()
                } else {
                    Log.e("jhjh", "최초에만 타야지?")
                    listAdapter = ListAdapter(imageViewModel.imageRepository.documentList)
                    imageListView.adapter = listAdapter
                }
            } else {
                Toast.makeText(baseContext, getString(R.string.no_image), Toast.LENGTH_LONG).show()
            }
        })

        val loadingDialog = LoadingDialog(this)
        imageViewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun textWatch() {
        Observable
            .create(ObservableOnSubscribe { emitter: ObservableEmitter<String>? ->
                searchInput.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        Log.e("jhjh", "s : $s")
                        emitter?.onNext(s.toString())
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }
                })
            })
            .debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.rxjava3.core.Observer<String> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: String) {
                    if (t.length > 0) {
                        // 새로운 검색어이니 페이지 초기화
                        mPage = 1
                        mKeyword = t
                        imageViewModel.getImageList(mKeyword, mPage)
                    }
                }

                override fun onError(e: Throwable?) {
                }
            })
    }

    private fun scrollWatch() {
        imageListView.addOnScrollListener(object : OnSingleScrollListener() {
            override fun OnSingleScrollListener(recyclerView: RecyclerView) {
                if (!imageListView.canScrollVertically(1)) {
                    if (imageViewModel.imageRepository.isEnd) {
                        Toast.makeText(baseContext, getString(R.string.no_more_image), Toast.LENGTH_LONG).show()
                    } else {
                        mPage++
                        imageViewModel.getImageList(mKeyword, mPage)
                    }
                }
            }
        })
    }

}