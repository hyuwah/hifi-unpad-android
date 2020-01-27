package unpad.fmipa.hifi.android.app.di

import org.koin.dsl.module
import unpad.fmipa.hifi.android.data.remote.RemoteSource
import unpad.fmipa.hifi.android.data.remote.RemoteSourceImpl
import unpad.fmipa.hifi.android.data.remote.RepositoryImpl
import unpad.fmipa.hifi.android.domain.Repository

val dataModule = module {

    single<RemoteSource> { RemoteSourceImpl(get()) }

    single<Repository> { RepositoryImpl(get()) }

}