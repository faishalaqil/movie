package fintest.movie.features.dashboard.di

import fintest.movie.features.dashboard.data.repository.impl.MovieRepositoryImpl
import org.koin.dsl.module

private const val BACKGROUND_DISPATCHER = "background_dispatcher"
private const val MAIN_DISPATCHER = "main_dispatcher"

val repoModule = module {
    single{
        //get() is constructor injection
        MovieRepositoryImpl(get())
    }
}
