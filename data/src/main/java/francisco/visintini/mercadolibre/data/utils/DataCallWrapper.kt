package francisco.visintini.mercadolibre.data.utils

import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.error.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Method that ensures that a successful service mapped to domain call of the type T is done
 * in the IO Dispatcher and that any possible error is handled correctly
 *
 * @param errorHandler error handler instance to ensure errors are correctly handled
 * @param successCall service call that also is mapped to <T>
 */
@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> wrapDataCall(
    errorHandler: ErrorHandler,
    successCall: suspend () -> T
): Result<T> {
    return try {
        withContext(Dispatchers.IO) {
            Result.Success(successCall.invoke())
        }
    } catch (throwable: Throwable) {
        errorHandler.trackError(throwable)
        Result.Error(errorHandler.mapError(throwable))
    }
}
