package lithium.kotlin.recipesbook.domain.datastore

import lithium.kotlin.recipesbook.domain.models.Recipe

interface BookmarksDao {

    fun getBookmarks(): List<Recipe>

    fun addBookmark(bookmark: Recipe)
}