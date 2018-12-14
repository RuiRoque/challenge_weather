package com.challenge.weather.mvvm.model

import com.challenge.weather.mvvm.model.repository.RepositoryManager

interface ModelContract {
    val repositoryManager: RepositoryManager
}
