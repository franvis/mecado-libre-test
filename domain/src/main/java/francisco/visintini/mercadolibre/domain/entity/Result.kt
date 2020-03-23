package francisco.visintini.mercadolibre.domain.entity

import francisco.visintini.mercadolibre.domain.error.ErrorEntity

sealed class Result<T> {
    class Success<T>(val result: T) : Result<T>()
    class Error<T>(val error: ErrorEntity) : Result<T>()
}
