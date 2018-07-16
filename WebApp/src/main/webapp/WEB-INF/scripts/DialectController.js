app.controller('DialectCtrl', ['$scope', '$http', 'DialectService',
    function($scope, $http, DialectService) {
        $scope.dialects = {};

        $scope.loadDialects = function() {
            $scope.dialects = DialectService.show();
        }

        $scope.loadDialects();
    }
]);