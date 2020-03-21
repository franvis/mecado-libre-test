package francisco.visintini.mercadolibre.domain.entity

sealed class Result<T> {
    class Success<T>(val result: T) : Result<T>()
    class Error<T>(val error: ErrorEntity) : Result<T>()
}
