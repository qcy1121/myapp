(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('RecordMySuffixDeleteController',RecordMySuffixDeleteController);

    RecordMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Record'];

    function RecordMySuffixDeleteController($uibModalInstance, entity, Record) {
        var vm = this;

        vm.record = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Record.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
