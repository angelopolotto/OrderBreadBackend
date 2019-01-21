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

//https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
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
    fun bCryptPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@Component
class DemoData {
    @Autowired
    private val bCryptPasswordEncoder: PasswordEncoder? = null

    @Autowired
    private val repo: UsersRepository? = null

    @EventListener
    fun appReady(event: ApplicationReadyEvent) {
        val user = Users(ObjectId(), "admin", "ADMIN", bCryptPasswordEncoder!!.encode("admin"))

        if (!repo!!.existsByUsername(user.username!!)) {
            repo.save(user)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<OrderbreadApplication>(*args)
}
