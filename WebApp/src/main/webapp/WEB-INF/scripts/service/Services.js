//This file contains some smaller services, it is not really necessary to put them in separate files
//About Service
app.factory('AboutService', function ($resource) {
    return $resource('/api/about', {}, {
        show: { method: 'GET' }
    })
});

//Query Service
app.factory('QueryService', function ($resource) {
    return $resource('/api/query', {}, {
        query: {method: 'POST'}
    })
});

//Dialect Service
app.factory('DialectService', function ($resource) {
    return $resource('api/dialect', {}, {
        show: { method: 'GET', isArray:true }
    })
});

//Database information service
app.factory('DbInfoService', function ($resource) {
    return $resource('/api/dbinfo/:id', {}, {
        get: { method: 'GET', params:{id: '@id'} }
    })
});

//Connection Service
app.factory('ConnectionDetailService', function ($resource) {
    return $resource('/api/connection/:id', {}, {
        show: { method: 'GET', isArray: true },
        get: { method: 'GET', params:{id: '@id'} },
        post: { method: 'POST'},
        put: { method: 'PUT', params: { id: '@id' }},
        delete: { method: "DELETE", params: {id: '@id'}}
    })
});
//Export Service
app.factory('ExportService', function($resource) {
    return $resource('/api/export?c=:id', {}, {
        get:{ method: 'GET', params:{id: '@id'}}
    });
});
//Settings Service
app.factory('SettingsService', function($resource){
    return $resource('/api/settings/:setting', {}, {
        show: {method: 'GET'},
        put: {method:'PUT', params:{setting: '@setting'}}
    })
});
//Language Service
app.factory('LanguageService', function($resource){
    return $resource('/api/language', {}, {
        show: {method: 'GET', isArray: true}
    })
});
//Log Polling
