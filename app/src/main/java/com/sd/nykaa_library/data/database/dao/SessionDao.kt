package com.sd.nykaa_library.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sd.nykaa_library.data.database.entity.Sessions

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(session: Sessions):Long

    @Query("select * from sessions")
    fun getSessions(): LiveData<List<Sessions>>

    @Query("select * from sessions where isActive=1")
    fun getActiveSession(): Sessions

    @Query("select * from sessions")
    fun getAllSessionsSingle(): Sessions

    @Update
    fun updateSession(session: Sessions)


}