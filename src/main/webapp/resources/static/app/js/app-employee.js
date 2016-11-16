var appEmployee = angular.module('appEmployee', []);

/* EMPLOYEE CONTROLLER */
appEmployee.controller('employeeController', function ($scope, $location, $rootScope) {

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
});

/* EMPLOYEE LIST CONTROLLER */
appEmployee.controller('employeeListController', function ($scope, $location, $rootScope,
                                                           CompanyService, DepartmentService, EmployeeService) {
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
                                EmployeeService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                                    $scope.employees = response.data;
                                    $location.search( {selectedCompanyId : selectedCompanyId, selectedDepartmentId: selectedDepartmentId } );
                                    console.log("employees = " + response.data);
                                    $scope.notFound = $scope.employees.length === 0 ? true : false;
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

    $scope.selectEmployees = function(departmentId) {
        console.log("Select employees by departmentId = " + departmentId);
        if(departmentId !== undefined){
            selectedDepartmentId = departmentId;

            EmployeeService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                $scope.employees = response.data;
                $location.search( {selectedCompanyId : selectedCompanyId, selectedDepartmentId: selectedDepartmentId } );
                console.log("employees = " + response.data);
                $scope.notFound = $scope.employees.length === 0 ? true : false;
            },
            function error(response) {
                console.log(response.data);
            });
        }
        else{
            selectedDepartmentId = null;
            $location.search( {selectedCompanyId : selectedCompanyId} );
            $scope.employees = {};
            $scope.notFound = true;
        }
    };

    $scope.selectRow = function(index) {
        console.log("Select row with index = " + index);
        $scope.selectedRow = index;
    };

    $scope.delete = function(employeeId) {
        console.log("Delete employeeId = " + employeeId);

        var answer = confirm("Are you sure you want to delete this employee ?");
        console.log("answer =" + answer);
        if (answer) {
            EmployeeService.delete(employeeId).then(function (response) {
                console.log(response.data);
                toastr.success("Deleting the employee was successful");
                console.log("Deleting the employee with id = " + employeeId + " was successful");

                EmployeeService.listByDepartmentId(selectedDepartmentId).then(function (response) {
                    $scope.employees = response.data;
                    $location.search( {selectedCompanyId : selectedCompanyId, selectedDepartmentId: selectedDepartmentId } );
                    console.log("employees = " + response.data);
                    $scope.notFound = $scope.employees.length === 0 ? true : false;
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

/* EMPLOYEE CREATE CONTROLLER */
appEmployee.controller('employeeCreateController', function ($scope, $location,
                                                             CompanyService, DepartmentService, EmployeeService) {
    var selectedCompanyId = null;
    var selectedDepartmentId = null;
    $scope.employee = {};
    $scope.depDisabled = true;

    console.log("employee create");

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
        console.log("Adding employee");

        var employeeObj = {
            firstName : $scope.employee.firstName,
            lastName : $scope.employee.lastName,
            description : $scope.employee.description,
            departmentId : selectedDepartmentId
        }
        console.log("employeeObj = " + employeeObj + " " + JSON.stringify(employeeObj));
        EmployeeService.create(employeeObj).then(function(response) {
            console.log("Status = " + response.status);
            console.log("Added id = " + response.data.id);
            toastr.success("Creating the employee was successful");
            console.log("Creating the employee with id = " + response.data.id + " was successful");
            $location.path("/employee/list").search({
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
        $scope.employee = {};
        selectedCompanyId = null;
        selectedDepartmentId = null;
    }
});

/* EMPLOYEE EDIT CONTROLLER */
appEmployee.controller('employeeEditController', function ($scope, $location, $stateParams, EmployeeService) {
    var employeeId = Number($stateParams.employeeId);
    var selectedCompanyId = Number($stateParams.companyId);
    var selectedDepartmentId = Number($stateParams.departmentId);
    console.log( "Edit employeeId = " + employeeId);
    console.log( "Selected companyId = " + selectedCompanyId);
    console.log( "Selected departmentId = " + selectedDepartmentId);

    EmployeeService.get(employeeId).then(function (response) {
        var data = response.data;
        console.log(data);

        $scope.employee = {};
        $scope.employee.firstName = data.firstName;
        $scope.employee.lastName = data.lastName;
        $scope.employee.description = data.description;
        $scope.companyName = data.companyName;
        $scope.departmentName = data.departmentName;
    },
    function error(response) {
        console.log(response.data);
    });

    $scope.save = function() {
        console.log("Saving employee");

        var employeeObj = {
            firstName : $scope.employee.firstName,
            lastName : $scope.employee.lastName,
            description : $scope.employee.description
        }
        console.log("employeeObj = " + employeeObj + " " + JSON.stringify(employeeObj));

        EmployeeService.update(employeeId, employeeObj).then(function(response) {
            console.log("Updated = " + response.data);
            console.log("Status = " + response.status);
            toastr.success("Updating the employee was successful");
            console.log("Updating the employee with id = " + employeeId + " was successful");
            console.log("PATH = [" + selectedCompanyId + ", " + selectedDepartmentId + "]");
            $location.path("/employee/list").search({
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
        $scope.employee = {};
    }
});
