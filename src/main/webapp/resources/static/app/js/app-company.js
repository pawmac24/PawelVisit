var appCompany = angular.module('appCompany', []);

/* COMPANY CONTROLLER */
appCompany.controller('companyController', function ($scope, $location, $rootScope) {

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
});

/* COMPANY LIST CONTROLLER */
appCompany.controller('companyListController', function ($scope, $location, $rootScope, CompanyService) {

    if($rootScope.loggeduser === "" || $rootScope.loggeduser === undefined) {
        $location.path("/");
        console.log("empty loggeduser");
    }
    else {
        CompanyService.listAll().then(function(response){
            $scope.companies = response.data;
            console.log(response.data + ", " + response.status);
        },
        function error(response) {
            console.log(response.data + ", " + response.status);
        });
    }

    $scope.delete = function(companyId) {
        console.log("Delete companyId = " + companyId);

        var answer = confirm("Are you sure you want to delete this company ?");
        console.log("answer =" + answer);
        if (answer) {
            CompanyService.delete(companyId).then(function (response) {
                console.log(response.data);
                clearFields();
                toastr.success("Deleting the company was successful");
                console.log("Deleting the company with id = " + companyId + " was successful");

                CompanyService.listAll().then(function(response) {
                    $scope.companies = response.data;
                    console.log(response.data + ", " + response.status);
                },
                function error(response) {
                    console.log("Error data = " + response.data + ", " + response.status);
                });
            },
            function error(response) {
                var data = response.data;
                console.log(data + ", " + response.status);
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

/* COMPANY CREATE CONTROLLER */
appCompany.controller('companyCreateController', function ($scope, $location, CompanyService) {

    $scope.save = function() {
        console.log("Adding company");

        var companyObj = {
            name : $scope.name,
            address : {
                city : $scope.address.city,
                postcode : $scope.address.postcode,
                street : $scope.address.street,
                number : $scope.address.number
            }
        }
        CompanyService.create(companyObj).then(function(response) {
            console.log("Status = " + response.status);
            console.log("Added id = " + response.data.id);
            toastr.success("Creating the company was successful");
            console.log("Creating the company with id = " + response.data.id + " was successful");
            $location.path("/company/list");
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
        $scope.companyId = "";
        $scope.name = "";
        $scope.address = {};
    }
});

/* COMPANY EDIT CONTROLLER */
appCompany.controller('companyEditController', function ($scope, $location, $stateParams, CompanyService) {

    var companyId = $stateParams.companyId;
    console.log( "Edit companyId = " + companyId);

    CompanyService.get(companyId).then(function (response) {
        var data = response.data;
        console.log(data);
        $scope.companyId = data.companyId;
        $scope.name = data.name;
        $scope.address = {};
        $scope.address.city = data.address.city;
        $scope.address.postcode = data.address.postcode;
        $scope.address.street = data.address.street;
        $scope.address.number = data.address.number;
        //$scope.address = data.address; //you can use this line instead
    },
    function error (response) {
        console.log(response.data);
    });


    $scope.save = function() {
        console.log("Saving company");

        var companyObj = {
            name : $scope.name,
            address : {
                city : $scope.address.city,
                postcode : $scope.address.postcode,
                street : $scope.address.street,
                number : $scope.address.number
            }
        }
        CompanyService.update($scope.companyId, companyObj).then(function(response) {
            console.log("Added = " + response.data);
            console.log("Status = " + response.status);
            toastr.success("Updating the company was successful");
            console.log("Updating the company with id = " + $scope.companyId + " was successful");
            $location.path("/company/list");
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
        $scope.name = "";
        $scope.address = {};
    }
});
