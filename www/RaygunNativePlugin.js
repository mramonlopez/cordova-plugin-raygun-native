module.exports = {
    startNativeRaygun: function (userIdentifier, apiKey) {
        var defaults = {user: userIdentifier, api: apiKey};
        cordova.exec(null, null, "RaygunNativePlugin", "startNativeRaygun", [defaults]);
    },

    testCrash: function () {
        var defaults = {};
        cordova.exec(null, null, "RaygunNativePlugin", "testCrash", [defaults]);
    },

    setUser: function (user, isAnonymous, email, firstName, fullName) {
        var defaults = {
                user: user.toString(),
                isAnonymous: !!isAnonymous,
                email: email || '',
                firstName: firstName || '',
                fullName: fullName || ''
            };

        cordova.exec(null, null, "RaygunNativePlugin", "setUser", [defaults]);
    }
};
