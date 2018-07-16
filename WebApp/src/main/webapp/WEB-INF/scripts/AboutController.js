app.controller('AboutCtrl', ['$scope', '$http', 'AboutService',
function($scope, $http, AboutService) {
    $scope.about = {};

    $scope.callService = function() {
        $scope.about = AboutService.show();
    }

    $scope.callService();
}]);