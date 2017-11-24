(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('record-my-suffix', {
            parent: 'entity',
            url: '/record-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myApp.record.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/record/recordsmySuffix.html',
                    controller: 'RecordMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('record');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('record-my-suffix-detail', {
            parent: 'record-my-suffix',
            url: '/record-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myApp.record.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/record/record-my-suffix-detail.html',
                    controller: 'RecordMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('record');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Record', function($stateParams, Record) {
                    return Record.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'record-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('record-my-suffix-detail.edit', {
            parent: 'record-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record/record-my-suffix-dialog.html',
                    controller: 'RecordMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Record', function(Record) {
                            return Record.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('record-my-suffix.new', {
            parent: 'record-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record/record-my-suffix-dialog.html',
                    controller: 'RecordMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                content: null,
                                createtime: null,
                                updatetime: null,
                                isdeleted: null,
                                isdone: null,
                                eventlevel: null,
                                openid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('record-my-suffix', null, { reload: 'record-my-suffix' });
                }, function() {
                    $state.go('record-my-suffix');
                });
            }]
        })
        .state('record-my-suffix.edit', {
            parent: 'record-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record/record-my-suffix-dialog.html',
                    controller: 'RecordMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Record', function(Record) {
                            return Record.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('record-my-suffix', null, { reload: 'record-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('record-my-suffix.delete', {
            parent: 'record-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record/record-my-suffix-delete-dialog.html',
                    controller: 'RecordMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Record', function(Record) {
                            return Record.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('record-my-suffix', null, { reload: 'record-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
