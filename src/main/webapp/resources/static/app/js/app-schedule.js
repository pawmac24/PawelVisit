var appSchedule = angular.module('appSchedule', []);

/* SCHEDULE CONTROLLER */
appSchedule.controller('scheduleController', function ($scope, $location, $rootScope) {

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
});

/* SCHEDULE LIST CONTROLLER */
appSchedule.controller('scheduleListController', function ($scope, $location, $rootScope, $stateParams,
                                                           ScheduleService, DateService, RoomService) {
    var selectedEmployeeId = $stateParams.employeeId;
    var selectedCompanyId = $stateParams.companyId;
    var selectedDepartmentId = $stateParams.departmentId;
    console.log( "Selected employeeId = " + selectedEmployeeId);
    console.log( "Selected companyId = " + selectedCompanyId);
    console.log( "Selected departmentId = " + selectedDepartmentId);
    $scope.selectedRoom = [];
    $scope.notFound = true;

    $scope.currentDay = DateService.clearTime(new Date());

    $scope.weeks = DateService.getConsecutiveWeeks(DateService.clearTime(new Date()), 5);

    $scope.selectWeek = function() {
        $scope.schedules = [];
        $scope.selectedRoom = [];

        if($scope.selectedWeek !== null ) {
            var dateFrom = $scope.selectedWeek.from.getTime();
            var dateTo = $scope.selectedWeek.to.getTime();
            console.log("Timestamp from = ",  dateFrom);
            console.log("Timestamp to = ",  dateTo);

            ScheduleService.listByEmployeeIdAndDates(selectedEmployeeId, dateFrom, dateTo).then(function (response) {
                var schedulesArray = [];
                schedulesArray = response.data;
                console.log("schedules = ", schedulesArray);
                for(var i = 0; i < schedulesArray.length; i++){
                    schedulesArray[i].start = new Date(schedulesArray[i].start);
                    schedulesArray[i].stop = new Date(schedulesArray[i].stop);
                }
                $scope.schedules = schedulesArray;
                console.log("schedules = ", schedulesArray);
                $scope.notFound = $scope.schedules.length === 0 ? true : false;

                if($scope.notFound === false){
                    for(var i = 0; i < $scope.schedules.length; i++){
                        $scope.selectedRoom.push(undefined);
                    }
                    RoomService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                        for(var i = 0; i < $scope.schedules.length; i++){
                            $scope.schedules[i].rooms = response.data;
                            //console.log("=== INSIDE FOR [", i, "]");
                            for(var j = 0; j < $scope.schedules[i].rooms.length; j++) {
                                //console.log("=== INSIDE FOR [", i, ",", j, "]");
                                if($scope.schedules[i].rooms[j].roomId === $scope.schedules[i].roomId){
                                    //console.log("=== HURRAY [", i, ",", j, "] = ", $scope.schedules[i].rooms[j]);
                                    $scope.selectedRoom[i] = {};
                                    $scope.selectedRoom[i] = $scope.schedules[i].rooms[j];
                                    break;
                                }
                            }
                        }
                        console.log("rooms = ", response.data);
                        console.log("schedules with rooms = ", $scope.schedules);
                    },
                    function error(response) {
                        console.log(response.data);
                    });
                }
                else{
                    console.log("===NOT FOUND SCHEDULES===");
                    $scope.schedules = [];
                    $scope.selectedRoom = [];

                    var startDate = new Date(dateFrom);
                    for(i = 1; i <= 7; i++) {
                        var newSchedule = {
                            start: startDate,
                            stop: startDate,
                            place: "XXX",
                            employeeId: selectedEmployeeId,
                            roomId: undefined
                        };
                        $scope.schedules.push(newSchedule);
                        startDate = DateService.plusDays(startDate, 1);
                    }
                    for(var i = 0; i < $scope.schedules.length; i++){
                        $scope.selectedRoom.push(undefined);
                    }
                    RoomService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                        for(var i = 0; i < $scope.schedules.length; i++){
                            $scope.schedules[i].rooms = response.data;
                        }
                        console.log("rooms = ", response.data);
                        console.log("schedules with rooms = ", $scope.schedules);
                        $scope.notFound = false;
                    },
                    function error(response) {
                        console.log(response.data);
                    });
                }
            },
            function error(response) {
                console.log(response.data);
            });
        }
        else{
            console.log("Please select the given week");
            $scope.notFound = true;
        }
    };

    $scope.changedSchedule = function(index) {
        console.log('From time changed to: ', $scope.schedules[index].start);
        console.log('To time changed to: ', $scope.schedules[index].stop);
    };

    $scope.selectRoom = function(index, roomId) {
        $scope.schedules[index].roomId = roomId;
        console.log("selectRoom = " + $scope.schedules[index].roomId);
    };


    $scope.addRow = function(index){

        var newSchedule = {
            start: $scope.schedules[index].stop,
            stop: $scope.schedules[index].stop,
            place: $scope.schedules[index].place,
            employeeId: selectedEmployeeId,
            roomId: undefined
        };
        $scope.schedules.splice((index + 1), 0, newSchedule);
        $scope.selectedRoom.splice((index + 1), 0, undefined);
        $scope.schedules[index + 1].rooms = $scope.schedules[index].rooms;
    };

    $scope.goBack = function() {
        console.log("Go back");

        $location.path("/employee/list").search({
            selectedCompanyId: selectedCompanyId,
            selectedDepartmentId: selectedDepartmentId
        });
    };

    $scope.save = function() {
        console.log("Save");
        console.log("Schedules = ", $scope.schedules);

        ScheduleService.createUpdate($scope.schedules).then(function (response) {
            console.log("Response data = ", response.data);
            toastr.success("Creating the schedule for given week was successful");
        },
        function error(response) {
            console.log(response.data);
            var errorMessage = "";
            if(response.data.fieldErrors){
                for(var i=0; i < response.data.fieldErrors.length; i++){
                    errorMessage += response.data.fieldErrors[i].message + "<br/>";
                }
            }
            else {
                errorMessage = response.data.errorMessage;
            }
            toastr.error(errorMessage);
        });
    }
});

/* SCHEDULE CREATE CONTROLLER */
appSchedule.controller('scheduleCreateController', function ($scope, $location,
                                                             ScheduleService) {
});

/* SCHEDULE EDIT CONTROLLER */
appRoom.controller('scheduleEditController', function ($scope, $location, $stateParams, RoomService) {

});
