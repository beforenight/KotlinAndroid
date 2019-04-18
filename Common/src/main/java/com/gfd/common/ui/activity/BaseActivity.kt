package com.gfd.common.ui.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.gfd.common.common.AppManager
import com.gfd.common.widgets.ProgressLoading

/**
 * @Author : 郭富东
 * @Date ：2018/8/1 - 16:11
 * @Email：878749089@qq.com
 * @descriptio：所有Activity的基类
 */
abstract class BaseActivity : AppCompatActivity() {

    private var mProgressLoading: ProgressLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = getLayoutId()
        if (layoutId != -1) {
            val rootView = LayoutInflater.from(this).inflate(layoutId, null)
            setContentView(rootView)
        }
        AppManager.instance.addActivity(this)
        initOperate()
        initView()
        initData()
        setListener()
    }

    /**
     * 设置布局id
     */
    abstract fun getLayoutId(): Int


    abstract fun initView()

    abstract fun initData()

    /** 设置监听 */
    open fun setListener() {

    }

    /**
     * 初始化操作，在onCreate中调用
     */
    open fun initOperate() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mProgressLoading?.dismiss()
        mProgressLoading = null
        AppManager.instance.finishActivity(this)
    }

    protected fun showDialogLoading() {
        if (mProgressLoading == null) {
            mProgressLoading = ProgressLoading(this)
        }
        mProgressLoading?.showLoading()
    }

    protected fun hideDialogLoading() {
        mProgressLoading?.hideLoading()
    }

    /**
     * 设置透明状态栏
     */
    fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            //根据上面设置是否对状态栏单独设置颜色
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            val localLayoutParams = window.attributes
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags)
        }
    }

}
