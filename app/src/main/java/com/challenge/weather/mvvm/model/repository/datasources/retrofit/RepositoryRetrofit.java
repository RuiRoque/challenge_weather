package com.challenge.weather.mvvm.model.repository.datasources.retrofit;

import com.challenge.weather.mvvm.model.repository.RepositoryCache;

import javax.inject.Inject;

public class RepositoryRetrofit {

    private BackendApi backendApi;
    private RepositoryCache repositoryCache;
    private static final Object LOCK = new Object();

    @Inject
    public RepositoryRetrofit(RepositoryCache repositoryCache, BackendApi backendApi) {
        this.repositoryCache = repositoryCache;
        this.backendApi = backendApi;
    }

    public <T> T getRetrofitServices(Class<T> service) {
        T retrofitService;
        synchronized (LOCK) {
            retrofitService = (T) repositoryCache.getRetrofitServicesFromCache(service.getName());
            if (retrofitService == null) {
                retrofitService = createRetrofitServices(service);
                repositoryCache.storeRetrofitServicesInCache(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }

    private <T> T createRetrofitServices(Class<T> service) {
        return backendApi.createApiEndpoints(service);
    }

    public String getFullUrl(String relativeUrl) {
        return backendApi.getFullUrl(relativeUrl);
    }
}
