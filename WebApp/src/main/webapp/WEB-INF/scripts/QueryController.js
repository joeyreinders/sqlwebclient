app.controller('SqlQueryCtrl', ['$rootScope', '$scope', '$http', 'QueryService', 'ngTableParams',
function($rootScope, $scope, $http, QueryService, ngTableParams) {
    $scope.queryString = '';

    //new system
    $scope.result = {};

    $scope.result.columns = [];
    $scope.result.message = {};
    $scope.result.message.error = '';
    $scope.result.message.info = '';
    $scope.result.message.duration = '';

    $scope.result.count = 0;
    $scope.result.rows = [];

    $scope.init = function() {
        $scope.tableParams = new ngTableParams({
            page: 1,            // show first page
            count: $rootScope.appSettings.PAGINATION_DEFAULT_VALUE
        }, {
            total: 0, // length of data
            getData: function($defer, params) {
                var data = $scope.result.rows;
                $defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });
    }

    //init everything
    $scope.init();

    $scope.doQuery = function() {
        $scope.executeQuery($scope.queryString);
    };

    $scope.executeQuery = function($queryString) {
        $scope.showLoading = true;
        $scope.result = QueryService.query(
            {
                connectionUuid: $rootScope.selectedConnection,
                queryString: $queryString,
                queryType: 'hql'
            },
            function() {
                $scope.reloadTable();
                $scope.showLoading = false;
            },
            function() {
                $scope.showLoading = false;
            }
        );
    }

    $scope.reloadTable = function() {
        $scope.columns = [];

        for(var i = 0; i < $scope.result.columns.length; i++) {
            var col = $scope.result.columns[i];
            $scope.columns[i] = { title: col.columnName, field: col.columnName, visible: true };
        }

        $scope.tableParams.total($scope.result.rows.length);
        $scope.tableParams.count(25); // this is the default count
        $scope.tableParams.page(1);
        $scope.tableParams.reload();
    }

    //TODO move this to a separate controller
    $scope.exportTo = function(exportType) {
        var url = 'api/export?c=' + $rootScope.selectedConnection + '&export=' + exportType;
        var json = angular.toJson($scope.result);

        $http({
            url: url,
            method: 'POST',
            data: json
        }
        ).success(function(data, status, headers, config){
            var hiddenElement = document.createElement('a');

                hiddenElement.href = 'data:text/sql,' + encodeURI(data);
                hiddenElement.target = '_blank';
                hiddenElement.download = 'export.sql';
                hiddenElement.click();
        });
    }
}

])
.controller('CodemirrorCtrl', ['$scope', function($scope) {

  // The modes
  $scope.modes = ['Scheme', 'XML', 'Javascript'];
  $scope.mode = $scope.modes[0];


  // The ui-codemirror option
  $scope.cmOption = {
    lineNumbers: true,
    indentWithTabs: true,
    onLoad : function(_cm){

      // HACK to have the codemirror instance in the scope...
      $scope.modeChanged = function(){
        _cm.setOption("mode", $scope.mode.toLowerCase());
      };
    }
  };
  }]);
;