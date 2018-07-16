app.controller('StatisticsCtrl', ['$scope', '$http', '$timeout',
function($scope, $http, $timeout) {
    $scope.stats = {};

    $scope.loadStatistics = function() {
        var url = 'api/statistics?callback=JSON_CALLBACK';
        $http.jsonp(url)
            .success(function( data ) {
                $scope.stats = data;
            }
        );
    }

    $scope.loadStatistics();

    var timer = $timeout($scope.loadStatistics, 500);

    $scope.$on('$destroy', function(){
      timer.cancel(timer);
    });
}

]);