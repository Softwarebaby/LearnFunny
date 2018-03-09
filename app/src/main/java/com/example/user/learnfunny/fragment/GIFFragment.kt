package com.xiaweizi.qnews.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.learnfunny.R
import com.example.user.learnfunny.adapter.GifAdapter
import com.example.user.learnfunny.common.Constants
import com.example.user.learnfunny.net.QClitent
import com.example.user.learnfunny.net.QNewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_gif.view.*


class GIFFragment : Fragment() {

    private var adapter: GifAdapter? = null
//    private var mRvGif: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_gif, null)
//        mRvGif = view.findViewById(R.id.rv_gif) as RecyclerView?
        view.rv_gif!!.layoutManager = LinearLayoutManager(activity)
        QClitent.getInstance()
                .create(QNewsService::class.java, Constants.BASE_JUHE_URL)
                .gifRandomData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gifBean ->
                    val result = gifBean.result
                    adapter = GifAdapter(activity, result)
                    view.rv_gif!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }) { throwable ->
                    Toast.makeText(activity, "获取数据失败", Toast.LENGTH_SHORT).show()
                    Log.i("----->", "error accept:${throwable.message}")
                }
        return view
    }


}
