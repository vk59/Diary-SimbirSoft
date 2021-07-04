package com.vk59.diary_simbirsoft.model

object AppConfig {
    val tasks = arrayListOf(
        TaskJson(1, "1625216400", "1625236400", "MyTask", "Описание"),
        TaskJson(2, "1625647000", "1625647400", "MyTask2", "Описание"),
        TaskJson(3, "1625816400", "1625836400", "MyTask3", "Описание")
//        TaskJson(4, "147600000", "147610000", "MyTask4", "Описание")
    )

    val dayInMillis = 86400000L
    val hourInMills = (dayInMillis / 24)
}