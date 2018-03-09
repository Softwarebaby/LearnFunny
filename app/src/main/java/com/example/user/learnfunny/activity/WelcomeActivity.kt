package com.example.user.learnfunny.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.user.learnfunny.R
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 *
 *@作者： Bob Du
 *@创建时间：2017/11/30 17:03
 *@文件名：WelcomeActivity.kt
 *
 */

class WelcomeActivity : AppCompatActivity() {
    val TAG = "WelcomeActivity"  //注：val是只读变量，该变量相当于java的final，因此必须初始化
                                                          //区别：var是可变变量，和java中的普通变量一样
    val animalSet = AnimatorSet();

    //类型后面加一个问号?，表示该变量是Nullable(允许为空)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        //配置出场动画
        //注:实例和类型之间的冒号前不要空格
        val translationX: ObjectAnimator = ObjectAnimator.ofFloat(iv_splash,"translationX",600f,0f)
        val translationY = ObjectAnimator.ofFloat(iv_splash,"translationY",-100f,90f,-80f,70f,-60f,50f)
        animalSet.playTogether(translationX,translationY)  //设置同时开始XY动画
        animalSet.duration = 2000 //设置动画时间
        //jump()  //此时无法实现动画
        addListener()
    }

    /*  实现出场动画 */
    private fun addListener() {
        animalSet.start()
        animalSet.addListener(object : Animator.AnimatorListener {  //重写方法
            override fun onAnimationStart(animation: Animator) {
                //throw UnsupportedOperationException("not implemented")
            }

            override fun onAnimationEnd(animation: Animator) {
                try{
                    //Thread.sleep(500)  //线程休眠0.5s
                    jump()
                }catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
                //throw UnsupportedOperationException("not implemented")
            }

            override fun onAnimationRepeat(animation: Animator) {
                //throw UnsupportedOperationException("not implemented")
            }
        })
    }
    /*  实现页面跳转 */
    fun jump() {
        val intent = Intent()
        intent.setClass(this@WelcomeActivity, MainActivity::class.java)  //使用反射获取class
        startActivity(intent)
        finish()
    }
}
