var appRoom = angular.module('appRoom', []);

/* ROOM CONTROLLER */
appRoom.controller('roomController', function ($scope, $location, $rootScope) {

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
});

/* ROOM LIST CONTROLLER */
appRoom.controller('roomListController', function ($scope, $location, $rootScope,
                                                   CompanyService, DepartmentService, RoomService) {

    var selectedCompanyId = null;
    var selectedDepartmentId = null;
    $scope.notFound = true;
    $scope.depDisabled = true;

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
    else {
        console.log("companies select list");
        selectedCompanyId = $location.search().selectedCompanyId;
        selectedDepartmentId = $location.search().selectedDepartmentId;
        console.log("query params " +
            "[selectedCompanyId, selectedDepartmentId] = " +
            "[" + selectedCompanyId + ", " + selectedDepartmentId + "]");

        CompanyService.listAll().then(function (response) {
            $scope.companies = response.data;
            console.log(response.data);
            //
            for (var n = 0; n < $scope.companies.length; n++) {
                console.log("companyId = " + $scope.companies[n].companyId + ", selectedCompanyId = " + selectedCompanyId);
                if($scope.companies[n].companyId === selectedCompanyId){
                    $scope.company = {};
                    $scope.company.selected = $scope.companies[n];
                    console.log($scope.company.selected);
                    //
                    DepartmentService.listByCompanyId(selectedCompanyId).then(function (response) {
                        $scope.departments = response.data;
                        console.log("departments = " + response.data);
                        //
                        console.log("selectedDepartmentId = " + selectedDepartmentId);
                        for (var m = 0; m < $scope.departments.length; m++) {
                            console.log("departmentId = " + $scope.departments[m].departmentId);
                            if($scope.departments[m].departmentId === selectedDepartmentId) {
                                $scope.depDisabled = false;
                                $scope.department = {};
                                $scope.department.selected = $scope.departments[m];
                                console.log($scope.department.selected);
                                //
                                RoomService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                                    $scope.rooms = response.data;
                                    $location.search( {selectedCompanyId : selectedCompanyId, selectedDepartmentId: selectedDepartmentId } );
                                    console.log("rooms = " + response.data);
                                    $scope.notFound = $scope.rooms.length === 0 ? true : false;
                                },
                                function error(response) {
                                    console.log(response.data);
                                });
                            }
                        }
                    },
                    function error(response) {
                        console.log(response.data);
                    });
                    break;
                }
            }
        },
        function error(response) {
            console.log(response.data);
        });
    }

    $scope.selectDepartments = function(companyId) {
        console.log("Select departments by companyId = " + companyId);
        $scope.notFound = true;
        if(companyId !== undefined){
            selectedCompanyId = companyId;
            selectedDepartmentId = null;

            DepartmentService.listByCompanyId(selectedCompanyId).then(function (response) {
                $scope.departments = response.data;
                $location.search( {selectedCompanyId : selectedCompanyId} );
                console.log("departments = " + response.data);
                $scope.depDisabled = response.data.length === 0 ? true : false;
            },
            function error(response) {
                console.log(response.data);
            });
        }
        else{
            selectedCompanyId = null;
            selectedDepartmentId = null;
            $location.search( {} );
            $scope.departments = {};
        }
    };

    $scope.selectRooms = function(departmentId) {
        console.log("Select rooms by departmentId = " + departmentId);
        if(departmentId !== undefined){
            selectedDepartmentId = departmentId;

            RoomService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                $scope.rooms = response.data;
                $location.search( {selectedCompanyId : selectedCompanyId, selectedDepartmentId: selectedDepartmentId } );
                console.log("rooms = " + response.data);
                $scope.notFound = $scope.rooms.length === 0 ? true : false;
            },
            function error(response) {
                console.log(response.data);
            });
        }
        else{
            selectedDepartmentId = null;
            $location.search( {selectedCompanyId : selectedCompanyId} );
            $scope.rooms = {};
            $scope.notFound = true;
        }
    };

    $scope.selectRow = function(index) {
        console.log("Select row with index = " + index);
        $scope.selectedRow = index;
    };

    $scope.delete = function(roomId) {
        console.log("Delete room with id = " + roomId);

        var answer = confirm("Are you sure you want to delete this room ?");
        console.log("answer =" + answer);
        if (answer) {
            RoomService.delete(roomId).then(function (response) {
                console.log(response.data);
                toastr.success("Deleting the room was successful");
                console.log("Deleting the room with id = " + roomId + " was successful");

                RoomService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                    $scope.rooms = response.data;
                    $location.search( {selectedCompanyId : selectedCompanyId, selectedDepartmentId: selectedDepartmentId } );
                    console.log("rooms = " + response.data);
                    $scope.notFound = $scope.rooms.length === 0 ? true : false;
                },
                function error(response) {
                    console.log("Error data = " + response.data);
                });
            },
            function error(response) {
                var data = response.data;
                console.log(data);
                if (data.errorMessage) {
                    toastr.error(data.errorMessage);
                }
            });
        }
    };
});

/* ROOM CREATE CONTROLLER */
appRoom.controller('roomCreateController', function ($scope, $location,
                                                     CompanyService, DepartmentService, RoomService) {

    var selectedCompanyId = null;
    var selectedDepartmentId = null;
    $scope.room = {};
    $scope.depDisabled = true;

    console.log("room create");

    CompanyService.listAll().then(function (response) {
        $scope.companies = response.data;
        console.log(response.data);
    },
    function error(response) {
        console.log(response.data);
    });

    $scope.setCompanyId = function(companyId) {
        console.log("Set companyId = " + companyId + " and select departments");
        $scope.depDisabled = true;
        if(companyId !== undefined){
            selectedCompanyId = companyId;
            selectedDepartmentId = null;

            DepartmentService.listByCompanyId(selectedCompanyId).then(function (response) {
                $scope.departments = response.data;
                console.log("departments = " + response.data);
                $scope.depDisabled = response.data.length === 0 ? true : false;
            },
            function error(response) {
                console.log(response.data);
            });
        }
        else{
            selectedCompanyId = null;
            selectedDepartmentId = null;
            $scope.departments = {};
        }
    };

    $scope.setDepartmentId = function(departmentId) {
        console.log("Set departmentId = " + departmentId);
        selectedDepartmentId = departmentId !== undefined ? departmentId : null;
    };

    $scope.save = function() {
        console.log("Adding room");

        var roomObj = {
            number : $scope.room.number,
            departmentId : selectedDepartmentId
        }
        console.log("roomObj = " + roomObj + " " + JSON.stringify(roomObj));
        RoomService.create(roomObj).then(function(response) {
            console.log("Status = " + response.status);
            console.log("Added id = " + response.data.id);
            toastr.success("Creating the room was successful");
            console.log("Creating the room with id = " + response.data.id + " was successful");
            $location.path("/room/list").search({
                selectedCompanyId: selectedCompanyId,
                selectedDepartmentId: selectedDepartmentId
            });
            clearFields();
        },
        function error(response) {
            console.log(response.data + ", " + response.status);
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
    };

    $scope.reset = function() {
        clearFields();
    }

    var clearFields = function(){
        $scope.room = {};
        selectedCompanyId = null;
        selectedDepartmentId = null;
    }
});

/* ROOM EDIT CONTROLLER */
appRoom.controller('roomEditController', function ($scope, $location, $stateParams, RoomService) {
    var roomId = $stateParams.roomId;
    var selectedCompanyId = $stateParams.companyId;
    var selectedDepartmentId = $stateParams.departmentId;
    console.log( "Edit roomId = " + roomId);
    console.log( "Selected companyId = " + selectedCompanyId);
    console.log( "Selected departmentId = " + selectedDepartmentId);

    RoomService.get(roomId).then(function (response) {
        var data = response.data;
        console.log(data);

        $scope.room = {};
        $scope.room.number = data.number;
        $scope.companyName = data.companyName;
        $scope.departmentName = data.departmentName;
    },
    function error(response) {
        console.log(response.data);
    });

    $scope.save = function() {
        console.log("Saving room");

        var roomObj = {
            number : $scope.room.number,
            roomId : roomId
        }
        console.log("roomObj = " + roomObj + " " + JSON.stringify(roomObj));

        RoomService.update(roomId, roomObj).then(function(response) {
            console.log("Updated = " + response.data);
            console.log("Status = " + response.status);
            toastr.success("Updating the room was successful");
            console.log("Updating the room with id = " + roomId + " was successful");
            console.log("PATH = [" + selectedCompanyId + ", " + selectedDepartmentId + "]");
            $location.path("/room/list").search({
                selectedCompanyId: selectedCompanyId,
                selectedDepartmentId: selectedDepartmentId
            });
            clearFields();
        },
        function error(response) {
            console.log(response.data + ", " + response.status);
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
    };

    $scope.reset = function() {
        clearFields();
    }

    var clearFields = function(){
        $scope.room = {};
    }
});
