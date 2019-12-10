package com.wbelote.climbtracker

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.room.*
import kotlinx.coroutines.launch


@Entity
data class Problem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val area: Int,
    val grade: String,
    val color: Int,
    val setter: String,
    val date: Int
)
@Entity
data class ProblemInfo(
    @ColumnInfo val area: Int,
    @ColumnInfo val grade: String,
    @ColumnInfo val color: Int,
    @ColumnInfo val setter: String,
    @ColumnInfo val date: Int
)

@Entity
data class Attempt(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val problemID: Int,
    val started: Boolean,
    val finished: Boolean,
    val date: Int
)

@Entity
data class AttemptInfo(
    @ColumnInfo val problemID: Int,
    @ColumnInfo val started: Boolean,
    @ColumnInfo val finished: Boolean,
    @ColumnInfo val date: Int
)


@Dao
interface ClimbDao {

    @Insert(entity = Problem::class)
    suspend fun addProblem(p: ProblemInfo): Long

    @Query("SELECT * FROM Problem")
    fun activeProblems(): LiveData<List<Problem>>

    @Query("SELECT * FROM Problem WHERE area = :area")
    fun problemsForArea(area: Int): LiveData<List<Problem>>

    @Query("SELECT * FROM Problem WHERE id = :id")
    fun problemForId(id: Int): List<Problem>

    @Insert(entity = Attempt::class)
    fun addAttempt(a: AttemptInfo)

}


@Database(entities = [Problem::class, Attempt::class], version = 1)
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
    private val allProblems: LiveData<List<Problem>>
    val areaProblems: LiveData<List<Problem>>
    init {
        allProblems = db.activeProblems()
        areaProblems = db.problemsForArea(GymInfo.currentArea.id)
    }

    var lastProblemID = -1

    fun addProblem(p: ProblemInfo) = viewModelScope.launch {
        lastProblemID = db.addProblem(p).toInt()
    }

    fun lastProblem() = db.problemForId(lastProblemID).firstOrNull()

    fun addAttempt(a: AttemptInfo) = viewModelScope.launch {
        db.addAttempt(a)
    }

}

class ClimbViewModelFactory(private var application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(Application::class.java).newInstance(application)
}