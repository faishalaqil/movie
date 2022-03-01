package fintest.movie.features.dashboard.di

import fintest.movie.features.dashboard.viewmodel.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        MovieViewModel()
    }
}