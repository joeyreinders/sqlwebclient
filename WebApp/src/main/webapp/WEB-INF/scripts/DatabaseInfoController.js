app.controller('DbInfoCtrl', ['$rootScope', '$scope', '$http', 'DbInfoService',
    function($rootScope, $scope, $http, DbInfoService) {
        $scope.dbInfo = {};
        $scope.selectedTable = {};
        $scope.selection = {};

        $scope.loadDbInfo = function() {
            $scope.dbInfo = {};
            $scope.selectedTable = {};
            $scope.selection = {};

            $scope.dbInfo = DbInfoService.get({id: $rootScope.selectedConnection});
        }

        var unregister = $rootScope.$watch('selectedConnection', function() {
            $scope.loadDbInfo();
        }, true);

        $scope.$on('$destroy', function(){
          unregister();
        });

        $scope.doSelection = function() {
            for(var i=0; i < $scope.dbInfo.tables.length; i++) {
                var table = $scope.dbInfo.tables[i];
                if(table.tableName == $scope.selection) {
                    $scope.selectedTable = table;
                    return;
                }
            }
        }

        $scope.loadDbInfo();
    }
]);