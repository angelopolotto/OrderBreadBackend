package app.mobify.orderbread.security

import app.mobify.orderbread.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*


@Component
class MongoUserDetailsService : UserDetailsService {
    @Autowired
    private val repository: UsersRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository!!.findByUsername(username)

        val authorities = Arrays.asList(SimpleGrantedAuthority(user.role))

        return User(user.username, user.password, authorities)
    }
}