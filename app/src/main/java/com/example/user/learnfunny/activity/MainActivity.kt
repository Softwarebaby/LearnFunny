package com.example.user.learnfunny.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.blankj.utilcode.utils.FileUtils
import com.blankj.utilcode.utils.SPUtils
import com.blankj.utilcode.utils.Utils
import com.example.user.learnfunny.R
import com.example.user.learnfunny.common.VersionUtils
import com.example.user.learnfunny.fragment.AboutFragment
import com.example.user.learnfunny.fragment.NewsFragment
import com.example.user.learnfunny.fragment.RobotFragment
import com.example.user.learnfunny.fragment.TodayFragment
import com.roughike.bottombar.BottomBar
import com.xiaweizi.qnews.fragment.GIFFragment
import com.xiaweizi.qnews.fragment.JokeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private var newsFragment: NewsFragment? = null      //新闻
    private var jokeFragment: JokeFragment? = null  //段子
    private var todayFragment: TodayFragment? = null  //历史上的今天
    private var robotFragment: RobotFragment? = null   //图灵机器人
    private var gifFragment: GIFFragment? = null  //搞笑动态图
    private var aboutFragment: AboutFragment? = null  //关于
    private var currentFragment: Fragment? = null
    private var builder: AlertDialog.Builder? = null
    private var bottomBar: BottomBar? = null
    private var mHandler: MyHandler? = null
    private var mIconImage: ImageView? = null
    private var mDialog: BottomSheetDialog? = null
    private var mSPUtils: SPUtils? = null
    private var mDirSize = ""  //当前文件缓存的大小
    private var mode: Int = 0

    companion object {  //Companion Objects中定义的成员类似于Java中的静态成员
        val FILE_PATH = "file://" +
                Environment.getExternalStorageDirectory().path +
                "/learnfunny" + "/image_cache" +
                "/camera.jpg"
        val TEMP_PATH = Environment.getExternalStorageDirectory().path +
                "/learnfunny" + "/image_cache" +
                "/camera.jpg"
        val SUCCESS = 0
        val FAILED = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*    日夜模式设置   */
        Utils.init(this)
        val util = SPUtils("mode")
        mode = util.getInt("mode", R.style.AppTheme)
        setTheme(mode)
        /*    主题设置   */
        val utill = SPUtils("theme_id")
        val theme_id = utill.getInt("theme_id", R.style.AppTheme)
        setTheme(theme_id)
        setContentView(R.layout.activity_main)

        mHandler = MyHandler(this)
        mSPUtils = SPUtils("head")
        Thread(Runnable { mDirSize = FileUtils.getDirSize(cacheDir) }).start()
        /********************底部bar 设置点击事件*******************/
        bottomBar = findViewById(R.id.bottomBar) as BottomBar
        bottomBar!!.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_news -> {
                    if (newsFragment == null)
                        newsFragment = NewsFragment()
                    switchFragment(newsFragment!!)
                    nv_left!!.setCheckedItem(R.id.nav_news)
                }
                R.id.tab_joke -> {
                    Thread(Runnable {
                        val intent = Intent()
                        intent.setClass(this@MainActivity, MaskingActivity::class.java)
                        startActivity(intent)
                    }).start()
                    if (jokeFragment == null)
                        jokeFragment = JokeFragment()
                    switchFragment(jokeFragment!!)
                    nv_left!!.setCheckedItem(R.id.nav_duanzi)
                }
                R.id.tab_today -> {
                    if (todayFragment == null)
                        todayFragment = TodayFragment()
                    switchFragment(todayFragment!!)
                    nv_left!!.setCheckedItem(R.id.nav_today_of_history)
                }
                R.id.tab_robot -> {
                    if (robotFragment == null)
                        robotFragment = RobotFragment()
                    switchFragment(robotFragment!!)
                    nv_left!!.setCheckedItem(R.id.nav_robot)
                }
                R.id.tab_about -> {
                    if (aboutFragment == null)
                        aboutFragment = AboutFragment()
                    switchFragment(aboutFragment!!)
                    nv_left!!.setCheckedItem(R.id.nav_other)
                }
            }
        }
        /**********************底部bar设置再次点击事件****************/
        bottomBar!!.setOnTabReselectListener { tabId ->
            when (tabId) {
                R.id.tab_news -> {
                    Toast.makeText(this,"每日趣闻",Toast.LENGTH_SHORT).show()
                }
                R.id.tab_joke -> {
                    if (gifFragment == null)
                        gifFragment = GIFFragment()
                    switchFragment(gifFragment!!)
                }
                R.id.nav_today_of_history -> {
                    Toast.makeText(this,"历史上的今天",Toast.LENGTH_SHORT).show()
                }
                R.id.tab_robot -> {
                    Toast.makeText(this,"图灵机器人",Toast.LENGTH_SHORT).show()
                }
                R.id.tab_about -> {
                    Toast.makeText(this,"关于",Toast.LENGTH_SHORT).show()
                }
            }
        }

        /*************************** 左侧 侧滑菜单 设置头像图片 **********************/
        mIconImage = nv_left!!.getHeaderView(0).findViewById(R.id.icon_image) as ImageView
        mIconImage!!.setOnClickListener {
            mDialog = BottomSheetDialog(this@MainActivity)
            val view = View.inflate(this@MainActivity,
                    R.layout.bottom_dialog_pic_selector, null)
            val xiangji = view.findViewById(R.id.tv_xiangji) as TextView
            val xiangce = view.findViewById(R.id.tv_xiangce) as TextView
            xiangce.setOnClickListener(listener)
            xiangji.setOnClickListener(listener)
            mDialog!!.setContentView(view)
            mDialog!!.setCancelable(true)
            mDialog!!.setCanceledOnTouchOutside(true)
            mDialog!!.show()
        }
        /************************ 左侧 侧滑菜单 设置选择事件***********************/
        nv_left!!.setCheckedItem(R.id.nav_news)
        nv_left!!.setNavigationItemSelectedListener { item ->
            nv_left!!.setCheckedItem(item.itemId)
            drawer_layout!!.closeDrawers()
            when (item.itemId) {
                R.id.nav_news -> bottomBar!!.selectTabAtPosition(0)
                R.id.nav_duanzi -> bottomBar!!.selectTabAtPosition(1)
                R.id.nav_today_of_history -> bottomBar!!.selectTabAtPosition(2)
                R.id.nav_robot -> bottomBar!!.selectTabAtPosition(3)
                R.id.nav_other -> bottomBar!!.selectTabAtPosition(4)
                R.id.nav_clear_cache -> clearCache()
                R.id.nav_version_update -> VersionUtils.updateVersion(this@MainActivity)
                R.id.nav_change_theme -> alertChangeTheme()
                R.id.nav_day_night -> changeToNightTheme()
            }
            false
        }
    }

    /**
     * 切换Fragment的显示
     * @param target 要切换的 Fragment
     */
    private fun switchFragment(target: Fragment) {
        // 如果当前的fragment 就是要替换的fragment 就直接return
        if (currentFragment === target) return
        // 获得 Fragment 事务
        val transaction = supportFragmentManager.beginTransaction()
        // 如果当前Fragment不为空，则隐藏当前的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        // 如果要显示的Fragment 已经添加了，那么直接 show
        if (target.isAdded) {
            transaction.show(target)
        } else {
            // 如果要显示的Fragment没有添加，就添加进去
            transaction.add(R.id.contentContainer, target, target.javaClass.name)
        }
        // 事务进行提交
        transaction.commit()
        //并将要显示的Fragment 设为当前的 Fragment
        currentFragment = target
    }

    /**
     * 2秒内连续点击 back 键，退出应用
     */
    internal var lastTime: Long = 0
    override fun onBackPressed() {
        //判断侧滑菜单是否开启，如果开启则关闭
        if(drawer_layout!!.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout!!.closeDrawers()
        }
        val curTime = System.currentTimeMillis()  //获取系统当前时间
        if (curTime - lastTime > 2000) {
            Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_SHORT).show()
            lastTime = curTime
        } else {
            super.onBackPressed()
        }
    }
    /**
     * 切换夜间/日间模式
     */
    private fun changeToNightTheme() {
        if(mode == R.style.AppTheme_Night) {
            mode = R.style.AppTheme
            Toast.makeText(this, "日间模式", Toast.LENGTH_SHORT).show()
        }
        else {
            mode = R.style.AppTheme_Night
            Toast.makeText(this, "夜间模式", Toast.LENGTH_SHORT).show()
        }
        val utils = SPUtils("mode")
        utils.putInt("mode", mode)
        val intent = intent
        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
    /**
     * 更换主题
     */
    private fun changeTheme(index: Int) {
        val themes = intArrayOf(R.style.AppTheme, R.style.AppTheme_Blue, R.style.AppTheme_Green, R.style.AppTheme_Orange, R.style.AppTheme_Pink, R.style.AppTheme_Sky, R.style.AppTheme_Purple, R.style.AppTheme_PP, R.style.AppTheme_Yellow)
        val utils = SPUtils("theme_id")
        utils.putInt("theme_id", themes[index])
        val intent = intent
        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
    private fun alertChangeTheme() {
        val view = View.inflate(this, R.layout.item_change_theme, null)
        builder = AlertDialog.Builder(this).setView(view)
        builder!!.show()
        view.findViewById(R.id.tv_red).setOnClickListener(listener)
        view.findViewById(R.id.tv_green).setOnClickListener(listener)
        view.findViewById(R.id.tv_blue).setOnClickListener(listener)
        view.findViewById(R.id.tv_orange).setOnClickListener(listener)
        view.findViewById(R.id.tv_pink).setOnClickListener(listener)
        view.findViewById(R.id.tv_sky).setOnClickListener(listener)
        view.findViewById(R.id.tv_purple).setOnClickListener(listener)
        view.findViewById(R.id.tv_pp).setOnClickListener(listener)
        view.findViewById(R.id.tv_yellow).setOnClickListener(listener)
    }
    private val listener = View.OnClickListener { v ->
        when (v.id) {
            R.id.tv_red -> changeTheme(0)
            R.id.tv_blue -> changeTheme(1)
            R.id.tv_green -> changeTheme(2)
            R.id.tv_orange -> changeTheme(3)
            R.id.tv_pink -> changeTheme(4)
            R.id.tv_sky -> changeTheme(5)
            R.id.tv_purple -> changeTheme(6)
            R.id.tv_pp -> changeTheme(7)
            R.id.tv_yellow -> changeTheme(8)
            R.id.tv_xiangji -> {
                //相机
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // 指定调用相机拍照后照片存储路径
                val imageUri = Uri.parse(FILE_PATH)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, 1000)
            }
            R.id.tv_xiangce -> {
                //相册
                intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(intent, 1001)
            }
        }
    }
    /**
     * 清理缓存
     */
    private fun clearCache() {

        AlertDialog.Builder(this@MainActivity).setTitle("确定要清理缓存")
                .setMessage("缓存大小：" + mDirSize)
                .setPositiveButton("清理"
                ) { dialog, which ->
                    Thread(Runnable {
                        FileUtils.deleteDir(cacheDir)
                        mHandler!!.sendEmptyMessage(SUCCESS)
                    }).start()
                }
                .setNegativeButton("取消", null)
                .show()
    }
    /**
    *处理图片选择事件
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                1000 -> {
                    val temp = File(TEMP_PATH)
                    startPhotoZoom(Uri.fromFile(temp))
                }
                1001 -> {
                    val temp1 = File(TEMP_PATH)
                    startPhotoZoom(Uri.fromFile(temp1))
                }
                1002 -> if (data != null) {
                    val extras = data.extras
                    if (extras != null) {
                        val bmp = extras.getParcelable<Bitmap>("data")
                        Log.i("--->", "onActivityResult: ")
                        mIconImage!!.setImageBitmap(bmp)
                        mSPUtils!!.putBoolean("has_head", true)
                        if (mDialog != null && mDialog!!.isShowing) {
                            mDialog!!.dismiss()
                        }
                    }
                }
                else -> {
                }
            }
        }
    }
    /**
     * 裁剪头像
     */
    private fun startPhotoZoom(uri: Uri) {

        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // crop 为true 是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", "true")

        // aspect 是宽高比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        // output 是裁剪图片的宽高
        intent.putExtra("outputX", 100)
        intent.putExtra("outputY", 100)
        intent.putExtra("return-data", true)
        intent.putExtra("noFaceDetection", true)
        startActivityForResult(intent, 1002)
    }

    internal inner class MyHandler(activity: MainActivity) : Handler() {
        var mActivity: WeakReference<MainActivity>

        init {
            mActivity = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            val theActivity = mActivity.get()
            if (theActivity == null || theActivity.isFinishing) {
                return
            }
            // 消息处理
            when (msg.what) {
                SUCCESS -> {
                    Toast.makeText(theActivity,"清理成功",Toast.LENGTH_SHORT).show();
                }
                FAILED -> {
                    Toast.makeText(theActivity,"清理失败",Toast.LENGTH_SHORT).show();
                }
                else -> {
                }
            }
        }
    }
}


