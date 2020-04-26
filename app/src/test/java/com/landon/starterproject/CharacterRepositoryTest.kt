package com.landon.starterproject

import com.landon.starterproject.data.CharacterRepository
import com.landon.starterproject.models.Character
import com.landon.starterproject.models.Image
import com.landon.starterproject.networking.Network
import io.reactivex.Observable
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.get
import org.koin.dsl.module
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.mock

class CharacterRepositoryTest : BaseTest() {

    private val mockNetwork: Network = mock(Network::class.java)
    private lateinit var repository: CharacterRepository

    @Before
    override fun before() {
        super.before()
        startKoin{
            modules(
                module {
                    single { mockNetwork }
                })
        }

        repository = CharacterRepository()

    }
    //
    @After
    override fun tearDown() {
        super.tearDown()
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