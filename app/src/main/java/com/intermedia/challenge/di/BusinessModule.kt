package com.intermedia.challenge.di

import com.intermedia.challenge.ui.characters.CharactersViewModel
import com.intermedia.challenge.data.repositories.CharactersRepository
import com.intermedia.challenge.data.repositories.ComicsRepository
import com.intermedia.challenge.ui.comics.ComicsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessModule = module {

    viewModel { CharactersViewModel(get()) }
    viewModel { ComicsViewModel(get()) }

    single { CharactersRepository(get()) }
    single { ComicsRepository(get()) }
}