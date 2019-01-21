package app.mobify.orderbread.repository

import app.mobify.orderbread.entity.Users
import org.springframework.data.mongodb.repository.MongoRepository


interface UsersRepository : MongoRepository<Users, String> {
    fun findByUsername(username: String): Users
    fun existsByUsername(username: String): Boolean
}