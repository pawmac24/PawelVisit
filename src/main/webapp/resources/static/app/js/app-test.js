var appTest = angular.module('appTest', []);

/* TEST CONTROLLER */
appTest.controller('testController', function ($scope, $http) {
    console.log("testController");

    //var scheduleArray = [
    //    {
    //        employeeId : 1956,
    //        roomId : 4,
    //        start: 1454324400000,
    //        stop: 1454346000000,
    //        place: "333P"
    //    },
    //    {
    //        employeeId : 1956,
    //        roomId : 5,
    //        start: 1454410800000,
    //        stop: 1454432400000,
    //        place: "111P"
    //    }
    //];
    //for(var i=0; i < scheduleArray.length; i++){
    //    console.log("scheduleArray[i] = " + scheduleArray[i] + " " + JSON.stringify(scheduleArray[i]));
    //    ScheduleService.create(scheduleArray[i]).then(function(response) {
    //            console.log(response.data + ", " + response.status);
    //        },
    //        function error(response) {
    //            console.log(response.data + ", " + response.status);
    //        });
    //}

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

    //The old deprecated style
    //CompanyService.listAll().success(function(data){
    //    $scope.companies = data;
    //    console.log(data);
    //}).
    //error(function (data) {
    //    console.log(data);
    //});

    /*
     angular.forEach(items, function (item) {
     if (item.done == false || showComplete == true) {
     resultArr.push(item);
     }
     });
     */

    var randomValue = Math.floor((Math.random() * 100) + 1);
});