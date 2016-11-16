var appServices = angular.module('appServices', []);

appServices.factory("CompanyService", function($http){
    var factoryObj = {};

    factoryObj.listAll = function(){
        return $http.get('companies/list');
    }

    factoryObj.get = function(companyId){
        return $http.get('companies/get/' + companyId);
    }

    factoryObj.create = function(companyObj){
        return $http.post("companies/add", companyObj);
    }

    factoryObj.update = function(companyId, companyObj){
        return $http.put("companies/update/" + companyId, companyObj);
    }

    factoryObj.delete = function(companyId){
        return $http.delete('companies/delete/' + companyId);
    }

    return factoryObj;
});

appServices.factory("DepartmentService", function($http){
    var factoryObj = {};

    factoryObj.listAll = function(){
        return $http.get('departments/list');
    }

    factoryObj.listByCompanyId = function(companyId){
        return $http.get('departments/list/' + companyId);
    }

    factoryObj.get = function(departmentId){
        return $http.get('departments/get/' + departmentId);
    }

    factoryObj.create = function(departmentObj){
        return $http.post("departments/add", departmentObj);
    }

    factoryObj.update = function(departmentId, departmentObj){
        return $http.put("departments/update/" + departmentId, departmentObj);
    }

    factoryObj.delete = function(companyId){
        return $http.delete('departments/delete/' + companyId);
    }

    return factoryObj;
});

appServices.factory("EmployeeService", function($http){
    var factoryObj = {};

    factoryObj.listAll = function(){
        return $http.get('employees/list');
    }

    factoryObj.listByDepartmentId = function(departmentId){
        return $http.get('employees/list/' + departmentId);
    }

    factoryObj.get = function(employeeId){
        return $http.get('employees/get/' + employeeId);
    }

    factoryObj.create = function(employeeObj){
        console.log("EmployeeService = " + JSON.stringify(employeeObj));
        return $http.post("employees/add", employeeObj);
    }

    factoryObj.update = function(employeeId, employeeObj){
        return $http.put("employees/update/" + employeeId, employeeObj);
    }

    factoryObj.delete = function(employeeId){
        return $http.delete('employees/delete/' + employeeId);
    }

    return factoryObj;
});

appServices.factory("RoomService", function($http){
    var factoryObj = {};

    factoryObj.listAll = function(){
        return $http.get('rooms/list');
    }

    factoryObj.listByDepartmentId = function(departmentId){
        return $http.get('rooms/list/' + departmentId);
    }

    factoryObj.get = function(roomId){
        return $http.get('rooms/get/' + roomId);
    }

    factoryObj.create = function(roomObj){
        return $http.post("rooms/add", roomObj);
    }

    factoryObj.update = function(roomId, roomObj){
        return $http.put("rooms/update/" + roomId, roomObj);
    }

    factoryObj.delete = function(roomId){
        return $http.delete('rooms/delete/' + roomId);
    }

    return factoryObj;
});

appServices.factory("ScheduleService", function($http){
    var factoryObj = {};

    factoryObj.listAll = function(){
        return $http.get('schedules/list');
    }

    factoryObj.listByEmployeeId = function(employeeId){
        return $http.get('schedules/list/' + employeeId);
    }

    factoryObj.listByEmployeeIdAndDates = function(employeeId, dateFrom, dateTo){
        return $http.get('schedules/list/' + employeeId + '/' + dateFrom + '/' + dateTo);
    }

    factoryObj.create = function(scheduleObj){
        return $http.post("schedules/add", scheduleObj);
    }

    factoryObj.createUpdate = function(scheduleListObj){
        return $http.post("schedules/createUpdate", scheduleListObj);
    }

    return factoryObj;
});

//appServices.factory("AuthenticationService", function($http){
//    var factoryObj = {};
//
//    factoryObj.setLoggedUser = function(data){
//        return $http.get('schedules/list');
//    }
//
//    return factoryObj;
//});
