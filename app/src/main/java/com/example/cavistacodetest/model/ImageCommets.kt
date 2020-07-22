package com.example.cavistacodetest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_comments")
 open class ImageComments{

    @PrimaryKey(autoGenerate = true) var id: Int = 0
        get() {
            return field
        }
        set(value) {field = value}

    @ColumnInfo(name = "imageId") var imageId:String ? =null

    @ColumnInfo(name = "comments")var comments:String? = null

    constructor(imageId: String?, comments: String?) {
        this.imageId = imageId
        this.comments = comments
    }

}