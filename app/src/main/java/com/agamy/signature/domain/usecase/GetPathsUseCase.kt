package com.agamy.signature.domain.usecase

import com.agamy.signature.data.repositoryimpl.DrawRepositoryImpl
import javax.inject.Inject

class GetPathsUseCase @Inject constructor(
    private val drawRepositoryImpl: DrawRepositoryImpl
) {
    suspend  operator fun invoke() =
        drawRepositoryImpl.getAllPaths()
}