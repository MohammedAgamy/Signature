package com.agamy.signature.domain.repository

import com.agamy.signature.domain.model.DrawModelPath
// contract for drawing repository to handle saving and retrieving drawn paths
// provides methods to save a drawn path and get all saved paths
// using interface segregation to define clear responsibilities
// this interface can be implemented by different data sources (e.g., in-memory, database, etc.)
// ensuring flexibility and scalability in managing drawn paths
// methods are marked as suspend to support coroutine-based asynchronous operations
//clearPaths method to remove all saved paths
interface DrawRepository {
    suspend fun savePath(path: DrawModelPath)
    suspend fun getAllPaths(): List<DrawModelPath>
    suspend fun clearPaths()

}