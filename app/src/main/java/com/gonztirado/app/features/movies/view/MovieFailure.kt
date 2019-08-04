package com.gonztirado.app.features.movies.view

import com.gonztirado.app.util.exception.Failure.FeatureFailure

class MovieFailure {
    class ListNotAvailable: FeatureFailure()
    class NonExistentMovie: FeatureFailure()
}

