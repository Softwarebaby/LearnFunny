package com.example.user.learnfunny.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 *
 *@作者： Bob Du
 *@创建时间：2017/12/1 20:30
 *@文件名：BaseFragment.kt
 *@功能：当有多个Fragment使用时，提供一个抽象基类BaseFragment, 来封装一下方法，使其Fragment的使用简单化
 */


abstract class BaseFragment : Fragment() {

    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    abstract fun fetchData()

    @JvmOverloads fun prepareFetchData(forceUpdate: Boolean = false): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

}