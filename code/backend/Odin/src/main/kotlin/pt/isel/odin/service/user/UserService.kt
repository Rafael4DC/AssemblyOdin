package pt.isel.odin.service.user

import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.user.models.UserRequest
import pt.isel.odin.http.controllers.user.models.toUser
import pt.isel.odin.model.User
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.NotFoundException
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

@Service
class UserService(private val userRepository: UserRepository) {

    fun getById(id: String): User {
        /*val optionalUser =*/ return userRepository.findById(id).get()

        //return success(optionalUser)
        /*.orElseGet {
            failure(NotFoundException("User with id $id not found"))
        }*/
    }

    fun getByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getAll(): List<User> {
        return userRepository.findAll()
    }

    fun save(userRequest: UserRequest): User {
        return userRepository.save(userRequest.toUser())
    }

    fun delete(id: String) {
        userRepository.deleteById(id)
    }
}
