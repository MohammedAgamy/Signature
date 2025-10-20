package com.agamy.signature.domain.usecase

import com.agamy.signature.data.repositoryimpl.DrawRepositoryImpl
import com.agamy.signature.domain.model.DrawModelPath
import javax.inject.Inject

class SavePathUseCase @Inject constructor(
    private val drawRepositoryImpl: DrawRepositoryImpl
) {


    suspend operator fun invoke(path: DrawModelPath) =
        drawRepositoryImpl.savePath(path)

}