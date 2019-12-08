package com.wbelote.climbtracker

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.*
import kotlinx.coroutines.launch


@Entity
data class Problem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val area: Int,
    val grade: String,
    val color: String,
    val setter: String,
    val date: Int
)
@Entity
data class ProblemInfo(
    @ColumnInfo val area: Int,
    @ColumnInfo val grade: String,
    @ColumnInfo val color: String,
    @ColumnInfo val setter: String,
    @ColumnInfo val date: Int
)


@Dao
interface ClimbDao {

    @Insert(entity = Problem::class)
    fun addProblem(p: ProblemInfo)

    @Query("SELECT area, grade, color, setter, date FROM Problem")
    fun activeProblems(): LiveData<List<ProblemInfo>>

//    @Query("SELECT area, grade, color, setter, date FROM Problem WHERE area = :area")
//    fun problemsForArea(area: Int): LiveData<List<ProblemInfo>>

}


@Database(entities = [Problem::class], version = 1)
abstract class ClimbDatabase : RoomDatabase() {

    abstract fun getDao(): ClimbDao

    companion object {
        @Volatile
        private var INSTANCE: ClimbDatabase? = null

        fun getDatabase(context: Context): ClimbDatabase {

            val tempInstance = INSTANCE
            tempInstance?.let {
                return it
            } ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClimbDatabase::class.java,
                    "climb"
                ).build()

                INSTANCE = instance
                return instance

            }

        }
    }

}


class ClimbViewModel(app: Application) : AndroidViewModel(app) {

    private val db: ClimbDao = ClimbDatabase.getDatabase(app).getDao()
    val allProblems: LiveData<List<ProblemInfo>>
    init {
        allProblems = db.activeProblems()
    }

    fun addProblem(p: ProblemInfo) = viewModelScope.launch {
        db.addProblem(p)
    }

}