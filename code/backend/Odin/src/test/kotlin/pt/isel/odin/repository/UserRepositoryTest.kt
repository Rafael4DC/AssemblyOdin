package pt.isel.odin.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

// @DataJpaTest // Clears Affectations after the tests
@SpringBootTest // Affects the Database
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `Find User Repository`() {
        val user = userRepository.findByEmail("A47539@alunos.isel.pt")
        println(user!!.username)
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
