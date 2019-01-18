package app.mobify.orderbread.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(value = "breads")
class Bread @PersistenceConstructor constructor(
        @Id
        var id: String?,
        var name: String?,
        var thumbnail: String?,
        var images: MutableList<String>?,
        var description: String?,
        var combinations: String?,
        var flavor: String?,
        var nutritional: String?,
        var ingredients: String?,
        var allergic: String?,
        var durability: String?,
        var dimensions: String?,
        var price: BigDecimal?,
        var currency: String?) {

    override fun toString(): String {
        return "Bread(id=$id, name='$name', description='$description')"
    }
}