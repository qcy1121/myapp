(function() {
    'use strict';
    angular
        .module('myApp')
        .factory('WxUser', WxUser);

    WxUser.$inject = ['$resource', 'DateUtils'];

    function WxUser ($resource, DateUtils) {
        var resourceUrl =  'api/wx-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.subscribetime = DateUtils.convertDateTimeFromServer(data.subscribetime);
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
