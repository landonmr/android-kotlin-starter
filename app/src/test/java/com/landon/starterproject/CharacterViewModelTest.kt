package com.landon.starterproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.landon.starterproject.data.CharacterRepository
import com.landon.starterproject.models.Character
import com.landon.starterproject.models.Image
import com.landon.starterproject.viewModels.CharacterViewModel
import com.landon.starterproject.viewModels.ViewState
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.lang.Exception
import java.lang.NullPointerException
import java.net.UnknownHostException
import java.util.concurrent.Callable


//@RunWith(MockitoJUnitRunner::class)
class CharacterViewModelTest : KoinTest {

    private val mockRepo: CharacterRepository = mock(CharacterRepository::class.java)
    private lateinit var vm: CharacterViewModel

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
                    single { mockRepo }
                })
        }

        // override Schedulers.io()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        // override AndroidSchedulers.mainThread()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        vm = CharacterViewModel()

    }
//
    @After
    fun tearDown() {
        RxAndroidPlugins.reset();
        stopKoin()
    }

    @Test
    fun testCharacterReturnedFromRepository() {
        val list = listOf(Character(1, "aaa", "aaa", Image("","")),
            Character(2, "bbb", "bbb", Image("","")))

        declareMock<CharacterRepository> {
            given(this.getCharacters()).will {
                Observable.just(list)
            }
        }

        vm.loadCharacters()

        assertEquals(list.size, vm.characters.size)
    }

    @Test
    fun testLoadedStateWhenDataFetchSuccess() {
        val list = listOf(Character(1, "aaa", "aaa", Image("","")),
            Character(2, "bbb", "bbb", Image("","")))

        declareMock<CharacterRepository> {
            given(this.getCharacters()).will {
                Observable.just(list)
            }
        }

        vm.loadCharacters()
        assertEquals(ViewState.Loaded, vm.state.value)
    }

    @Test
    fun testErrorStateOnWhenDataFetchFails() {
        val list = listOf(Character(1, "aaa", "aaa", Image("","")),
            Character(2, "bbb", "bbb", Image("","")))

        declareMock<CharacterRepository> {
            given(this.getCharacters()).will {
                Observable.error<Exception>(UnknownHostException())
            }
        }

        vm.loadCharacters()
        assertEquals(ViewState.Error, vm.state.value)
    }
}