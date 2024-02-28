package lithium.kotlin.recipesbook.core.model

sealed interface Result<T>{
    data class Success<T>(val data: T): Result<T>
    data class Error<T>(val data: T? = null, val message: String): Result<T>
}