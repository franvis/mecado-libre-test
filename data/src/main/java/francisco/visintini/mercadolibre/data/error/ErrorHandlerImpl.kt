package francisco.visintini.mercadolibre.data.error

import android.util.Log
import com.squareup.moshi.JsonDataException
import francisco.visintini.mercadolibre.domain.entity.ErrorEntity
import francisco.visintini.mercadolibre.domain.repository.ErrorHandler
import java.io.IOException
import java.net.HttpURLConnection
import retrofit2.HttpException

class ErrorHandlerImpl : ErrorHandler {
    override fun mapError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.NetworkError
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied

                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable

                    else -> ErrorEntity.UnknownError
                }
            }
            is JsonDataException -> ErrorEntity.ParsingError
            else -> ErrorEntity.UnknownError
        }
    }

    /**
     * Tracks error to the different tracking systems.
     */
    override fun trackError(throwable: Throwable) {
        // NO OP.

        // TODO Write tracking logic for different kinds of throwable here of what and when you want to track.
        //  If more than one tracking system is needed (Google analytics, firebase, etc...), depending on the business,
        //  then this function can be refactored into a better solution
    }

    /**
     * Tracks error to the different tracking systems.
     */
    override fun logError(throwable: Throwable) {
        Log.e("Data layer error", throwable.localizedMessage?.toString()?.let { it } ?: "No localized message")

        // TODO Write logging logic for different kinds of throwable here of what and when you want to log.
        //  If more than one logging system is needed (Splunk, LogDNA, etc...), depending on the business,
        //  then this function can be refactored into a better solution
    }
}
