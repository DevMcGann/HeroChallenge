package com.intermedia.challenge.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.intermedia.challenge.data.models.NetResult
import com.intermedia.challenge.data.services.ComicService
import java.time.Instant
import java.util.*

class ComicsRepository (
    private val comicService: ComicService
): BaseRepository() {

    private val year = Calendar.getInstance().get(Calendar.YEAR)

    suspend fun getComics(offset: Int = 25, limit: Int = 25, orderBy : String = "onsaleDate", startYear : Int = year): NetResult<ComicService.ComicResponse> =
        handleResult(comicService.getComics(authParams.getMap(), offset, limit, orderBy, startYear))
}