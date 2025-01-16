package edu.cc231008.bookstoreapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//This part helps to initialize the Database
@Database(entities = [BookEntity::class, WishlistEntity::class], version = 5)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDAO

    companion object {
        @Volatile //@Volatile is a keyword that helps to access database very quickly while other threads are running concurrently.
        private var Instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {

            // The code part below ensures that only one thread is created for instance.
            // If Instance already exists, it is returned immediately, avoiding any unnecessary object creation.
            // If Instance is null, the code inside the synchronized block is executed to create a new instance.
            return Instance ?: synchronized(this) {
                //This part ensures the database is created correctly with the schema defined in the PostDatabase class.
                val instance = Room.databaseBuilder(
                    context,
                    BookDatabase::class.java, //class serves as the blueprint for the database schema.
                    "book_database"
                )
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     * "migration" - refers to the process of updating the database schema to a new version.
                     * That is why we defined version (version = 1)
                     */
                    .fallbackToDestructiveMigration() //This part will attempt to migrate the database, if the database schema is updated.
                    .build() //This method finalizes the creation of the database instance.
                Instance = instance //The newly created database instance is assigned to the Instance variable.
                return instance
            }
        }
    }
}