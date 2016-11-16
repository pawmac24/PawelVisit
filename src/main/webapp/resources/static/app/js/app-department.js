var appDepartment = angular.module('appDepartment', []);

/* DEPARTMENT CONTROLLER */
appDepartment.controller('departmentController', function ($scope, $http, $location, $rootScope) {
    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
});

/* DEPARTMENT LIST CONTROLLER */
appDepartment.controller('departmentListController', function ($scope, $location, $rootScope, CompanyService, DepartmentService) {
    var selectedCompanyId = null;
    $scope.notFound = true;

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
    else {
        console.log("companies select list");
        selectedCompanyId = $location.search().selectedCompanyId;
        console.log("query param selectedCompanyId = " + selectedCompanyId);

        CompanyService.listAll().then(function (response) {
            $scope.companies = response.data;
            console.log(response.data);
            console.log("selectedCompanyId = " + selectedCompanyId);
            for (var n = 0; n < $scope.companies.length; n++) {
                console.log("companyId = " + $scope.companies[n].companyId);
                if($scope.companies[n].companyId === selectedCompanyId){
                    $scope.company = {};
                    $scope.company.selected = $scope.companies[n];
                    console.log($scope.company.selected);
                    //
                    DepartmentService.listByCompanyId(selectedCompanyId).then(function (response) {
                        $scope.departments = response.data;
                        console.log("departments = " + response.data);
                        $scope.notFound = $scope.departments.length === 0 ? true : false;
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
        console.log("Select departments companyId = " + companyId);
        if(companyId !== undefined){
            selectedCompanyId = companyId;

            DepartmentService.listByCompanyId(selectedCompanyId).then(function (response) {
                $scope.departments = response.data;
                $location.search( {selectedCompanyId : selectedCompanyId} );
                console.log("departments = " + response.data);
                $scope.notFound = $scope.departments.length === 0 ? true : false;
            },
            function error(response) {
                console.log(response.data);
            });
        }
        else{
            selectedCompanyId = null;
            $location.search( {} );
            $scope.departments = {};
            $scope.notFound = true;
        }
    }

    $scope.delete = function(departmentId) {
        console.log("Delete departmentId = " + departmentId);

        var answer = confirm("Are you sure you want to delete this department ?");
        console.log("answer =" + answer);
        if (answer) {
            DepartmentService.delete(departmentId).then(function (response) {
                console.log(response.data);
                clearFields();
                toastr.success("Deleting the department was successful");
                console.log("Deleting the department with id = " + departmentId + " was successful");

                DepartmentService.listByCompanyId(selectedCompanyId).then(function (response) {
                    $scope.departments = response.data;
                    console.log(response.data);
                    $scope.notFound = $scope.departments.length === 0 ? true : false;
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

    $scope.selectRow = function(index) {
        console.log("Select row with index = " + index);
        $scope.selectedRow = index;
    }

    var clearFields = function(){
        $scope.companyId = "";
        $scope.name = "";
        $scope.address = {};
    }
});

/* DEPARTMENT CREATE CONTROLLER */
appDepartment.controller('departmentCreateController', function ($scope, $location, $rootScope, CompanyService, DepartmentService) {

    var selectedCompanyId = null;

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
    else {
        console.log("department create");

        CompanyService.listAll().then(function (response) {
            $scope.companies = response.data;
            console.log(response.data);
        },
        function error(response) {
            console.log(response.data);
        });
    }

    $scope.setCompanyId = function(companyId) {
        console.log("Set companyId = " + companyId);
        selectedCompanyId = companyId !== undefined ? companyId : null;
    }

    $scope.save = function() {
        console.log("Adding department");

        var departmentObj = {
            name : $scope.name,
            address : {
                city : $scope.address.city,
                postcode : $scope.address.postcode,
                street : $scope.address.street,
                number : $scope.address.number
            },
            companyId : selectedCompanyId
        }
        DepartmentService.create(departmentObj).then(function(response) {
            console.log("Status = " + response.status);
            console.log("Added id = " + response.data.id);
            toastr.success("Creating the department was successful");
            console.log("Creating the department with id = " + response.data.id + " was successful");
            $location.path("/department/list").search({selectedCompanyId: selectedCompanyId});
            clearFields();
        },
        function error(response) {
            console.log(response.data);
            console.log(response.status);
            toastr.error(response.data.errorMessage);
        });
    };

    $scope.reset = function() {
        clearFields();
    }

    var clearFields = function(){
        $rootScope.companyId = "";
        $scope.name = "";
        $scope.address = {};
    }
});

/* DEPARTMENT EDIT CONTROLLER */
appDepartment.controller('departmentEditController', function ($scope, $location, $stateParams, DepartmentService) {

    var departmentId = $stateParams.departmentId;
    console.log( "Edit departmentId = " + departmentId);

    DepartmentService.get(departmentId).then(function (response) {
        var data = response.data;
        console.log(data);
        $scope.companyId = data.companyId;
        $scope.departmentId = data.departmentId;
        $scope.name = data.name;
        $scope.address = {};
        $scope.address.city = data.address.city;
        $scope.address.postcode = data.address.postcode;
        $scope.address.street = data.address.street;
        $scope.address.number = data.address.number;
        $scope.companyName = data.companyName;
        //$scope.address = data.address; //you can use this line instead
    },
    function error(response) {
        console.log(response.data);
    });


    $scope.save = function() {
        console.log("Saving department");

        var departmentObj = {
            departmentId : $scope.departmentId,
            name : $scope.name,
            address : {
                city : $scope.address.city,
                postcode : $scope.address.postcode,
                street : $scope.address.street,
                number : $scope.address.number
            }
        }
        DepartmentService.update($scope.departmentId, departmentObj).then(function(response) {
            console.log("Added = " + response.data);
            console.log("Status = " + response.status);
            toastr.success("Updating the department was successful");
            console.log("Updating the department with id = " + $scope.departmentId + " was successful");
            $location.path("/department/list").search({selectedCompanyId: $scope.companyId});
            clearFields();
        },
        function error(response) {
            console.log(response.data + ", " + response.status);
            toastr.error(data.errorMessage);
        });
    };

    $scope.reset = function() {
        clearFields();
    }

    var clearFields = function(){
        $scope.name = "";
        $scope.address = {};
    }
});
