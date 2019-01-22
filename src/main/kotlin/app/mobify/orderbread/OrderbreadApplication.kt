package app.mobify.orderbread

import app.mobify.orderbread.entity.Users
import app.mobify.orderbread.repository.UsersRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
class OrderbreadApplication

// https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
@Configuration
class MyConfiguration {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

// https://stackoverflow.com/questions/44749286/spring-boot-insert-sample-data-into-database-upon-startup
@Component
class DemoData {
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val repo: UsersRepository? = null

    @EventListener
    fun appReady(event: ApplicationReadyEvent) {
        val user = Users(ObjectId(), "admin", listOf("ROLE_ADMIN"), passwordEncoder!!.encode("admin"))
        val user2 = Users(ObjectId(), "user", listOf("ROLE_USER"), passwordEncoder.encode("user"))
        val user3 = Users(ObjectId(), "mod", listOf("ROLE_MODERATOR"), passwordEncoder.encode("mod"))

        if (!repo!!.existsByUsername(user.username!!)) {
            repo.save(user)
        }

        if (!repo.existsByUsername(user2.username!!)) {
            repo.save(user2)
        }

        if (!repo.existsByUsername(user3.username!!)) {
            repo.save(user3)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<OrderbreadApplication>(*args)
}
