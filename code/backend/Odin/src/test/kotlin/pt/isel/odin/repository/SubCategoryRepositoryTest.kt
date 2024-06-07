package pt.isel.odin.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.jvm.optionals.toList

// @DataJpaTest // Clears Affectations after the tests
@SpringBootTest // Affects the Database
class SubCategoryRepositoryTest {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    fun `Get Category`() {
        val cat = categoryRepository.findById(1)
        println(cat.toList())
    }

    /*@Test
    fun `Save User Repository`(){
        val user = User(
            username = "test",
            email = "asdsad@gmail.com",
        )
        userRepository.save(user)
    }

    @Test
    fun `Find User Repository`(){
        val user = userRepository.findById(1)
        println(user)
    }

    @Test
    fun `Delete User Repository`(){
        userRepository.deleteById(1)
    }*/
}
