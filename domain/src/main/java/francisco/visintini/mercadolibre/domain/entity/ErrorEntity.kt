package francisco.visintini.mercadolibre.domain.entity

sealed class ErrorEntity {
    // abstract val throwable: Throwable
    //
    // class NetworkError(override val throwable: Throwable): ErrorEntity()
    //
    // class ParsingError(override val throwable: Throwable): ErrorEntity()
    //
    // class UnknownError(override val throwable: Throwable): ErrorEntity()

    object NetworkError : ErrorEntity()
    object ParsingError : ErrorEntity()
    object NotFound : ErrorEntity()
    object AccessDenied : ErrorEntity()
    object ServiceUnavailable : ErrorEntity()
    object UnknownError : ErrorEntity()
}
