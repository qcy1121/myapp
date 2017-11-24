(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('WxUserMySuffixDetailController', WxUserMySuffixDetailController);

    WxUserMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WxUser', 'Record'];

    function WxUserMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, WxUser, Record) {
        var vm = this;

        vm.wxUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myApp:wxUserUpdate', function(event, result) {
            vm.wxUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
