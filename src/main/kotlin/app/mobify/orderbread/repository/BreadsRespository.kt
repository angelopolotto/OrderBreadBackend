package app.mobify.orderbread.repository

import app.mobify.orderbread.entity.Breads
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource(collectionResourceRel = "breads", path = "breads")
interface BreadsRespository: MongoRepository<Breads, String> {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findByName(@Param("name") name: String): List<Breads>
}