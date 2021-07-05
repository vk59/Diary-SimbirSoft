package com.vk59.diary_simbirsoft.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class TaskRealm(): RealmObject() {
    var id: Long? = null
    var dateStart: String? = null
    var dateFinish: String? = null
    var name: String? = null
    var description: String? = null
}