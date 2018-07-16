app.controller('IndexCtrl', ['$scope', '$rootScope', '$location', 'ConnectionDetailService',
function ($scope, $rootScope, $location, ConnectionDetailService) {
  $scope.isCurrentLocation = function (path) {
    return $location.path().substr(0, path.length) === path;
  }

  $scope.selectedConnection = {}

  $rootScope.$on('$routeChangeError', function (e, current, previous, rejection) {
    console.log('Route Change Error');
    console.log(rejection);
  });

  $rootScope.$on('$locationChangeSuccess', function(event, newUrl, oldUrl) {
      console.log('old url: ' + oldUrl + ' new url: ' + newUrl);
  });

  $scope.onSelectedConnectionChange = function() {
      console.log('connection changed');
      $rootScope.selectedConnection = $scope.selectedConnection;
  }

  var temp = ConnectionDetailService.get({q: 'default'},
      function() {
          $scope.selectedConnection = temp.uuid;
          $rootScope.selectedConnection = temp.uuid;
      }
  );
}]);