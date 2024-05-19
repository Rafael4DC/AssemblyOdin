package pt.isel.odin.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

// @DataJpaTest // Clears Affectations after the tests
@SpringBootTest // Affects the Database
class TechRepositoryTest {

    @Autowired
    lateinit var techRepository: TechRepository

    @Test
    fun `Get All Techs`() {
        val techs = techRepository.findAll()
        println(techs)
    }

    /*@Test
    fun `Get Tech By Student Email`(){
        val tech = techRepository.getByStudentId("A47539@alunos.isel.pt")
        println(tech)
    }

    @Test
    fun `Get Tech By Teacher Email`(){
        val tech = techRepository.getByUserId("Tom_Feest@gmail.com")
        println(tech)
    }*/

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
