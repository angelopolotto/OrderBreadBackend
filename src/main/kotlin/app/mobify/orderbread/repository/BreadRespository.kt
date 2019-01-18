package app.mobify.orderbread.repository


import app.mobify.orderbread.entity.Bread
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "breads", path = "breads")
interface BreadRespository: MongoRepository<Bread, String> {
    fun findByName(@Param("name") name: String): List<Bread>
}