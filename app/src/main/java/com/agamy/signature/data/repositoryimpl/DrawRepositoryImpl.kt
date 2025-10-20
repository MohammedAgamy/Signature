package com.agamy.signature.data.repositoryimpl

import com.agamy.signature.domain.model.DrawModelPath
import com.agamy.signature.domain.repository.DrawRepository
import javax.inject.Inject
import javax.inject.Singleton

// implementation of DrawRepository to handle saving and retrieving drawn paths
// currently methods are not yet implemented
// marked as Singleton for dependency injection
// uses @Inject constructor for Hilt to provide instances
// this class will manage the data operations related to drawn paths


@Singleton
class DrawRepositoryImpl @Inject constructor() : DrawRepository {

    // in-memory storage for drawn paths
    // using mutable list to store DrawModelPath objects
    // this is a simple implementation and can be replaced with persistent storage later
    // methods to save and retrieve paths are implemented to interact with this list
    private val path= mutableListOf<DrawModelPath>()

    // saves a drawn path to the in-memory list
    override suspend fun savePath(path: DrawModelPath) {
        this.path.add(path)
    }

    // retrieves all saved drawn paths from the in-memory list
    override suspend fun getAllPaths(): List<DrawModelPath> = path

    override suspend fun clearPaths() {
       this.path.clear()
    }

}