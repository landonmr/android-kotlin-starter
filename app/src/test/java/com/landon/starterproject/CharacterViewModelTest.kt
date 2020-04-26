package com.landon.starterproject

import com.landon.starterproject.data.CharacterRepository
import com.landon.starterproject.models.Character
import com.landon.starterproject.models.Image
import com.landon.starterproject.viewModels.CharacterViewModel
import com.landon.starterproject.viewModels.ViewState
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.net.UnknownHostException


//@RunWith(MockitoJUnitRunner::class)
class CharacterViewModelTest : BaseTest() {

    private val mockRepo: CharacterRepository = mock(CharacterRepository::class.java)
    private lateinit var vm: CharacterViewModel

    @Before
    override fun before() {
        super.before()
        startKoin{
            modules(
                module {
                    single { mockRepo }
                })
        }

        vm = CharacterViewModel()

    }

    @After
    override fun tearDown() {
        super.tearDown()
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