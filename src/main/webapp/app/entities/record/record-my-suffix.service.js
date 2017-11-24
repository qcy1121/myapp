(function() {
    'use strict';
    angular
        .module('myApp')
        .factory('Record', Record);

    Record.$inject = ['$resource', 'DateUtils'];

    function Record ($resource, DateUtils) {
        var resourceUrl =  'api/records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createtime = DateUtils.convertDateTimeFromServer(data.createtime);
                        data.updatetime = DateUtils.convertDateTimeFromServer(data.updatetime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
