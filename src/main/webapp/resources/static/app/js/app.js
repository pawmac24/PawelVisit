//For ngRoute use ngRoute instead ui.router
var mainApp = angular.module('mainApp',
    [
        'ui.router', 'ngMessages', 'ui.grid', 'ui.bootstrap',
        'appCompany', 'appDepartment', 'appEmployee', 'appRoom', 'appSchedule',
        'appServices', 'appUtils',
        'appTest'
    ]);

//For ngRoute
//mainApp.config(function ($routeProvider, $httpProvider) {
//    $routeProvider
//
//        .when('/xxx', {
//            templateUrl: 'static/app/pages/xxx.html',
//            controller: 'xxxController'
//        })
//        //...
//        .otherwise('/');
//})

mainApp.config(function($stateProvider, $urlRouterProvider, $httpProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider

        // route for home page
        .state('home', {
            url: '/',
            templateUrl: 'static/app/pages/home.html',
            controller: 'mainController'
        })

        // route for about page
        .state('about', {
            url: '/about',
            templateUrl: 'static/app/pages/about.html',
            controller: 'aboutController'
        })

        // route for contact page
        .state('contact', {
            url: '/contact',
            templateUrl: 'static/app/pages/contact.html',
            controller: 'contactController'
        })

        // route for the event page
        .state('event', {
            url: '/event',
            templateUrl: 'static/app/pages/event.html',
            controller: 'eventController'
        })

        // route for company page
        .state('company', {
            url: '/company',
            templateUrl: 'static/app/pages/company/company.html',
            controller: 'companyController'
        })

        // route for company list page
        .state('company.list', {
            url: '/list',
            templateUrl: 'static/app/pages/company/company_list.html',
            controller: 'companyListController'
        })

        // route for company create page
        .state('company.create', {
            url: '/create',
            templateUrl: 'static/app/pages/company/company_create.html',
            controller: 'companyCreateController'
        })

        // route for company edit page
        .state('company.edit', {
            url: '/edit/:companyId',
            templateUrl: 'static/app/pages/company/company_edit.html',
            controller: 'companyEditController'
        })

        // route for department page
        .state('department', {
            url: '/department',
            templateUrl: 'static/app/pages/department/department.html',
            controller: 'departmentController'
        })

        // route for department list page
        .state('department.list', {
            url: '/list',
            templateUrl: 'static/app/pages/department/department_list.html',
            controller: 'departmentListController'
        })

        // route for department create page
        .state('department.create', {
            url: '/create',
            templateUrl: 'static/app/pages/department/department_create.html',
            controller: 'departmentCreateController'
        })

        // route for department edit page
        .state('department.edit', {
            url: '/edit/:departmentId',
            templateUrl: 'static/app/pages/department/department_edit.html',
            controller: 'departmentEditController'
        })

        // route for employee page
        .state('employee', {
            url: '/employee',
            templateUrl: 'static/app/pages/employee/employee.html',
            controller: 'employeeController'
        })

        // route for employee list page
        .state('employee.list', {
            url: '/list',
            templateUrl: 'static/app/pages/employee/employee_list.html',
            controller: 'employeeListController'
        })

        // route for employee create page
        .state('employee.create', {
            url: '/create',
            templateUrl: 'static/app/pages/employee/employee_create.html',
            controller: 'employeeCreateController'
        })

        // route for employee edit page
        .state('employee.edit', {
            url: '/edit/:employeeId/companyId/:companyId/departmentId/:departmentId',
            templateUrl: 'static/app/pages/employee/employee_edit.html',
            controller: 'employeeEditController'
        })

        // route for room page
        .state('room', {
            url: '/room',
            templateUrl: 'static/app/pages/room/room.html',
            controller: 'roomController'
        })

        // route for room list page
        .state('room.list', {
            url: '/list',
            templateUrl: 'static/app/pages/room/room_list.html',
            controller: 'roomListController'
        })

        // route for room create page
        .state('room.create', {
            url: '/create',
            templateUrl: 'static/app/pages/room/room_create.html',
            controller: 'roomCreateController'
        })

        // route for room edit page
        //http://jakzaprogramowac.pl/pytanie/8854,stateparams-converting-value-to-string
        //http://angular-ui.github.io/ui-router/site/#/api/ui.router.state.$stateProvider
        .state('room.edit', {
            url: '/edit/{roomId:int}/companyId/{companyId:int}/departmentId/{departmentId:int}',
            templateUrl: 'static/app/pages/room/room_edit.html',
            controller: 'roomEditController'
        })

        // route for schedule page
        .state('schedule', {
            url: '/schedule',
            templateUrl: 'static/app/pages/schedule/schedule.html',
            controller: 'scheduleController'
        })

        // route for schedule list page
        .state('schedule.list', {
            url: '/list/{employeeId:int}/companyId/{companyId:int}/departmentId/{departmentId:int}',
            templateUrl: 'static/app/pages/schedule/schedule_list.html',
            controller: 'scheduleListController'
        })

        // route for schedule create page
        .state('schedule.create', {
            url: '/create',
            templateUrl: 'static/app/pages/schedule/schedule_create.html',
            controller: 'scheduleCreateController'
        })

        // route for schedule edit page
        .state('schedule.edit', {
            url: '/edit',
            templateUrl: 'static/app/pages/schedule/schedule_edit.html',
            controller: 'scheduleEditController'
        })

        // route for test page
        .state('test', {
            url: '/test',
            templateUrl: 'static/app/pages/test/test.html',
            controller: 'testController'
        })

        // route for login page
        .state('login', {
            url: '/login',
            templateUrl: 'static/app/pages/login.html',
            controller: 'loginController'
        });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})
.run(function($rootScope, $location){
    // register listener to watch route changes
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        console.log("$routeChangeStart event = " + event);
        console.log("next.templateUrl =" + next.templateUrl);
        console.log("$routeChangeStart next = " + next);
        console.log("current.templateUrl =" + (current ? current.templateUrl : undefined));
        console.log("$routeChangeStart current = " + current);
    });
});


// create the controller and inject Angular's $scope
mainApp.controller('mainController', function ($rootScope, $scope, $location, $http) {
    // create a message to display in our view
    $scope.message = 'Everyone come and see how good I look!';

    $scope.logout = function () {
        $http.post('logout', {}).then(function(response) {
            console.log("Logout succeeded = ", response.status);
            $rootScope.authenticated = false;
            $rootScope.loggeduser = "";
            $rootScope.currentUser = {};
            $location.path("/");
        },
        function error(response) {
            console.log("Logout failed", response.status);
            $rootScope.authenticated = true;
        });
    };

});

mainApp.controller('aboutController', function ($scope) {
    $scope.message = 'Look! I am an about page.';
});

mainApp.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});

mainApp.controller('eventController', function ($scope) {
    $scope.count = 0;
    $scope.$on('MyEvent', function () {
        $scope.count++;
    });
});

mainApp.controller('loginController', function ($rootScope, $scope, $http, $location) {

    var authenticate = function (credentials, callback) {
        console.log("Method authenticate");
        console.log("Credentials " + (credentials ? JSON.stringify(credentials) : {}));

        var headers = credentials ? {
            Authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
        } : {};
        console.log("My headers = " + JSON.stringify(headers));

        $http({
            method: 'GET',
            url: 'user',
            headers: headers
        }).then(function(response) {
            console.log("Success data = " + JSON.stringify(response.data));
            console.log("Success data = ", response.data, response.status);
            if (response.data.name) {
                var principal = response.data.principal;
                console.log("Success principal[username, userId] = ", principal.username, principal.userId);
                console.log("Success principal.employee = ", principal.employee);
                $rootScope.loggeduser = principal.username + ", " + principal.employee.firstName + " " + principal.employee.lastName;
                $rootScope.currentUser = {
                    userId: principal.userId,
                    userName: principal.username,
                    employeeId: principal.employee.employeeId,
                    firstName: principal.employee.firstName,
                    lastName: principal.employee.lastName,
                    departmentId: principal.employee.department.departmentId,
                    companyId: principal.employee.department.companyId
                };
                $rootScope.authenticated = true;
            } else {
                $rootScope.loggeduser = "";
                $rootScope.currentUser = {};
                $rootScope.authenticated = false;
            }
            callback && callback($rootScope.authenticated);
        },
        function error(response) {
            console.log("Error status=", response.data, response.status);
            $rootScope.authenticated = false;
            callback && callback(false);
        });
    }

    console.log("Before calling authenticate");
    //authenticate();
    console.log("After calling authenticate");

    console.log("Before clearing credentials");
    $scope.credentials = {};
    console.log("After clearing credentials");

    $scope.login = function () {
        console.log("Login function");
        authenticate($scope.credentials, function (authenticated) {
            console.log("inside callback authenticated");
            if (authenticated) {
                console.log("Login succeeded");
                $location.path("/");
                $scope.error = false;
                $rootScope.authenticated = true;
            } else {
                console.log("Login failed");
                $location.path("/login");
                $scope.error = true;
                $rootScope.authenticated = false;
            }
        })
    };
});