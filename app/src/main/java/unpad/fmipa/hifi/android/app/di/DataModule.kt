package unpad.fmipa.hifi.android.app.di

import org.koin.dsl.module
import unpad.fmipa.hifi.android.data.remote.RemoteSource
import unpad.fmipa.hifi.android.data.remote.RemoteSourceImpl
import unpad.fmipa.hifi.android.data.remote.RepositoryImpl
import unpad.fmipa.hifi.android.data.remote.mapper.CalendarEventAirtableMapper
import unpad.fmipa.hifi.android.data.remote.mapper.Mapper
import unpad.fmipa.hifi.android.data.remote.model.Record
import unpad.fmipa.hifi.android.domain.Repository
import unpad.fmipa.hifi.android.domain.model.AirtableCalendarEvent

val dataModule = module {

    single<Mapper<List<Record>, List<AirtableCalendarEvent>>> { CalendarEventAirtableMapper() }

    single<RemoteSource> { RemoteSourceImpl(get()) }

    single<Repository> { RepositoryImpl(get(), get()) }

}