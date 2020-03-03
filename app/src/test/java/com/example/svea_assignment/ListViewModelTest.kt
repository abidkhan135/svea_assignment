package com.example.svea_assignment

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.svea_assignment.di.DaggerViewModelComponent
import com.example.svea_assignment.model.Place
import com.example.svea_assignment.model.PlaceApiService
import com.example.svea_assignment.model.Places
import com.example.svea_assignment.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var placeService: PlaceApiService

    val application = Mockito.mock(Application::class.java)
    var listViewModel = ListViewModel(application, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .apiModule(ApiModuleTest(placeService))
            .build()
            .inject(listViewModel)
    }

    @Test
    fun getPlacesSuccess() {
        val place = Place("STOCKHOLM", "stockholm", 1234.34, 435.56, "This is test", "//test")
        val placeList = listOf(place)
        val places = Places(placeList, 10)
        val testSingle = Single.just(places)
        Mockito.`when`(placeService.getAllPlaces()).thenReturn(testSingle)
        listViewModel.refresh()
        Assert.assertEquals(1, listViewModel.place.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getPlacesFailure() {
        val testSingle = Single.error<Places>(Throwable())
        Mockito.`when`(placeService.getAllPlaces()).thenReturn(testSingle)
        listViewModel.refresh()
        Assert.assertEquals(null, listViewModel.place.value)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}