package com.sd.nykaa_library.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.nykaa_library.constants.AppConstants

@Entity(tableName = AppConstants.TABLE_SESSION)
data class Sessions(
    @PrimaryKey(autoGenerate = true)
    var id: Long=0,
    var startTime: Long=0,
    var endTime: Long=0,
    var assignedDuration: Int=0,
    var isActive: Boolean=false
)