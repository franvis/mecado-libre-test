package francisco.visintini.mercadolibre.data.search.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.ShippingInfo

@JsonClass(generateAdapter = true)
internal data class ShippingDto(
    @Json(name = "free_shipping") val freeShipping: Boolean
) {
    fun mapToDomain() = ShippingInfo(freeShipping = freeShipping)
}

/*
"shipping": {
        "free_shipping": true,
        "mode": "me2",
        "tags": [
          "self_service_in",
          "mandatory_free_shipping"
        ],
        "logistic_type": "drop_off",
        "store_pick_up": false
      }
 */