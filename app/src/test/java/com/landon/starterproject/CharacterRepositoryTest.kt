package com.landon.starterproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.landon.starterproject.data.CharacterRepository
import com.landon.starterproject.models.Character
import com.landon.starterproject.models.Image
import com.landon.starterproject.networking.Network
import com.landon.starterproject.viewModels.CharacterViewModel
import com.landon.starterproject.viewModels.ViewState
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.get
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.lang.Exception
import java.lang.NullPointerException
import java.net.UnknownHostException
import java.util.concurrent.Callable

class CharacterRepositoryTest : KoinTest {

    private val mockNetwork: Network = mock(Network::class.java)
    private lateinit var repository: CharacterRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        startKoin{
            modules(
                module {
                    single { mockNetwork }
                })
        }

        // override Schedulers.io()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        // override AndroidSchedulers.mainThread()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        repository = CharacterRepository()

    }
    //
    @After
    fun tearDown() {
        RxAndroidPlugins.reset();
        stopKoin()
    }


    @Test
    fun testNetworkModelIsInjected() {
        val list = listOf(Character(1, "aaa", "aaa", Image("","")),
            Character(2, "bbb", "bbb", Image("","")))

        declareMock<Network> {
            given(this.getCharacters()).will {
                Observable.just(list)
            }
        }

        assertNotNull(get<Network>())
    }

    @Test
    fun testApiCallToGetCharacters() {
        val list = listOf(Character(1, "aaa", "aaa", Image("","")),
            Character(2, "bbb", "bbb", Image("","")))

        declareMock<Network> {
            given(this.getCharacters()).will {
                Observable.just(list)
            }
        }

        repository.getCharacters()

        Mockito.verify(get<Network>(), Mockito.times(1)).getCharacters()
    }
}