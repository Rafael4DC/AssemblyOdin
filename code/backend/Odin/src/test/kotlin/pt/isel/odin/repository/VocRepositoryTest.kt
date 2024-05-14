package pt.isel.odin.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest // Affects the Database
//@DataJpaTest // Clears Affectations after the tests
class VocRepositoryTest{

    @Autowired
    lateinit var vocRepository: VocRepository

    @Test
    fun `Find User Repository`(){
        val vocs = vocRepository.findByStudentId(29)
        println(vocs)
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