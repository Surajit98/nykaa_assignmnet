package com.sd.nykaa_library.constants

class AppConstants() {

    companion object {

        const val DB_NAME: String = "app_database"
        const val TABLE_SESSION: String = "sessions"

        const val CHANNEL_NAME = "Nykaa Session"
        const val CHANNEL_ID = "Nykaa Session Channel"


        const val START_SERVICE = "start_nykaa_session_service"
        const val STOP_SERVICE = "stop_nykaa_session_service"
        const val FROM_REBOOT = "from_reboot_nykaa_session_service"
    }


}