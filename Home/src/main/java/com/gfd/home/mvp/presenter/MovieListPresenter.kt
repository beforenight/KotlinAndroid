package com.gfd.home.mvp.presenter

import com.gfd.home.entity.MovieData
import com.gfd.home.mvp.MovieListContract
import com.gfd.home.service.MovieListService
import javax.inject.Inject

/**
 * @Author ：郭富东
 * @Date：2019/1/30:13:33
 * @Email：878749089@qq.com
 * @descriptio：
 */
class MovieListPresenter @Inject constructor() : MovieListContract.Presenter, MovieListService.GetMovieListCallBack {

    @Inject
    lateinit var mView: MovieListContract.View

    @Inject
    lateinit var mService: MovieListService

    private var isLoading = false

    override fun getMovieList(cityId: Int, type: Int, isLoading: Boolean) {
        this.isLoading = isLoading
        if (this.isLoading) {
            mView.showLoading()
        }
        mService.getMovieList(cityId, type, this)

    }

    override fun onMovieList(datas: List<MovieData>) {
        mView.showMovies(datas)
        if (this.isLoading) {
            mView.hideLoading()
        }
    }

}