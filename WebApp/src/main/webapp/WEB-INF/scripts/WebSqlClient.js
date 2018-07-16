'use strict';
var app = angular.module('WebSqlClient', ['ngRoute', 'ngResource', 'ngTable', 'ngTableExport', 'pascalprecht.translate']);

app.config(function($routeProvider){
    $routeProvider
        .when('/', {
            templateUrl: 'pages/partials/Workspace.html',
            controller: 'SqlQueryCtrl'
        })
        .when('/workspace', {
            templateUrl: 'pages/partials/Workspace.html',
            controller: 'SqlQueryCtrl'
        })
        .when('/configuration', {
            templateUrl: 'pages/partials/configuration.html',
        })
        .when('/settings', {
            templateUrl: 'pages/partials/settings.html',
            controller: 'SettingsCtrl'
        })
        .when('/about', {
            templateUrl: 'pages/partials/about.html',
            controller: 'AboutCtrl'
        })
        .when('/dbinfo',{
            templateUrl: 'pages/partials/database_info.html',
            controller: 'DbInfoCtrl'
        })
        .when('/stats',{
            templateUrl: 'pages/partials/statistics.html',
            controller: 'StatisticsCtrl'
        })
        .when('/fileupload', {
            templateUrl: 'pages/partials/fileupload.html'
        })
        ;
});

//Translation provider
app.config(function($translateProvider) {
    $translateProvider.useStaticFilesLoader({
        prefix: 'api/translation/',
        suffix: ''
    });

    $translateProvider.preferredLanguage('en');
});

//Startup configuration
app.run(['$rootScope', '$translate', 'SettingsService', 'ConnectionDetailService',
    function($rootScope, $translate, SettingsService, ConnectionDetailService)
    {
        $rootScope.appSettings = SettingsService.show(function(){
            $translate.use($rootScope.appSettings.LANGUAGE);
        });
        $rootScope.connections = ConnectionDetailService.show();
    }
]);

//Start websocket
