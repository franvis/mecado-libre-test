package francisco.visintini.mercadolibre.test.messages

sealed class ErrorMessage {
    data class NetworkErrorRetry(val onRetryTapped: () -> Unit) : ErrorMessage()
}
