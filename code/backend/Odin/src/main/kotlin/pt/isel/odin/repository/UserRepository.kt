package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.User

abstract class UserRepository: JpaRepository<User, Long> {
}

