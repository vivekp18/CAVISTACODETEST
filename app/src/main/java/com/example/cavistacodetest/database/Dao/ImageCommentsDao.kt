package com.example.cavistacodetest.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cavistacodetest.model.ImageComments


@Dao
 interface ImageCommentsDao {

    @get:Query("SELECT * FROM image_comments" )
    val getAllImageShapesList : LiveData<List<ImageComments>>?

    @Query("SELECT * FROM image_comments WHERE imageId =:image_id")
    fun getSingleImageComment(image_id:String) : LiveData<ImageComments>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageShapes: ImageComments) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageShapes: List<ImageComments>)


    @Query("UPDATE image_comments SET comments = :comments WHERE imageId =:image_id")
    fun update(comments: String?, image_id:String)

   @Query("DELETE FROM image_comments ")
    fun deleteAll()
}

