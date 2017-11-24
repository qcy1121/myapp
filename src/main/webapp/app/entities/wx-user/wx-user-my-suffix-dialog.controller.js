(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('WxUserMySuffixDialogController', WxUserMySuffixDialogController);

    WxUserMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WxUser', 'Record'];

    function WxUserMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WxUser, Record) {
        var vm = this;

        vm.wxUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.records = Record.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wxUser.id !== null) {
                WxUser.update(vm.wxUser, onSaveSuccess, onSaveError);
            } else {
                WxUser.save(vm.wxUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myApp:wxUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.subscribetime = false;
        vm.datePickerOpenStatus.createtime = false;
        vm.datePickerOpenStatus.updatetime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
