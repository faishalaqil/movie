package fintest.movie.features.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fintest.movie.BuildConfig
import fintest.movie.features.dashboard.data.dto.*
import fintest.movie.features.dashboard.data.repository.impl.MovieRepositoryImpl
import fintest.movie.features.dashboard.di.moshi
import fintest.movie.features.dashboard.utils.*
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.InterruptedIOException
import java.net.*

class MovieViewModel: ViewModel(), KoinComponent {
    private val networkHelper by inject<NetworkHelper>()
    private val movieRepository by inject<MovieRepositoryImpl>()
    private val resourceHelper by inject<ResourcesHelper>()

    //Status balikan server
    private val _genres  = MutableLiveData<Resource<GenresDto>>()
    //public untuk di observe di Fragment,LiveData
    val genres: LiveData<Resource<GenresDto>>
        get() = _genres

    //Status balikan server
    private val _trending  = MutableLiveData<Resource<TrendingDto>>()
    //public untuk di observe di Fragment,LiveData
    val trending: LiveData<Resource<TrendingDto>>
        get() = _trending

    //Status balikan server
    private val _movie  = MutableLiveData<Resource<MovieDto>>()
    //public untuk di observe di Fragment,LiveData
    val movie: LiveData<Resource<MovieDto>>
        get() = _movie

    //Status balikan server
    private val _trailer  = MutableLiveData<Resource<TrailerDto>>()
    //public untuk di observe di Fragment,LiveData
    val trailer: LiveData<Resource<TrailerDto>>
        get() = _trailer

    //Status balikan server
    private val _review  = MutableLiveData<Resource<ReviewDto>>()
    //public untuk di observe di Fragment,LiveData
    val review: LiveData<Resource<ReviewDto>>
        get() = _review

    //Status balikan server
    private val _movieGenre  = MutableLiveData<Resource<TrendingDto>>()
    //public untuk di observe di Fragment,LiveData
    val movieGenre: LiveData<Resource<TrendingDto>>
        get() = _movieGenre

    fun getTrending () {
        viewModelScope.launch {
            //loading
            _trending.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    movieRepository.getTrending().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _trending.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _trending.postValue(Resource.error(moshiBean!!.status_message!!, null))
                                }catch (e :java.lang.Exception){
                                    _trending.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _trending.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trending.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _trending.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getGenres () {
        viewModelScope.launch {
            //loading
            _genres.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    movieRepository.getGenres().let {
                        //2xx,3xx response code
                         if (it.isSuccessful) {
                            _genres.postValue(Resource.success(it.body()))
                        } else {
                             val errorBody = it.errorBody()?.string()
                             if(!errorBody.isNullOrEmpty()){
                                 try {
                                     val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                     val moshiBean = jsonAdapter.fromJson(errorBody)
                                     _genres.postValue(Resource.error(moshiBean!!.status_message!!, null))
                                 }catch (e :java.lang.Exception){
                                     _genres.postValue(Resource.error("ERROR", null))
                                 }
                             }
                             else _genres.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _genres.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _genres.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getMovie (code : String) {
        viewModelScope.launch {
            //loading
            _movie.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    movieRepository.getMovie(code).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _movie.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _movie.postValue(Resource.error(moshiBean!!.status_message!!, null))
                                }catch (e :java.lang.Exception){
                                    _movie.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _movie.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movie.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _movie.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getTrailer (code : String) {
        viewModelScope.launch {
            //loading
            _trailer.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    movieRepository.getTrailer(code).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _trailer.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _trailer.postValue(Resource.error(moshiBean!!.status_message!!, null))
                                }catch (e :java.lang.Exception){
                                    _trailer.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _trailer.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _trailer.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _trailer.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getReview (code : String, page : String) {
        viewModelScope.launch {
            //loading
            _review.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    movieRepository.getReview(code, page).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _review.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _review.postValue(Resource.error(moshiBean!!.status_message!!, null))
                                }catch (e :java.lang.Exception){
                                    _review.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _review.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _review.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _review.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getMovieGenre (code : String, page : String) {
        viewModelScope.launch {
            //loading
            _movieGenre.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    movieRepository.getMovieGenre(code, page).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _movieGenre.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _movieGenre.postValue(Resource.error(moshiBean!!.status_message!!, null))
                                }catch (e :java.lang.Exception){
                                    _movieGenre.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _movieGenre.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _movieGenre.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _movieGenre.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }
}