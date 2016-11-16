var appUtils = angular.module('appUtils', []);

appUtils.factory("DateService", function(){
    var factoryObj = {};

    factoryObj.plusDays = function(startDate,numberOfDays){
        var returnDate = new Date(
            startDate.getFullYear(),
            startDate.getMonth(),
            startDate.getDate() + numberOfDays,
            startDate.getHours(),
            startDate.getMinutes(),
            startDate.getSeconds());
        return returnDate;
    }

    factoryObj.minusDays = function(startDate,numberOfDays){
        var returnDate = new Date(
            startDate.getFullYear(),
            startDate.getMonth(),
            startDate.getDate() - numberOfDays,
            startDate.getHours(),
            startDate.getMinutes(),
            startDate.getSeconds());
        return returnDate;
    }

    factoryObj.clearTime = function(myDate){
        var givenDate = myDate;
        console.log("before change changedDate =", givenDate);
        givenDate.setHours(0,0,0);
        console.log("after change changedDate =", givenDate);
        return givenDate;
    }

    factoryObj.getWeek = function(day){
        var givenDay = day;
        var givenDayOfWeekValue = givenDay.getDay();
        var mondayDayOfWeekValue = 1;
        var sundayDayOfWeekValue = 7;

        var monday = givenDay;
        if(givenDayOfWeekValue > mondayDayOfWeekValue){
            monday = factoryObj.minusDays(givenDay, givenDayOfWeekValue - mondayDayOfWeekValue);
        }

        var sunday = givenDay;
        if(givenDayOfWeekValue < sundayDayOfWeekValue){
            sunday = factoryObj.plusDays(givenDay, sundayDayOfWeekValue - givenDayOfWeekValue);
        }

        var week = {from: monday, to: sunday}
        return week;
    }

    factoryObj.getConsecutiveWeeks  = function(day, numberOfWeeks) {
        var givenDay = day;
        var weeks = [];
        for(var n = 0; n < numberOfWeeks; n++){
            var week = factoryObj.getWeek(givenDay);
            weeks.push(week);
            var givenDay = factoryObj.plusDays(givenDay, 7);
        }
        console.log(weeks);
        return weeks;
    }

    return factoryObj;
});
