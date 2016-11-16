var appTest = angular.module('appTest', []);

/* TEST CONTROLLER */
appTest.controller('testController', function ($scope, $http) {
    console.log("testController");

    $http.get("xxx/prepare").then(function(response) {
        console.log(response.data + ", " + response.status);

        $http.get("xxx/find").then(function(response) {
            console.log(response.data + ", " + response.status);

            $http.get("xxx/delete").then(function(response) {
                console.log(response.data + ", " + response.status);
            },
            function error(response) {
                console.log(response.data + ", " + response.status);
            });
        },
        function error(response) {
            console.log(response.data + ", " + response.status);
        });
    },
    function error(response) {
        console.log(response.data + ", " + response.status);
    });
});

/* TEST CONTROLLER */
appTest.controller('testCompanyController', function ($scope, $http, $location, $rootScope, CompanyService, ScheduleService) {
    console.log("testCompanyController");

    var randomValue = Math.floor((Math.random() * 100) + 1);
});