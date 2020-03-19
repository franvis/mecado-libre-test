package francisco.visintini.mercadolibre.domain.repository

import francisco.visintini.mercadolibre.domain.entity.ErrorEntity

interface ErrorHandler {

    fun mapError(throwable: Throwable): ErrorEntity

    fun trackError(throwable: Throwable)

    fun logError(throwable: Throwable)
}
