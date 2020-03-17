package francisco.visintini.mercadolibre.domain.entity

sealed class Result<T> {
    class Success<T>(val t: T) : Result<T>()
    class Error<T>(val error: ErrorEntity) : Result<T>()
}
