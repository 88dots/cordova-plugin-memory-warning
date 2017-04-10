var exec = require('cordova/exec');

exports.isMemoryUsageUnsafe = function(success, fail) {
    exec(success, fail, "CordovaPluginMemoryWarning", "isMemoryUsageUnsafe");
};
