package app.mobify.orderbread.repository

import app.mobify.orderbread.entity.Breads
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize

// https://docs.spring.io/spring-data/rest/docs/current/reference/html/
// https://stackoverflow.com/questions/42506546/spring-data-rest-is-there-a-way-to-restrict-the-supported-operations
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "breads", path = "breads")
interface BreadsRepository : MongoRepository<Breads, String> {
    // overrides
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOD', 'ROLE_USER')")
    override fun findAll(p0: Pageable): Page<Breads>

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOD')")
    override fun deleteById(p0: String)

    // queries
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOD', 'ROLE_USER')")
    fun findByNameContains(@Param("name") name: String): List<Breads>
}