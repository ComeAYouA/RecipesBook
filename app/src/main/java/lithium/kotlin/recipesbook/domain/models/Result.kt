package lithium.kotlin.recipesbook.domain.models

sealed interface Result<T>{
    data class Success<T>(val data: T): Result<T>
    data class Error<T>(val data: T? = null, val message: String): Result<T>
}