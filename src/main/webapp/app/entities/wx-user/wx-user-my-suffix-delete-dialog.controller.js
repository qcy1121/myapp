(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('WxUserMySuffixDeleteController',WxUserMySuffixDeleteController);

    WxUserMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'WxUser'];

    function WxUserMySuffixDeleteController($uibModalInstance, entity, WxUser) {
        var vm = this;

        vm.wxUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WxUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
