package francisco.visintini.mercadolibre.domain.error

interface ErrorHandler {

    fun mapError(throwable: Throwable): ErrorEntity

    fun trackError(throwable: Throwable)

    fun logError(throwable: Throwable)
}
