(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('wx-user-my-suffix', {
            parent: 'entity',
            url: '/wx-user-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myApp.wxUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wx-user/wx-usersmySuffix.html',
                    controller: 'WxUserMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('wxUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('wx-user-my-suffix-detail', {
            parent: 'wx-user-my-suffix',
            url: '/wx-user-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myApp.wxUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wx-user/wx-user-my-suffix-detail.html',
                    controller: 'WxUserMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('wxUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WxUser', function($stateParams, WxUser) {
                    return WxUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'wx-user-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('wx-user-my-suffix-detail.edit', {
            parent: 'wx-user-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wx-user/wx-user-my-suffix-dialog.html',
                    controller: 'WxUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WxUser', function(WxUser) {
                            return WxUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wx-user-my-suffix.new', {
            parent: 'wx-user-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wx-user/wx-user-my-suffix-dialog.html',
                    controller: 'WxUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                openid: null,
                                subscribe: null,
                                nickName: null,
                                sex: null,
                                language: null,
                                city: null,
                                province: null,
                                country: null,
                                subscribetime: null,
                                headimgurl: null,
                                iscancel: null,
                                createtime: null,
                                updatetime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('wx-user-my-suffix', null, { reload: 'wx-user-my-suffix' });
                }, function() {
                    $state.go('wx-user-my-suffix');
                });
            }]
        })
        .state('wx-user-my-suffix.edit', {
            parent: 'wx-user-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wx-user/wx-user-my-suffix-dialog.html',
                    controller: 'WxUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WxUser', function(WxUser) {
                            return WxUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wx-user-my-suffix', null, { reload: 'wx-user-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wx-user-my-suffix.delete', {
            parent: 'wx-user-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wx-user/wx-user-my-suffix-delete-dialog.html',
                    controller: 'WxUserMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WxUser', function(WxUser) {
                            return WxUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wx-user-my-suffix', null, { reload: 'wx-user-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
