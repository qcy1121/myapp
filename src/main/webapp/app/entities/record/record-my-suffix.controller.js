(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('RecordMySuffixController', RecordMySuffixController);

    RecordMySuffixController.$inject = ['Record'];

    function RecordMySuffixController(Record) {

        var vm = this;

        vm.records = [];

        loadAll();

        function loadAll() {
            Record.query(function(result) {
                vm.records = result;
                vm.searchQuery = null;
            });
        }
    }
})();
