package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.model.User
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.UserService

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElseThrow { NotFoundException("No User Found") }
    }

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun save(user: User): User {
        return userRepository.save(user)
    }

    override fun delete(id: Long) {
        userRepository.deleteById(id)
    }
}
