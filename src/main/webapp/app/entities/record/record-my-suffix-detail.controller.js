(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('RecordMySuffixDetailController', RecordMySuffixDetailController);

    RecordMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Record', 'WxUser'];

    function RecordMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Record, WxUser) {
        var vm = this;

        vm.record = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myApp:recordUpdate', function(event, result) {
            vm.record = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
