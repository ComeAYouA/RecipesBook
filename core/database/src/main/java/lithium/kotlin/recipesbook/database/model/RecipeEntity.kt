package lithium.kotlin.recipesbook.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity(
    @PrimaryKey
    val id: Long,
    val isBookMarked: Boolean
)