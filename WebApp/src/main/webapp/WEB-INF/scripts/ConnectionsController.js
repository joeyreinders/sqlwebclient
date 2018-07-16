app.controller('ConnectionsCtrl', ['$rootScope', '$scope', '$http', '$window', 'ConnectionDetailService',
function($rootScope, $scope, $http, $window, ConnectionDetailService) {
    $scope.selectedConnection = {};
    $scope.connectionDetail = {};
    $scope.editableDetail = {};

    $scope.loadConnections = function() {
        $scope.connections = ConnectionDetailService.show();
    };

    $scope.onSelectedConnectionChange = function() {
        $rootScope.selectedConnection = $scope.selectedConnection;
    }

    $scope.doSelection = function() {
        $scope.editableDetail = ConnectionDetailService.get({id: $scope.connectionDetail});
    }

    $scope.testConnection = function() {
        var url = 'api/connection?testConnection';
        var json = angular.toJson($scope.editableDetail);
        $http.post(url, json)
            .success(function(data){
                $window.alert(data.message);
            });
    }

    $scope.doDelete = function() {
        ConnectionDetailService.delete(
            {id: $scope.editableDetail.uuid},
            function() {
                $scope.loadConnections();
            }
        );
    }

    $scope.doSave = function() {
        var url ='api/connection?callback=JSON_CALLBACK';
        var json = angular.toJson($scope.editableDetail);

        var method = 'PUT'
        if($scope.editableDetail.uuid === 'undefined') {
            method = 'POST';
        } else {
            url = url + '&uuid=' + $scope.editableDetail.uuid;
        }

        $http({
            url: url,
            method: method,
            data: json
        }
        ).success(function(data){
            console.log('Saved');
            loadConnections();
        });
    }

    $scope.doNew = function() {
        $scope.connectionDetail = {};
        $scope.editableDetail = {};
    }

    $scope.doCancel = function() {
        $scope.connectionDetail = {};
        $scope.editableDetail = {};
        $scope.loadConnections();
    }

    //$scope.loadConnections();
    //$scope.loadDefaultConnection();
}]);