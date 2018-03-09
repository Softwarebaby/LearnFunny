package com.example.user.learnfunny.fragment

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.learnfunny.R
import kotlinx.android.synthetic.main.fragment_news.view.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * Created by user on 2017/12/1.
 */
/*
    Kotlin特点：空（null）安全
 */
class NewsFragment : Fragment() {
    private var typesCN: Array<String>? = null         //顶部 tab 内容数组
    private var types: Array<String>? = null         //顶部 tab 英文内容数组
    private var newsViewPagerAadpter: NewsViewPagerAadpter? = null    //ViewPager 适配器

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //双感叹号!!，表示一定不能为空
        val view = inflater!!.inflate(R.layout.fragment_news,null)  //获取fragment的视图界面
        typesCN = resources.getStringArray(R.array.news_type_cn)
        types  = resources.getStringArray(R.array.news_type_en)

        //初始化VIewPager，设置适配器
        newsViewPagerAadpter = NewsViewPagerAadpter(activity.supportFragmentManager)
        view.main_viewpager!!.adapter = newsViewPagerAadpter
        /*   顶部指示器数据加载  */
        val magicIndicator = view.findViewById(R.id.magic_indicator) as MagicIndicator
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return if (typesCN == null) 0 else types!!.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.BLACK
                colorTransitionPagerTitleView.selectedColor = Color.RED
                colorTransitionPagerTitleView.text = typesCN!![index]
                colorTransitionPagerTitleView.setOnClickListener { view.main_viewpager!!.currentItem = index }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, view.main_viewpager!!)

        return view
    }

    private inner class NewsViewPagerAadpter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            //根据位置创建相应的fragment
            return NewsDetailFragment(types!![position])
        }

        override fun getCount(): Int {
            return types!!.size
        }

    }
}