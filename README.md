# cordova-plugin-memory-warning

Cordova plugin to dispatch memory warnings to javascript.

## Usage iOS

```javascript
// listen for 'memorywarning' events
document.addEventListener('memorywarning', function () {
    // release memory
});

// checkMemoryUsage method does nothing on iOS
```

## Usage Android

```javascript
// android does not have app specific memory warnings
// so you should manually check memory state before performing memory intensive operations
// returns a promise that resolves with boolean true when memory usage is at an unsafe level
cordova.plugins.CordovaPluginMemoryWarning.checkMemoryUsage().then(function (isMemoryUsageUnsafe) {
    if (isMemoryUsageUnsafe) {
        // release memory
    }
});

// android does have system level memory warnings
// so you should also listen for 'memorywarning' events
document.addEventListener('memorywarning', function () {
    // release memory
});
```