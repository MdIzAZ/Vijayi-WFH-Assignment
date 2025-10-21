import com.example.vijayiwfhassignment.data.api.WatchmodeApiService
import com.example.vijayiwfhassignment.data.mapper.toTitleDetails
import com.example.vijayiwfhassignment.domain.models.TitleDetails
import com.example.vijayiwfhassignment.domain.repo.TitlesRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TitlesRepositoryImpl(
    private val apiService: WatchmodeApiService
) : TitlesRepository {

    override fun getMoviesAndTvShows(): Single<Pair<List<TitleDetails>, List<TitleDetails>>> {
        val moviesWithDetailsSingle = fetchDetailsForType("movie")
        val tvShowsWithDetailsSingle = fetchDetailsForType("tv_series")

        return Single.zip(
            moviesWithDetailsSingle,
            tvShowsWithDetailsSingle,
            BiFunction { movies, tvShows -> Pair(movies, tvShows) }
        )
    }

    private fun fetchDetailsForType(type: String): Single<List<TitleDetails>> {
        return apiService.getTitles(types = type)
            .flatMap { titlesResponse ->
                val body = titlesResponse.body()
                if (!titlesResponse.isSuccessful || body == null) {
                    return@flatMap Single.error(Exception(mapHttpError(titlesResponse.code())))
                }

                if (body.titles.isEmpty()) {
                    return@flatMap Single.just(emptyList())
                }

                val detailRequests = body.titles.map { titleDto ->
                    apiService.getTitleDetails(titleDto.id)
                        .flatMap { response ->
                            if (response.isSuccessful && response.body() != null) {
                                Single.just(response.body()!!.toTitleDetails())
                            } else {
                                Single.error(Exception(mapHttpError(response.code())))
                            }
                        }
                }

                Single.zip(detailRequests) { results ->
                    results.map { it as TitleDetails }
                }
            }
    }

    override fun getTitleDetails(titleId: Int): Single<TitleDetails> {
        return apiService.getTitleDetails(titleId)
            .flatMap { response ->
                if (response.isSuccessful && response.body() != null) {
                    Single.just(response.body()!!.toTitleDetails())
                } else {
                    Single.error(Exception(mapHttpError(response.code())))
                }
            }
            .onErrorResumeNext { throwable: Throwable ->
                val message = when (throwable) {
                    is UnknownHostException -> "No internet connection or unable to reach server."
                    is SocketTimeoutException -> "Request timed out. Please try again."
                    else -> throwable.message ?: "An unknown error occurred."
                }
                Single.error(Exception(message))
            }
    }

    fun mapHttpError(code: Int): String = when (code) {
        200 -> "Request successful."
        400 -> "Bad Request: Your request parameters are incorrect."
        401 -> "Unauthorized: Invalid or missing API key."
        403 -> "Forbidden: Your API key doesn't have permission for this request."
        404 -> "Not Found: The requested resource doesn't exist."
        429 -> "Too Many Requests: You have hit the rate limit. Please try again later."
        500 -> "Internal Server Error: Something went wrong on Watchmode's server."
        502 -> "Bad Gateway: Watchmode server received an invalid response."
        503 -> "Service Unavailable: Watchmode server is temporarily down or busy."
        504 -> "Gateway Timeout: Watchmode server took too long to respond."
        else -> "Unexpected Error: HTTP $code"
    }
}
