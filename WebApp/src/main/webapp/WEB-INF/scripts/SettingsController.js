app.controller('SettingsCtrl', ['$rootScope', '$scope', '$http', '$translate', 'SettingsService', 'LanguageService',
function($rootScope, $scope, $http, $translate, SettingsService, LanguageService){
    $scope.languages = LanguageService.show();

    $scope.saveSetting = function(code) {
        var newValue = $rootScope.appSettings[code];
        SettingsService.put({setting: code, value: newValue});

        if('LANGUAGE' === code) {
            $translate.use(newValue);
        }
    }
}]);