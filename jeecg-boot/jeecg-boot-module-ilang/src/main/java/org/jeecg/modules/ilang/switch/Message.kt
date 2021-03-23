package org.jeecg.modules.ilang.switch

data class Message(
        val header: String = "wifi_",
        var deviceId: String = "",
        var state: String = ""
)
