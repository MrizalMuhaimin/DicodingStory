package com.example.dicodingstory.data.db.remote


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.dicodingstory.data.db.entities.KeyEntity
import com.example.dicodingstory.data.db.entities.ListStoryEntity
import com.example.dicodingstory.data.db.room.StoryRoomDatabase
import com.example.dicodingstory.data.network.api.dicodingstory.ApiServiceDicodingStory



@ExperimentalPagingApi
class RemoteMediatorStory (
    private val database: StoryRoomDatabase,
    private val apiService: ApiServiceDicodingStory,
    private val token: String

    ) : RemoteMediator<Int, ListStoryEntity>()
{
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val responseData = apiService.getStory(token, page, state.config.pageSize)
            val endOfPaginationReached = responseData.listStory.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.KeyStoryDao().deleteKeys()
                    database.StoryDao().deleteAll()

                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached ) null else page + 1
                val keys = responseData.listStory.map { keys ->
                    KeyEntity(id = keys.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.KeyStoryDao().addAll(keys)
                database.StoryDao().addStory(responseData.listStory)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListStoryEntity>): KeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.KeyStoryDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListStoryEntity>): KeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.KeyStoryDao().getRemoteKeysId(data.id)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListStoryEntity>): KeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.KeyStoryDao().getRemoteKeysId(id)

            }
        }
    }
}