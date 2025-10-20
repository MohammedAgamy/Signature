package com.agamy.signature.domain.usecase

import com.agamy.signature.data.repositoryimpl.DrawRepositoryImpl
import javax.inject.Inject

class ClearPathsUseCase @Inject constructor(
    private val drawRepositoryImpl: DrawRepositoryImpl
) {

    suspend  operator fun invoke() =
        drawRepositoryImpl.clearPaths()
}