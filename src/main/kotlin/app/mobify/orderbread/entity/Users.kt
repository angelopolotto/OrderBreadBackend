package app.mobify.orderbread.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id


class Users(
        @Id
        var id: ObjectId?,
        var username: String?,
        var roles: List<String>?,
        var password: String?)